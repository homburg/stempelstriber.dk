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
import services.ImageProxy.{width => imageWidth}


object Home {
  import Implicits._

  lazy val headCss = Head.render
  lazy val inlineHeadCss = Style.render

  def container(children: Modifier[Builder]*) = div(c := "container", children)

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

  // <html>
//   <head>
//     <!-- Begin custom code copied from Developer Console -->
//     <!-- Note: this is just an example. The html you download from Developer Console will be tailored for your site -->
//     <script type="text/javascript" src="//www.gstatic.com/authtoolkit/js/gitkit.js"></script>
//     <link type=text/css rel=stylesheet href="//www.gstatic.com/authtoolkit/css/gitkit.css" />
//     <script type=text/javascript>
//       window.google.identitytoolkit.signInButton(
//         '#navbar', // accepts any CSS selector
//         {
//           widgetUrl: "https://localhost:8000/callback",
//           signOutUrl: "/",
//           // Optional - Begin the sign-in flow in a popup window
//           //popupMode: true,
// 
//           // Optional - Begin the sign-in flow immediately on page load.
//           //            Note that if this is true, popupMode param is ignored
//           //loginFirst: true,
// 
//           // Optional - Cookie name (default: gtoken)
//           //            NOTE: Also needs to be added to config of ‘widget
//           //                  page’. See below
//           //cookieName: ‘example_cookie’,
//         }
//       );
//     </script>
//     <!-- End custom code copied from Developer Console -->
//   </head>
//   <body>
//     <!-- Begin sign in button widget -->
//     <div id="navbar"></div>
//     <!-- End sign in button widget -->
//   </body>
// </html>

// callback part
// <!DOCTYPE html>
// <html>
//   <head>
//     <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
// 
//     <!-- Copy and paste here the "Widget javascript" you downloaded from Developer Console as gitkit-widget.html -->
// 
//     <script type="text/javascript" src="//www.gstatic.com/authtoolkit/js/gitkit.js"></script>
//     <link type="text/css" rel="stylesheet" href="//www.gstatic.com/authtoolkit/css/gitkit.css" />
//     <script type="text/javascript">
//       var config = {
//           apiKey: 'AIza...',
//           signInSuccessUrl: '/',
//           signInOptions: ["google", "password"],
//           oobActionUrl: '/',
//           siteName: 'this site',
// 
//           // Optional - function called after sign in completes and before
//           // redirecting to signInSuccessUrl. Return false to disable
//           // redirect.
//           // callbacks: {
//           //  signInSuccess: function(tokenString, accountInfo,
//           //    opt_signInSuccessUrl) {
//           //      return true;
//           //    }
//           // },
// 
//           // Optional - key for query parameter that overrides
//           // signInSuccessUrl value (default: 'signInSuccessUrl')
//           // queryParameterForSignInSuccessUrl: 'url'
// 
//           // Optional - URL of site ToS (linked and req. consent for signup)
//           // tosUrl: 'http://example.com/terms_of_service',
// 
//           // Optional - URL of callback page (default: current url)
//           // callbackUrl: 'http://example.com/callback',
// 
//           // Optional - Cookie name (default: gtoken)
//           //            NOTE: Also needs to be added to config of the ‘page with
//           //                  sign in button’. See above
//           // cookieName: ‘example_cookie’,
// 
//            // Optional - UI configuration for accountchooser.com
//            /*acUiConfig: {
//                title: 'Sign in to example.com',
//                favicon: 'http://example.com/favicon.ico',
//                branding: 'http://example.com/account_choooser_branding'
//            },
//            */
// 
//            // Optional - Function to send ajax POST requests to your Recover URL
//            //            Intended for CSRF protection, see Advanced Topics
//            //      url - URL to send the POST request to
//            //     data - Raw data to include as the body of the request
//            //completed - Function to call with the object that you parse from
//            //            the JSON response text. {} if no response
//            /*ajaxSender: function(url, data, completed) {
//                          },
//            */
//       };
//       // The HTTP POST body should be escaped by the server to prevent XSS
//       window.google.identitytoolkit.start(
//           '#gitkitWidgetDiv', // accepts any CSS selector
//           config,
//           'JAVASCRIPT_ESCAPED_POST_BODY');
//     </script>
// 
//     <!-- End modification -->
// 
//   </head>
//   <body>
// 
//     <!-- Include the sign in page widget with the matching 'gitkitWidgetDiv' id -->
//     <div id="gitkitWidgetDiv"></div>
//     <!-- End identity toolkit widget -->
// 
//   </body>
// </html>


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

  def comic(comic: Comic, prev: Option[Comic] = None, next: Option[Comic] = None): Html = document(
    div(
      c:=Style.container.htmlClass,
      div(
        c:=Style.leftColumn.htmlClass,
        comic.tests.map { url =>
          a(href:=url, c:="imagelightbox",
            img(src:=imageWidth(71, url), c:=Style.fullWidth.htmlClass)
          )
        }
      ),
      div(
        c:=Style.rightColumn.htmlClass,
        img(src:=imageWidth(637, comic.comic), c:=Style.fullWidth.htmlClass)
      )
    ),
    div(c:=Style.navigation.self.htmlClass,
      prev.map { prevComic =>
        a(c:=Style.navigation.left.htmlClass, href:=routes.Application.c(prevComic.id), img(src:=routes.Assets.at("images/pil-left.png"), rel:="prerender"))
      },
      next.map { nextComic =>
        a(c:=Style.navigation.right.htmlClass, href:=routes.Application.c(nextComic.id), img(src:=routes.Assets.at("images/pil-right.png"), rel:="prerender"))
      }
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
          javascript(src:=routes.Assets.at("imagelightbox.min.js"))
        )
      )
    )
  }
}

