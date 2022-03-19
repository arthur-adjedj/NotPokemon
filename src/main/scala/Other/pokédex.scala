import java.awt.Graphics


class DrawPokedexPanel extends MyPanel {
    ready = true

    var backgroundImgName = "/Pokedex/Background.png"
    var backgroundImg = Utils.loadImage(backgroundImgName)

    var pokeList : List[Monster] = List()

    //image to draw when the pokémon was never caught nor seen
    var unknownPokemon = Utils.loadImage("")

    var pokemonArray : Array[Monster] = Array.empty
    def nbPokes = pokeList.length

    //position in the array of the currently displayed monster
    var currentMonster : Int = 0

    var number = 0
    var type_ = Normal
    var attack = 0
    var defense = 0
    var evasion = 0
    var weight = 0
    var height_ = 0
    var description = "this is a test"


    def drawPokemon(g : Graphics) = {
        var pokemonToDisplay = pokemonArray(currentMonster)
        if (pokemonToDisplay.wasSeen) {
            g.drawImage(Utils.loadImage(pokemonToDisplay.imgNameFront),0,0,null)
        } else {
            g.drawImage(unknownPokemon,0,0,null)
        }
    }

    def drawText(g : Graphics) = {

    }

    def display(g : Graphics) = {
        g.drawImage(backgroundImg,0,20,null)
        drawPokemon(g)
        drawText(g)
    }

}