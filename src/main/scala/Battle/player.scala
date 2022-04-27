import java.util.concurrent.TimeUnit
import java.sql.Time
import java.lang.Thread


class Character extends Object with Saveable {
    var team : Array[Monster] = Array.fill(6){EmptyMonster}
    List.range(0, team.size).foreach(x => team(x).indexInTeam = x)
    var name : String = ""
    var index : Int = -1
    var opponent : Character = EmptyCharacter
    var battle : Battle = EmptyBattle

    var alreadyBeaten : Boolean = false

    var displayer : CharacterDisplayer = EmptyCharacterDisplayer

    var inventory : Array[Item] = Array.fill(40){EmptyItem}

    var currentMonster : Monster = EmptyMonster
    var currentAttack : Attack = EmptyAttack
    var availableAttacks : Array[Attack] = Array.fill(4){EmptyAttack}

    var playing : Boolean = false
    var runningAway : Boolean = false

    def initialise : Unit = {}


    def enterBattle : Unit = {
        playing = true
        runningAway = false
        team.foreach(x => x.enterBattle)
        currentMonster = team.filter(x => x.alive)(0)
        currentMonster.enterField
    }

    def leaveBattle : Unit = {}

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
            var previousMonster = currentMonster
            currentMonster = alives(scala.util.Random.nextInt(l))
            currentMonster.enterField
            previousMonster.leaveField
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
            previousMonster.leaveField
            if (previousMonster.alive) {
                endTurn
            }
            true // tells the button that the action succeeded
        } else {
            false
        }
    }

    def changeMonster (monster : Monster) : Boolean = {
        changeMonster(Utils.findFirstOccurenceArray(team, monster))
    }

    def changeMonster (b : Boolean) : Unit = {}

    def lose : Unit = {
        battle.loser = this
        if (playing) {
            opponent.win
            playing = false
            opponent.playing = false
            Player.endTurn
        }
        leaveBattle
    }


    def enteringBattleMessage : List[String] = {
        List(name + " is entering the battle !")
    }

    def losingMessage : List[String] = {
        if (!runningAway) {
            List(name + " just lost !")
        } else {
            List()
        }
    }

    def winningMessage : List[String] = {
        List(name + " just won !")
    }

    def win : Unit = {
        battle.winner = this
        leaveBattle
    }
    
    def switchPokemon(pika : Monster, n : Int) : Unit  = {
        Utils.frame.pokedexPane.addCaught(pika)
        team(n) = pika
        pika.owner = this
        pika.indexInTeam = n
    }

    override def toStringSave (tabs : Int) : String = {
        "\t"*tabs + "Character : " + "\n" + 
        "\t"*(tabs+1) + "Index : " + index + "\n" + 
        "\t"*(tabs+1) + "Name : " + name + "\n" + 
        "\t"*(tabs+1) + "Beaten : " + alreadyBeaten + "\n" 
    }
}

abstract class Opponent extends Character with Intelligence {
    override def newTurn : Unit = {
        super.newTurn
        var l = availableAttacks.length
        chooseAction
    }

    override def lose : Unit = {
        alreadyBeaten = true
        super.lose
    }
}

abstract class WildOpponent extends Opponent {
    name = "Wild"
    var isCaptured : Boolean = false

    override def initialise : Unit = {
        super.initialise
        team(0) = Utils.frame.mapPane.mapDisplayer.getWildMonster
        team(0).owner = this
        team(0).gainLvl(Utils.frame.mapPane.mapDisplayer.getWildLevel - 1, false)
    }

    override def enterBattle : Unit = {
        isCaptured = false
        super.enterBattle
    }
    override def changeMonster (captured : Boolean) : Unit = {
        // if the monster is captured it displays a different message
        isCaptured = captured
        super.changeMonster
    }

    override def losingMessage : List[String] = {
        if (isCaptured) {
            List("You just captured " + currentMonster.name + " !")
        } else {
            List(currentMonster.name + " just lost !")
        }
    }

    override def enteringBattleMessage : List[String] = {
        List("A wild " + team(0).name + " appears !")
    }

}


object EmptyCharacter extends Character {

}

object Player extends Character {
    switchPokemon(new Pikachu, 0)
    team(0).gainLvl(5)

    switchPokemon(new Squirtle, 1)
    team(1).gainLvl(5)
    
    switchPokemon(new Bulbasaur, 2)
    team(2).gainLvl(5)

    switchPokemon(new Charmander, 3)
    team(3).gainLvl(5)

    switchPokemon(new Rattata, 4)
    team(4).gainLvl(5)

    if (Utils.debug) {
        team(0).gainLvl(50)
    }

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

    override def initialise : Unit = {
        team.foreach(y => (Utils.frame.pokedexPane.pokemonArray.foreach(x => if (x.originalName == y.originalName && y.owner == Player) x.wasSeen = true)))
        team.foreach(y => (Utils.frame.pokedexPane.pokemonArray.foreach(x => if (x.originalName == y.originalName && y.owner == Player) x.wasCaught = true)))
    }

    override def leaveBattle : Unit = {
        team.foreach(x => x.leaveBattle)
    }
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

    override def enteringBattleMessage : List[String] = List()
    override def losingMessage : List[String] = List()
    override def winningMessage : List[String] = List()

    override def toStringSave (tabs : Int) : String = {
        "\t"*tabs + "Player : " + "\n" +
        "\t"*(tabs+1) + "Name : " + name + "\n" + 
        team.filter(x => x.name != "Empty").map(x => "\t"*(tabs+1) + "Pokemon " + x.indexInTeam + " :\n" + x.toStringSave(tabs+2) + "\n").foldLeft("")((x, y) => x+y) + 
        inventory.filter(x => x.amount > 0).sortWith((x, y) => x.order <= y.order).map(x => "\t"*(tabs+1) + "Item : " + x.name + " (" + x.amount + ")\n").foldLeft("")((x, y) => x+y)
        // "\t"*tabs + ""
        // "\t"*tabs + ""
        // "\t"*tabs + ""
        // "\t"*tabs + ""
        // "\t"*tabs + ""
        // "\t"*tabs + ""
        // "\t"*tabs + ""
        // "\t"*tabs + ""
        // "\t"*tabs + ""
        // "\t"*tabs + ""
        // "\t"*tabs + ""
        // "\t"*tabs + ""
        // "\t"*tabs + ""
        // "\t"*tabs + ""
        // "\t"*tabs + ""
        // "\t"*tabs + ""
        // "\t"*tabs + ""        
    }
}