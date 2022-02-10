import java.util.concurrent.TimeUnit


class Player {
    var team : Array[Monster] = Array.fill(6){EmptyMonster}
    var name : String = ""
    var opponent : Player = EmptyPlayer
    var battle : Battle = EmptyBattle

    var inventory : Array[Item] = Array.fill(40){EmptyItem}

    var currentMonster : Monster = EmptyMonster
    var availableAttacks : Array[Attack] = Array.fill(4){EmptyAttack}

    var playing : Boolean = true


    def enterBattle : Unit = {
        playing = true
        team.foreach(x => x.enterBattle)
        currentMonster = team.filter(x => x.alive)(0)
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
        opponent.win
        playing = false
        opponent.playing = false
        FirstPlayer.endTurn
    }

    def win : Unit = {

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

    team(1) = new Bulbasaur
    team(1).gainLvl(5)
    team(1).owner = this

    team(3) = new Charmander
    team(3).gainLvl(5)
    team(3).owner = this

    team(4) = new Rattata
    team(4).gainLvl(5)
    team(4).owner = this

    gainItem(FreshWater, 5)
    gainItem(MonsterBall, 10)
    gainItem(SuperMonsterBall, 10)
    gainItem(UltraMonsterBall, 10)
    gainItem(MonsterBall, 10)
    gainItem(FullRestore, 1)

    name = "You"
    var hisTurn : Boolean = false
    var usableInventory : Array[Item] = Array.fill(40){EmptyItem}

    override def changeMonster : Unit = {
        if (team.forall(x => !x.alive || x.name == "Empty")) {
            lose
        }
    }

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

    def useItem (x : Int) : Boolean = {
        if (inventory(x).name != "Empty") {
            if (useItem(usableInventory(x))) {
                true
            } else {
                false
            }
        } else {
            false
        }
    }

    def useItem (item : Item) : Boolean = {
        if (!item.needsTarget) {
            if (item.use) {
                updateInventory
                true
            } else {
                false
            }
        } else {
            false
        }
    }

    def gainItem (item : Item, amount : Int) : Unit = {
        def add_amount (o : Item, name : String) : Unit = {
            if (o.name == name) {
                o.amount += amount
            }
        }

        var exists = inventory.exists(x => x.name == item.name)
        if (exists) {
            inventory.foreach(x => add_amount(x, item.name))
        } else {
            inventory(inventory.filter(x => x.name != "Empty").length) = item
            item.amount = amount
            println("You found " + amount + " " + item.name)
            updateInventory
        }
    }

    def updateInventory : Unit = {
        inventory = inventory.map(x => if (x.amount == 0) EmptyItem else x).sortWith((x, y) => x.order <= y.order)
        usableInventory = inventory.map(x => if (x.amount == 0 || !x.usable) EmptyItem else x).sortWith((x, y) => x.order <= y.order)
    }
}

object SecondPlayer extends Player {
    team(0) = new Squirtle
    team(0).owner = this
    team(0).gainLvl(3)

    /*
    team(1) = new Pikachu
    team(1).owner = this
    team(1).gainLvl(3)
    */
    name = "Opponent"

    override def newTurn : Unit = {
        super.newTurn
        var l = availableAttacks.length
        castAttack(availableAttacks(scala.util.Random.nextInt(l)))
    }
}

object ThirdPlayer extends Player {
    team(0) = new Rattata
    team(0).owner = this
    team(0).gainLvl(4)

    team(1) = new Bulbasaur
    team(1).owner = this
    team(1).gainLvl(4)
    name = "Opponent"

    override def newTurn : Unit = {
        super.newTurn
        var l = availableAttacks.length
        castAttack(availableAttacks(scala.util.Random.nextInt(l)))
    }
}

object WildPlayer extends Player {
    team(0) = new Rattata
    team(0).owner = this
    team(0).gainLvl(4)

    name = "Wild"

    override def newTurn : Unit = {
        super.newTurn
        var l = availableAttacks.length
        castAttack(availableAttacks(scala.util.Random.nextInt(l)))
    }
}