import java.awt.{Color,Graphics,Font}
import java.awt.event._


class DrawPokedexPanel extends MyPanel {
    var poke_font : Font = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream("PokemonPixelFont.ttf"))
    poke_font = poke_font.deriveFont(Font.PLAIN,43)

    var backgroundImgName = "/Pokedex/Background.png"
    var backgroundImg = Utils.loadImage(backgroundImgName)

    var wasSeenIcon = Utils.loadImage("/Pokedex/PokeSeen.png")
    var wasCaughtIcon = Utils.loadImage("/Pokedex/PokeCaught.png")

    //image to draw when the pokémon was never caught nor seen
    var unknownPokemon = Utils.loadImage("Pokedex/UnknownMonster.png")

    var pokemonArray : Array[Monster] = Array( new Bulbasaur,
                                               new Ivysaur,
                                               new Venusaur,
                                               new Charmander,
                                               new Charmeleon,
                                               new Charizard,
                                               new Squirtle,
                                               new Wartortle,
                                               new Blastoise,
                                               new Rattata,
                                               new Raticate,
                                               new Pikachu,
                                               new Raichu,
                                               new Vulpix,
                                               new Ninetales,
                                               new Oddish,
                                               new Gloom,
                                               new Vileplume,
                                               new Meowth,
                                               new Persian,
                                               new Psyduck,
                                               new Golduck,
                                               new Growlithe,
                                               new Arcanine,
                                               new Poliwag,
                                               new Poliwhirl,
                                               new Bellsprout,
                                               new Weepinbell,
                                               new Victreebel,
                                               new Ponyta,
                                               new Rapidash,
                                               new Seel,
                                               new Shellder,
                                               new Krabby,
                                               new Kingler,
                                               new Voltorb,
                                               new Electrode,
                                               new Exeggcute,
                                               new Exeggutor,
                                               new Lickitung,
                                               new Chansey,
                                               new Tangela,
                                               new Kangaskhan,
                                               new Horsea,
                                               new Seadra,
                                               new Goldeen,
                                               new Seaking,
                                               new Staryu,
                                               new Electabuzz,
                                               new Magmar,
                                               new Tauros,
                                               new Magikarp,
                                               new Gyarados,
                                               new Eevee,
                                               new Vaporeon,
                                               new Jolteon,
                                               new Flareon,
                                               new Porygon,
                                               new Snorlax)


    //position in the array of the currently displayed monster
    var currentPokemonIndex : Int = 0
    var topIndexList : Int = 0
    var currentPokemon = pokemonArray(currentPokemonIndex)
    var currentPokemonImage = Utils.loadImage(currentPokemon.imgNameFront)

    pokemonArray(2).wasSeen = true
    pokemonArray(4).wasSeen = true
    pokemonArray(5).wasSeen = true
    pokemonArray(2).wasCaught = true
    pokemonArray(4).wasCaught = true
    pokemonArray(5).wasCaught = true


    def discoverAll : Unit = {
        pokemonArray.foreach(x => x.wasSeen = true)
    }

    //TODO grey out the icon when pokémon is seen but not caught
    def drawPokemon(g : Graphics) = {
        if (currentPokemon.wasSeen) {
            g.drawImage(currentPokemonImage,53,170,null)
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
        var description = if (currentPokemon.wasSeen) Utils.cutString(currentPokemon.description,40) else List("...") //TODO add desc and split it in lines

        g.setFont(poke_font)

        g.setColor(Color.WHITE)
        g.drawString("Pokedex",(614 - metrics.stringWidth("Pokedex")) / 2, 55)

        g.setColor(new Color(68,64,69))
        //draws the name of the pokémon
        g.drawString(name,40 + (200 - metrics.stringWidth(name)) / 2 , 113) // the name is horizontally centered inside the box

        //as well as his height/weight 
        g.drawString(characteristics(0),40,422)
        g.drawString(characteristics(1),40,454)

        //draws the pokémon description
        var shift : Int = 75
        var nOfLines : Int = 0
        description foreach (x => { g.drawString(x,40,530 + shift*nOfLines); nOfLines += 1 } )


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

    def changeCurrentPokemon (n : Int) : Unit = {
        if (0 <= topIndexList + n && topIndexList + n < pokemonArray.size) {
            currentPokemonIndex = topIndexList + n
            currentPokemon = pokemonArray(currentPokemonIndex)
            currentPokemonImage = Utils.loadImage(currentPokemon.imgNameFront)
            if (currentPokemonIndex < topIndexList) {
                topIndexList = currentPokemonIndex
            }
            if (currentPokemonIndex > topIndexList + 9) {
                topIndexList = currentPokemonIndex - 9
            }
        }

    }

    def changeCurrentPokemonBrut (n : Int) : Unit = changeCurrentPokemon(n - topIndexList)

    def moveList (i : Int) : Unit = {
        if (0 <= topIndexList + i && topIndexList + i + 9 < pokemonArray.size) {
            topIndexList += i
        }
        if (currentPokemonIndex < topIndexList) {
            changeCurrentPokemon(0)
        } else if (currentPokemonIndex > topIndexList + 9) {
            changeCurrentPokemon(9)
        }
    }

    //Draws the left list of pokémons
    def drawList(g : Graphics) : Unit = {
        var yshift : Int = 38 //vertical shift between each line
        for (i <- 0 to 9) {
            if (topIndexList + i < pokemonArray.size) {
                var text = nbToDisplay(topIndexList + i + 1) + " " + (
                    if (! pokemonArray(topIndexList + i).wasSeen) "----------"
                    else pokemonArray(topIndexList + i).name
                    )
                if(pokemonArray(topIndexList + i).wasCaught){
                    g.drawImage(wasCaughtIcon, 280, 90+i*yshift,null)
                }else { if (pokemonArray(topIndexList + i).wasSeen)
                    g.drawImage(wasSeenIcon, 280, 90+i*yshift,null)
                }
                
                g.drawString(text,328,115 + i*yshift)
            }
        }
    }

    override def onKeyPressed (e : KeyEvent) : Unit = {
        e.getKeyChar.toLower match {
            case 'z' => changeCurrentPokemonBrut(currentPokemonIndex - 1)
            case 's' => changeCurrentPokemonBrut(currentPokemonIndex + 1)
            case '5' => if (Utils.debug) discoverAll
            case _ => 
        }
    }

    override def paintComponent(g : Graphics) = {
        super.paintComponent(g)
        g.drawImage(backgroundImg,0,20,null)
        drawPokemon(g)
        drawText(g)
        drawList(g)
        Utils.buttonList.foreach(x => x.display(g))
        endPaintComponent(g)
    }

}