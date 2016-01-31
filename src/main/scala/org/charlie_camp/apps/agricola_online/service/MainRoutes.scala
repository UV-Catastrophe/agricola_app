package org.charlie_camp.apps.agricola_online.service

import akka.actor.Actor
import spray.routing.HttpService

class MainRoutes extends Actor with HttpService {
  def actorRefFactory = context

  def receive = runRoute(mainRoute)

  val mainRoute = {
    path("v1" / "test") {
      complete("hello! I'm alive...")
    }
  }
}
