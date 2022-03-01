import java.util.concurrent.TimeUnit
import java.sql.Time
import java.lang.Thread


class Character {
    var team : Array[Monster] = Array.fill(6){EmptyMonster}
    var name : String = ""
    var opponent : Character = EmptyCharacter
    var battle : Battle = EmptyBattle

    var alreadyBeaten : Boolean = false

    var displayer : CharacterDisplayer = EmptyCharacterDisplayer

    var inventory : Array[Item] = Array.fill(40){EmptyItem}

    var currentMonster : Monster = EmptyMonster
    var currentAttack : Attack = EmptyAttack
    var availableAttacks : Array[Attack] = Array.fill(4){EmptyAttack}

    var playing : Boolean = true
    var runningAway : Boolean = false


    def enterBattle : Unit = {
        playing = true
        runningAway = false
        team.foreach(x => x.enterBattle)
        currentMonster = team.filter(x => x.alive)(0)
        currentMonster.enterField

    }

    def newTurn : Unit = {
        currentMonster.newTurn
        availableAttacks = currentMonster.attacks.filter(x => x.name != "Empty")
    }

    def endTurn : Unit = {
        currentMonster.endTurn
    }

    def chooseAttack (x : Int) : Boolean = {
        if (currentMonster.attacks(x).name != "Empty") {
            chooseAttack(currentMonster.attacks(x))
            true // tells the button that the attack is casted
        } else {
            false
        }
    }

    def chooseAttack (attack : Attack) : Unit = {
        currentAttack = attack
        endTurn
    }

    def castAttack (x : Int) : Boolean = {
        if (currentMonster.attacks(x).name != "Empty") {
            castAttack(currentMonster.attacks(x))
            true // tells the button that the attack is casted
        } else {
            false
        }
    }

    def castAttack (attack : Attack) : Unit = {
        if (attack.name != "Empty") {
            currentMonster.castAttack(attack, opponent.currentMonster)
            endTurn
        }
    }

    def changeMonster : Unit = {
        // if every member of the team is KO, then lose else choose a random monster
        if (team.exists(x => x.alive && x.name != "Empty")) {
            var alives = team.filter(x => x.alive && x.name != "Empty")
            var l = alives.length
            currentMonster = alives(scala.util.Random.nextInt(l))
            currentMonster.enterField
        } else {
            lose
        }
    }

    def changeMonster (n : Int) : Boolean = {
        if (team(n) == currentMonster && team(n).alive) {
            DiscussionLabel.changeText(team(n).name + " is already on the battlefield !")
            true // tells the button that the action succeeded
        } else if (team(n).alive && team(n).name != "Empty" && team(n) != currentMonster) {
            DiscussionLabel.changeText(team(n).name + " enters the battlefield !")
            var previousMonster = currentMonster
            currentMonster = team(n)
            currentMonster.enterField 
            if (previousMonster.alive) {
                endTurn
            }
            true // tells the button that the action succeeded
        } else {
            false
        }
    }

    def changeMonster (b : Boolean) : Unit = {}

    def lose : Unit = {
        if (playing) {
            if (!runningAway) {
                printLosingMessage
            }
            opponent.win
            playing = false
            opponent.playing = false
            Player.endTurn
        }
    }

    def printLosingMessage : Unit = {
        DiscussionLabel.changeText(name + " just lost.")
    }

    def win : Unit = {

    }
}

abstract class Opponent extends Character {
    override def newTurn : Unit = {
        super.newTurn
        var l = availableAttacks.length
        chooseAttack(availableAttacks(scala.util.Random.nextInt(l)))
    }

    override def lose : Unit = {
        alreadyBeaten = true
        super.lose
    }
}

abstract class WildOpponent extends Opponent {
    name = "Wild"

    override def changeMonster (captured : Boolean) : Unit = {
        // if the monster is captured it displays a different message
        if (captured) {
            DiscussionLabel.changeText("You just captured " + currentMonster.name + ".")
        }
        if (team.exists(x => x.alive && x.name != "Empty")) {
            var alives = team.filter(x => x.alive && x.name != "Empty")
            var l = alives.length
            currentMonster = alives(scala.util.Random.nextInt(l))
            currentMonster.enterField
        } else {
            lose(captured)
        }
    }

    def lose (captured : Boolean) : Unit = {
        if (playing) {
            if (!captured) {
                DiscussionLabel.changeText(currentMonster.name + " just lost.")
            }
            opponent.win
            playing = false
            opponent.playing = false
            Player.endTurn
        }
        
    }

    override def lose : Unit = {
        lose(false)
    }
}


object EmptyCharacter extends Character {

}

object Player extends Character {
    team(0) = new Pikachu
    team(0).gainLvl(5,false)
    team(0).owner = this

    team(2) = new Squirtle
    team(2).gainLvl(5,false)
    team(2).owner = this

    team(1) = new Bulbasaur
    team(1).gainLvl(5,false)
    team(1).owner = this

    team(3) = new Charmander
    team(3).gainLvl(5,false)
    team(3).owner = this

    team(4) = new Rattata
    team(4).gainLvl(5,false)
    team(4).owner = this

    gainItem(FreshWater, 5)
    gainItem(MonsterBall, 10)
    gainItem(SuperBall, 10)
    gainItem(UltraBall, 10)
    gainItem(MonsterBall, 10)
    gainItem(FullRestore, 1)

    displayer = PlayerDisplayer

    name = "You"
    var hisTurn : Boolean = false // used for looping and waiting for the action of the player
    var usableInventory : Array[Item] = Array.fill(40){EmptyItem} // the inventory that is displayed

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
                true // tells the button that the item is used
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
                endTurn
                true // tells the button that the item is used
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
            Utils.print("You found " + amount + " " + item.name)
            updateInventory
        }
    }

    def updateInventory : Unit = {
        inventory = inventory.map(x => if (x.amount == 0) EmptyItem else x).sortWith((x, y) => x.order <= y.order)
        usableInventory = inventory.map(x => if (x.amount == 0 || !x.usable) EmptyItem else x).sortWith((x, y) => x.order <= y.order)
    }

    
}