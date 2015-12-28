package services.session

import play.api.mvc.Request
import com.google.identitytoolkit.GitkitClient
import play.api.Play.current

class GitKit[T](val request: Request[T]) {
    lazy val gToken = request.cookies.get("gtoken")
    lazy val user = gToken.map { token =>
      GitKit.client.validateToken(token.value)
    }
}

object GitKit {
    lazy val client = GitkitClient.createFromJson(current.configuration.getString("gitkit_server_config_json").get)
}

