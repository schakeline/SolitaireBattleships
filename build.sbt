name := "SolitaireBattleship-WUI"

version := "1.0-SNAPSHOT"

lazy val root = Project(id = "root", base = file("."))
  .aggregate(tui_gui)
  .dependsOn(tui_gui)

lazy val tui_gui = Project(id = "tui_gui", base =file("tui_gui"))

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache
)     

play.Project.playScalaSettings
