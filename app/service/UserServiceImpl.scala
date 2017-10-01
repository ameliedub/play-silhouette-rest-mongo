package service

import javax.inject.Inject

import com.mohiva.play.silhouette.api.LoginInfo
import dao.UserDao
import models.User
import reactivemongo.api.commands.WriteResult

import scala.concurrent.Future

class UserServiceImpl @Inject()(userDao: UserDao) extends UserService {

  override def retrieve(loginInfo: LoginInfo): Future[Option[User]] =
    userDao.find(loginInfo)

  override def save(user: User): Future[WriteResult] =
    userDao.save(user)
}
