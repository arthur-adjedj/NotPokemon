import javax.swing.{JFrame, JPanel, JLabel}
import java.io.File
import java.awt.{Color,Graphics,BasicStroke,Font}
import java.awt.image.BufferedImage
import java.util.concurrent.TimeUnit

import java.lang.Math
import java.sql.Time

import java.io.FileReader
import java.io.FileWriter

import scala.collection.mutable.Queue

object Utils {

    // some global variables

    var importedImagesString : List[String] = List()
    var importedImages : List[BufferedImage] = List()

    var characterDisplayers : List[CharacterDisplayer] = List()
    var repaintables : List[Repaintable] = List()
    var updatable : List[Updatable] = List()
    var frame : MyUI = EmptyUI

    // we need to define those buttons here because we may need them in descriptables
    // we will move them in part 3 using the companions

    //battle
    var castAttackButtonList : List[CastAttackButton] = (0 to 3).map(x => new CastAttackButton(x)).toList
    var useItemButtonList : List[UseItemButton] = (0 to 3).map(x => new UseItemButton(x)).toList
    var changeMonsterButtonList : List[ChangeMonsterButton] = (0 to 5).map(x => new ChangeMonsterButton(x)).toList
    var battleMenuButtonList : List[BattleButton] = List(AttackButton, BagButton, MonsterButton, RunButton)

    var battleButtonList : List[BattleButton] = List.concat(battleMenuButtonList, castAttackButtonList, useItemButtonList, changeMonsterButtonList,
                                                            List(NextPageItemButton, BackAttackButton, BackBagButton))

    //map
    var showPokemonMapButtonList : List[PokemonMapButton] = (0 to 5).map(x => new PokemonMapButton(x)).toList

    var mapMenuButtonList : List[MapButton] = List(ShowInventoryButton, ShowTeamButton, ShowPokedexButton, SaveButton, TrainerButton, OptionsButton)

    var mapButtonList : List[MapButton] = List.concat(mapMenuButtonList, showPokemonMapButtonList)


    //pokedex
    var choosePokemonPokedexButtonList : List[ChoosePokemonPokedexButton] = (0 to 9).map(x => new ChoosePokemonPokedexButton(x)).toList
    var moveListPokedexButtonList : List[MoveListPokedexButton] = List(new MoveListPokedexButton(-1), new MoveListPokedexButton(1))
    var pokedexButtonList : List[PokedexButton] = List.concat(choosePokemonPokedexButtonList, moveListPokedexButtonList)

    //all contexts
    var choiceButtonList : List[ChoiceButton] = (0 to 5).map(x => new ChoiceButton(x)).toList
    var otherButtonsList : List[MyButton] = List.concat(List(CloseButton, HelpButton), choiceButtonList)

    // cannot create the list and adding buttons during their creation because of a weird behaviour with 'object'

    var buttonList : List[MyButton] = List.concat(otherButtonsList, battleButtonList, pokedexButtonList, mapButtonList)



    var descriptables : List[Descriptable] = List.concat(List(PlayerMonsterDisplayer, OpponentMonsterDisplayer), buttonList)

    var mapDisplayers : Array[MapDisplayer] = Array.fill(2){EmptyMapDisplayer}

    var debug = false

    var choiceType : String = "No choice"
    var choiceDone : Int = -1
    var lastContext : String = ""

    // These names are ambiguous on purpose to avoid any spoil
    var easterEggs : List[EasterEgg] = List(EasterEgg1, EasterEgg2, EasterEgg3)

    //BufferedImages of each type. Since these are drawn in multiple contexts, these are loaded at all times.
    var typeIcons : List[BufferedImage] = List(loadImage("/TypeIcons/NormalIcon.png"),
                                               loadImage("/TypeIcons/FireIcon.png"),
                                               loadImage("/TypeIcons/WaterIcon.png"),
                                               loadImage("/TypeIcons/GrassIcon.png"),
                                               loadImage("/TypeIcons/ElectricIcon.png"),
                                               loadImage("/TypeIcons/IceIcon.png"))
    
    def typeIconNumber(x : Type) : Int = {x match {
        case Normal => 0
        case Fire => 1
        case Water => 2
        case Grass => 3
        case Electric => 4
        case Ice => 5
        }
    }

    //Most common positions, indexed from left to right, up to down
    def buttonPosition(n : Int) : (Int,Int) = (3+ 308*(n%2), 405 + 144*(n/2))

    // some strategies
    var aggroStrat = Strategy(1, 0, 0, 0, 0)
    var tempoStrat = Strategy(1, 1, 1, 0, 0)
    var controlStrat = Strategy(1, 1, 1, 0, 1)

