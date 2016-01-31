package org.charlie_camp.apps.agricola_online.service

import akka.actor.{ActorSystem, Props}
import akka.io.{IO, Tcp}
import akka.pattern.ask
import akka.util.Timeout
import grizzled.slf4j.Logging
import spray.can.Http

import scala.concurrent.Await
import scala.concurrent.duration._

object Boot extends App with Logging {
  implicit val system = ActorSystem("primary")

  val service = system.actorOf(Props[MainRoutes], "main-routes-actor")

  implicit val timeout = Timeout(5.seconds)

  val tcpBound = Await.result(
    (IO(Http) ? Http.Bind(service, interface = "localhost", port = 8080)).mapTo[Tcp.Bound],
    60.seconds
  )

  val actualPort = tcpBound.localAddress.getPort

  info(s"Service running.  actualPort=$actualPort")
  debug("Showing DEBUG logs")
}
