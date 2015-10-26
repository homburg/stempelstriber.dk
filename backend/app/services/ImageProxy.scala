package services

import play.api.Play.current

object ImageProxy {
  private lazy val baseUrl = current.configuration.getString("imageProxy.baseUrl")
  def width(width: Int, url: String) = s"http://$baseUrl/$width/$url"
}
