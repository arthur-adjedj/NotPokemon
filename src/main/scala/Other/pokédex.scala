import java.awt.Graphics



class DrawPokedexPanel extends MyPanel {

    var backgroundImgName = "/Pokedex/Background.png"
    var backgroundImg = Utils.loadImage(backgroundImgName)

    var pokeList : List[Monster] = List()

    //image to draw when the pokémon was never caught nor seen
    var unknownPokemon = Utils.loadImage("Pokedex/UnknownMonster.png")

    var pokemonArray : Array[Monster] = Array(EmptyMonster)
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
            g.drawImage(Utils.loadImage(pokemonToDisplay.imgNameFront),53,170,null)
        } else {
            g.drawImage(unknownPokemon,53,170,null)
        }
    }

    def drawText(g : Graphics) = {

    }

    override def paintComponent(g : Graphics) = {
        super.paintComponent(g)
        g.drawImage(backgroundImg,0,20,null)
        drawPokemon(g)
        drawText(g)
        endPaintComponent(g)
    }

}