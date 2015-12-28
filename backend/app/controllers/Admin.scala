package controllers

import play.api.mvc.{Controller, Action}
import services.User
import play.api.libs.concurrent.Execution.Implicits.defaultContext

object Admin extends Controller {
  def setupDb = Action.async {
    User.setup map { case _ => Ok("Done!") }
  }
}
