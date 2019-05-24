import utils.ExternalJsUtil

import scala.scalajs.js

object Main {

  def main(args: Array[String]): Unit = {
    println(getClass.getName + s": Hello console world at ${new js.Date}!")
    new ExternalJsUtil().testMethod()
  }
}
