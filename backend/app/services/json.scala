package services.json

import play.api.libs.json._
import com.google.identitytoolkit.GitkitUser

object Writes {
  implicit val gitkitUserWrites = new Writes[GitkitUser] {
    def writes(user: GitkitUser) = Json.obj(
      "name" -> user.getName
      , "email" -> user.getEmail
      , "photoUrl" -> user.getPhotoUrl
    )
  }
}
