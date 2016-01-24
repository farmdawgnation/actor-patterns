package me.frmr.actorpatterns

import akka.actor._
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent._
import scala.concurrent.duration._
import scala.sys.ShutdownHookThread

object BossmanMain {
  def main(arguments: Array[String]) {
    val system = ActorSystem()

    val warden = system.actorOf(Props(classOf[Warden]), "Warden")

    system.actorOf(Props(classOf[Worker], warden), "AlphaWorker")
    system.actorOf(Props(classOf[Worker], warden), "BetaWorker")

    ShutdownHookThread {
      implicit val timeout = Timeout(10.minutes)

      println("Shutting down application.")

      val future = (warden ? ShutdownMonitored)
      Await.ready(future, timeout.duration)
    }
  }
}
