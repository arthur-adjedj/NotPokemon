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
        var target = FirstPlayer.currentMonster
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
        var target = FirstPlayer.currentMonster
        target.alive && target.hp != target.hpMax
    }
}

object FreshWater extends Item {
    name = "Fresh Water"
    needsTarget = false
    order = 1

    override def use : Boolean = {
        var target = FirstPlayer.currentMonster
        if (target.alive && target.hp != target.hpMax) {
            target.heal(30)
            amount -= 1
            true
        } else {
            false
        }
    }

    override def usable : Boolean = {
        var target = FirstPlayer.currentMonster
        target.alive && target.hp != target.hpMax
    }
}

object MonsterBall extends Item {
    name = "Monster Ball"
    needsTarget = false
    order = 0

    override def usable : Boolean = {
        FirstPlayer.opponent.name == "Wild" && FirstPlayer.team.filter(x => x.name != "Empty").length != 6 && FirstPlayer.playing
    }

    override def use : Boolean = {
        //println("You use a MonsterBall")
        var slot = Utils.findFirstOccurenceArray(FirstPlayer.team, EmptyMonster)
        var opponent = FirstPlayer.opponent
        opponent.currentMonster.owner = FirstPlayer
        FirstPlayer.team(slot) = opponent.currentMonster
        opponent.team(Utils.findFirstOccurenceArray(opponent.team, opponent.currentMonster)) = EmptyMonster
        FirstPlayer.opponent.playing = false
        opponent.changeMonster(true)
        amount -= 1
        true
    }
}

object SuperMonsterBall extends Item {
    name = "Super Monster Ball"
    needsTarget = false
    order = 0

    override def usable : Boolean = {
        FirstPlayer.opponent.name == "Wild" && FirstPlayer.team.filter(x => x.name != "Empty").length != 6 && FirstPlayer.playing
    }

    override def use : Boolean = {
        //println("You use a SuperMonsterBall")
        var slot = Utils.findFirstOccurenceArray(FirstPlayer.team, EmptyMonster)
        var opponent = FirstPlayer.opponent
        opponent.currentMonster.owner = FirstPlayer
        FirstPlayer.team(slot) = opponent.currentMonster
        opponent.team(Utils.findFirstOccurenceArray(opponent.team, opponent.currentMonster)) = EmptyMonster
        FirstPlayer.opponent.playing = false
        opponent.changeMonster(true)
        amount -= 1
        true
    }
}

object UltraMonsterBall extends Item {
    name = "Ultra Monster Ball"
    needsTarget = false
    order = 0

    override def usable : Boolean = {
        FirstPlayer.opponent.name == "Wild" && FirstPlayer.team.filter(x => x.name != "Empty").length != 6 && FirstPlayer.playing
    }

    override def use : Boolean = {
        //println("You use a UltraMonsterBall")
        var slot = Utils.findFirstOccurenceArray(FirstPlayer.team, EmptyMonster)
        var opponent = FirstPlayer.opponent
        opponent.currentMonster.owner = FirstPlayer
        FirstPlayer.team(slot) = opponent.currentMonster
        opponent.team(Utils.findFirstOccurenceArray(opponent.team, opponent.currentMonster)) = EmptyMonster
        FirstPlayer.opponent.playing = false
        opponent.changeMonster(true)
        amount -= 1
        true
    }
}






object EmptyItem extends Item {
    name = "Empty"
    order = 99999
}