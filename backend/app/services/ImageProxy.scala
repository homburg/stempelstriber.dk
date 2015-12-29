package services

import play.api.Play.current

class ImageProxy(private val quality: Option[Int] = None) {
  def this(q: Int) = this(Some(q))

  private lazy val baseUrl = current.configuration.getString("imageProxy.baseUrl").get.stripSuffix("/")
  def url(url: String, width: Option[Int] = None) = {
    val properties = List(
      width map { _ + "x" },
      quality map { "q" + _ }
    ).flatten

    val propertyPart = properties mkString ","

    s"$baseUrl/${propertyPart}/$url"
  }
}
