import java.awt.Graphics
import java.util.concurrent.TimeUnit


abstract class Block (iMap : Int, jMap : Int, imgNam : String) {
    var x : Int = 0
    var y : Int = 0
    var i : Int = iMap
    var j : Int = jMap

    var imgName : String = imgNam
    var img = Utils.loadImage(imgName)

    var walkable : Boolean = true
    var interactable : Boolean = false

    def display (g : Graphics) : Unit = {
        g.drawImage(img, x, y, null)
    }

    def updateCoordinates (xMap : Int, yMap : Int, sizeBlock : Int) : Unit = {
        x = xMap + i*sizeBlock
        y = yMap + j*sizeBlock
    }
    
    def interact : Unit = {

    }


}
//renders a "base block" given a certain orientation with updown and leftright in {-1,0,1}
class MultiBlock(iMap : Int, jMap : Int, updown : Int, leftright : Int, base : String) extends Block(iMap,jMap,"Maps/Tile.png") {
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

class MultiCliff(iMap : Int, jMap : Int, updown : Int, leftright : Int) extends MultiBlock(iMap, jMap, updown, leftright, "Cliff") {
    walkable = false
}

class GrassBlock (iMap : Int, jMap : Int) extends Block (iMap, jMap, "Blocks/Grass.png") {
    interactable = true
    override def interact : Unit = {
        if (scala.util.Random.nextFloat() < 0.1) {
            Utils.frame.listening = false
            WildCharacter.initialise
            Utils.frame.startBattle(Player, WildCharacter)
        }
    }
}

class EmptyBlock (iMap : Int, jMap : Int) extends Block (iMap, jMap, "Empty.png") {
    override def display (g : Graphics) : Unit = {}
}