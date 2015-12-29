package view

import play.twirl.api.Html
import scalatags.Text.all._
import view.shared.Shared._
import view.Implicits._
import playscalajs.html.{scripts => scalaJsScripts}

object Session {
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

}
