package controllers

import home.Home
import play.api.http.MimeTypes
import play.api.mvc._
import play.api.libs.json.Json
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import pprint.Config.Defaults._
import services.Comic.Language
import services.{Comic, User}

object Application extends Controller {

  def testDb = Action.async { r =>
    User.doNames map {
      case strs => Ok(strs.toString)
    }
  }

  def setupDb = Action.async {
    User.setup map { case _ => Ok("Done!") }
  }

  def index = Action { request =>
    Comic.comics.headOption match {
      case Some(p) => Redirect(routes.Application.c(p._1, Language.default.name))
      case None => NotFound
    }
  }

  def comicWithoutLanguage(id: Int) = Action {
    Redirect(routes.Application.c(id, Language.default.name))
  }

  def c(id: Int, lang: String = Language.default.name) = Action {
    val comicOption = Comic.byId(id)
    val siblings = Comic.siblings(comicOption)
    comicOption match {
      case Some(comic) =>
        comic.page.get(lang) match {
          case Some(_) =>
            Ok(Home.comic(Language.from(lang), comic, siblings._1, siblings._2)).withHeaders(CONTENT_TYPE -> HTML)
          case None => if (lang != Language.default.name) {
            Redirect(routes.Application.c(id, Language.default.name))
          } else {
            NotFound
          }
        }
      case None => NotFound
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
