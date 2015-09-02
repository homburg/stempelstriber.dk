package home

import controllers.routes
import scalatags.text.Builder
import play.twirl.api.{Html, HtmlFormat}
import playscalajs.html.{scripts => scalaJsScripts}

import scalatags.Text.all.{`class` => c, _}
import scalatags.Text.tags2.{title => headTitle, style => headStyle}
import scalatags.generic.Modifier
import shared.Data.Comic
import scalacss.Defaults._


object Home {
  import Implicits._

  lazy val headCss = Head.render
  lazy val inlineHeadCss = Style.render

  def container(children: Modifier[Builder]*) = div(c := "container", children)

  def row(children: Modifier[Builder]*) = div(c := "row", children)

  def colMd(width: Int)(children: Modifier[Builder]*) = div(c := s"col-md-${width}", children)

  def jsScript(children: Modifier[Builder]*) = script(`type`:="text/javascript", children)

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

  def comic(comic: Comic): Html = document(
    div(
      c:=Style.container.htmlClass,
      div(
        c:=Style.leftColumn.htmlClass,
        comic.tests.map { url =>
          a(href:=url, c:="imagelightbox",
            img(src:=url, c:=Style.fullWidth.htmlClass)
          )
        }
      ),
      div(
        c:=Style.rightColumn.htmlClass,
        img(src:=comic.comic, c:=Style.fullWidth.htmlClass)
      )
    )
  )

  def document(children: Modifier[Builder]*): Html = {
    Html(
      html(
        head(
          headTitle("stempelstriber.dk"),
          headStyle(`type`:="text/css", headCss),
          headStyle(`type`:="text/css", inlineHeadCss),
          headStyle(`type`:="text/css", """
            #imagelightbox-overlay {
              -webkit-animation: fade-in .25s linear;
              animation: fade-in .25s linear;
            }
              @-webkit-keyframes fade-in
              {
                from  { opacity: 0; }
                to    { opacity: 1; }
              }
              @keyframes fade-in
              {
                from  { opacity: 0; }
                to    { opacity: 1; }
              }
          """)
        ),
        body(
          children,
          raw(scalaJsScripts("frontend")),
          jsScript(src:=routes.Assets.at("imagelightbox.min.js"))
        )
      )
    )
  }
}

