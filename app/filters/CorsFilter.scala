package filters

import scala.concurrent.{ExecutionContext, Future}
import javax.inject._
import akka.stream.Materializer
import play.api.mvc._

@Singleton
class CorsFilter @Inject()(implicit override val mat: Materializer, exec: ExecutionContext) extends Filter {

  override def apply(nextFilter: RequestHeader => Future[Result])(requestHeader: RequestHeader): Future[Result] = {
    nextFilter(requestHeader).map { result =>
      result.withHeaders("Access-Control-Allow-Origin" -> "*", "Access-Control-Allow-Methods" -> "GET, POST, OPTIONS", "Access-Control-Allow-Headers" -> "DNT,X-CustomHeader,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type")
    }
  }

}