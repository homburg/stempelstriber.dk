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
import scalatags.generic.Modifier

object Home {
  implicit object callAsString extends AttrValue[Call] {
    override def apply(t: Builder, a: Attr, v: Call): Unit = t.setAttr(a.name, v.toString)
  }

  implicit def typedTagAsString(t: TypedTag[String]) = t.toString
  implicit def htmlFormatAsString(t: HtmlFormat.Appendable) = t.toString

  def container(children: Modifier[Builder]*) = div(c:="container", children)
  def row(children: Modifier[Builder]*) = div(c:="row", children)
  def colMd(width: Int)(children: Modifier[Builder]*) = div(c:=s"col-md-${width}", children)

  object fields {
    private def iinput(t: String, attrName: String, children: Modifier[Builder]*) = {
      input(`type`:=t, name:=attrName, children, c:="form-control")
    }

    def text(name: String, children: Modifier[Builder]*) = iinput("text", name, children)
    def email(name: String, children: Modifier[Builder]*) = iinput("email", name, children)
    def password(name: String, children: Modifier[Builder]*) = iinput("password", name, children)
    def file(name: String, children: Modifier[Builder]*) = iinput("file", name, children)
    def submit(children: Modifier[Builder]*) = input(`type`:="submit", children)
  }

  val text = Html(
    html(
      head(
        link(
          rel:="stylesheet",
          href:=routes.Assets.at("lib/bootstrap/css/bootstrap.min.css")
        )
      ),
      body(
        container(
          row(
            colMd(12)(
              h1("Now with implicits"),
              p("Test-tekst"),
              form(
                action:=routes.Application.upload(),
                method:="POST",
                "enctype".attr:="multipart/form-data",
                fields.text("here", value:="there"),
                fields.file("image"),
                fields.submit()
              )
            )
          ),
          raw(scalaJsScripts("frontend"))
        )
      )
    )
  )
}
