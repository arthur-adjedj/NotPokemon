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
        currentMonster = team(0)
        currentMonster.enterBattle

    }

    def newTurn : Unit = {
        currentMonster.newTurn
        availableAttacks = currentMonster.attacks.filter(x => x.name != "Empty")
        var l = availableAttacks.length
        castAttack(availableAttacks(scala.util.Random.nextInt(l)))

        endTurn

    }

    def endTurn : Unit = {

    }

    def castAttack (attack : Attack) : Unit = {
        currentMonster.castAttack(attack, opponent.currentMonster)
    }

    def changeMonster : Unit = {
        if (team.exists(x => x.alive && x.name != "Empty")) {
            
        } else {
            lose
        }
    }

    def lose : Unit = {
        println(name + " just lost")
        playing = false
        opponent.playing = false
    }
}


object EmptyPlayer extends Player {

}

object FirstPlayer extends Player {
    team(0) = new Pikachu
    team(0).owner = this
    name = "You"
    opponent = SecondPlayer
}

object SecondPlayer extends Player {
    team(0) = new Squirtle
    team(0).owner = this
    name = "Opponent"
    opponent = FirstPlayer
}