package services

import java.io.File

import play.Play
import play.api.mvc.{AnyContent, Request}
import play.api.Play.current
import shared.Data.{Comic => ComicModel}
import shared.Data
import upickle.default._
import scala.collection.SortedMap

import com.typesafe.config.ConfigException


object Comic {

  sealed trait Language {
    def name: String
    lazy val imgUrl: String = s"images/flag-${this.name}.png"
  }
  object Language {
    def other(language: Language) = options.find(_ != language).get

    lazy val default = Danish
    private lazy val options = List(Danish, English)
    def from(str: String): Language = {
      options.find(_.name == str).getOrElse(Danish)
    }

    def from(request: Request[AnyContent]): Language = {
      request.acceptLanguages.headOption.fold[Language](Danish) { lang =>
        from(lang.code)
      }
    }

    case object Danish extends Language { val name = "da" }
    case object English extends Language { val name = "en" }
  }

  private def comicList: Seq[ComicModel] = {
    try {
      read[Seq[ComicModel]](data)
    } catch {
      case e: Exception =>
        e match {
          case ij: upickle.Invalid.Json =>
            println("Invalid json")
            println(ij.msg)
          case id: upickle.Invalid.Data =>
            println("Invalid data")
            println(id.data)
            println(id.msg)
          case otherwise =>
            println("got exception...")
            println(e.getMessage)
        }

        Seq.empty
    }
  }

  def comics: SortedMap[Int, ComicModel] = {
      SortedMap(comicList.map(c => c.id -> c): _*)
    }

    def comicData: String = write[Seq[Data.Comic]](comicList)

    def byId(id: Int): Option[Data.Comic] = comics.get(id)
    def siblings(comic: Option[Data.Comic]): (Option[Data.Comic], Option[Data.Comic])  = comic match {
      case Some(c) => {
        val cs = comics.values.toVector
        val index = cs.indexOf(c)
        val csl = cs.lift
        (csl(index-1), csl(index+1))
      }
      case None => (None, None)
    }

    lazy val staticComicsJsonFile = Play.application().getFile("conf/comics.json")

    def data = {
      println("Reading from file: " + comicsJsonFile.getAbsolutePath)
      scala.io.Source.fromFile(comicsJsonFile).getLines.mkString("\n")
    }

    def configComicsJsonFile = {
      val path = current.configuration.getString("comics_json_file")
      println("Config says comics file: " + path.getOrElse("<no file>"))
      path.map { new File(_) }
    }
    def comicsJsonFile: File = {
      configComicsJsonFile match {
        case None => staticComicsJsonFile
        case Some(file) => {
          println(s"Config comics.json file is file ${file.isFile} and can read ${file.canRead}")
          if (file.isFile && file.canRead) {
            println("Using file: " + file.getName)
            file
          } else {
            staticComicsJsonFile
          }
        }
      }
    }
}
