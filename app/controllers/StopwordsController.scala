package controllers

import scala.util.{Failure, Success, Try}
import javax.inject.Inject
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import IndonesianNLP._

class StopwordsController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  val formalizer = new IndonesianSentenceFormalization

  def remover: Action[JsValue] = Action(parse.json) { implicit request: Request[JsValue] =>
    val string = (request.body \ "string").as[String]

    Try {
      formalizer.initStopword()
      formalizer.deleteStopword(string)
    } match {
      case Success(removed) => Ok(Json.obj("status" -> "success", "data" -> removed))
      case Failure(failure) => InternalServerError(Json.obj("status" -> "error", "message" -> failure.toString))
    }
  }
}