import sbt._
import Keys._

object V {
  val Akka = "2.5.7"
  val Scalatest = "3.0.1"
  val Slick = "3.2.1"
  val SlickPg = "0.15.4"
  val Cats = "1.0.0-RC1"
}

object Dependencies {
  val akkaActor = "com.typesafe.akka" %% "akka-actor" % V.Akka % "provided"

  val scalatest = "org.scalatest" %% "scalatest" % V.Scalatest

  val slick = "com.typesafe.slick" %% "slick" % V.Slick % "provided"

  val slickPg = "com.github.tminglei" %% "slick-pg" % V.SlickPg % "provided"

  val cats = "org.typelevel" %% "cats-core" % V.Cats % "provided"
}
