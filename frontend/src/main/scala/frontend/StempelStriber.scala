package frontend

import scala.scalajs.js
import org.scalajs.dom
import shared.SharedMessages
import scala.scalajs.js.Dynamic.literal

object StempelStriber extends js.JSApp {

  def main(): Unit = {
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
