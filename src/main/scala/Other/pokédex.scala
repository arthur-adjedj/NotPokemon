import java.awt.{Color,Graphics,Font}



class DrawPokedexPanel extends MyPanel {
    var poke_font : Font = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream("PokemonPixelFont.ttf"))
    poke_font = poke_font.deriveFont(Font.PLAIN,43)

    var backgroundImgName = "/Pokedex/Background.png"
    var backgroundImg = Utils.loadImage(backgroundImgName)

    //image to draw when the pokémon was never caught nor seen
    var unknownPokemon = Utils.loadImage("Pokedex/UnknownMonster.png")

    var pokemonArray : Array[Monster] = Array( new Charmander,
                                               new Squirtle,
                                               new Charmander,
                                               new Pikachu,
                                               new Rattata,
                                               new Bulbasaur,
                                               new Squirtle,
                                               new Charmander,
                                               new Pikachu,
                                               new Rattata)

    pokemonArray(0).wasSeen = true
    pokemonArray(2).wasSeen = true
    pokemonArray(3).wasSeen = true
    pokemonArray(3).wasCaught = true
    //position in the array of the currently displayed monster
    var currentPokemonIndex : Int = 0
    def currentPokemon = pokemonArray(currentPokemonIndex)


    def drawPokemon(g : Graphics) = {

        if (currentPokemon.wasSeen) {
            g.drawImage(Utils.loadImage(currentPokemon.imgNameFront),53,170,null)
        } else {
            g.drawImage(unknownPokemon,53,170,null)
        }
    }

    def drawText(g : Graphics) = {
        var metrics = g.getFontMetrics(poke_font); // used to center text

        var name = if (currentPokemon.wasSeen) currentPokemon.name else "?"
        var characteristics =  if (currentPokemon.wasSeen) {
            List("Height : " + currentPokemon.height, "Weight : " + currentPokemon.weight)
        } else 
            List("Height : ???","Weight : ???")
        var description = "" //TODO add desc and split it in lines

        g.setFont(poke_font)
        g.setColor(Color.WHITE)
        g.drawString("Pokedex",(614 - metrics.stringWidth(name)) / 2, 55)
        g.setColor(new Color(68,64,69))
        //draws the name of the pokémon
        g.drawString(name,40 + (200 - metrics.stringWidth(name)) / 2 , 113) // the name is horizontally centered inside the box
        //as well as his height/weight 
        g.drawString(characteristics(0),40,422)
        g.drawString(characteristics(1),40,454)
    }

    def nbToDisplay(n : Int) : String = {
        if (n < 10) {
            return "00" + n.toString()
        }
        if (n < 100) {
            return "0" + n.toString()
        }
        return n.toString()
    }


    //TODO add caught/seen icons next to names
    def drawList(g : Graphics) {
        var yshift : Int = 38 //vertical shift between each line
        for (i <- 0 to 9) {
            if (currentPokemonIndex + i < pokemonArray.size) {
                var text = nbToDisplay(currentPokemonIndex + i + 1) + " " + (
                    if (! pokemonArray(currentPokemonIndex + i).wasSeen) "----------"
                    else pokemonArray(currentPokemonIndex + i).name
                    )
                g.drawString(text,328,115 + i*yshift)
            }
        }
    }

    override def paintComponent(g : Graphics) = {
        super.paintComponent(g)
        g.drawImage(backgroundImg,0,20,null)
        drawPokemon(g)
        drawText(g)
        drawList(g)
        endPaintComponent(g)
    }

}