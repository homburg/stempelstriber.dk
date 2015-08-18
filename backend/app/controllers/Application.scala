package controllers

import play.api.mvc._
import shared.SharedMessages
import home.Home

object Application extends Controller {
  def index = Action {
    Ok(Home.text)
  }
}
