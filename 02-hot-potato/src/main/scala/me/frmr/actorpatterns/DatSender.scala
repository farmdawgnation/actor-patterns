package me.frmr.actorpatterns

import akka.actor._

case object SendDatMessage

class DatSender extends Actor with ActorLogging {
  def receive = {
    case SendDatMessage =>
      log.info("Gonna send dat message")
      context.system.actorSelection("akka://default/user/FirstReceiver") ! DatMessage

    case DatResponse =>
      log.info("Got dat response!")
      log.info("Shutting things down.")
      context.system.shutdown()
  }
}
