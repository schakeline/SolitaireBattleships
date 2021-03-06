//import de.johoop.jacoco4sbt.JacocoPlugin._

name := "SolitaireBattleships"

version := "0.0.0"

scalaVersion := "2.10.3"

resolvers ++= Seq(
  "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Typesafe Snapshots" at "http://repo.typesafe.com/typesafe/snapshots/",
  "snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
  "releases" at "http://oss.sonatype.org/content/repositories/releases",
  "sonatype-public" at "https://oss.sonatype.org/content/groups/public"
)

libraryDependencies ++= Seq(
  "com.github.scopt" %% "scopt" % "3.1.0",
  "org.scalatest" % "scalatest_2.10" % "2.0.RC3", 
  "org.scala-lang" % "scala-swing" % "2.10.3",
  "org.scala-lang" % "scala-actors" % "2.10.3"
  //"play" % "play_2.10" % "2.1-SNAPSHOT"
)

//org.scalastyle.sbt.ScalastylePlugin.Settings

//jacoco.settings
