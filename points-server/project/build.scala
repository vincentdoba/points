import org.scalatra.sbt._
import sbt.Keys._
import sbt._

object PointsBuild extends Build {
  val Organization = "com.lateralthoughts.internal"
  val Name = "points-server"
  val Version = "0.1.0-SNAPSHOT"
  val ScalaVersion = "2.11.7"
  val ScalatraVersion = "2.4.0.RC3"

  lazy val project = Project (
    "points-server",
    file("."),
    settings = ScalatraPlugin.scalatraSettings ++ Seq(
      organization := Organization,
      name := Name,
      version := Version,
      scalaVersion := ScalaVersion,
      resolvers += Classpaths.typesafeReleases,
      resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases",
      libraryDependencies ++= Seq(
        "org.scalatra" %% "scalatra" % ScalatraVersion,
        "org.scalatra" %% "scalatra-json" % ScalatraVersion,
        "org.scalatra" %% "scalatra-specs2" % ScalatraVersion % "test",
        "org.scalatra" %% "scalatra-scalatest" % ScalatraVersion % "test",
        "ch.qos.logback" % "logback-classic" % "1.1.2" % "runtime",
        "org.eclipse.jetty" % "jetty-webapp" % "9.2.10.v20150310" % "container",
        "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided",
        "org.json4s"   %% "json4s-jackson" % "3.3.0",
        "org.json4s" %% "json4s-ext" % "3.3.0"
      )
    )
  )
}
