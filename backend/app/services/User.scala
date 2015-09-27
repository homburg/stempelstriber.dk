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
    def id = column[Int]("id", O.PrimaryKey,  O.AutoInc) // This is the primary key column
    def name = column[String]("name")
    // Every table needs a * projection with the same type as the table's type parameter
    def * = (id, name)
  }
  val names = TableQuery[Name]

  def setup: Future[Any] = {

    val f = db.run(names.schema.drop)

    val p: Promise[Unit] = Promise()

    f onComplete {
      case Failure(t) => {
        // Missing database?
        if (t.getMessage.contains("no such table")) {
          p.success(Unit)
        } else {
          p.failure(t)
        }
      }
      case _ => p.success(Unit)
    }

    p.future map {
      case _ => {
        println("Creating...")
        db.run(DBIO.seq(names.schema.create
          , names ++= Seq((0, "Brian")
            , (0, "Thomas")
            , (0, "Joe")
          )
        ))
      }
    }
  }

  def doNames: Future[Seq[String]] = db.run(names.map(_.name).result)
}
