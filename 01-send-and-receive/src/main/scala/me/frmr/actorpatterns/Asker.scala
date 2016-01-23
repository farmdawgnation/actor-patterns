package me.frmr.actorpatterns

import akka.actor._

case object AskTheQuestion
case object WhatsMyNumber
case class YourNumberIs(number: Int)

class Asker extends Actor with ActorLogging {
  def receive = {
    case AskTheQuestion =>
      context.system.actorSelection("akka://system/user/Askee") ! WhatsMyNumber

    case YourNumberIs(number) =>
      log.info("My number is " + number)
  }
}
