package controllers

import play.api.mvc._
import pprint.Config.Defaults._
import services.Comic.Language
import services.{Comic, User}
import services.session.GitKit
import play.api.http.MimeTypes
import play.api.libs.json.Json
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import view.style.Style

import scala.collection.JavaConversions._
import services.json.Writes._

import view.{Test => View}

object Test extends Controller {

  def index = Action {
    Ok(View.index)
  }

  def testDb = Action.async { r =>
    User.doNames map {
      case strs => Ok(strs.toString)
    }
  }

  def comics = Action {
    Ok(pprint.tokenize(Comic.comics).mkString)
  }

  def comicsJson = Action {
    Ok(Json.prettyPrint(Json.parse(Comic.comicData))).withHeaders(CONTENT_TYPE -> MimeTypes.JSON)
  }

  def language = Action { request =>
    Ok(Language.from(request).toString)
  }
  
  def session = Action { request => 
    // Download all accounts from Google Identity Toolkit
    val users = GitKit.client.getAllUsers()

    val gitKit = new GitKit(request)

    gitKit.user.fold(NotFound("No session")) { user =>
      Ok(Map(
        "user data" -> Json.prettyPrint(Json.toJson(user))
        , "user" -> gitKit.user.toString
        , "users" -> users.map(_.toString).mkString("\n\n")
        , "gtoken" -> gitKit.gToken.toString
      ).toString)
    }
  }
}
