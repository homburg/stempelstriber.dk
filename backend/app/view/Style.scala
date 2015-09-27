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
    backgroundColor(rgba( 255, 255, 255, 0.9 )),
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

  val body = style(
  )

  private val contentWidth = 708.px

  val noSelect = style(
    userSelect:="none",
    color.red,
    backgroundColor(rgba(255, 255, 255, 0.9))
  )
  

  val container = style(
    display.flex,
    justifyContent.spaceBetween,
    width(contentWidth),
    margin(0.px, auto),

    media.maxWidth(contentWidth)(
      width(100.%%)

    )
  )

  object leftColumn {
    val self = style(width(10.%%)
      , display.flex
      , flexDirection.column
      , marginTop(32.px)
    )

    val link = style(cursor.pointer
      , fullWidth
    )
  }

val rightColumn = style(
    width(90.%%)
  )
  
  val fullWidth = style(
    width(100.%%)
  )

  object navigation {
    val self = style(margin(7.px, auto)
      , display.flex
      , justifyContent.spaceAround
    )

    val left = style(noSelect
      , textAlign.center
      , width(50.%%)
    )

    val right = left
  }
}
