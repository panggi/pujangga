package controllers

import scala.util.{Failure, Success, Try}
import javax.inject._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import IndonesianNLP._

@Singleton
class FormalizerController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  val formalize = new IndonesianSentenceFormalization

  def formalizer: Action[JsValue] = Action(parse.json) { implicit request: Request[JsValue] =>
    val string = (request.body \ "string").as[String]

    Try {
      formalize.formalizeSentence(string)
    } match {
      case Success(formalized) => Ok(Json.obj("status" -> "success", "data" -> formalized))
      case Failure(failure) => InternalServerError(Json.obj("status" -> "error", "message" -> failure.toString))
    }
  }
}