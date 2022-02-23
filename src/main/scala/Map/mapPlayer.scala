class MapPlayer{
    var name = ""
    var sprites : Array[String] = Array.fill(4)("Empty")

    var battlePlayer : Player = EmptyPlayer

    def dialogue() : Unit = {}

    var stopsYou : Boolean = false
}