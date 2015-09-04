package controllers

import home.Home
import play.api.Play.current
import play.api.libs.MimeTypes
import play.api.libs.ws._
import play.api.mvc._
import services.Comic
import upickle.default.read
import pprint.Config.Defaults._
import shared.Data

object Application extends Controller {
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
      case Some(comic) => {
        Ok(Home.comic(comic, siblings._1, siblings._2)).withHeaders(CONTENT_TYPE -> HTML)
      }
      case None => NotFound
    }
  }

  def comics = Action {
    Ok(pprint.tokenize(Comic.comics).mkString)
  }
}
