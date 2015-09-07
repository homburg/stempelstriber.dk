package services

import sys.env

object ImageProxy {
  private lazy val baseUrl = env("IMAGE_PROXY_HOST")
  def width(width: Int, url: String) = s"http://$baseUrl/$width/$url"
}