    def start : Unit = {
        Repainter.start
        Updater.start
    }

    def print[T] (s : T) : Unit = {
        if (debug) {
            println(s)
        }
    }

    def loadImage (name : String) : BufferedImage = {
        // we're doing some memoisation to avoid loading the same image twice
        var index = importedImagesString.indexOf(name)
    
        // if the image doesn't load, it returns a wrong image but doesn't crash
        // sometimes, it's the JVM's fault
        if (index == -1) {
            index = 0
            try {
                importedImages = javax.imageio.ImageIO.read(getClass.getResource(name)) :: importedImages
                Utils.print("Success while importing " + name)
            }
            catch {
                case _ : Throwable => {
                    importedImages = javax.imageio.ImageIO.read(getClass.getResource("Empty.png")) :: importedImages
                    Utils.print("Issues while importing " + name)
                }
            }
            importedImagesString = name :: importedImagesString


        } 
        importedImages(index)
        
    }

    def findFirstOccurenceArray [T](array : Array[T], element : T) : Int = {
        var i : Int = 0
        var found : Boolean = false
        while (!found && i < array.length) {
            found = array(i) == element
            i += 1
        }
        if (found) {
            i - 1 
        } else {
            -1
        }
    }

    def swap [T](array : Array[T], i : Int, j : Int) : Unit = {
        var temp = array(j)
        array(j) = array(i)
        array(i) = temp
    }

    def cutString (s : String, charPerLine : Int) : List[String] = {
        if (s.length <= charPerLine) {
            List(s)
        } else {
            var l = s.substring(0, charPerLine).lastIndexOf(" ")
            var text1 = s.substring(0, l)
            var text2 = s.substring(l+1)
            text1 :: cutString(text2, charPerLine)
            
        }
    }

    def waitDiscussionLabel : Unit = {
        waitDiscussionLabel(false)
    }

    def waitDiscussionLabel (waitLonger : Boolean): Unit = {
        while (!DiscussionLabel.messageQueue.isEmpty || DiscussionLabel.changingText) {
            TimeUnit.MILLISECONDS.sleep(10)    
        }
        if (waitLonger) {
            TimeUnit.MILLISECONDS.sleep(200)
        }
    }

    def waitForBooleanFunction (f : Unit => Boolean) : Unit = {
        while (!f()) {
            TimeUnit.MILLISECONDS.sleep(20)
        }
    }

    def distance (i1 : Int, j1 : Int, i2 : Int, j2 : Int) : Double = {
        Math.sqrt(Math.pow(i2-i1, 2) + Math.pow(j2-j1, 2))
    }

    def bestMove (iCurrent : Int, jCurrent : Int, iGoal : Int, jGoal : Int) : (Int, Int) = {
        if (iCurrent > iGoal) {
            (-1, 0)
        } else if (iCurrent < iGoal) {
            (1, 0)
        } else if (jCurrent > jGoal) {
            (0, -1)
        } else if (jCurrent < jGoal) {
            (0, 1)
        } else {
            (0, 0)
        }

    }

    def oppositeDirection (direction : Direction) : Direction = {
        direction match {
            case Up => Down
            case Down => Up
            case Right => Left
            case Left => Right
        }
    }

    def directionToString (direction : Direction) : String = {
        direction match {
            case Up => "Up"
            case Down => "Down"
            case Right => "Right"
            case Left => "Left"
        }
    }

    def askChoice (typ : String) = {
        choiceDone = -1
        lastContext = frame.currentState
        frame.currentState = "Choice"
        frame.listeningToKeyboard = false
        choiceType = typ
    }

    def makeChoice (n : Int) = {
        frame.currentState = lastContext
        choiceDone = n
        choiceType = "No choice"
        frame.listeningToKeyboard = true
        DiscussionLabel.changeText("")
    }

    def save : Unit = {
        var saveString = characterDisplayers.map(x => x.toStringSave(0)).foldLeft("")((x, y) => x+"-"+y)
        var filewriter = new FileWriter("save.txt")
        filewriter.write(saveString)
        filewriter.close
    }

    def loadSave : Unit = {
        var sizeToRead = 100000
        var file = new File("save.txt")
        if (!file.exists) {
            DiscussionLabel.changeText("You need a save if you want to load it !")
        } else {
            var filereader = new FileReader("save.txt")
            var charArray = new Array[Char](sizeToRead)
            filereader.read(charArray, 0, sizeToRead)
            var saveString = String.copyValueOf(charArray)
            parseSave(saveString)
        }
    }

    def parseSave (saveString : String) : Unit = {
        Utils.print(saveString)
        var splited = saveString.split("-")
        splited.foreach(find_parse)
    }

