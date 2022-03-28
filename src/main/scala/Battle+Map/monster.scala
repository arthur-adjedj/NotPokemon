import java.text.Normalizer
import java.lang.Math
import javax.management.Descriptor
import java.awt.image.BufferedImage

abstract class Monster extends Object with ScoreForStrategy {
    var xp : Int = 0
    var level : Int = 0

    var catchRate : Int = 45

    var baseHpStat : Int = 1
    var baseAttackStat : Int = 1
    var baseDefenseStat : Int = 1
    var baseSpeedStat : Int = 1
    var hpMax : Int = 100
    var hp : Int = 100

    var IVHp : Int = scala.util.Random.nextInt(16)
    var IVAttack : Int = scala.util.Random.nextInt(16)
    var IVDefense : Int = scala.util.Random.nextInt(16)
    var IVSpeed : Int = scala.util.Random.nextInt(16)

    var EVHp : Int = 0
    var EVAttack : Int = 0
    var EVDefense : Int = 0
    var EVSpeed : Int = 0


    var attackStat : Int = 100
    var defenseStat : Int = 100
    var speedStat : Int = 100


    var attackBattle : Int = 100
    var defenseBattle : Int = 100
    var speedBattle : Int = 100
    var accuracyBattle : Float = 1
    var evasionBattle : Float = 1

    // For the strategy
    var turnsOnField : Int = 0
    def scoreForStrategy (self : Monster, ennemy : Monster) : Int = {
        1 + 50*(Math.pow(2, attackStage + defenseStage + speedStage / 2)*hpRate*monsterType.multDamage(ennemy.monsterType)*Math.pow(2, -turnsOnField)).toInt
    }
    var action = "monster"


    var alive : Boolean = true
    var wild : Boolean = false
    var monstersSeen : List[Monster] = List()

    var hpMaxPerLevel : Int = 10

    var attackStage : Int = 0
    var defenseStage : Int = 0
    var speedStage : Int = 0
    var accuracyStage : Int = 0
    var evasionStage : Int = 0

    var status : List[Status] = List()
    var attacks : Array[Attack] = Array.fill(4){EmptyAttack}

    var baseXp : Int = 1
    var xpGraph : String = "Fast"
    var previousXpStep : Int = 0
    var nextXpStep : Int = 0
    def xpRate : Float = {(xp - previousXpStep).toFloat / (nextXpStep - previousXpStep).toFloat}
    def hpRate : Float = {hp.toFloat / hpMax.toFloat}

    var monsterType : Type = EmptyType

    var name : String = ""
    var owner : Character = EmptyCharacter

    //these values are put as strings because their values can greatly vary between pokemons, 
    //from grams to tons and from cm to tens of meters
    var height : String = "0 m"
    var weight : String = "0 kg"
    var wasSeen : Boolean = false
    var wasCaught : Boolean = false
    var description : String = "Lorem Ipsum"

    def imgNameFront : String = {"Monsters/" + originalName + "Front.png"}
    def imgNameBack : String = {"Monsters/" + originalName + "Back.png"}
    var uiYShift : Int = 0
    var originalName : String = name
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
        accuracyStage = 0
        evasionStage = 0

        turnsOnField = 0

