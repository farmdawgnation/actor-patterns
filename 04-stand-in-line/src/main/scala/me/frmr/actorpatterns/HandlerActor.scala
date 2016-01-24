package me.frmr.actorpatterns

import akka.actor._
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global

case object AnswersNowCurrent
case class QueuedHandlerRequest(key: String, ref: ActorRef)

class HandlerActor extends Actor with ActorLogging {
  private var hasCurrentAnswer = false
  private var fetchingCurrentAnswer = false
  private var waitingRequesters: List[QueuedHandlerRequest] = Nil

  def receive = {
    case Request(key) if hasCurrentAnswer =>
      log.info(s"I have current answers. Sending response for $key")
      sender ! Response(key)

    case Request(key) if fetchingCurrentAnswer =>
      log.info(s"Waiting for answers before responding to $key")
      waitingRequesters = waitingRequesters :+ QueuedHandlerRequest(key, sender)

    case Request(key) =>
      log.info(s"Received request for $key but answers aren't current. Kicking off fetch.")
      waitingRequesters = waitingRequesters :+ QueuedHandlerRequest(key, sender)

      fetchingCurrentAnswer = true
      Future {
        doHardThing()
      }

    case AnswersNowCurrent =>
      log.info("Answers have been updated. Now dispatching responses.")
      waitingRequesters.foreach {
        case QueuedHandlerRequest(key, ref) =>
          ref ! Response(key)
      }
      waitingRequesters = Nil
      hasCurrentAnswer = true
      fetchingCurrentAnswer = false
  }

  private def doHardThing() {
    // Simulate calling out to an API somewhere.
    Thread.sleep(1000)

    // Tell the actor that things are now good.
    self ! AnswersNowCurrent
  }
}
