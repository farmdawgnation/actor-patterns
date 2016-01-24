package me.frmr.actorpatterns

import akka.actor._

import scala.util._
import scala.concurrent.duration._

case class DoTheThing(key: String)
case class TheThingIsDone(key: String)

case object StopWork
case object WorkerTick

class Worker(wardenRef: ActorRef) extends Actor with ActorLogging {
  implicit val executionContext = context.dispatcher

  var outstandingTasks: Set[String] = Set.empty
  var workTicker: Cancellable = context.system.scheduler.schedule(2.seconds, 2.seconds, self, WorkerTick)

  def receive = {
    case WorkerTick =>
      log.info("Worker ticked.")
      self ! DoTheThing(randomString(10))

    case DoTheThing(key) =>
      log.info(s"Got a work request for $key")

      val delay = Random.nextInt(10)
      outstandingTasks = outstandingTasks + key
      context.system.scheduler.scheduleOnce(delay.seconds, self, TheThingIsDone(key))

    case TheThingIsDone(key) =>
      log.info(s"Work on $key completed.")
      outstandingTasks = outstandingTasks - key

    case StopWork =>
      if (outstandingTasks.size == 0) {
        log.info("Stop request received. No outstanding tasks. Terminating.")
        context.stop(self)
      } else {
        log.info("Stop request received. Re-triggering in 1s to allow current work to finish.")
        context.system.scheduler.scheduleOnce(1.seconds, self, StopWork)
      }

      if (! workTicker.isCancelled) {
        log.info("Stopping ticker.")
        workTicker.cancel()
      }
  }

  private def randomString(length: Int) = scala.util.Random.alphanumeric.take(length).mkString

  override def preStart() {
    wardenRef ! RegisterActor(self)
  }

  override def postStop() {
    wardenRef ! DeregisterActor(self)
  }
}
