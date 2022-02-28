import java.util.concurrent.TimeUnit

object Release {
  def main(argv : Array[String]) : Unit = {
    Utils.debug = false
    var frame = new UI
    Utils.frame = frame
    frame.initialise
    frame.initialiseMap
  }
}

object Debug {
  def main(argv : Array[String]) : Unit = {
    Utils.debug = true
    Utils.print("En cas de problème, rappelons que 1/2 = 0")
    var frame = new UI
    Utils.frame = frame
    frame.initialise
    frame.initialiseMap 
  }
}

object Tests {
  def main(argv : Array[String]) : Unit = {
    Utils.debug = true
    BattleTest.start
  }
}