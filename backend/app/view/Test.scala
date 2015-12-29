package view

import play.twirl.api.Html
import view.shared.Shared._
import scalatags.Text.all._
import view.Implicits._

object Test {
  def index = Html(
    html(
      head(bootstrapDarkly)
      , body(container(row(colMd(12)(h1("Test")))))
    )
  )
}

