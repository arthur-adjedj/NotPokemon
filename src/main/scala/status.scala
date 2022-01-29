abstract class Status {
    var name : String = "Empty"
    var duration : Int = 0
    var durationLeft : Int = 0
    def onAdd : Unit = {}
    def onDelete : Unit = {}
    def onEndTurn : Unit = {durationLeft -=1}
}

class NoStatus extends Status {
}

class Paralysis extends Status {
    name = "Paralysis"
    //TODO
}

class Confusion extends Status {
    name = "Confusion"
    //TODO
}

class Protection extends Status {
    name = "Protection"
    //TODO
}
class Burn extends Status {
    name = "Burn"
    //TODO
}

class Freeze extends Status {
    name = "Freeze"
    //TODO
}


class Poison extends Status {
    name = "Poison"
    //TODO
}

class Sleep extends Status {
    name = "Sleep"
    //TODO
}