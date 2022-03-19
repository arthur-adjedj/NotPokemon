

object Pokedex {
    var backgroundImgName = ""
    var backgroundImg = Utils.loadImage(backgroundImgName)

    var pokeList : List[Monster] = List()
    var nbPokes = pokeList.length
}

class DrawPokedexPanel extends MyPanel {

    var currentMonster = Utils.loadImage("Empty.png")

    var number = 0
    var type_ = Normal
    var attack = 0
    var defense = 0
    var evasion = 0
    var weight = 0
    var height_ = 0
    var description = ""


}