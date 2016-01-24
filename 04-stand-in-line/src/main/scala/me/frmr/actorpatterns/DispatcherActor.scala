package me.frmr.actorpatterns

import akka.actor._

import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Random


case object Kickoff
case object Trigger
case class Request(key: String)
case class Response(key: String)

class DispatcherActor extends Actor with ActorLogging {
  private var requestsSent = 0
  private var requestsAwaiting = 0
  private val handlerActor = context.actorSelection("akka://default/user/Handler")

  def randomString(length: Int) = scala.util.Random.alphanumeric.take(length).mkString

  def receive = {
    case Kickoff =>
      (1.until(5)).foreach { delay =>
        log.info(s"Scheduling trigger for $delay seconds")
        context.system.scheduler.scheduleOnce(delay.seconds) {
          self ! Trigger
        }
      }

    case Trigger =>
      val keyToRequest = randomString(8)
      log.info(s"Sending request for $keyToRequest")
      handlerActor ! Request(keyToRequest)

      requestsSent = requestsSent + 1
      requestsAwaiting = requestsAwaiting + 1

    case Response(key) =>
      log.info(s"Got response for $key")
      requestsAwaiting = requestsAwaiting - 1

      if (requestsSent == 4 && requestsAwaiting == 0) {
        context.system.shutdown()
      }
  }
}
