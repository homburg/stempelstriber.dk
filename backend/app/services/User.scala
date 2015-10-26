package services

import slick.driver.SQLiteDriver.api._
import slick.driver.SQLiteDriver.Table
import scala.concurrent.ExecutionContext.Implicits.global
import org.sqlite.JDBC
import scala.util.Failure
import scala.concurrent.{Promise, Future}

object User {
  Class.forName("org.sqlite.JDBC")
  lazy val db = Database.forURL("jdbc:sqlite:///tmp/dev.sqlite3")

  class Name(tag: Tag) extends Table[(Int, String)](tag, "name") {
    // This is the primary key column
    def id = column[Int]("id", O.PrimaryKey,  O.AutoInc)
    def name = column[String]("name")

    // Every table needs a * projection with the same type as the table's type parameter
    def * = (id, name)
  }

  val names = TableQuery[Name]

  def setup: Future[Any] = db.run(DBIO.seq(names.schema.drop
    , names.schema.create
    , names ++= Seq((0, "Brian")
      , (0, "Thomas")
      , (0, "Joe")
    )
  ))

  def doNames: Future[Seq[String]] = db.run(names.map(_.name).result)
}
