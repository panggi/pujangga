package controllers

import scala.collection.mutable.ListBuffer
import scala.util.{Failure, Success, Try}
import javax.inject.Inject

import play.api.libs.json.{JsArray, JsValue, Json}
import play.api.mvc._
import IndonesianNLP._

import scala.collection.JavaConverters._

class NamedEntityController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  val nETagger = new IndonesianNETagger

  def tagger: Action[JsValue] = Action(parse.json) { implicit request: Request[JsValue] =>
    val string = (request.body \ "string").as[String]

    Try {
      nETagger.extractNamedEntity(string)
    } match {
      case Success(extracted) =>

        val namedEntityList = new ListBuffer[JsValue]()

        for(tag <- extracted.asScala) {
          namedEntityList += Json.toJson(tag)
        }

        Ok(Json.obj("status" -> "success", "data" -> JsArray(namedEntityList)))
      case Failure(failure) => InternalServerError(Json.obj("status" -> "error", "message" -> failure.toString))
    }
  }
}