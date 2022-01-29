import java.text.Normalizer
abstract class Monster {
    var hpMax : Int = 100
    var hp : Int = 100
    var level : Int = 1

    var attackStat : Int = 100
    var defenseStat : Int = 100
    var speedStat : Int = 100

    var attackBattle : Int = 100
    var defenseBattle : Int = 100
    var speedBattle : Int = 100

    var accuracyBattle : Float = 1
    var evasionBattle : Float = 1

    var hpMaxPerLevel : Int = 10

    var attackStage : Int = 0
    var defenseStage : Int = 0
    var speedStage : Int = 0
    var accuracyStage : Int = 0
    var evasionStage : Int = 0

    var status : List[Status] = List()
    var attacks : Array[Attack] = Array[Attack](EmptyAttack, EmptyAttack, EmptyAttack, EmptyAttack)

    var monsterType : Type = Normal
    var name : String = ""

    def originalName : String = {this.getClass.getSimpleName}
    def typeName : String = {monsterType.name}

    def enterBattle : Unit = {
        attackBattle = attackStat
        defenseBattle = defenseStat
        speedBattle = speedStat 
        accuracyBattle = 1
        evasionBattle = 1

        attackStage = 0
        defenseStage = 0
        speedStage = 0
        accuracyStage  = 0
        evasionStage = 0
    }

    def castAttack (attack : Attack, other : Monster) : Unit = {
        var random = scala.util.Random.nextFloat()
        var thisAccuracyEff = this.accuracyBattle * calcModifier(this, "accuracy")
        var otherEvasionEff = other.evasionBattle * calcModifier(other, "evasion")
        for (i <- 1 to attack.nOfHits){
            if (random <= attack.accuracy*thisAccuracyEff*otherEvasionEff) {
                other.receiveAttack(attack, this)
            } else {
                if (random <= attack.accuracy) {
                    println("Attack missed")
                } else if (random <= attack.accuracy*thisAccuracyEff) {
                    println("You missed your attack")
                } else {
                    println("He dodged")
                }
            }
        }
    }

    def calcModifier (monster : Monster, stat : String) : Int = {
        var stage = {stat match {
            case "attack" => monster.attackStage
            case "defense" => monster.defenseStage
            case "speed" => monster.speedStage
            case "accuracy" => monster.accuracyStage
            case "evasion" => monster.evasionStage
        }}

        {stage match {
            case -6 => 25/100
            case -5 => 28/100
            case -4 => 33/100
            case -3 => 40/100
            case -2 => 50/100
            case -1 => 66/100
            case 0 => 100/100
            case 1 => 150/100
            case 2 => 200/100
            case 3 => 250/100
            case 4 => 300/100
            case 5 => 350/100
            case 6 => 400/100
        }}

    }

    def receiveAttack (attack : Attack, other : Monster) : Unit = {
        
        var otherAttackEff = other.attackBattle * calcModifier(other, "attack")
        var thisDefenseEff = defenseBattle * calcModifier(this, "defense")

        var random = scala.util.Random.nextFloat()*38/255 + 217/255

        var damage = ((((2/5*other.level+2)*attack.power*otherAttackEff/thisDefenseEff)/50+2)*random).toInt

        takeDamage(damage)
        
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

    monsterType = Electric
    name = "Pikachuuuuu"
    attacks(0) = QuickAttack
    attacks(1) = DoubleSlap
    attacks(2) = Thunder
    attacks(3) = EmptyAttack
}

class Carapuce extends Monster {
    hpMax = 44
    hp = 44
    attackStat = 48
    defenseStat = 65
    speedStat = 50


    monsterType = Water
    name = "Carapuuuuuce"

}

object EmptyMonster extends Monster {
    name = "Empty"
}

