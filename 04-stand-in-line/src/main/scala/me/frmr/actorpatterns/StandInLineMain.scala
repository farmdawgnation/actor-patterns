package me.frmr.actorpatterns

import akka.actor._

object StandInLineMain {
  def main(arguments: Array[String]) {
    val system = ActorSystem()

    val handlerActor = system.actorOf(Props(classOf[HandlerActor]), "Handler")
    val dispatcherActor = system.actorOf(Props(classOf[DispatcherActor]), "Dispatcher")

    dispatcherActor ! Kickoff
  }
}
