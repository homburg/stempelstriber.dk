package shared

object Data {
  case class Comic(
    comic: String,
    mobile: String = "",
    id: Int,
    tests: Seq[String],
    created_at: String,
    updated_at: String
  ) {
    lazy val src = if (mobile != "") {
      mobile
    } else {
      comic
    }
  }
}

