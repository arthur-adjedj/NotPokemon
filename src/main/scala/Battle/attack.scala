abstract class Attack extends Object with ScoreForStrategy {
    var name : String = ""
    var action : String = "attack" // To notify the IA that there can be a buff/debuff/status
    var power : Int = 0
    var critChance : Float = 0
    var priority : Int = 0
    // The accuracy should normally be between 0 and 1, but is set to infinity for undodgeable attack to ensure the ennemy's evasion doesn't counteract
    var accuracy : Float = 1 
    var attackType : Type = Normal
    def scoreForStrategy (self : Monster, ennemy : Monster) : Int = {
        1 + (power*attackType.multDamage(ennemy.monsterType)*Math.pow(2, self.getStage("attack")) - ennemy.getStage("defense")).toInt
    }
    def nOfHits() : Int = 1 
    def cast(self : Monster,ennemy: Monster) : Unit = () // this function is called everytime an attack is casted
    
    // if the attack handles the amount of damages (for example a constant amount of damage) it returns this very amount, 
    // else it returns -1 and the basic formula is applied
    // WARNING : this function is meant to be overwritten in these particular cases
    def handledDamages (self : Monster, ennemy : Monster) : Int = { 
        if (power == 0) { // the basic formula doesn't give 0 damages when the power is 0, hence this special case
            0
        } else {
            -1
        }
    }
}

abstract class BuffAttack extends Attack {
    action = "buff"
    var stat : String = ""
    var proba : Float = 1
    var amount : Int = 1
    override def scoreForStrategy (self : Monster, ennemy : Monster) : Int = {
        super.scoreForStrategy(self, ennemy) + (40*(6 - self.getStage(stat)).min(amount)*proba).toInt
    }

    override def cast (self : Monster, ennemy : Monster) : Unit = {
        if (scala.util.Random.nextFloat() < proba) {
            DiscussionLabel.changeText(ennemy.name + "'s " + stat + " has increased.")
            self.changeStage(stat, amount)
        }
    }
}

abstract class DebuffAttack extends Attack {
    action = "debuff"
    var stat : String = ""
    var proba : Float = 1
    var amount : Int = 1
    override def scoreForStrategy (self : Monster, ennemy : Monster) : Int = {
        super.scoreForStrategy(self, ennemy) + (40*(ennemy.getStage(stat) + 6).min(-amount)*proba*amount*(-1)).toInt
    }

    override def cast (self : Monster, ennemy : Monster) : Unit = {
        if (scala.util.Random.nextFloat() < proba) {
            DiscussionLabel.changeText(ennemy.name + "'s " + stat + " is lowered.")
            ennemy.changeStage(stat, -amount)
        }
    }
}

abstract class StatusAttack extends Attack {
    action = "status"
    var status : Status = new NoStatus
    var proba : Float = 1

    override def scoreForStrategy (self : Monster, ennemy : Monster) : Int = {
        (super.scoreForStrategy(self, ennemy) + 50*proba).toInt
    }

    override def cast (self : Monster, ennemy : Monster) : Unit = {
        if (scala.util.Random.nextFloat() < proba) {
            ennemy.receiveStatus(status.copy)
        }
    }
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
        case x if x<= 0.325 => 2
        case x if x<= 0.65 => 3
        case x if x<= 0.775 => 4
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

object ThunderWave extends StatusAttack {
    status = new Paralysis
    name = "Thunder Wave"

    accuracy = 0.9f
    attackType = Electric
    override def toString : String = 
        "The user launches a weak jolt of electricity that paralyzes the target."
}

object Growl extends DebuffAttack {
    stat = "attack"

    name = "Growl"
    power = 40
    override def toString : String = 
        "The user growls in an endearing way, making opposing" +
        "Monster less wary. This lowers their Attack stat."
}

object Swift extends Attack {
    name = "Swift"
    power = 40
    accuracy = Float.PositiveInfinity // this attack cannot missed and cannot be dodged
    attackType = Normal
    override def toString : String = 
        "Star-shaped rays are shot at the opposing Monster. This attack never misses."    
}

object Agility extends BuffAttack {
    stat = "speed"
    amount = 2

    name = "Agility"
    accuracy = Float.PositiveInfinity // this attack cannot missed and cannot be dodged
    override def toString : String = 
        "The user relaxes and lightens its body to move faster. This sharply raises the Speed stat."
    override def cast(self: Monster, ennemy: Monster): Unit = 
        self.speedStage = (6).min(self.speedStage + 2)
}

object Thunder extends StatusAttack {
    status = new Paralysis
    proba = 0.3f

