package services

import play.api.Play.current

object ImageProxy {
  private lazy val baseUrl = current.configuration.getString("imageProxy.baseUrl").get.stripSuffix("/")
  def width(width: Int, url: String) = s"$baseUrl/$width/$url"
}
