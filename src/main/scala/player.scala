import java.util.concurrent.TimeUnit


class Player {
    var team : Array[Monster] = Array.fill(6){EmptyMonster}
    var name : String = ""
    var opponent : Player = EmptyPlayer
    var battle : Battle = EmptyBattle

    var currentMonster : Monster = EmptyMonster
    var availableAttacks : Array[Attack] = Array.fill(4){EmptyAttack}

    var playing : Boolean = true


    def enterBattle : Unit = {
        playing = true
        team.foreach(x => x.enterBattle)
        currentMonster = team(0)
        currentMonster.enterField

        battle.ui.updateImages

    }

    def newTurn : Unit = {
        currentMonster.newTurn
        availableAttacks = currentMonster.attacks.filter(x => x.name != "Empty")
        


    }

    def endTurn : Unit = {
        currentMonster.endTurn
    }

    def castAttack (x : Int) : Boolean = {
        if (currentMonster.attacks(x).name != "Empty") {
            castAttack(currentMonster.attacks(x))
            true
        } else {
            false
        }
    }

    def castAttack (attack : Attack) : Unit = {
        currentMonster.castAttack(attack, opponent.currentMonster)
        endTurn
    }

    def changeMonster : Unit = {
        if (team.exists(x => x.alive && x.name != "Empty")) {
            var alives = team.filter(x => x.alive && x.name != "Empty")
            var l = alives.length
            currentMonster = alives(scala.util.Random.nextInt(l))
            currentMonster.enterField
            battle.ui.updateImages
        } else {
            lose
        }
    }

    def changeMonster (n : Int) : Boolean = {
        if (team(n) == currentMonster && team(n).alive) {
            DiscusionLabel.changeText(team(n).name + " is already on the battlefield!")
            true
        } else if (team(n).alive && team(n).name != "Empty" && team(n) != currentMonster) {
            var previousMonster = currentMonster
            currentMonster = team(n)
            currentMonster.enterField 
            battle.ui.updateImages
            if (previousMonster.alive) {
                endTurn
            }
            true
        } else {
            false
        }
    }

    def lose : Unit = {
        DiscusionLabel.changeText(name + " just lost")
        playing = false
        opponent.playing = false
    }
}


object EmptyPlayer extends Player {

}

object FirstPlayer extends Player {
    team(0) = new Pikachu
    team(0).gainLvl(5)
    team(0).owner = this

    team(2) = new Squirtle
    team(2).gainLvl(5)
    team(2).owner = this

    /* team(1) = new Bulbasaur
    team(1).gainLvl(5)
    team(1).owner = this

    team(3) = new Charmander
    team(3).gainLvl(5)
    team(3).owner = this

    team(4) = new Rattata
    team(4).gainLvl(5)
    team(4).owner = this

    */

    name = "You"
    opponent = SecondPlayer
    var hisTurn : Boolean = false

    override def newTurn : Unit = {
        hisTurn = true
        super.newTurn
        while (hisTurn) {
            TimeUnit.MILLISECONDS.sleep(10)
        }
    }

    override def endTurn : Unit = {
        super.endTurn
        hisTurn = false
    }
}

object SecondPlayer extends Player {
    team(0) = new Squirtle
    team(0).owner = this
    team(0).gainLvl(1000)

    team(1) = new Pikachu
    team(1).owner = this
    name = "Opponent"
    opponent = FirstPlayer

    override def newTurn : Unit = {
        super.newTurn
        var l = availableAttacks.length
        castAttack(availableAttacks(scala.util.Random.nextInt(l)))
    }

}