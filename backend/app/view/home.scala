package home

import controllers.routes
import play.twirl.api.Html
import playscalajs.html.{scripts => scalaJsScripts}
import services.ImageProxy.{width => imageWidth}
import shared.Data.Comic

import scalacss.Defaults._
import scalacss.ScalatagsCss._
import scalatags.Text._
import scalatags.Text.all.{`class` => c}
import scalatags.Text.all._
import scalatags.Text.tags2.{style => headStyle, title => headTitle}
import scalatags.generic.Modifier
import scalatags.text.Builder


object Home {
  import Implicits._

  def Test() = {
    val c = container(row(colMd(12)(h1("Test"))))
    Html(c)
  }

  def container(children: Modifier[Builder]*) = {
    div(c := "container", children)
  }

  def row(children: Modifier[Builder]*) = div(c := "row", children)

  def colMd(width: Int)(children: Modifier[Builder]*) = div(c := s"col-md-${width}", children)

  def javascript(children: Modifier[Builder]*) = script(`type`:="text/javascript", children)

  val integrity = "integrity".attr
  val crossorigin = "crossorigin".attr

  val bootstrapCss = link(href:="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css", rel:="stylesheet", integrity:="sha256-MfvZlkHCEqatNoGiOXveE8FIwMzZg4W85qfrfIFBfYc= sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ==", crossorigin:="anonymous")
  val bootstrapDarkly = link(href:="https://maxcdn.bootstrapcdn.com/bootswatch/3.3.5/darkly/bootstrap.min.css", rel:="stylesheet", integrity:="sha256-IsefKVCcMUlgpXQgIMRsqbIqC6aP153JkybxTa7W7/8= sha512-mCNEsmR1i3vWAq8hnHfbCCpc6V5fP9t0N1fEZ1wgEPF/IKteFOYZ2uk7ApzMXkT71sDJ00f9+rVMyMyBFwsaQg==", crossorigin:="anonymous")

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

  def signIn = Html(
    html(
      head(
        bootstrapDarkly,
        link(`type`:="text/css", rel:="stylesheet", href:="//www.gstatic.com/authtoolkit/css/gitkit.css")
      ),
      body(
        container(
          row(
            colMd(12)(
              h1("Sign in"),
              div(id:="navbar")
            )
          )
        ),
        javascript(raw("window.page = \"SignIn\"")),
        javascript(src:="//www.gstatic.com/authtoolkit/js/gitkit.js"),
        raw(scalaJsScripts("frontend"))
      )
    )
  )

  def widget = Html(
    html(
      head(
        bootstrapDarkly,
        link(`type`:="text/css", rel:="stylesheet", href:="//www.gstatic.com/authtoolkit/css/gitkit.css")
      ),
      body(
        container(
          row(
            colMd(12)(
              h1("Widget"),
              div(id:="gitkitWidgetDiv")
            )
          )
        ),
        javascript(raw("window.page = \"Widget\"")),
        javascript(src:="//www.gstatic.com/authtoolkit/js/gitkit.js"),
        raw(scalaJsScripts("frontend"))
      )
    )
  )

  def comic(comic: Comic, prev: Option[Comic] = None, next: Option[Comic] = None): Html = {
    // val navigation: TypedTag[String] = div(Style.navigation.self
    //   , div(Style.navigation.left, )
    //   , div(Style.navigation.right, )
    // )

    val testElements = comic.tests.map { url =>
      a(href:=url, c:="imagelightbox ", img(src:=imageWidth(112, url)))
    }

    document(
      div(Style.container
        , div(Style.outerColumns.left
          , prev.map { prevComic =>
            a(href := routes.Application.c(prevComic.id), img(src := routes.Assets.at("images/pil-venstre.png"), rel := "prerender", Style.fullWidth))
          }
        )
        , div(Style.centerColumn
          , a(Style.havhestenLink.self
            , img(src := routes.Assets.at("images/clipboard-link.png"), Style.havhestenLink.img)
            , href := "https://theismadsen.dk"
          )
          , img(src:=imageWidth(637, comic.comic), Style.fullWidth)
        )
        , div(Style.outerColumns.right
          , next.map { nextComic =>
            a(href := routes.Application.c(nextComic.id), img(src := routes.Assets.at("images/pil-hoejre.png"), rel := "prerender", Style.fullWidth))
          }
        )
      )
      , div(Style.container
        , div(Style.centerColumn
          , div(comic.tests.headOption.map { _ => div(Style.align.right, img(src := routes.Assets.at("images/tests-title.png"))) })
          , div(Style.align.right, testElements)
        )
      )
    )
  }

  def document(children: Modifier[Builder]*): Html = {
    Html(
      html(
        head(
          headTitle("stempelstriber.dk"),
          Head.render[TypedTag[String]],
          Style.render[TypedTag[String]],
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
          javascript(src:=routes.Assets.at("imagelightbox.min.js"))
        )
      )
    )
  }
}

