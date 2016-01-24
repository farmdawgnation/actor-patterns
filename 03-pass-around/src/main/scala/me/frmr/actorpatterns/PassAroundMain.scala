package me.frmr.actorpatterns

import akka.actor._

object PassAroundMain {
  def main(arguments: Array[String]) {
    val system = ActorSystem()

    val dispatcherActor = system.actorOf(Props(classOf[DispatcherActor]), "DispatcherActor")
    val delegateActor = system.actorOf(Props(classOf[Delegate]), "Delegate")

    dispatcherActor ! EngageDispatch
  }
}
