package controllers

import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer
import scala.util.{Failure, Success, Try}
import javax.inject.Inject
import play.api.libs.json.{JsArray, JsValue, Json}
import play.api.mvc._
import IndonesianNLP._

class PhraseController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  val phraseChunker = new IndonesianPhraseChunker

  def chunker: Action[JsValue] = Action(parse.json) { implicit request: Request[JsValue] =>
    val string = (request.body \ "string").as[String]

    Try {
      phraseChunker.doPhraseChunker(string)
    } match {
      case Success(chunked) =>

        val chunkListString = new ListBuffer[String]()
        val chunkListJs = new ListBuffer[JsValue]()

        for(chunk <- chunked.asScala) {
          for(phrase <- chunk) {
            chunkListString += phrase
            if(chunk.indexOf(phrase) % 2 == 1) {
              chunkListJs += Json.toJson(phrase)
            }
          }
        }
        val chunkMap = chunkListString.grouped(2).collect { case ListBuffer(k, v) => k -> v }.toMap

        Ok(Json.obj("status" -> "success", "data" -> Json.obj("map" -> Json.toJson(chunkMap), "list" -> JsArray(chunkListJs))))
      case Failure(failure) => InternalServerError(Json.obj("status" -> "error", "message" -> failure.toString))
    }
  }
}