    def find_parse (saveString : String) : Unit = {
        if (saveString.startsWith("CharacterDisplayer :")) {
            loadCharacterDisplayer(saveString)
        } else if (saveString.startsWith("PlayerDisplayer :")) {
            loadPlayerDisplayer(saveString)
        } else if (saveString.startsWith("Player : ")) {
            loadPlayer(saveString)
        } else if (saveString.startsWith("Pokemon")) {
            loadPokemon(saveString)
        } else if (saveString.startsWith("MapItem : ")) {
            loadMapItem(saveString)
        } else {
            Utils.print("Not handled" + saveString)
        }
    }

    def loadCharacterDisplayer (saveString : String) : Unit = {
        var splited = saveString.split("\n")
        var data = splited.slice(1, splited.length).map(x => x.substring(1, x.length))
        var index = data(0).split(" : ")(1).toInt
        var i = data(1).split(" : ")(1).toInt
        var j = data(2).split(" : ")(1).toInt
        var map = data(3).split(" : ")(1).toInt
        var characterString = data.slice(4, data.length).foldLeft("")((x, y) => x + "\n" + y)

        var characterDisplayer = characterDisplayers.filter(x => x.index == index)(0)
        characterDisplayer.teleport(i, j, map)
        loadCharacter(characterString, characterDisplayer.player)
    }

    def loadCharacter (saveString : String, character : Character) : Unit = {
        var splited = saveString.split("\n")
        var data = splited.slice(2, splited.length).map(x => x.substring(1, x.length))
        var name = data(0).split(" : ")(1)
        var alreadyBeaten = data(1).split(" : ")(1).toBoolean
        
        character.name = name
        character.alreadyBeaten = alreadyBeaten
    }
    
    def loadPlayerDisplayer (saveString : String) : Unit = {
        var splited = saveString.split("\n")
        var data = splited.slice(1, splited.length).map(x => if (!x.isEmpty) x.substring(1, x.length) else "")
        
        var i = data(0).split(" : ")(1).toInt
        var j = data(1).split(" : ")(1).toInt
        var map = data(2).split(" : ")(1).toInt
        var playerString = data.slice(3, data.length).foldLeft("")((x, y) => x + "\n" + y)

        PlayerDisplayer.teleport(i, j, map)

        PlayerDisplayer.notUsableMapInventory = Queue()
        PlayerDisplayer.usableMapInventory = Queue()


        loadPlayer(playerString)

    }

    def loadPlayer (saveString : String) : Unit = {
        var splited = saveString.split("\n")
        var data = splited.slice(3, splited.length).map(x => if (!x.isEmpty) x.substring(1, x.length) else "")
        
        var name = data(0).split(" : ")(1)
        var i = 1
        while (i < data.length && data(i).split(" : ")(0) == "Item") {
            var name = data(i).split(" : ")(1).split(" => ")(0)
            var amount = data(i).split(" : ")(1).split(" => ")(1).toInt
            Player.gainItem(name, amount)
            i += 1
        }
        Player.team = Array.fill(6){EmptyMonster}
    }

    def loadPokemon (saveString : String) : Unit = {
        var splited = saveString.split("\n")
        var data = splited.slice(0, splited.length).map(x => if (!x.isEmpty) x.substring(1, x.length) else "")
        var number = data(0).split("n ")(1).split(" :")(0).toInt
        var specie = data(1).split(" : ")(1)
        var name = data(2).split(" : ")(1)
        var level = data(3).split(" : ")(1).toInt
        var xp = data(4).split(" : ")(1).toInt

        var IVAttack = data(5).split(" : ")(1).toInt
        var IVDefense = data(6).split(" : ")(1).toInt
        var IVSpeed = data(7).split(" : ")(1).toInt
        var IVHp = data(8).split(" : ")(1).toInt

        var EVAttack = data(9).split(" : ")(1).toInt
        var EVDefense = data(10).split(" : ")(1).toInt
        var EVSpeed = data(11).split(" : ")(1).toInt
        var EVHp = data(12).split(" : ")(1).toInt

        var hp = data(13).split(" : ")(1).toInt

        var attack0 = attackFromName(data(14).split(" : ")(1))
        var attack1 = attackFromName(data(15).split(" : ")(1))
        var attack2 = attackFromName(data(16).split(" : ")(1))
        var attack3 = attackFromName(data(17).split(" : ")(1))


        var poke = newPokemon(specie)

        poke.name = name
        poke.level = level 
        poke.xp = xp
        poke.IVAttack = IVAttack
        poke.IVDefense = IVDefense
        poke.IVSpeed = IVSpeed
        poke.IVHp = IVHp
        poke.EVAttack = EVAttack
        poke.EVDefense = EVDefense
        poke.EVSpeed = EVSpeed
        poke.EVHp = EVHp
        poke.hp = hp
        poke.attacks(0) = attack0
        poke.attacks(1) = attack1
        poke.attacks(2) = attack2
        poke.attacks(3) = attack3

        poke.setStats
        
        Player.switchPokemon(poke, number)
    }
    
