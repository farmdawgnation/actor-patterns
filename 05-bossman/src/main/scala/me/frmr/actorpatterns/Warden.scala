package me.frmr.actorpatterns

import akka.actor._

case class RegisterActor(ref: ActorRef)
case class DeregisterActor(ref: ActorRef)
case class Broadcast(message: Any)
case object ShutdownMonitored

class Warden extends Actor with ActorLogging {
  private var monitoredActors: Set[ActorRef] = Set.empty
  private var inShutdown = false

  def receive = {
    case RegisterActor(ref) =>
      log.info(s"$ref registered.")
      monitoredActors = monitoredActors + ref

    case DeregisterActor(ref) =>
      log.info(s"$ref deregistered.")
      monitoredActors = monitoredActors - ref

      if (inShutdown && monitoredActors.size == 0) {
        log.info("All monitored actors have shut down. Shutting down system.")
        context.system.shutdown()
      }

    case Broadcast(message) =>
      log.info(s"Broadcasting $message")
      monitoredActors.foreach(_ ! message)

    case ShutdownMonitored =>
      log.info("Requesting shutdown of monitored workers.")
      monitoredActors.foreach(_ ! StopWork)
      inShutdown = true
  }
}
