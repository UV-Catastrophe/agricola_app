package org.charlie_camp.apps.agricola_online.service

import akka.actor.{Props, Actor}
import org.charlie_camp.apps.agricola_online.util.AgrolLogging
import spray.routing.HttpService

object MainRoutes {
  def props(gameStore: GameStoreSlick): Props = {
    Props(classOf[MainRoutes], gameStore)
  }
}

class MainRoutes(gameStore: GameStoreSlick) extends Actor with HttpService with AgrolLogging {
  def actorRefFactory = context

  def receive = runRoute(mainRoute)

  val mainRoute = {
    logRequestResponse(agrolRequestResponseLogger _) {
      path("v1" / "test") {
        complete("hello! I'm alive...")
      }
    }
  }


}

