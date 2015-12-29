package controllers

import play.twirl.api.Html
import view.{Session => View}
import play.api.mvc._
import services.session.GitKit

object Session extends Controller {
  def signIn = Action {
    Ok(View.signIn)
  }

  def widget = Action { Ok(View.widget) }

  def success = Action { request =>

    val gitKit = new GitKit(request)

    Ok(gitKit.user.fold("Login failed")(_.getEmail))
  }

  def signOut = Action {
    Redirect(routes.Application.index()).withNewSession
  }
}