    def loadMapItem(saveString : String) : Unit = {
        var allItems = Array(new Bike, new Surf)
        var obj = saveString.replace("\n", "").split(" : ")(1)
        Utils.print(obj)
        if (obj.startsWith("Key")) {
            var id = obj.split(" ")(1).toInt
            PlayerDisplayer.getMapItem(new Key(id))
        } else {
            allItems.foreach(x => Utils.print(x.name))
            var item = allItems.filter(x => x.name == obj)(0)
            PlayerDisplayer.getMapItem(item)
        }
    }

    def newPokemon(specie : String) : Monster = {
        return frame.pokedexPane.pokemonArray.filter(x => x.originalName == specie)(0).getClass.newInstance
    }

    def attackFromName(name : String) : Attack = {
        var allAttacks = Array(QuickAttack, DoubleSlap, ThunderShock, ThunderWave, Growl, Swift, Agility, Thunder, Tackle, TailWhip, WaterGun, Splash,
                          Withdraw, RapidSpin, WaterPulse, Protect, AquaTail, ShellSmash, IronDefense, HydroPump, SkullBash, VineWhip, Growth, Scratch,
                          Ember, Flamethrower, Crunch)

        return allAttacks.filter(x => x.name == name)(0)
    }
}

trait Repaintable {
    Utils.repaintables = this :: Utils.repaintables
    def repaint() : Unit
}

trait Updatable {
    Utils.updatable = this :: Utils.updatable
    def update : Unit
}

class Mover extends Thread {
    // used to move the image of a character
    var lastMoveX : Int = 0
    var lastMoveY : Int = 0
    var characterDisplayer : CharacterDisplayer = EmptyCharacterDisplayer

    
    def move (moveX : Int, moveY : Int) : Unit = {
        characterDisplayer.i += moveX
        characterDisplayer.j += moveY
        lastMoveX = moveX
        lastMoveY = moveY
        start
    }

    override def run : Unit = {
        for (i <- 0 to characterDisplayer.mapDisplayer.sizeBlock - 1) {
            characterDisplayer.changeCoordinates(lastMoveX, lastMoveY)
            if (i % (characterDisplayer.mapDisplayer.sizeBlock/4) == 0 && 
             !Utils.frame.mapPane.mapDisplayer.grid(characterDisplayer.i)(characterDisplayer.j).reverse.head.stopsAnimation) {
                characterDisplayer.nx = (characterDisplayer.nx + 1) % 4
            }
            if (i != characterDisplayer.mapDisplayer.sizeBlock - 1) {
                TimeUnit.MILLISECONDS.sleep(100/characterDisplayer.speed)
            }
        }
        characterDisplayer.endMove
    }
}

class MoverToBattle (i : Int, j : Int, c : CharacterDisplayer) extends Thread {
    // be careful to have a cleared path to avoid softlock
    var iTarget : Int = i
    var jTarget : Int = j
    var characterDisplayer : CharacterDisplayer = c

    override def run : Unit = {
        while (Utils.distance(characterDisplayer.i, characterDisplayer.j, iTarget, jTarget) > 1 || characterDisplayer.isMoving) {
            var (i, j) = Utils.bestMove(characterDisplayer.i, characterDisplayer.j, iTarget, jTarget)
            characterDisplayer.move(i, j)
        }
        Utils.frame.startBattle(Player, characterDisplayer.player)

    }
}

object Repainter extends Thread {
    override def run : Unit = {
        while (true) {
            Utils.repaintables.foreach(x => x.repaint())
            TimeUnit.MILLISECONDS.sleep(100/6)
        }
    }
}

object Updater extends Thread {
    override def run : Unit = {
        while (true) {
            Utils.updatable.foreach(x => x.update)
            //Utils.print(Utils.updatable)
            TimeUnit.MILLISECONDS.sleep(100/6)
        }
    }
}

trait Descriptable {

    def onMouseOver (g : Graphics, xMouse : Int, yMouse : Int, width : Int, height : Int) : Unit = {}
    def isMouseOver (x : Int, y : Int) : Boolean = false
    var context : String = "All"
}

object EmptyDescriptable extends Object with Descriptable {
    override def isMouseOver (x : Int, y : Int) = true
}