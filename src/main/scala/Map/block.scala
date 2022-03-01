import java.awt.Graphics
import java.util.concurrent.TimeUnit


abstract class Block (imgNam : String) {
    var x : Int = 0
    var y : Int = 0
    var i : Int = 0
    var j : Int = 0

    var imgName : String = imgNam
    var img = Utils.loadImage(imgName)

    var originalWalkable : Boolean = true
    var walkable : Boolean = true
    var interactable : Boolean = false
    var slippery : Boolean = false

    def initialise (iMap : Int, jMap : Int) : Unit = {
        walkable = originalWalkable
        i = iMap
        j = jMap
    }

    def display (g : Graphics) : Unit = {
        g.drawImage(img, x, y, null)
    }

    def updateCoordinates (xMap : Int, yMap : Int, sizeBlock : Int) : Unit = {
        // when the map is moving
        x = i*sizeBlock - xMap
        y = j*sizeBlock - yMap
    }
    
    def interact (player : CharacterDisplayer) : Unit = {}

    def onWalk (player : CharacterDisplayer) : Unit = {}



}
//renders a "base block" given a certain orientation with updown and leftright in {-1,0,1}
class MultiBlock(updown : Int, leftright : Int, base : String) extends Block("Maps/Tile.png") {
    var upDownStr : String = updown match {
        case (-1) => "Bot"
        case 1 => "Top"
        case _ => ""
    }
    var leftRightStr : String = leftright match {
        case (-1) => "Left"
        case 1 => "Right"
        case _ => ""
    }

    imgName = "Blocks/" + base + upDownStr + leftRightStr + ".png"
    img = Utils.loadImage(imgName)
}

class RockBlock extends Block("Blocks/Rock.png") {
    originalWalkable = false
}

class MultiCliff(updown : Int, leftright : Int) extends MultiBlock(updown, leftright, "Cliff") {
    originalWalkable = false
}

class GrassBlock extends Block ("Blocks/Grass.png") {
    interactable = true
    override def onWalk (player : CharacterDisplayer) : Unit = {
        if (player.player.name == "You") {
            if (scala.util.Random.nextFloat() < 0.1) {
                Utils.frame.listeningToKeyboard = false
                WildCharacter.initialise
                Utils.frame.startBattle(Player, WildCharacter)
            }
        }
    }
}

class IceBlock extends Block ("Blocks/Ice.png") {
    slippery = true
}

class EmptyBlock extends Block ("Empty.png") {
    override def display (g : Graphics) : Unit = {}
}