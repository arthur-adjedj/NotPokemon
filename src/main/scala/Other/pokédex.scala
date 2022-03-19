import java.awt.Graphics


object Pokedex {
    var backgroundImgName = ""
    var backgroundImg = Utils.loadImage(backgroundImgName)

    var pokeList : List[Monster] = List()
    var nbPokes = pokeList.length
}

class DrawPokedexPanel extends MyPanel {

    //image to draw when the pokémon was never caught nor seen
    var unknownPokémon = Utils.loadImage("")

    var pokémonArray : Array[Monster] = Array.empty

    //position in the array of the currently displayed monster
    var currentMonster : Int = 0

    var number = 0
    var type_ = Normal
    var attack = 0
    var defense = 0
    var evasion = 0
    var weight = 0
    var height_ = 0
    var description = ""


    def drawPokémon(g : Graphics) = {
        var pokémonToDisplay = pokémonArray(currentMonster)
        if (pokémonToDisplay.wasSeen) {
            g.drawImage(Utils.loadImage(pokémonToDisplay.imgNameFront),0,0,null)
        } else {
            g.drawImage(unknownPokémon,0,0,null)
        }
    }

    def drawText(g : Graphics) = {

    }

    def display(g : Graphics) =
        drawPokémon(g)

}