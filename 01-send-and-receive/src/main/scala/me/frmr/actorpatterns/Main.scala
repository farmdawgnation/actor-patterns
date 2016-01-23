package me.frmr.actorpatterns

import akka.actor._

object Main {
  def main(args: Array[String]) {
    implicit val system = ActorSystem()

    val asker = system.actorOf(Props(classOf[Asker]), "Asker")
    val aksee = system.actorOf(Props(classOf[Askee]), "Askee")

    asker ! AskTheQuestion
  }
}
