package me.frmr.actorpatterns

import akka.actor._

case object EngageDispatch
case class AskDelegate(challengeString: String)
case class DelegateResponse(responseString: String)

class DispatcherActor extends Actor with ActorLogging {
  def receive = {
    case EngageDispatch =>
      log.info("Received engage dispatch request")
      context.system.actorSelection("akka://default/user/Delegate") ! AskDelegate("abcd")

    case DelegateResponse(response) =>
      log.info(s"Delegate response is $response")
      log.info("Shutting down system.")
      context.system.shutdown()
  }
}
