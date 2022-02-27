import scala.util.Random
import scala.concurrent.duration.Duration

abstract class Item {
    var name = ""

    var needsTarget : Boolean = false
    var order : Int = 0 // For sorting the inventory

    def use : Boolean = {amount -= 1; true}
    def use (target : Monster) : Boolean = {amount -= 1; true}

    def usable : Boolean = {true}
    
    var amount : Int = 0

    override def toString : String = {
        name
    }
}

object FullRestore extends Item {
    name = "Full Restore"
    needsTarget = false
    order = 1

    override def use : Boolean = {
        var target = Player.currentMonster
        if (target.alive && target.hp != target.hpMax) {
            target.heal(target.hpMax)
            target.status = List()
            amount -= 1
            true
        } else {
            false
        }
    }

    override def usable : Boolean = {
        var target = Player.currentMonster
        target.alive
    }
}

object FreshWater extends Item {
    name = "Fresh Water"
    needsTarget = false
    order = 1

    override def use : Boolean = {
        var target = Player.currentMonster
        if (target.alive) {
            target.heal(30)
            amount -= 1
            true
        } else {
            false
        }
    }

    override def usable : Boolean = {
        var target = Player.currentMonster
        target.alive
    }
}

class Ball extends Item {

    var ballBonus : Float = 1f

    name = "Monster Ball"
    needsTarget = false
    order = 0

    override def usable : Boolean = {
         Player.team.filter(x => x.name != "Empty").length != 6 && Player.playing
    }

    def catchProbability : Float = {
        var monster = Player.currentMonster
        (3f*(monster.hpMax.toFloat) - 2f*(monster.hp.toFloat))*monster.catchRate.toFloat*ballBonus/((3f*(monster.hpMax.toFloat))*255)
    }

    override def use : Boolean = {
        DiscussionLabel.changeText("You use a " + name + ".")
        if (Player.opponent.name != "Wild" ) {
            DiscussionLabel.changeText("You can't capture that monster !")
        } else {
        if ( scala.util.Random.nextFloat() > catchProbability) {
            DiscussionLabel.changeText(Player.opponent.currentMonster.name + " escaped from the " + name+".")

        } else {
            var slot = Utils.findFirstOccurenceArray(Player.team, EmptyMonster)
            var opponent = Player.opponent
            opponent.currentMonster.owner = Player
            Player.team(slot) = opponent.currentMonster
            opponent.team(Utils.findFirstOccurenceArray(opponent.team, opponent.currentMonster)) = EmptyMonster
            Player.opponent.playing = false
            opponent.changeMonster(true)
            }
        }
        amount -= 1
        true
    }
}


object MonsterBall extends Ball

object SuperBall extends Ball {
    name = "Super Ball"
    ballBonus = 1.5f
}

object UltraBall extends Ball {
    name = "Ultra Ball"
    ballBonus = 2f
}

object MasterBall extends Ball {
    name = "Master Ball"
    ballBonus =  Float.PositiveInfinity
}


object EmptyItem extends Item {
    name = "Empty"
    order = 99999
}