package home

import scalatags.Text.GenericAttr
import scalatags.Text.all._
import scalatags.Text.all.{`class` => c}
import play.twirl.api.Html
import play.api.mvc.Call
import play.twirl.api.HtmlFormat
import controllers.routes
import playscalajs.html.{scripts => scalaJsScripts}
import scalatags.Text.TypedTag
import scala.language.implicitConversions


import scalatags.text.Builder

object Home {
  implicit object callAsString extends AttrValue[Call] {
    override def apply(t: Builder, a: Attr, v: Call): Unit = t.setAttr(a.name, v.toString)
  }

  implicit def typedTagAsString(t: TypedTag[String]) = t.toString
  implicit def htmlFormatAsString(t: HtmlFormat.Appendable) = t.toString

  val text = Html(
    html(
      head(
        link(
          rel:="stylesheet",
          href:=routes.Assets.at("lib/bootstrap/css/bootstrap.min.css")
        )
      ),
      body(
        c := "container",
        div(
          `class` := "row",
          div(
            c := "col-md-12",
            h1("Now with implicits"),
            p("Test-tekst")
          )
        ),
        raw(scalaJsScripts("frontend"))
      )
    )
  )
}
