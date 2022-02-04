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
        if (team(n).alive && team(n).name != "Empty" && team(n) != currentMonster) {
            currentMonster = team(n)
            currentMonster.enterField
            battle.ui.updateImages
            endTurn
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

    team(1) = team(2)
    team(3) = team(2)
    team(4) = team(2)
    team(5) = team(2)


    name = "You"
    opponent = SecondPlayer
    var hisTurn : Boolean = false

    override def newTurn : Unit = {
        hisTurn = true
        super.newTurn
        while (hisTurn) {
            TimeUnit.SECONDS.sleep(1)
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