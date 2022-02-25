import java.util.concurrent.TimeUnit

object Release {
  def main(argv : Array[String]) : Unit = {
    Utils.debug = false
    var map = new MapUI
    map.initialise    
  }
}

object Debug {
  def main(argv : Array[String]) : Unit = {
    Utils.debug = true
    Utils.print("En cas de problème, rappelons que 1/2 = 0")
    //Demo.start
    var map = new MapUI
    map.initialise    
  }
}