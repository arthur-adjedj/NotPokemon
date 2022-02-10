import java.util.concurrent.TimeUnit


object MyApp {
  def main(argv : Array[String]) : Unit = {
    println("En cas de problème, rappelons que 1/2 = 0")
    var b = new Battle(FirstPlayer, SecondPlayer)
    b.initialise
    b.start

    b = new Battle(FirstPlayer, WildPlayer)
    b.initialise
    b.start

    b = new Battle(FirstPlayer, ThirdPlayer)
    b.initialise
    b.start
    
  }
  
}