    name = "Thunder"
    power = 110
    accuracy= 0.7f
    attackType = Electric
    override def toString : String = 
        "A wicked thunderbolt is dropped on the target to inflict damage."+
        " This may also leave the target with paralysis."
}

object Tackle extends Attack {
    name = "Tackle"
    power = 40
    override def toString : String = 
        "A physical attack in which the user charges "+
        "and slams into the target with its whole body."
}

object TailWhip extends DebuffAttack {
    stat = "defense"

    name = "Tail Whip"
    override def toString : String = 
        "The user wags its tail cutely, making opposing Monster less wary and lowering their Defense stat."
}

object WaterGun extends Attack {
    name = "Water Gun"
    power = 40
    attackType = Water
    override def toString : String = 
        "The target is blasted with a forceful shot of water."
}

object Splash extends Attack {
    name = "Splash"
    power = 0
    attackType = Water
    override def toString : String = 
        "Has no effect whatsoever."
}

object Withdraw extends BuffAttack {
    stat = "defense"

    name = "Withdraw"
    attackType = Water
    override def toString : String = 
        "The user wags its tail cutely, making opposing Monster less wary and lowering their Defense stat."
}

object RapidSpin extends BuffAttack {
    stat = "speed"

    name = "Rapid Spin"
    power = 50
    override def toString : String = 
        "A spin attack that can also eliminate such moves as Bind, Wrap, Leech Seed, and Spikes."
    
}

object WaterPulse extends StatusAttack {
    status = new Confusion
    proba = 0.2f

    name = "Water Pulse"
    power = 60
    attackType = Water
    override def toString : String = 
        "The user attacks the target with a pulsing blast of water. This may also confuse the target."
    
}

object Protect extends StatusAttack {
    status = new Protection
    name = "Protect"
    override def toString : String = 
        "Enables the user to evade all attacks. Its chance of failing rises if it is used in succession."
}

object AquaTail extends Attack {
    name = "Aqua Tail"
    power = 90
    accuracy = 0.9f
    attackType = Water
    override def toString : String = 
        "The user attacks by swinging its tail as if it were a vicious wave in a raging storm."
}

object ShellSmash extends DebuffAttack {
    stat = "defense"

    name = "Shell Smash"
    override def toString : String = 
        "A spin attack that can also eliminate such moves as Bind, Wrap, Leech Seed, and Spikes."
}

object IronDefense extends BuffAttack {
    stat = "defense"
    amount = 2


    name = "Iron Defense"
    override def toString : String = 
        "The user hardens its body’s surface like iron, sharply raising its Defense stat."
}
object HydroPump extends Attack {
    name = "Hydro Pump"
    power = 110
    accuracy = 0.8f
    attackType = Water
    override def toString : String = 
        "The target is blasted by a huge volume of water launched under great pressure."
}

object SkullBash extends BuffAttack {
    stat = "defense" 

    name = "Skull Bash"
    power = 130
    override def toString : String = 
        "The user tucks in its head to raise its Defense, then rams the target."
}

object VineWhip extends Attack {
    name = "Vine Whip"
    power = 45
    override def toString : String = 
        "The target is struck with slender, whiplike vines to inflict damage."

    attackType = Grass
}

object Growth extends BuffAttack {
    stat = "attack"
    name = "Growth"
    override def toString : String = 
        "The user's body grows all at once, raising the Attack stats."
}

object Scratch extends Attack {
    name = "Scratch"
    power = 40
    override def toString : String = 
        "Hard, pointed, sharp claws rake the target to inflict damage."
}

object Ember extends StatusAttack {
    status = new Burn

    name = "Ember"
    attackType = Fire
    override def toString : String = 
        "The target is attacked with small flames."+
        " This may also leave the target with a burn."
}

object Flamethrower extends StatusAttack {
    status = new Burn
    proba = 0.1f

    name = "Flamethrower"
    power = 90
    attackType = Fire
    override def toString : String = 
        "The target is scorched with an intense blast of fire."+
        " This may also leave the target with a burn."

}

object Crunch extends DebuffAttack {
    stat = "defense"
    proba = 0.2f

    name = "Crunch"
    power = 80
    override def toString : String = 
        "The user crunches up the target with sharp fangs."+
        " This may also lower the target's Defense stat."
}
