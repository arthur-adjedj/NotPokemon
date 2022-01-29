abstract class Status {
    var name : String = ""
    var duration : Int  = 0
}

object NoStatus extends Status {
    name = "none"
    duration = 0
}

object Paralysis extends Status {
    //TODO
}