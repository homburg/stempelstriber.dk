package controllers

import view.home.Home
import play.api.Play.current
import play.api.mvc._
import com.google.identitytoolkit.GitkitClient

import scala.collection.JavaConversions._

object Session extends Controller {
  def signIn = Action {
    Ok(Home.signIn)
  }

  def widget = Action { Ok(Home.widget) }

  def success = Action { request =>
    val gitkitClient = GitkitClient.createFromJson(current.configuration.getString("gitkit_server_config_json").get)

    val gToken = request.cookies.get("gtoken")
    // Verifies a GitkitToken
    val gitkitUser = gitkitClient.validateToken(gToken.map(_.value).getOrElse(""))

    // Download all accounts from Google Identity Toolkit
    val users = gitkitClient.getAllUsers()

    Ok(Map(
      "user" -> gitkitUser.toString,
      "users" -> users.map(_.toString).mkString(", "),
      "gtoken" -> gToken.toString
    ).toString)
  }
}
