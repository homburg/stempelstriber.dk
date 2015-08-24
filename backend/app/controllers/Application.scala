package controllers

import scala.concurrent.Future
import play.api.mvc._
import shared.SharedMessages
import home.Home
import play.api.libs.ws._
import play.api.Play.current

object Application extends Controller {
  implicit val context = scala.concurrent.ExecutionContext.Implicits.global

  def index = Action {
    Ok(Home.text)
  }

  def upload = Action(parse.multipartFormData) { request => 
    request.body.file("image").map { image =>
      Ok(image.ref.file.getCanonicalPath())
    }.getOrElse {
      NotFound
    }
  }

  def view(path: String) = Action.async {
    WS.url(s"https://stempelstriber-dev.firebaseio.com${path}?print=pretty")
      .getStream().map {
        case (response, body) =>
          if (response.status == 200) {
            Ok.feed(body).as("text/plain").withHeaders("x-path" -> path)
          } else {
            NotFound
          }
      }
  }
}
