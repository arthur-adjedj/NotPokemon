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
        ennemy.attackStage = (-6).max(ennemy.attackStage -1)
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
    accuracy = Float.PositiveInfinity
    override def toString : String = 
        "The user relaxes and lightens its body to move faster. This sharply raises the Speed stat."
    override def cast(self: Monster, ennemy: Monster): Unit = 
        self.speedStage = (6).min(self.speedStage + 2)
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
            ennemy.receiveStatus(new Paralysis)
        }
    }
}

object Tackle extends Attack {
    name = "Tackle"
    power = 40
    override def toString : String = 
        "A physical attack in which the user charges "+
        "and slams into the target with its whole body."
}

object TailWhip extends Attack {
    name = "Tail Whip"
    override def toString : String = 
        "The user wags its tail cutely, making opposing Pokémon less wary and lowering their Defense stat."
    override def cast(self: Monster, ennemy: Monster): Unit = 
        ennemy.defenseStage = (-6).max(ennemy.defenseStage-1)
}

object WaterGun extends Attack {
    name = "Water Gun"
    power = 40
    attackType = Water
    override def toString : String = 
        "The target is blasted with a forceful shot of water."
}

object Withdraw extends Attack {
    name = "Withdraw"
    attackType = Water
    override def toString : String = 
        "The user wags its tail cutely, making opposing Pokémon less wary and lowering their Defense stat."
    override def cast(self: Monster, ennemy: Monster): Unit = 
        self.defenseStage = (6).min(self.defenseStage+1)
    
}

object RapidSpin extends Attack {
    name = "Rapid Spin"
    power = 50
    override def toString : String = 
        "A spin attack that can also eliminate such moves as Bind, Wrap, Leech Seed, and Spikes."
    override def cast(self: Monster, ennemy: Monster): Unit = 
        self.speedStage = (6).min(self.speedStage+1)
    
}


object WaterPulse extends Attack {
    name = "Water Pulse"
    power = 60
    override def toString : String = 
        "The user attacks the target with a pulsing blast of water. This may also confuse the target."
    override def cast(self: Monster, ennemy: Monster): Unit = {
        if (scala.util.Random.nextFloat() < 0.2f){
            ennemy.receiveStatus(new Confusion) 
       }
    }
    
}


object Protect extends Attack {
    name = "Protect"
    override def toString : String = 
        "Enables the user to evade all attacks. Its chance of failing rises if it is used in succession."
    override def cast(self: Monster, ennemy: Monster): Unit = {
            ennemy.receiveStatus(new Protection) 
       
    }
}


object AquaTail extends Attack {
    name = "Aqua Tail"
    power = 90
    accuracy = 0.9f
    override def toString : String = 
        "The user attacks by swinging its tail as if it were a vicious wave in a raging storm."
}

object ShellSmash extends Attack {
    name = "Shell Smash"
    override def toString : String = 
        "A spin attack that can also eliminate such moves as Bind, Wrap, Leech Seed, and Spikes."
    override def cast(self: Monster, ennemy: Monster): Unit = 
        ennemy.defenseStage = (-6).max(ennemy.defenseStage-1)
    
}

object IronDefense extends Attack {
    name = "Iron Defense"
    override def toString : String = 
        "The user hardens its body’s surface like iron, sharply raising its Defense stat."
    override def cast(self: Monster, ennemy: Monster): Unit = 
        self.defenseStage = (6).min(self.defenseStage+2)
    
}

//TODO faire attacks squirtle

