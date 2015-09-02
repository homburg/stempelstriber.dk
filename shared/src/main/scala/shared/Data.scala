package shared

object Data {
  case class Comic(
    comic: String,
    id: Int,
    tests: Seq[String],
    created_at: String,
    updated_at: String
  )
}

