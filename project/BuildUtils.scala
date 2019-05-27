import sbt._

case class ResourceKeys(staticDir: File,
                        templatesDir: File,
                        providedJsDir: File,
                        productName: String,
                        version: String,
                        isRelease: Boolean)

object BuildUtils {

  def collectResources(targetDir: File, keys: ResourceKeys): Unit = {
    println(s"Collecting static files from ${keys.staticDir.getPath}")
    
    copyDirContent(keys.staticDir, targetDir)
    copyDirContent(keys.providedJsDir, targetDir)

    val replacements = Map(
      "%version%" -> keys.version,
      "%isRelease%" -> keys.isRelease.toString,
      "%productName%" -> keys.productName,
    )

    println(s"Collecting template files from ${keys.templatesDir.getPath}")

    val templates = (keys.templatesDir * "*").filter(_.isFile).get
    templates.foreach { t =>
      copyFile(t, targetDir, replacements)
    }

    println(s"Resources were copied to ${targetDir.getPath}")
  }
  
  def copyDirContent(sourceDir: File, targetDir: File): Unit = {
    val dirs = (sourceDir * "*").filter(_.isDirectory).get
    val files = (sourceDir * "*").filter(_.isFile).get
    dirs.foreach { d =>
      println(s"\tCopying /${d.getName}/")
      IO.copyDirectory(d, targetDir / d.getName, overwrite = true)
    }
    files.foreach { f =>
      println(s"\tCopying /${f.getName}")
      IO.copyFile(f, targetDir / f.getName)
    }
  }

  def copyFile(sourceFile: File, targetFolder: File, replacements: Map[String, String]): Unit = {
    println(s"\tCopying ${sourceFile.getName} with ${replacements.size} replacements.")
    val source = IO.read(sourceFile, IO.utf8)
    val content = replacements.foldLeft(source) { (acc, next) =>
      acc.replaceAllLiterally(next._1, next._2)
    }
    IO.write(targetFolder / sourceFile.getName, content, IO.utf8)
  }
}
