package view

import scalatags.Text.all._
import play.api.mvc.Call
import scalatags.text.Builder
import scalatags.Text.TypedTag
import play.twirl.api.{Html, HtmlFormat}
import scala.language.implicitConversions

object Implicits {
  implicit object callAsString extends AttrValue[Call] {
    override def apply(t: Builder, a: Attr, v: Call): Unit = t.setAttr(a.name, v.toString)
  }

  implicit def typedTagAsString(t: TypedTag[String]): String = t.toString

  implicit def htmlFormatAsString(t: HtmlFormat.Appendable): String = t.toString
}
