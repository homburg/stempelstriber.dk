package services

import awscala.Region
import awscala.s3._

import scala.sys.env
import java.io.File

object Files {
  def bucketName = env("S3_BUCKET")

  implicit def s3 = {
    val s3 = S3()
    s3.setRegion(Region.EU_CENTRAL_1)
    s3
  }

  def buckets = s3.buckets

  def bucket: Option[Bucket] = {
    s3.bucket(bucketName.trim)
  }

  def files: Seq[Either[String, S3ObjectSummary]] = bucket.map(_.ls("")).getOrElse(Seq.empty)

  def file(key:  String): Option[S3Object] = bucket.flatMap(_.get(key))

  def put(key: String, file: File): Option[PutObjectResult] = bucket.map(_.put(key, file))
}
