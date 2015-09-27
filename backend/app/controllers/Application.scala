package controllers

import home.Home
import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import pprint.Config.Defaults._
import services.{Comic, User}

object Application extends Controller {

  def testDb = Action.async { r =>
    User.doNames map {
      case strs => Ok(strs.mkString(", "))
    }
  }

  def setupDb = Action.async {
    User.setup map { case _ => Ok("Done!") }
  }

  def index = Action {
    Comic.comics.lastOption match {
      case Some(p) => Redirect(routes.Application.c(p._1))
      case None => NotFound
    }
  }

  def c(id: Int) = Action {
    val comicOption = Comic.byId(id)
    val siblings = Comic.siblings(comicOption)
    comicOption match {
      case Some(comic) =>
        Ok(Home.comic(comic, siblings._1, siblings._2)).withHeaders(CONTENT_TYPE -> HTML)
      case None => NotFound
    }
  }

  def comics = Action {
    Ok(pprint.tokenize(Comic.comics).mkString)
  }
}
