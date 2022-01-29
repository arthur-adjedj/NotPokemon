abstract class Status {
    var name : String = "Empty"
    var duration : Int = 0
    var durationLeft : Int = 0
    def onAdd : Unit = {}
    def onDelete : Unit = {}
    def onEndTurn : Unit = {}
}

class NoStatus extends Status {
}

class Paralysis extends Status {
    //TODO
}