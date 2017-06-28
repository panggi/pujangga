import javax.inject._
import play.api._
import play.api.http.HttpFilters
import filters.CorsFilter

@Singleton
class Filters @Inject() (
                          env: Environment,
                          corsFilter: CorsFilter) extends HttpFilters {

  override val filters = {
    Seq(corsFilter)
  }

}