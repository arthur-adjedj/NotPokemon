abstract class Attack {
    val name : String = ""
    val power : Int = 0
    val critChance : Float = 0
    val priority : Int = 0
    val accuracy : Float = 1
    val attackType : Type     
    def inducedStatus() : Status = NoStatus
    def nOfHits() : Int = 1
}

object QuickAttack extends Attack {
    override val name = "Quick Attack"
    override def toString : String = 
        "The user lunges at the foe at a speed that makes " +
        "it almost invisible. It is sure to strike first."
    override val power = 40
    override val priority = 1
    val attackType= Normal
}


object DoubleSlap extends Attack {
    override val name = "Double Slap"
    override def toString : String = 
        "The target is slapped repeatedly, back and forth,"+
        " two to five times in a row."
    override val power = 15
    override val accuracy: Float = 0.85f
    val attackType = Normal
    override def nOfHits(): Int = { scala.util.Random.nextFloat() match {
        case x if x<= 32.5 => 2
        case x if x<= 65 => 3
        case x if x<= 77.5 => 4
        case _ => 5

    }}

}

