# Scala.js Project Template

Generic template for a Scala.js project with DOM and jQuery.

The project is in no way a minimal one, as it aims to showcase everything you will probably end up doing.

The project doesn't contain any UI components framework (see Scala.js-React Project Template for that). 

* Shows mapping code in both directions: Scala.js &lt;—&gt; JavaScript.
* Shows using utility Scala code in SBT build task.
* Shows using libraries from WebJars and from external JavaScript files.
* Shows passing parameters to build and generating resources from templates.
* Includes coursier, wartremover and scalafmt.

## Usage

Build target folder is `bin` in project root. That's where all resources are copied and where the `fastOptJs`/`fullOptJS` output is directed.

The `version` parameter is optional, it defaults to the `version` setting in `build.sbt`.

### Clean and build from command line

`/build-dev.sh -Dversion="0.1.0"` 

or
 
`/build-release.sh -Dversion="0.1.0"`

Open `/bin/index.html` to see the result.

### Clean and build from SBT's interactive mode

Start SBT with `sbt -mem 2000 -Dversion="0.1.0"`. The memory setting here prevents SBT from crashing with out of memory exception after a dozen rebuilds in interactive mode.

* Use `release` or `dev` commands to *clean*, *build* and *collect resources*. 
* Use `buildRelease` or `buildDev` to *build* and *collect resources*.
* Use `fastOptJS` or `fullOptJS` as you normally would with any Scala.js project to just *build main javascript files*.  

## Project structure

* `/bin` – this is where all build files go. The directory is created by build commands and is deleted completely by `clean` command.
* `/project` – a standard SBT project directory, which contains `BuildUtils.scala`, that does file manipulation during build.
* `/resources` – contains static resources (like css and images) and templates (html) that get processed by build logic.
* `/src/main/resources/` – the standard directory for js libraries. See `js/example.js` there.

