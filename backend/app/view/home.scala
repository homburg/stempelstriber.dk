package home

import scalatags.Text.all._
import play.twirl.api.Html
import controllers.routes
import playscalajs.html.{scripts => scalaJsScripts}

object Home {
  val text = Html(
    html(
      head(
        link(rel:="stylesheet", href:=routes.Assets.at("lib/bootstrap/css/bootstrap.min.css").toString)
      ),
      body(
        `class` := "container",
        div(
          `class` := "row",
          div(
            `class` := "col-md-12",
            h1("Test"),
            p("Test-tekst")
          )
        ),
        raw(scalaJsScripts("frontend").toString)
      )
    ).toString
  )
}
