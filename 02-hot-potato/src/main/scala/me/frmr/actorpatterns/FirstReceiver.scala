package me.frmr.actorpatterns

import akka.actor._

case object DatMessage
case object DatResponse

class FirstReceiver extends Actor with ActorLogging {
  def receive = {
    case msg @ DatMessage =>
      log.info("Forwarding dat message")
      context.system.actorSelection("akka://default/user/SecondReceiver") forward msg
  }
}
