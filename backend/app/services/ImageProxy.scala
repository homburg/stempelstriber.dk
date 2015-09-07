package services

object ImageProxy {
  def width(width: Int, url: String) = s"http://image.c.homburg.dk/$width/$url"
}
