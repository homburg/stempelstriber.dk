package controllers

import view.home.Home
import play.api.mvc._
import services.session.GitKit

object Session extends Controller {
  def signIn = Action {
    Ok(Home.signIn)
  }

  def widget = Action { Ok(Home.widget) }

  def success = Action { request =>

    val gitKit = new GitKit(request)

    Ok(gitKit.user.fold("Login failed")(_.getEmail))
  }

  def signOut = Action {
    Redirect(routes.Application.index()).withNewSession
  }
}
