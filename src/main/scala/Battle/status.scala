
abstract class Status {
    var name : String = "Empty"
    var duration : Int = 0
    var durationLeft : Int = 0
    var owner : Monster = EmptyMonster
    def onAdd (target : Monster) : Unit = {
        owner = target
        DiscussionLabel.changeText(owner.name + " now has " + name + " !")
    }
    def onDelete : Unit = {
        DiscussionLabel.changeText(owner.name + " doesn't have " + name + " anymore !")
    }
    def onEndTurn : Unit = {
        durationLeft -= 1
        if (durationLeft == 0) {
            onDelete
        }
    }

    def copy : Status = {
        // Unfortunatly we didn't find anything to have a copy of an object...
        new NoStatus
    }

}

class NoStatus extends Status {

}

class Paralysis extends Status {
    name = "Paralysis"
    duration = 3
    durationLeft = 3
    override def onAdd (target : Monster) : Unit = {
        super.onAdd(target)
        target.speedBattle /= 4
    }

    override def onDelete : Unit = {
        super.onDelete
        owner.speedBattle *= 4
    }

    override def copy : Paralysis = new Paralysis
}

class Confusion extends Status {
    name = "Confusion"
    override def copy : Confusion = new Confusion
}

class Protection extends Status {
    name = "Protection"
    override def copy : Protection = new Protection
}

class Burn extends Status {
    name = "Burn"
    override def onAdd (target : Monster) : Unit = {
        super.onAdd(target)
        target.attackBattle /= 2
    }

    override def onDelete : Unit = {
        owner.attackBattle *= 2
    }

    override def onEndTurn : Unit = {
        owner.takeDamage(owner.hpMax / 16)
        super.onEndTurn
    }
    override def copy : Burn = new Burn
}

class Freeze extends Status {
    name = "Freeze"
    override def copy : Freeze = new Freeze
}


class Poison extends Status {
    name = "Poison"
    override def onEndTurn : Unit = {
        owner.takeDamage(owner.hpMax / 16)
        super.onEndTurn
    }
    override def copy : Poison = new Poison
}

class Sleep extends Status {
    name = "Sleep"
    override def copy : Sleep = new Sleep
}