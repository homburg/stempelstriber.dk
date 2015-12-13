package services

import play.api.Play.current

object ImageProxy {
  private lazy val baseUrl = current.configuration.getString("imageProxy.baseUrl").get.stripSuffix("/")
  private val quality = 92
  def width(width: Int, url: String) = s"$baseUrl/${width}x,q${quality}/$url"
}
