import org.querki.jquery._
import org.scalajs.dom.document

import scala.scalajs.js.annotation._

@JSExportTopLevel("MainDom")
object MainDom {

  @JSExport
  def start(): Unit = {
    println(getClass.getName + ": Hello DOM world!")

    useDom()
    useJquery()
  }

  def useDom(): Unit = {
    val hello = document.createElement("div")
    hello.appendChild(document.createTextNode("Hello world with DOM!"))
    document.body.appendChild(hello)
  }

  def useJquery(): Unit = {
    $("body").append("<div>Hello world with jQuery!</div>")
    $("body").append("<div id='clickTest'>Click me!</div>")
    $("#clickTest").click(onClick _)
  }

  def onClick(e: JQueryEventObject): Unit = {
    $("body").append("<div>Clicked!</div>")
  }
}
