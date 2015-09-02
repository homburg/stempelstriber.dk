package services

import shared.Data
import sys.env
import upickle.default._
import scala.collection.SortedMap

object Comic {
    def comics: SortedMap[Int, Data.Comic] = {
      val f = scala.io.Source.fromFile(env("DATA_FILE")).mkString
      val comicList = read[Seq[Data.Comic]](f)
      SortedMap(comicList.map(c => c.id -> c): _*)
    }

    def byId(id: Int): Option[Data.Comic] = comics.get(id)
}
