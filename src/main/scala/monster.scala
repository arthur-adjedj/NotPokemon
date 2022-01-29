import java.text.Normalizer
abstract class Monster {
    var hpMax : Int = 100
    var hp : Int = 100
    var level : Int = 1

    var attackStat : Int = 100
    var defenseStat : Int = 100
    var speedStat : Int = 100

    var hpMaxPerLevel : Int = 10

    var attackStage : Int = 0
    var defenseStage : Int = 0
    var speedStage : Int = 0

    var statuts : Array[Status] = new Array[Status](10)

    val monsterType : Type
    var name : String = ""

    def originalName : String = {this.getClass.getSimpleName}
    def typeName : String = {monsterType.name}
    def castAttack (attack : Attack, other : Monster) : Boolean = {
        if (scala.util.Random.nextFloat() <= attack.accuracy) {
            other.receiveAttack(attack, this); true
        } else {false}
    }

    def receiveAttack (attack : Attack, other : Monster) : Boolean = {
        var otherAttackEff = other.attackStat + 
        damage = (((2/5*other.level+2)*attack.power*other.attack/defense)/50+2)
        takeDamage()

    }

    def heal (amount : Int) : Unit = {
        hp += amount
        if (hp > hpMax) {
            hp = hpMax
        }
    }

    def takeDamage (amount : Int) : Unit = {
        hp -= amount
        if (hp <= 0) {
            die
        }
    }

    def die = {
        println("I'm dying")
    }

    def levelUp = {
        hpMax += hpMaxPerLevel
        hp = hpMax
        level += 1
    }

    override def toString : String = {
        name + " is a " + originalName + " monster of type " + typeName
    }

}

class Pikachu extends Monster {
    hpMax = 35
    hp = 35
    attackStat = 55
    defenseStat = 40
    speedStat = 90

    attackStatPerLevel = 10
    defenseStatPerLevel = 10
    hpMaxPerLevel = 10
    override val monsterType = Electric
    name = "Pikachuuuuu"
}

class Carapuce extends Monster {
    hpMax = 44
    hp = 44
    attackStat = 48
    defenseStat = 65
    speedStat = 50

    attackStatPerLevel = 10
    defenseStatPerLevel = 10
    hpMaxPerLevel = 10

    override val monsterType = Water
    name = "Carapuuuuuce"

}

