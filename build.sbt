enablePlugins(ScalaJSPlugin)
scalaJSUseMainModuleInitializer := true
scalafmtOnCompile := true

name := "scalajs-project-template"
version := "0.1"
scalaVersion := "2.12.8"

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.7"
libraryDependencies += "org.querki" %%% "jquery-facade" % "1.2"

jsDependencies +=
  "org.webjars" % "jquery" % "2.2.1" / "jquery.js" minified "jquery.min.js"
