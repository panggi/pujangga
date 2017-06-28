package controllers

import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer
import scala.util.{Failure, Success, Try}
import javax.inject._
import play.api.libs.json.{JsArray, JsValue, Json}
import play.api.mvc._
import IndonesianNLP._

@Singleton
class PartOfSpeechController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def tagger: Action[JsValue] = Action(parse.json) { implicit request: Request[JsValue] =>
    val string = (request.body \ "string").as[String]

    Try {
      IndonesianPOSTagger.doPOSTag(string)
    } match {
      case Success(tagged) =>
        val tagListString = new ListBuffer[String]()
        val tagListJs = new ListBuffer[JsValue]()

        for(tag <- tagged.asScala) {
          for(word <- tag) {
            tagListString += word
            if(tag.indexOf(word) % 2 == 1) {
              tagListJs += Json.toJson(word)
            }
          }
        }
        val tagMap = tagListString.grouped(2).collect { case ListBuffer(k, v) => k -> v }.toMap

        Ok(Json.obj("status" -> "success", "data" -> Json.obj("map" -> Json.toJson(tagMap), "list" -> JsArray(tagListJs))))
      case Failure(failure) => InternalServerError(Json.obj("status" -> "error", "message" -> failure.toString))
    }
  }
}