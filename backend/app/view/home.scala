package view.home

import controllers.routes
import play.twirl.api.Html
import playscalajs.html.{scripts => scalaJsScripts}
import services.Comic.Language
import shared.Data.Comic

import scalacss.Defaults._
import scalacss.ScalatagsCss._
import scalatags.Text._
import scalatags.Text.all.{`class` => c}
import scalatags.Text.all._
import scalatags.Text.tags2.{style => headStyle, title => headTitle}

import view.style.{Style, Head}

import view.shared.Shared._
import view.shared.Fields._

import scalatags.generic.Modifier
import scalatags.text.Builder

import view.Implicits._

object Home {

  def comicImage(language: Language, width: Int, comic: Comic) = {
    // val comicUrl: String = comic.mobile.getOrElse(language.name, "")
    val page = comic.page.get(language.name)
    val mobileUrl = page.fold("")(_.mobileUrl)
    val comicUrl = page.fold("")(_.url)
    if (mobileUrl == "") {
      img(Style.fullWidth
        , src:=imageProxy.url(comicUrl, width=Some(width))
      )
    } else {
      div(
        img(Style.responsive.wideComic
          , src:=imageProxy.url(comicUrl, width=Some(width))
        )
        , img(Style.responsive.narrowComic
          , src:=imageProxy.url(mobileUrl, width=Some(700))
        )
      )
    }
  }

  def comic(language: Language, comic: Comic, prev: Option[Comic] = None, next: Option[Comic] = None): Html = {

    val testElements = comic.tests.map { url =>
      a(href:=url, c:="imagelightbox ", img(src:=imageProxy.url(url, width=Some(127))))
    }

    val testTitleImage = img(src := routes.Assets.at(language match {
      case Language.English => "images/forproeve-title-engelsk.png"
      case _ => "images/tests-title.png"
    }))

    val title = language match {
      case Language.English => "The Local"
      case _ => "Havhesten"
    }

    document(title
      , div(Style.container
        , div(Style.outerColumns.left
          , prev.map { prevComic =>
            a(href := routes.Application.c(prevComic.id, language.name)
              , rel := "prerender"
              , span(Style.responsive.wide
                , img(src := routes.Assets.at("images/pil-venstre.png"), Style.fullWidth)
              )
              , span(Style.responsive.narrow
                , img(src := routes.Assets.at("images/pil-venstre-mobil.png"), Style.fullWidth)
              )
            )
          }
        )
        , div(Style.centerColumn
          , a(Style.languageLink.self
            , img(src := routes.Assets.at(Language.other(language).imgUrl), Style.languageLink.img)
            , href := routes.Application.c(comic.id, Language.other(language).name)
          )
          , a(Style.havhestenLink.self
            , img(src := routes.Assets.at("images/clipboard-link.png"), Style.havhestenLink.img)
            , href := "https://theismadsen.dk"
          )
          , comicImage(language, Style.imageWidth, comic)
        )
        , div(Style.outerColumns.right
          , next.map { nextComic =>
            a(href := routes.Application.c(nextComic.id, language.name)
              , rel := "prerender"
              , span(Style.responsive.wide
                , img(src := routes.Assets.at("images/pil-hoejre.png"), Style.fullWidth)
              )
              , span(Style.responsive.narrow
                , img(src := routes.Assets.at("images/pil-hoejre-mobil.png"), Style.fullWidth)
              )
            )
          }
        )
      )
      , div(Style.responsive.wide
        , div(Style.container
          , div(Style.centerColumn
            , div(comic.tests.headOption.map { _ =>
              div(Style.testsTitle, testTitleImage)
            })
            , div(Style.align.right, testElements)
          )
        )
      )
    )
  }

  def document(title: String, children: Modifier[Builder]*): Html = {
    Html(
      html(
        head(
          headTitle(title),
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

