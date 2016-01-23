package me.frmr.actorpatterns

import akka.actor._

class Askee extends Actor with ActorLogging {
  def receive = {
    case WhatsMyNumber =>
      log.info("I was asked for a number. Sending it back.")
      sender ! YourNumberIs(5)
  }
}
