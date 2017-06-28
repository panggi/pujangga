package controllers

import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer
import scala.util.{Failure, Success, Try}
import javax.inject._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import IndonesianNLP._

@Singleton
class SentencesController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  val sentenceTokenizer = new IndonesianSentenceTokenizer
  val sentenceDetector = new IndonesianSentenceDetector
  val refResolution = new IndonesianReferenceResolution

  def tokenizer: Action[JsValue] = Action(parse.json) { implicit request: Request[JsValue] =>
    val string = (request.body \ "string").as[String]

    Try {
      sentenceTokenizer.tokenizeSentence(string)
    } match {
      case Success(tokenized) =>
        val tokenList = new ListBuffer[JsValue]()

        for(token <- tokenized.asScala) {
          tokenList += Json.toJson(token)
        }

        Ok(Json.obj("status" -> "success", "data" -> tokenList))
      case Failure(failure) => InternalServerError(Json.obj("status" -> "error", "message" -> failure.toString))
    }
  }

  def tokenizerComposite: Action[JsValue] = Action(parse.json) { implicit request: Request[JsValue] =>
    val string = (request.body \ "string").as[String]

    Try {
      sentenceTokenizer.tokenizeSentenceWithCompositeWords(string)
    } match {
      case Success(tokenized) =>
        val tokenList = new ListBuffer[JsValue]()

        for(token <- tokenized.asScala) {
          tokenList += Json.toJson(token)
        }

        Ok(Json.obj("status" -> "success", "data" -> tokenList))
      case Failure(failure) => InternalServerError(Json.obj("status" -> "error", "message" -> failure.toString))
    }
  }

  def splitter: Action[JsValue] = Action(parse.json) { implicit request: Request[JsValue] =>
    val string = (request.body \ "string").as[String]

    Try {
      sentenceDetector.splitSentence(string)
    } match {
      case Success(split) =>
        val sentenceList = new ListBuffer[JsValue]()

        for(sentence <- split.asScala) {
          sentenceList += Json.toJson(sentence)
        }

        Ok(Json.obj("status" -> "success", "data" -> sentenceList))
      case Failure(failure) => InternalServerError(Json.obj("status" -> "error", "message" -> failure.toString))
    }
  }
}