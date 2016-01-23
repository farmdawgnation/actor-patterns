package me.frmr.actorpatterns

import akka.actor._

class Askee extends Actor with ActorLogging {
  def receive = {
    case WhatsMyNumber =>
      sender ! YourNumberIs(5)
  }
}
