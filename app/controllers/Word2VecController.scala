package controllers

import scala.collection.mutable.ListBuffer
import scala.collection.JavaConverters._
import scala.util.{Failure, Success, Try}
import java.io.File
import javax.inject._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import play.api.Configuration
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer
import org.deeplearning4j.models.word2vec.Word2Vec

@Singleton
class Word2VecController @Inject() (cc: ControllerComponents, configuration: Configuration) extends AbstractController(cc) {
  val word2VecIdFile = new File(configuration.underlying.getString("models.word2vec"))
  val word2VecIdModel: Word2Vec = WordVectorSerializer.readWord2VecModel(word2VecIdFile)

  def nearestWords: Action[JsValue] = Action(parse.json) { implicit request: Request[JsValue] =>
    val string = (request.body \ "string").as[String]
    val n = (request.body \ "n").as[Int]

    Try {
      word2VecIdModel.wordsNearest(string, n)
    } match {
      case Success(word2vec) =>
        val wordList = new ListBuffer[JsValue]()
        for(word <- word2vec.asScala) {
          wordList += Json.toJson(word)
        }
        Ok(Json.obj("status" -> "success", "data" -> wordList))
      case Failure(failure) => InternalServerError(Json.obj("status" -> "error", "message" -> failure.toString))
    }
  }

  def arithmetic: Action[JsValue] = Action(parse.json) { implicit request: Request[JsValue] =>
    val string01 = (request.body \ "first_string").as[String]
    val string02 = (request.body \ "second_string").as[String]
    val string03 = (request.body \ "third_string").as[String]
    val n = (request.body \ "n").as[Int]

    Try {
      word2VecIdModel.wordsNearest(List(string01, string02).asJavaCollection,List(string03).asJavaCollection, n)
    } match {
      case Success(word2vec) =>
        val wordList = new ListBuffer[JsValue]()
        for(word <- word2vec.asScala) {
          wordList += Json.toJson(word)
        }
        Ok(Json.obj("status" -> "success", "data" -> wordList))
      case Failure(failure) => InternalServerError(Json.obj("status" -> "error", "message" -> failure.toString))
    }
  }

  def similarityWords: Action[JsValue] = Action(parse.json) { implicit request: Request[JsValue] =>
    val string01 = (request.body \ "first_string").as[String]
    val string02 = (request.body \ "second_string").as[String]

    Try {
      word2VecIdModel.similarity(string01, string02)
    } match {
      case Success(word2vec) => Ok(Json.obj("status" -> "success", "data" -> word2vec))
      case Failure(failure) => InternalServerError(Json.obj("status" -> "error", "message" -> failure.toString))
    }
  }
}