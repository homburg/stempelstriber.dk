package frontend

import scala.scalajs.js
import scala.scalajs.js.Dynamic.global
import org.scalajs.dom
import shared.SharedMessages
import scala.scalajs.js.Dynamic.literal

object StempelStriber extends js.JSApp {

  val oauthConfig = literal(
    widgetUrl="http://localhost:9000/oauth/callback",
    signInSuccessUrl="http://localhost:9000/oauth/success",
    signOutUrl="http://localhost:9000/oauth/signout",
    oobActionUrl="http://localhost:9000/oauth/email",
    apiKey="AIzaSyALrEXnxZZquprXb4yzFGmII28h84ZjJn4",
    siteName="stempelstriber.dk",
    signInOptions=js.Array("password","google")
    // Optional - Begin the sign-in flow in a popup window
    //popupMode: true,

    // Optional - Begin the sign-in flow immediately on page load.
    //            Note that if this is true, popupMode param is ignored
    //loginFirst: true,

    // Optional - Cookie name (default: gtoken)
    //            NOTE: Also needs to be added to config of ‘widget
    //                  page’. See below
    //cookieName: ‘example_cookie’,
  )

  def main(): Unit = {
    val page = Some(js.Dynamic.global.page: Any).collect({ case x: String => x }).getOrElse("")

    page match {
      case "SignIn" => signIn
      case "Widget" => widget
      case _ => default
    }
  }

  def widget() {
      //var config = {
          // Optional - function called after sign in completes and before
          // redirecting to signInSuccessUrl. Return false to disable
          // redirect.
          // callbacks: {
          //  signInSuccess: function(tokenString, accountInfo,
          //    opt_signInSuccessUrl) {
          //      return true;
          //    }
          // },

          // Optional - key for query parameter that overrides
          // signInSuccessUrl value (default: 'signInSuccessUrl')
          // queryParameterForSignInSuccessUrl: 'url'

          // Optional - URL of site ToS (linked and req. consent for signup)
          // tosUrl: 'http://example.com/terms_of_service',

          // Optional - URL of callback page (default: current url)
          // callbackUrl: 'http://example.com/callback',

          // Optional - Cookie name (default: gtoken)
          //            NOTE: Also needs to be added to config of the ‘page with
          //                  sign in button’. See above
          // cookieName: ‘example_cookie’,

           // Optional - UI configuration for accountchooser.com
           /*acUiConfig: {
               title: 'Sign in to example.com',
               favicon: 'http://example.com/favicon.ico',
               branding: 'http://example.com/account_choooser_branding'
           },
           */

           // Optional - Function to send ajax POST requests to your Recover URL
           //            Intended for CSRF protection, see Advanced Topics
           //      url - URL to send the POST request to
           //     data - Raw data to include as the body of the request
           //completed - Function to call with the object that you parse from
           //            the JSON response text. {} if no response
           /*ajaxSender: function(url, data, completed) {
                         },
           */

      // The HTTP POST body should be escaped by the server to prevent XSS
      global.google.identitytoolkit.start(
          "#gitkitWidgetDiv", // accepts any CSS selector
          oauthConfig,
          "JAVASCRIPT_ESCAPED_POST_BODY");
  }

  def signIn() {
    println("Sign in!")
    global.google.identitytoolkit.signInButton(
      "#navbar", // accepts any CSS selector
      oauthConfig
    )
  }

  def default = {
    val jQuery = js.Dynamic.global.jQuery
    jQuery({ () =>
      val overlayOn = { () =>
        if (jQuery("#imagelightbox-overlay").size() == 0) {
          jQuery("""<div id="imagelightbox-overlay"></div>""")
            .appendTo("body")
        }
      }
      val overlayOff = { () =>
        jQuery("#imagelightbox-overlay").remove()
      }

      jQuery(".imagelightbox").imageLightbox(literal(
        onStart = overlayOn, onEnd = overlayOff
      ))
    })
  }
}
