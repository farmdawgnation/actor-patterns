package me.frmr.actorpatterns

import akka.actor._

case object AskTheQuestion
case object WhatsMyNumber
case class YourNumberIs(number: Int)

class Asker extends Actor with ActorLogging {
  def receive = {
    case AskTheQuestion =>
      log.info("Asking the question.")
      context.system.actorSelection("akka://default/user/Askee") ! WhatsMyNumber

    case YourNumberIs(number) =>
      log.info("My number is " + number)
      log.info("Shutting everything down.")
      context.system.shutdown()
  }
}
