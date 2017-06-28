package controllers

import javax.inject._
import play.api.Configuration
import play.api.libs.json.Json
import play.api.mvc._

@Singleton
class HomeController @Inject()(cc: ControllerComponents,
                               configuration: Configuration
                              ) extends AbstractController(cc) {

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(Json.obj("application" -> configuration.underlying.getString("app.name"), "version" -> configuration.underlying.getString("app.version")))
  }
}
