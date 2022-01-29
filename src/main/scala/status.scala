abstract class Status {
    def name : String 
    def duration : Int 
}

object NoStatus extends Status {
    def name = "none"
    def duration: Int = 0
}