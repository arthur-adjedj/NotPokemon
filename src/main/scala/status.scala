abstract class Status {
    var name : String = "Empty"
    var duration : Int = 0
    var durationLeft : Int = 0
    def onAdd (target : Monster) : Unit = {}
    def onDelete (target : Monster) : Unit = {}
    def onEndTurn (target : Monster) : Unit = {
        durationLeft -= 1
        if (durationLeft == 0) {
            onDelete(target)
        }
    }
}

class NoStatus extends Status {
}

class Paralysis extends Status {
    name = "Paralysis"
    duration = 3
    durationLeft = 3
    override def onAdd (target : Monster) : Unit = {
        target.speedBattle /= 4
        //println(name + " is set on " + target.name)
    }

    override def onDelete (target : Monster) : Unit = {
        target.speedBattle *= 4
        //println(name + " is removed from " + target.name)
    }
}

class Confusion extends Status {
    name = "Confusion"
}

class Protection extends Status {
    name = "Protection"
}

class Burn extends Status {
    name = "Burn"
    override def onAdd (target : Monster) : Unit = {
        target.attackBattle /= 2
    }

    override def onDelete (target : Monster) : Unit = {
        target.attackBattle *= 2
    }

    override def onEndTurn (target : Monster) : Unit = {
        target.takeDamage(target.hpMax / 16)
        super.onEndTurn(target)
    }
}

class Freeze extends Status {
    name = "Freeze"
}


class Poison extends Status {
    name = "Poison"
    override def onEndTurn (target : Monster) : Unit = {
        target.takeDamage(target.hpMax / 16)
        super.onEndTurn(target)
    }
}

class Sleep extends Status {
    name = "Sleep"
}