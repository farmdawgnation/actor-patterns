package me.frmr.actorpatterns

import akka.actor._

object Main {
  def main(args: Array[String]) {
    implicit val system = ActorSystem()

    val datsender = system.actorOf(Props(classOf[DatSender]), "DatSender")
    val firstreceiver = system.actorOf(Props(classOf[FirstReceiver]), "FirstReceiver")
    val secondreceiver = system.actorOf(Props(classOf[SecondReceiver]), "SecondReceiver")

    datsender ! SendDatMessage
  }
}
