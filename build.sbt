name := "scalajs-project-template"
version := "0.1"
scalaVersion := "2.12.8"

scalaJSUseMainModuleInitializer := true
scalafmtOnCompile := true

scalacOptions in ThisBuild ++= Seq(
  "-encoding",
  "utf-8",
  "-feature",
  "-unchecked",
  "-deprecation",
  "-Xlint:-unused,_",
  "-Xfuture",
  "-Xcheckinit",
  "-Xfatal-warnings",
  "-Ywarn-adapted-args",
  "-Ywarn-numeric-widen",
  "-Ywarn-unused:imports"
)

lazy val wartremoverSettings = Seq(
  wartremoverErrors in (Compile, compile) ++= Seq(
    Wart.EitherProjectionPartial,
    Wart.IsInstanceOf,
    Wart.TraversableOps,
    Wart.Null,
    Wart.OptionPartial,
    Wart.Return,
    Wart.StringPlusAny,
    Wart.TryPartial
  )
)

lazy val project =
  Project("ScalajsProjectTemplate", file("."))
    .enablePlugins(ScalaJSPlugin)
    .settings(
      wartremoverSettings,
      libraryDependencies ++= Seq(
        "org.scala-js" %%% "scalajs-dom" % "0.9.7",
        "org.querki" %%% "jquery-facade" % "1.2"
      ),
      jsDependencies ++= Seq(
        "org.webjars" % "jquery" % "2.2.1" / "jquery.js" minified "jquery.min.js",
        ProvidedJS / "js/example.js"
      )
    )
