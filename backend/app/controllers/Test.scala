package controllers

import play.api.mvc._
import pprint.Config.Defaults._
import services.Comic.Language
import services.Comic
import services.User
import play.api.http.MimeTypes
import play.api.libs.json.Json
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import view.style.Style

object Test extends Controller {
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
}
