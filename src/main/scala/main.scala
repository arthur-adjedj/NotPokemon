import java.util.concurrent.TimeUnit


object MyApp {
  def main(argv : Array[String]) : Unit = {
    var b = new Battle(FirstPlayer, SecondPlayer)
    
    b.initialise
    b.start
    
  }
  
}