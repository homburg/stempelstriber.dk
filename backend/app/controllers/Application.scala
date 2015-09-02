package controllers

import awscala.s3.S3ObjectSummary
import home.Home
import play.api.Play.current
import play.api.libs.MimeTypes
import play.api.libs.iteratee.Enumerator
import play.api.libs.ws._
import play.api.mvc._
import services.{Files, Comic}
import sys.env
import upickle.default.read
import pprint.Config.Defaults._
import shared.Data

object Application extends Controller {
  implicit val context = scala.concurrent.ExecutionContext.Implicits.global

  def index = Action {
    Ok(Home.text)
  }

  def c(id: Int) = Action {
    Comic.byId(id) match {
      case Some(comic) => Ok(Home.comic(comic)).withHeaders(CONTENT_TYPE -> HTML)
      case None => NotFound
    }
  }

  def file(path: String) = Action {
    Files.file(path) match {
      case Some(f) => {
        val meta = f.getObjectMetadata
        var ok = Ok.stream(Enumerator.fromStream(f.content))
        if (meta.getContentType != null) {
          ok = ok.withHeaders(CONTENT_TYPE -> meta.getContentType)
        }
        var contentEncoding: Option[String] = Option(meta.getContentEncoding)
        val key: String = f.getKey
        def mimeType: Option[String] = MimeTypes.forFileName(key)

        contentEncoding = contentEncoding match {
          case Some(s: String) => Some(s)
          case None => {
            mimeType match {
              case Some(s: String) => Some(s)
              case None => None
            }
          }
        }

        contentEncoding.foreach { value =>
          ok = ok.withHeaders(CONTENT_TYPE -> value)
        }

        ok
      }
      case None => NotFound
    }
  }

  def files = Action {

    def mapS3toString(e: Either[String, S3ObjectSummary]): String = e match {
      case Left(s) => s
      case Right(summ) => {
        val file = Files.file(summ.getKey)
        file.map { file =>
          file.publicUrl.toString
        }.getOrElse(summ.getKey)
      }
    }

    val ok = try {
      val strs: Seq[String] = Files.files.map(mapS3toString)
      Ok(Home.files(strs))
    } catch {
      case e: Exception => InternalServerError(e.getMessage)
    }
    ok.withHeaders("X-Bucket" -> Files.bucketName.trim)

    // Ok(fileStream.mkString("\n")).withHeaders("content-type" -> "text/plain")
  }

  def buckets = Action {
   Ok(Files.buckets.map(_.name).mkString("\n"))
    .withHeaders(CONTENT_TYPE -> TEXT)
  }

  def upload = Action(parse.multipartFormData) { request => 
    request.body.file("image").map { image =>
      val filename = image.filename
      val file = image.ref.file
      println(s"Putting: ${filename}")
      val res = Files.put(filename, file)
      Redirect(routes.Application.file(filename))
    }.getOrElse {
      BadRequest
    }
  }

  def settings = Action {
    Ok(
      current.configuration.getString("sssetting").get
    )
  }

  def status = Action {
    Ok(Seq(
      "first",
      Files.bucketName,
      "last"
    ).toString).withHeaders(CONTENT_TYPE -> TEXT)
  }

  def view(path: String) = Action.async {
    WS.url(s"https://stempelstriber-dev.firebaseio.com${path}?print=pretty")
      .getStream().map {
        case (response, body) =>
          if (response.status == 200) {
            Ok.feed(body).as("text/plain").withHeaders("x-path" -> path)
          } else {
            NotFound
          }
      }
  }

  def comics = Action {
    Ok(pprint.tokenize(Comic.comics).mkString)
  }
}
