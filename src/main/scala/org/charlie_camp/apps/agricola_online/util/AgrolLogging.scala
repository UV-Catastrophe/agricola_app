package org.charlie_camp.apps.agricola_online.util

import grizzled.slf4j.Logging
import org.joda.time.DateTime
import spray.http.{HttpResponse, HttpRequest}
import spray.routing.directives.LogEntry

import scala.language.implicitConversions

trait AgrolLogging extends Logging {
  object LoggingImplicitConversions {
    implicit def createLoggingWrapper(msg: String): LoggingStringWrapper = LoggingStringWrapper(msg)
  }

  import LoggingImplicitConversions._

  def agrolRequestResponseLogger(req: HttpRequest): Any => Option[LogEntry] = {
    val start = DateTime.now.getMillis
    val body = req.entity.asString
    info("Received request".withLogParameters("method" -> req.method, "uri" -> req.uri, "body" -> body))

    val responseLogger: Any => Option[LogEntry] = {
      case res: HttpResponse =>
        val time = DateTime.now.getMillis - start
        val responseBody = res.entity.asString
        val msg = "Returning response".withLogParameters("method" -> req.method, "uri" -> req.uri, "status" -> res.status, "time" -> time, "body" -> responseBody)
        info(msg)
        None

      case _ =>
        None
    }

    responseLogger
  }
}

case class LoggingStringWrapper(msg: String) {
  def withLogParameters(params: (String, Any)*): String = {
    val paramsFormatted = params
      .map { case (key, value) => s"""$key="$value"""" }
      .mkString(" ")

    Seq(msg.stripSuffix("."), paramsFormatted).mkString(". ")
  }
}
