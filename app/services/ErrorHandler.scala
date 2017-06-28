package services

import scala.concurrent._
import javax.inject._
import play.api.http.HttpErrorHandler
import play.api.libs.json._
import play.api.mvc.Results._
import play.api.mvc._

@Singleton
class ErrorHandler extends HttpErrorHandler {

  def onClientError(request: RequestHeader, statusCode: Int, message: String) = {
    statusCode match {
      case 404 =>
        Future.successful (
          Status (statusCode) (Json.obj ("status" -> "error", "message" -> "Not Found") )
        )
      case _ =>
        Future.successful (
          Status (statusCode) (Json.obj ("status" -> "error", "message" -> message) )
        )
    }
  }

  def onServerError(request: RequestHeader, exception: Throwable) = {
    Future.successful(
      InternalServerError(Json.obj("status" -> "error", "message" -> exception.getMessage))
    )
  }
}