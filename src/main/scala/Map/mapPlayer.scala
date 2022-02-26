class MapCharacter{
    var name = ""
    var sprites : Array[String] = Array.fill(4)("Empty")

    var battleCharacter : Character = EmptyCharacter

    def dialogue() : Unit = {}

    var stopsYou : Boolean = false
}