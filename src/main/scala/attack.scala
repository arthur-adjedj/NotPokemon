abstract class Attack {
    var name : String = ""
    var power : Int = 0
    var critChance : Float = 0
    var priority : Int = 0
    var accuracy : Float = 1
    var attackType : Type = Normal
    def nOfHits() : Int = 1
    def cast(self : Monster,ennemy: Monster) : Unit = ()
}

object EmptyAttack extends Attack {
    name = "Empty"
}


object QuickAttack extends Attack {
    name = "Quick Attack"
    power = 40
    priority = 1
    override def toString : String = 
        "The user lunges at the foe at a speed that makes " +
        "it almost invisible. It is sure to strike first."
}

object DoubleSlap extends Attack {
    name = "Double Slap"
    power = 15
    accuracy = 0.85f
    override def toString : String = 
        "The target is slapped repeatedly, back and forth,"+
        " two to five times in a row."
    
    override def nOfHits(): Int = { scala.util.Random.nextFloat() match {
        case x if x<= 32.5 => 2
        case x if x<= 65 => 3
        case x if x<= 77.5 => 4
        case _ => 5
    }}

}

object ThunderShock extends Attack {
    name = "Thunder Shock"
    power = 40
    attackType = Electric
    override def toString : String = 
        "A jolt of electricity crashes down on the target to " +
        "inflict damage. This may also leave the target with paralysis."
}

object ThunderWave extends Attack {
    name = "Thunder Wave"
    accuracy = 0.9f
    attackType = Electric
    override def toString : String = 
        "The user launches a weak jolt of electricity that paralyzes the target."
    override def cast(self: Monster, ennemy: Monster): Unit = ()//TODO add Paralysis
}

object Growl extends Attack {
    name = "Growl"
    power = 40
    override def toString : String = 
        "The user growls in an endearing way, making opposing" +
        "Pokémon less wary. This lowers their Attack stat."
    override def cast(self: Monster, ennemy: Monster): Unit = 
        ennemy.attackStage = (-6).min(ennemy.attackStage -1)
}

object Swift extends Attack {
    name = "Swift"
    power = 40
    accuracy = Float.PositiveInfinity
    attackType = Normal
    override def toString : String = 
        "Star-shaped rays are shot at the opposing Pokémon. This attack never misses."    
}

object Agility extends Attack {
    name = "Agility"
    power = 0
    accuracy = Float.PositiveInfinity
    override def toString : String = 
        "The user relaxes and lightens its body to move faster. This sharply raises the Speed stat."
    override def cast(self: Monster, ennemy: Monster): Unit = 
        self.speedStage = (6).max(self.speedStage + 2)
}

object Thunder extends Attack {
    name = "Thunder"
    power = 110
    accuracy= 0.7f
    attackType = Electric
    override def toString : String = 
        "A wicked thunderbolt is dropped on the target to inflict damage."+
        " This may also leave the target with paralysis."
    override def cast(self: Monster, ennemy: Monster): Unit = {
        if (scala.util.Random.nextFloat() < 0.3f){
            var TO_DO = ennemy.status
        }
    }
}

//TODO faire attacks squirtle

