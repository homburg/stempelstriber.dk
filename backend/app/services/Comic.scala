package services

import shared.Data
import upickle.default._
import scala.collection.SortedMap

object Comic {
    lazy val comics: SortedMap[Int, Data.Comic] = {
      val comicList = read[Seq[Data.Comic]](data)
      SortedMap(comicList.map(c => c.id -> c): _*)
    }

    def byId(id: Int): Option[Data.Comic] = comics.get(id)
    def siblings(comic: Option[Data.Comic]): Tuple2[Option[Data.Comic], Option[Data.Comic]]  = comic match {
      case Some(c) => {
        val cs = comics.values.toVector
        val index = cs.indexOf(c)
        val csl = cs.lift
        (csl(index-1), csl(index+1))
      }
      case None => (None, None)
    }

    val data = """[
        {
            "comic": "http://stempelstriber-dev.s3.amazonaws.com/comics/local-10.jpg",
            "created_at": "2015-09-02T19:41:57.572264",
            "id": 10,
            "tests": [],
            "updated_at": "2015-09-02T19:41:57.572355"
        },
        {
            "comic": "http://stempelstriber-dev.s3.amazonaws.com/comics/local-07.jpg",
            "created_at": "2015-09-02T19:41:57.572380",
            "id": 7,
            "tests": [
                "http://stempelstriber-dev.s3.amazonaws.com/tests/fejl07-3.jpg",
                "http://stempelstriber-dev.s3.amazonaws.com/tests/fejl07-2.jpg",
                "http://stempelstriber-dev.s3.amazonaws.com/tests/fejl07-1.jpg"
            ],
            "updated_at": "2015-09-02T19:41:57.572390"
        },
        {
            "comic": "http://stempelstriber-dev.s3.amazonaws.com/comics/local-01.jpg",
            "created_at": "2015-09-02T19:41:57.572405",
            "id": 1,
            "tests": [],
            "updated_at": "2015-09-02T19:41:57.572414"
        },
        {
            "comic": "http://stempelstriber-dev.s3.amazonaws.com/comics/local-09.jpg",
            "created_at": "2015-09-02T19:41:57.572428",
            "id": 9,
            "tests": [
                "http://stempelstriber-dev.s3.amazonaws.com/tests/fejl09-6.jpg",
                "http://stempelstriber-dev.s3.amazonaws.com/tests/fejl09-4.jpg",
                "http://stempelstriber-dev.s3.amazonaws.com/tests/fejl09-3.jpg",
                "http://stempelstriber-dev.s3.amazonaws.com/tests/fejl09-2.jpg",
                "http://stempelstriber-dev.s3.amazonaws.com/tests/fejl09-5.jpg",
                "http://stempelstriber-dev.s3.amazonaws.com/tests/fejl09-1.jpg"
            ],
            "updated_at": "2015-09-02T19:41:57.572435"
        },
        {
            "comic": "http://stempelstriber-dev.s3.amazonaws.com/comics/local-04.jpg",
            "created_at": "2015-09-02T19:41:57.572450",
            "id": 4,
            "tests": [
                "http://stempelstriber-dev.s3.amazonaws.com/tests/fejl04-1.jpg",
                "http://stempelstriber-dev.s3.amazonaws.com/tests/fejl04-2.jpg"
            ],
            "updated_at": "2015-09-02T19:41:57.572458"
        },
        {
            "comic": "http://stempelstriber-dev.s3.amazonaws.com/comics/local-12.jpg",
            "created_at": "2015-09-02T19:41:57.572472",
            "id": 12,
            "tests": [
                "http://stempelstriber-dev.s3.amazonaws.com/tests/fejl12-1.jpg",
                "http://stempelstriber-dev.s3.amazonaws.com/tests/fejl12-2.jpg",
                "http://stempelstriber-dev.s3.amazonaws.com/tests/fejl12-3.jpg"
            ],
            "updated_at": "2015-09-02T19:41:57.572480"
        },
        {
            "comic": "http://stempelstriber-dev.s3.amazonaws.com/comics/local-03.jpg",
            "created_at": "2015-09-02T19:41:57.572494",
            "id": 3,
            "tests": [
                "http://stempelstriber-dev.s3.amazonaws.com/tests/fejl03-1.jpg"
            ],
            "updated_at": "2015-09-02T19:41:57.572501"
        },
        {
            "comic": "http://stempelstriber-dev.s3.amazonaws.com/comics/local-06.jpg",
            "created_at": "2015-09-02T19:41:57.572515",
            "id": 6,
            "tests": [],
            "updated_at": "2015-09-02T19:41:57.572522"
        },
        {
            "comic": "http://stempelstriber-dev.s3.amazonaws.com/comics/local-13.jpg",
            "created_at": "2015-09-02T19:41:57.572537",
            "id": 13,
            "tests": [],
            "updated_at": "2015-09-02T19:41:57.572544"
        },
        {
            "comic": "http://stempelstriber-dev.s3.amazonaws.com/comics/local-05.jpg",
            "created_at": "2015-09-02T19:41:57.572563",
            "id": 5,
            "tests": [],
            "updated_at": "2015-09-02T19:41:57.572571"
        },
        {
            "comic": "http://stempelstriber-dev.s3.amazonaws.com/comics/local-08.jpg",
            "created_at": "2015-09-02T19:41:57.572589",
            "id": 8,
            "tests": [],
            "updated_at": "2015-09-02T19:41:57.572597"
        },
        {
            "comic": "http://stempelstriber-dev.s3.amazonaws.com/comics/local-02.jpg",
            "created_at": "2015-09-02T19:41:57.572610",
            "id": 2,
            "tests": [
                "http://stempelstriber-dev.s3.amazonaws.com/tests/fejl02-4.jpg",
                "http://stempelstriber-dev.s3.amazonaws.com/tests/fejl02-3.jpg",
                "http://stempelstriber-dev.s3.amazonaws.com/tests/fejl02-1.jpg",
                "http://stempelstriber-dev.s3.amazonaws.com/tests/fejl02-2.jpg"
            ],
            "updated_at": "2015-09-02T19:41:57.572618"
        },
        {
            "comic": "http://stempelstriber-dev.s3.amazonaws.com/comics/local-11.jpg",
            "created_at": "2015-09-02T19:41:57.572632",
            "id": 11,
            "tests": [
                "http://stempelstriber-dev.s3.amazonaws.com/tests/fejl11-1.jpg",
                "http://stempelstriber-dev.s3.amazonaws.com/tests/fejl11-2.jpg"
            ],
            "updated_at": "2015-09-02T19:41:57.572639"
        }
    ]"""
}
