import complete.DefaultParsers._

name := "scalajs-project-template"
version := "0.0.1"
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

lazy val targetDirectory = settingKey[File]("Target build directory")
lazy val buildVersion = settingKey[String]("Build version from -Dversion parameter.")
lazy val buildRelease = inputKey[Unit]("Build release application")
lazy val buildDev = inputKey[Unit]("Build dev application")

targetDirectory := baseDirectory.value / "bin"

artifactPath in (Compile, fastOptJS) := targetDirectory.value / "js" / "main.js"
artifactPath in (Compile, fullOptJS) := targetDirectory.value / "js" / "main.js"
artifactPath in (Compile, packageJSDependencies) := targetDirectory.value / "js" / "dependencies.js"
clean ~= { _ =>
  IO.delete(new File("./bin"))
}

Compile / fastOptJS ~= { result =>
  println(s"\nfastOptJS result:\n${result.data.getPath}\n")
  result
}
Compile / fullOptJS ~= { result =>
  println(s"\nfullOptJS result:\n${result.data.getPath}\n")
  result
}

lazy val project =
  Project("ScalajsProjectTemplate", file("."))
    .enablePlugins(ScalaJSPlugin)
    .settings(
      buildVersion := sys.props.get("version").getOrElse(version.value),
      wartremoverSettings,
      targetDirectory := baseDirectory.value / "bin",
      libraryDependencies ++= Seq(
        "org.scala-js" %%% "scalajs-dom" % "0.9.7",
        "org.querki" %%% "jquery-facade" % "1.2"
      ),
      jsDependencies ++= Seq(
        "org.webjars" % "jquery" % "2.2.1" / "jquery.js" minified "jquery.min.js",
        ProvidedJS / "js/example.js"
      ),
      buildRelease := {
        val log = streams.value.log
        log.info(s"Starting RELEASE build")

        val keys = ResourceKeys(
          baseDirectory.value / "resources" / "static",
          baseDirectory.value / "resources" / "templates",
          baseDirectory.value / "src" / "main" / "resources",
          "Scala.js Project Template",
          buildVersion.value,
          isRelease = true
        )

        BuildUtils.collectResources(targetDirectory.value, keys)
      },
      buildRelease := buildRelease.dependsOn(Compile / compile, Compile / fullOptJS).evaluated,
      buildDev := {
        val log = streams.value.log
        log.info(s"Starting DEV build")

        val keys = ResourceKeys(
          baseDirectory.value / "resources" / "static",
          baseDirectory.value / "resources" / "templates",
          baseDirectory.value / "src" / "main" / "resources",
          "Scala.js Project Template (dev version)",
          buildVersion.value,
          isRelease = false
        )

        BuildUtils.collectResources(targetDirectory.value, keys)
      },
      buildDev := buildDev.dependsOn(Compile / compile, Compile / fastOptJS).evaluated
    )

// shortcuts for clean & build

commands += Command.command("release") { s =>
  "clean" ::
    "buildRelease" ::
    s
}

commands += Command.command("dev") { s =>
  "clean" ::
    "buildDev" ::
    s
}
