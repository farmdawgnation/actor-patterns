package me.frmr.actorpatterns

import akka.actor._

class SecondReceiver extends Actor with ActorLogging {
  def receive = {
    case msg @ DatMessage =>
      log.info("Got dat message")
      sender ! DatResponse
  }
}
