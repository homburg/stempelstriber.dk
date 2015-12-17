package home

import scalacss.Defaults._

object Head extends StyleSheet.Standalone {
  import dsl._

  "*" - (
    margin.`0`,
    padding.`0`
  )

  // ImageLightBox.js
  "#imagelightbox" - (
    position.fixed,
    zIndex(9999),
    touchAction:="none"
  )

  "#imagelightbox-overlay" - (
    backgroundColor(rgba( 0, 0, 0, 0.6 )),
    position.fixed,
    zIndex(9998),
    top(0.px),
    bottom(0.px),
    right(0.px),
    left(0.px)
  )
}

object Style extends StyleSheet.Inline {
  import dsl._

  val body = style()

  val testsTitle = style(align.right
  )

  private val sideColumnPercentWidth = 6
  private val totalWidth = 960
  private val contentWidth = totalWidth.px

  private val centerPercentWidth = (100 - sideColumnPercentWidth*2)
  private val centerContentWidth = centerPercentWidth.%%

  val imageWidth = (totalWidth * centerPercentWidth / 100)

  // val phoneWidth = 980
  val phoneWidth = 980.px
  val phoneResolution = (1.5).dppx
  val narrowWidth = 480.px

  object align {
    val right = style(display.flex
      , flexDirection.row
      , justifyContent.flexEnd
      , flexWrap.wrap
    )

    val center = style(display.flex
      , flexDirection.row
      , justifyContent.center
    )
  }

  val noSelect = style(
    userSelect:="none",
    color.red,
    backgroundColor(rgba(255, 255, 255, 0.9))
  )
  
  val containerBase = style(boxSizing.borderBox
    , width(contentWidth)
    , margin(21.px, auto)
    , media.maxWidth(contentWidth)(
      width(100.%%)
    )
  )

  val container = style(containerBase, justifyContent.center, display.flex)

  object outerColumns {
    val both = style(width(sideColumnPercentWidth.%%)
      , display.flex
      , alignItems.center
      , justifyContent.center
      , height(100.vh)
    )

    val left = style(both
      , marginRight(6.px)
    )

    val right = style(both
      , marginLeft(6.px)
    )

    val link = style(cursor.pointer
      , fullWidth
    )
  }

  val centerColumn = style(position.relative
    , width(centerContentWidth)
  )

  object responsive {
    val wide = style(display.inline
      , media.minResolution(phoneResolution).portrait.maxWidth(phoneWidth)(display.none)
      , media.maxWidth(narrowWidth)(display.none)
    )
    
    val wideComic = style(wide, fullWidth)
    
    val narrow = style(display.none
      , media.minResolution(phoneResolution).portrait.maxWidth(phoneWidth)(display.inline)
      , media.maxWidth(narrowWidth)(display.inline)
    )

    val narrowComic = style(narrow, fullWidth)
  }

  lazy val testElementSection = style(containerBase, responsive.wide)

  object havhestenLink {
    val self = style(position.absolute
      , width(sideColumnPercentWidth.%%)
      , right(`0`)
    )

    val img = style(width(52.%%))
  }
  
  val fullWidth = style(
    width(100.%%)
  )
}
