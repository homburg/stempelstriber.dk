package home

import scalatags.Text.all._
import play.twirl.api.Html
import controllers.routes

object Home {
  val text = Html(
    html(
      head(),
      body(
        h1("Test"),
        p("Test-tekst"),
        pre(routes.WebJarAssets.at("css/botstrap.min.css").toString)
      )
    ).toString
  )
}
