package controllers

import scala.util.{Failure, Success, Try}
import javax.inject.Inject
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import IndonesianNLP._

class StemmerController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  val stem = new IndonesianStemmer

  def stemmer: Action[JsValue] = Action(parse.json) { implicit request: Request[JsValue] =>
    val string = (request.body \ "string").as[String]

    Try {
      stem.stemSentence(string)
    } match {
      case Success(stemmed) => Ok(Json.obj("status" -> "success", "data" -> stemmed))
      case Failure(failure) => InternalServerError(Json.obj("status" -> "error", "message" -> failure.toString))
    }
  }
}