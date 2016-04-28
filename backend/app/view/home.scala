package view.home

import controllers.routes
import play.twirl.api.Html
import play.api.mvc.RequestHeader
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

  def comic(request: RequestHeader, language: Language, comic: Comic, prev: Option[Comic] = None, next: Option[Comic] = None): (Html, List[(String, String)]) = {

    val testElements = comic.tests.map { url =>
      a(href:=url, c:="imagelightbox ", img(src:=imageProxy.url(url, width=Some(127))))
    }

    val testTitleImage = img(src := routes.Assets.at(language match {
      case Language.English => "images/en/tests-title.png"
      case _ => "images/da/tests-title.png"
    }))

    val title = language match {
      case Language.English => "The Local"
      case _ => "Havhesten"
    }

    val bodyChildren = List(
      div(Style.container
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

    val page = comic.page.get(language.name)

    val ogComicData = List(
      "prefix".attr := "og: http://ogp.me/ns# fb: http://ogp.me/ns/fb# article: http://ogp.me/ns/article#"
      , meta(property := "og:title", content := s"$title - ${comic.id}")
      , meta(property := "og:type", content := "article")
      , meta(property := "og:url", content := routes.Application.c(comic.id, language.name).absoluteURL()(request))
      , meta(property := "fb:app_id", content := "1081496458568945")
    )
    
    val ogPageData = List(
      page.map { p =>
        meta(property := "og:image", content := p.url)
      }
    )

    val headChildren = ogComicData ++ ogPageData.flatten

    val headers = List(
      ("Link" -> s"</assets/frontend-jsdeps.min.js>; rel=preload")
      , ("Link" -> s"</assets/frontend-opt.js>; rel=preload")
      , ("Link" -> s"</assets/frontend-launcher.js>; rel=preload")

      , ("Link" -> s"</assets/images/pil-hoejre-mobil.png>; rel=preload")
      , ("Link" -> s"</assets/images/pil-hoejre.png>; rel=preload")

      , ("Link" -> s"</assets/images/pil-venstre-mobil.png>; rel=preload")
      , ("Link" -> s"</assets/images/pil-venstre.png>; rel=preload")

      , ("Link" -> s"</assets/images/pil-venstre.png>; rel=preload")
    )
    (document(title, headChildren, bodyChildren), headers)
  }

  def document(title: String, headChildren: List[Modifier[Builder]], bodyChildren: List[Modifier[Builder]]): Html = {
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
          , headChildren
        ),
        body(
          bodyChildren,
          raw(scalaJsScripts("frontend")),
          javascript(src:=routes.Assets.at("imagelightbox.min.js"))
        )
      )
    )
  }
}

