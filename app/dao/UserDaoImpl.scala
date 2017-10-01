package dao

import javax.inject.Inject

import com.mohiva.play.silhouette.api.LoginInfo
import models.User
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.commands.WriteResult
import reactivemongo.play.json._
import reactivemongo.play.json.collection._

import scala.concurrent.Future

class UserDaoImpl @Inject()(val reactiveMongoApi: ReactiveMongoApi) extends UserDao {

  def users = reactiveMongoApi.database.map(_.collection[JSONCollection]("user"))

  override def find(loginInfo: LoginInfo): Future[Option[User]] =
    users.flatMap(_.find(Json.obj("username" -> loginInfo.providerKey)).one[User])

  override def save(user: User): Future[WriteResult] =
    users.flatMap(_.insert(user))
}
