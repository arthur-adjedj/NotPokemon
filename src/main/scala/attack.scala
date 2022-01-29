abstract class Attack {
    val name : String = ""
    val power : Int = 0
    val critChance : Float = 0
    val priority : Int = 0
    val accuracy : Float = 1
    val attackType : Type     
    def nOfHits() : Int = 1
    def cast(self : Monster,ennemy: Monster) : Unit = ()
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

object ThunderShock extends Attack {
    override val name = "Thunder Shock"
    override def toString : String = 
        "A jolt of electricity crashes down on the target to " +
        "inflict damage. This may also leave the target with paralysis."
    override val power = 40
    val attackType = Electric
}

object ThunderWave extends Attack {
    override val name = "Thunder Wave"
    override def toString : String = 
        "The user launches a weak jolt of electricity that paralyzes the target."
    override val accuracy: Float = 0.9f
    val attackType = Electric
    override def cast(self: Monster, ennemy: Monster): Unit = ()//TODO add Paralysis
}

object Growl extends Attack {
    override val name = "Growl"
    override def toString : String = 
        "The user growls in an endearing way, making opposing" +
        "Pokémon less wary. This lowers their Attack stat."
    override val power = 40
    val attackType = Normal 
    override def cast(self: Monster, ennemy: Monster): Unit = 
        ennemy.attackStage = (-6).min(ennemy.attackStage -1)
}

object Swift extends Attack {
    override val name = "Swift"
    override def toString : String = 
        "Star-shaped rays are shot at the opposing Pokémon. This attack never misses."
    override val power = 40
    override val accuracy: Float = Float.PositiveInfinity
    val attackType = Normal
}

object Thunder extends Attack {
    override val name = "Thunder"
    override def toString : String = 
        "A wicked thunderbolt is dropped on the target to inflict damage."+
        " This may also leave the target with paralysis."
    override val power = 110
    override val accuracy: Float = 0.7f
    val attackType = Electric
    override def cast(self: Monster, ennemy: Monster): Unit = {
        if (scala.util.Random.nextFloat() < 0.3f){
            //TODO add paralysis
        }
    }
}

//TODO faire attacks squirtle

