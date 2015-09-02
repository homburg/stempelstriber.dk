package home

import controllers.routes
import play.api.mvc.Call
import play.twirl.api.{Html, HtmlFormat}
import playscalajs.html.{scripts => scalaJsScripts}

import scala.language.implicitConversions
import scalatags.Text.TypedTag
import scalatags.Text.all.{`class` => c, _}
import scalatags.generic.Modifier
import scalatags.text.Builder
import shared.Data.Comic

object Home {

  implicit object callAsString extends AttrValue[Call] {
    override def apply(t: Builder, a: Attr, v: Call): Unit = t.setAttr(a.name, v.toString)
  }

  implicit def typedTagAsString(t: TypedTag[String]): String = t.toString

  implicit def htmlFormatAsString(t: HtmlFormat.Appendable): String = t.toString

  def container(children: Modifier[Builder]*) = div(c := "container", children)

  def row(children: Modifier[Builder]*) = div(c := "row", children)

  def colMd(width: Int)(children: Modifier[Builder]*) = div(c := s"col-md-${width}", children)

  object fields {
    private def iinput(t: String, attrName: String, children: Modifier[Builder]*) = {
      input(`type` := t, name := attrName, children, c := "form-control")
    }

    def text(name: String, children: Modifier[Builder]*) = iinput("text", name, children)

    def email(name: String, children: Modifier[Builder]*) = iinput("email", name, children)

    def password(name: String, children: Modifier[Builder]*) = iinput("password", name, children)

    def file(name: String, children: Modifier[Builder]*) = iinput("file", name, children)

    def submit(children: Modifier[Builder]*) = input(`type` := "submit", children)
  }

  def text: Html = document(
    container(
      row(
        colMd(12)(
          h1("Now with implicits"),
          p("Test-tekst"),
          form(
            action := routes.Application.upload(),
            method := "POST",
            "enctype".attr := "multipart/form-data",
            fields.text("here", value := "there"),
            fields.file("image"),
            fields.submit()
          )
        )
      ),
      raw(scalaJsScripts("frontend"))
    )
  )

  def comic(c: Comic): Html = document(
    container(
      row(
        colMd(12)(
          img(src:=c.comic, style:="display:block;width:100%; padding: 0px; margin: 10px; margin-bottom: 0px;"),
          c.tests.map(url => img(src:=url, style:="display: inline-block; width: 250px;padding: 0px; margin: 10px;"))
        )
      )
    )
  )

  def files(fileList: Seq[String]): Html = {
    document(container(row(colMd(12)(
      h1("Files"),
      ul(
        style:="list-style-type: none;",
        (fileList map { f => li(a(href:=f, f)) })
      )
    ))))
  }

  def document(children: Modifier[Builder]*): Html = {
    Html(
      html(
        head(
          link(
            rel := "stylesheet",
            href := routes.Assets.at(path="/public", "lib/bootstrap/css/bootstrap.min.css")
          )
        ),
        body(children)
      )
    )
  }
}
