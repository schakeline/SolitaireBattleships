name := "SolitaireBattleship-WUI"

version := "1.0-SNAPSHOT"

lazy val root = project.in(file(".")).aggregate(tui_gui)
lazy val tui_gui = project.in(file("tui_gui"))

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache
)     

play.Project.playScalaSettings
