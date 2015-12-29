package view.shared

import scalatags.Text._
import scalatags.Text.all._
import scalatags.Text.all.{`class` => c}
import services.ImageProxy

import scalatags.generic.Modifier
import scalatags.text.Builder

object Shared {
  def container(children: Modifier[Builder]*) = {
    div(c := "container", children)
  }

  def row(children: Modifier[Builder]*) = div(c := "row", children)

  def colMd(width: Int)(children: Modifier[Builder]*) = div(c := s"col-md-${width}", children)

  def javascript(children: Modifier[Builder]*) = script(`type`:="text/javascript", children)

  val integrity = "integrity".attr
  val crossorigin = "crossorigin".attr

  val imageProxy = new ImageProxy(92)

  val bootstrapCss = link(href:="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css", rel:="stylesheet", integrity:="sha256-MfvZlkHCEqatNoGiOXveE8FIwMzZg4W85qfrfIFBfYc= sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ==", crossorigin:="anonymous")
  val bootstrapDarkly = link(href:="https://maxcdn.bootstrapcdn.com/bootswatch/3.3.5/darkly/bootstrap.min.css", rel:="stylesheet", integrity:="sha256-IsefKVCcMUlgpXQgIMRsqbIqC6aP153JkybxTa7W7/8= sha512-mCNEsmR1i3vWAq8hnHfbCCpc6V5fP9t0N1fEZ1wgEPF/IKteFOYZ2uk7ApzMXkT71sDJ00f9+rVMyMyBFwsaQg==", crossorigin:="anonymous")
}

object Fields {
  private def iinput(t: String, attrName: String, children: Modifier[Builder]*) = {
    input(`type` := t, name := attrName, children, c := "form-control")
  }

  def text(name: String, children: Modifier[Builder]*) = iinput("text", name, children)
  def email(name: String, children: Modifier[Builder]*) = iinput("email", name, children)
  def password(name: String, children: Modifier[Builder]*) = iinput("password", name, children)
  def file(name: String, children: Modifier[Builder]*) = iinput("file", name, children)
  def submit(children: Modifier[Builder]*) = input(`type` := "submit", children)
}
