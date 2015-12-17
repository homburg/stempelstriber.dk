package shared

object Data {
  case class Page(
    url: String,
    mobileUrl: String = ""
  )

  case class Comic(
    page: Map[String,  Page],
    id: Int,
    tests: Seq[String],
    created_at: String,
    updated_at: String
  )
}

