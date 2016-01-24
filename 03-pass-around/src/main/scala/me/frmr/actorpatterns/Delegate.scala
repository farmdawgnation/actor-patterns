package me.frmr.actorpatterns

import akka.actor._

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class Delegate extends Actor with ActorLogging {
  def receive = {
    case AskDelegate(challenge) =>
      log.info(s"Received ask delegate with challenge $challenge")
      doAsk(challenge, sender)
  }

  private def doAsk(challenge: String, originalSender: ActorRef) = {
    context.system.scheduler.scheduleOnce(1 seconds) {
      log.info("DO ASK IS INVOKING!")
      originalSender ! DelegateResponse("abcd")
    }
  }
}