        monstersSeen = List()
        status = List()
    }

    def enterField : Unit = {
        Utils.frame.pokedexPane.pokemonArray.foreach(x => if (x.originalName == originalName) x.wasSeen = true)
        newMonsterSeen(owner.opponent.currentMonster)
        owner.opponent.currentMonster.newMonsterSeen(this)
        turnsOnField = 0
    }

    def leaveField : Unit = {
        turnsOnField = 0
    }
    
    def isCaught : Unit = {
        Utils.frame.pokedexPane.pokemonArray.foreach(x => if (x.originalName == originalName) x.wasCaught = true)
    }

    def newMonsterSeen (other : Monster) : Unit = {
        if (monstersSeen.forall(x => x.name != other.name) && other.name != "Empty"){
            monstersSeen = other :: monstersSeen 
        }
    }

    def newTurn : Unit = {
        turnsOnField += 1
    }

    def endTurn : Unit = {
        endTurnStatus
    }

    def getSpeed : Int = {
        (speedBattle.toFloat * calcModifier(this, "speed")).toInt
    }

    //It adds text to explain the effectiveness of the attack
    def effectivenessText (attack : Attack, other : Monster) : String = {
        if (attack.handlesDamages(this, other) == -1) {
            if (attack.attackType.multDamage(other.monsterType) > 1) "It's super effective !" 
            else if (attack.attackType.multDamage(other.monsterType) < 1) 
                "It's not very effective..." 
                else ""
        } else {
            ""
        }
    }


    def castAttack (attack : Attack, other : Monster) : Unit = {
        // checks the misses and the status of both monster and cast the attack when possible 
        var random = scala.util.Random.nextFloat()
        var thisAccuracyEff = this.accuracyBattle * calcModifier(this, "accuracy")
        var otherEvasionEff = other.evasionBattle * calcModifier(other, "evasion")
        // these checks are done here because we are dealing with the pokemon's status
        // doing this in Status would make us calling a 'canAttack' function. We decided to keep it here
        if (status.exists(x => x.name == "Freeze")) {
            DiscussionLabel.changeText(name + " cannot attack because he's frozen.")
        } else if (status.exists(x => x.name == "Sleep")) {
            DiscussionLabel.changeText(name + " cannot attack because he's sleeping.")
        } else if (status.exists(x => x.name == "Paralysis") && scala.util.Random.nextFloat() <= 1f/4f) {
            DiscussionLabel.changeText(name + " cannot attack because he's paralysed.")
        } else if (other.status.exists(x => x.name == "Protection")) {
            DiscussionLabel.changeText(other.name + " is protected.")
        } else {
            for (i <- 1 to attack.nOfHits){
                if (other.alive) {
                    random = scala.util.Random.nextFloat()
                    if (status.exists(x => x.name == "Confusion") && random <= 0.5) {
                        this.receiveAttack(attack, this)
                        attack.cast(this, this)
                    } else if (random <= attack.accuracy*thisAccuracyEff*otherEvasionEff) {
                        if (other.owner.runningAway) {
                            DiscussionLabel.changeText("Before you go, " + name + " casts " + attack.name + ". " + effectivenessText(attack,other))
                        } else {
                            DiscussionLabel.changeText(name + " casts " + attack.name + ". " + effectivenessText(attack,other))
                        }

                        Utils.waitDiscussionLabel
                        
                        attack.cast(this, other)
                        other.receiveAttack(attack, this)
                    } else {
                        if (random <= attack.accuracy) {
                            DiscussionLabel.changeText(attack.name + " missed.")
                        } else if (random <= attack.accuracy*thisAccuracyEff) {
                            DiscussionLabel.changeText(name + " missed his attack.")
                        } else {
                            DiscussionLabel.changeText(other.name + " dodged.")
                        }
                    }
                }
            }
        }
    }

    def getStage (stat : String) : Int = {
        stat match {
            case "attack" => attackStage
            case "defense" => defenseStage
            case "speed" => speedStage
            case "accuracy" => accuracyStage
            case "evasion" => evasionStage
            case _ => Utils.print(stat + " stat is unreachable"); 0
        }
    }

    def changeStage (stat : String, amount : Int) : Unit = {
        stat match {
            case "attack" => attackStage = (attackStage + amount).max(-6).min(6)
            case "defense" => defenseStage = (defenseStage + amount).max(-6).min(6)
            case "speed" => speedStage = (speedStage + amount).max(-6).min(6)
            case "accuracy" => accuracyStage = (accuracyStage + amount).max(-6).min(6)
            case "evasion" => evasionStage = (evasionStage + amount).max(-6).min(6)
            case _ => Utils.print(stat + " stat is unreachable")
        }
    }

    def calcModifier (monster : Monster, stat : String) : Float = {
        // calculates the modifier due to a stage
        var stage = monster.getStage(stat)

        {stage match {
            case -6 => 25f/100f
            case -5 => 28f/100f
            case -4 => 33f/100f
            case -3 => 40f/100f
            case -2 => 50f/100f
            case -1 => 66f/100f
            case 0 => 100f/100f
            case 1 => 150f/100f
            case 2 => 200f/100f
            case 3 => 250f/100f
            case 4 => 300f/100f
            case 5 => 350f/100f
            case 6 => 400f/100f
        }}

    }

    def receiveAttack (attack : Attack, other : Monster) : Unit = {
        // if the attack doesn't handle the damages, the basic formula is used
        var handledDamages = attack.handlesDamages(other, this)
        if (handledDamages != -1) {
            takeDamage(handledDamages)
        } else {
            var otherAttackEff = other.attackBattle * calcModifier(other, "attack")
            var thisDefenseEff = defenseBattle * calcModifier(this, "defense")

            var random = scala.util.Random.nextFloat()*38f/255f + 217f/255f
            var damage = ((((2f/5f*other.level.toFloat+2f)*attack.power.toFloat*otherAttackEff.toFloat/thisDefenseEff.toFloat)/50f+2f)*random*attack.attackType.multDamage(this.monsterType)).toInt

            takeDamage(damage)
        } 
    }

    def receiveStatus (stat : Status) : Unit = {
        // if the status is already applyied, then reset its timer
        def max_duration (s : Status, name : String) : Unit = {
            if (s.name == name) {
                s.durationLeft = s.duration
            }
        }

        var exists = status.exists(x => x.name == stat.name)
        if (exists) {
            status.foreach(x => max_duration(x, stat.name))
        } else {
            status = stat :: status
            stat.onAdd(this)
        }
    }

    def removeStatus (stat : Status) : Unit = {
        status = status.filter(x => x.name != stat.name)
    }

    def endTurnStatus : Unit = {
        status.foreach(x => x.onEndTurn)
        status = status.filter(x => x.durationLeft != 0)
    }

    def heal (amount : Int) : Unit = {
        hp += amount
        if (hp > hpMax) {
            hp = hpMax
        }
        if (hp > 0) {
            alive = true
            if (owner != null) {
                owner.alreadyBeaten = false
            }
        }
    }

    def takeDamage (amount : Int) : Unit = {
        hp -= amount
        if (hp <= 0) {
            hp = 0
            die
        }
    }

    def die : Unit = {
        DiscussionLabel.changeText(name + " is KO !")
        alive = false

        //calculate this xp given to all the opponents seen
        var monstersSeenAlive = monstersSeen.filter(x => x.alive && x.name != "Empty" && x.level < 100)
        var exp : Float = baseXp.toFloat*level.toFloat/7f/monstersSeenAlive.length.toFloat
        if (!wild) {
            exp *= 3f/2f
        }
        monstersSeenAlive.foreach(x => x.gainXp(exp.toInt,true))
        monstersSeenAlive.foreach(x => x.EVHp = (x.EVHp + baseHpStat).min(65535))
        monstersSeenAlive.foreach(x => x.EVAttack = (x.EVAttack + baseAttackStat).min(65535))
        monstersSeenAlive.foreach(x => x.EVDefense = (x.EVDefense + baseDefenseStat).min(65535))
        monstersSeenAlive.foreach(x => x.EVSpeed = (x.EVSpeed + baseSpeedStat).min(65535))

        owner.changeMonster

    }

    def gainXp (amount : Int,print : Boolean) : Unit = {
        if (level < 100) {
            xp += amount
            if (xp >= nextXpStep) {
                var diff = xp - nextXpStep
                xp = nextXpStep
                levelUp(print)
                gainXp(diff,print)
            }
        }
    }

    def levelUp(print : Boolean) : Unit = {
        level += 1

        var previousHpMax = hpMax
        hpMax = (((baseHpStat + IVHp) * 2 + (Math.sqrt(EVHp)/4f).toInt) * level)/100 + level + 10
        if (level == 1) {
            hp = hpMax
        } else {
            heal(hpMax - previousHpMax)
        }
        attackStat = (((baseAttackStat + IVAttack) * 2 + (Math.sqrt(EVAttack)/4f).toInt) * level)/100 + 5
        defenseStat = (((baseDefenseStat + IVDefense) * 2 + (Math.sqrt(EVDefense)/4f).toInt) * level)/100 + 5
        speedStat = (((baseSpeedStat + IVSpeed) * 2 + (Math.sqrt(EVSpeed)/4f).toInt) * level)/100 + 5



        previousXpStep = nextXpStep
        xpGraph match {
            case "Fast" => nextXpStep = (0.8 * Math.pow(level+1, 3)).toInt
            case "Medium Fast" => nextXpStep = (Math.pow(level+1, 3)).toInt
            case "Medium Slow" => nextXpStep = (1.2 * Math.pow(level+1, 3) - 15 * Math.pow(level+1, 2) + 100 * (level+1) - 140).toInt
            case "Slow" => nextXpStep = (1.25 * Math.pow(level+1, 3)).toInt
        }
        if (level > 1 && print) {
            DiscussionLabel.changeText(name + " is now level " + level)
        }
    }

    def gainLvl (n : Int,print : Boolean) : Unit = {
        for (i <- 1 to n) {
            gainXp(nextXpStep - xp,print)
        }
    }

    def gainLvl (n : Int) : Unit = gainLvl(n, false)

    def talk : Boolean = {
        if (alive) {
            DiscussionLabel.changeText("(" + name + ") : I'm ready for the battle !")
        } else {
            DiscussionLabel.changeText("(" + name + ") : I'm K.O. please heal me !")
        }
        true
    }

    override def toString : String = {
        name + " is a " + originalName + " monster of type " + typeName + ". "
    }

    levelUp(false)

}

object EmptyMonster extends Monster {
    name = "Empty"
    originalName = "Empty"
    alive = false
    monsterType = EmptyType

    override def talk : Boolean = false
}

