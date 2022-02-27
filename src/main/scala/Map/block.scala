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

//TODO refactor so that it isn't that shitty
class CliffBlockRight (iMap : Int, jMap : Int) extends Block (iMap, jMap, "Blocks/CliffRight.png") {
    walkable = false
}

class CliffBlockTop (iMap : Int, jMap : Int) extends Block (iMap, jMap, "Blocks/CliffTop.png") {
    walkable = false
}
class CliffBlockBot (iMap : Int, jMap : Int) extends Block (iMap, jMap, "Blocks/CliffBot.png") {
    walkable = false
}

class CliffBlockTopRight (iMap : Int, jMap : Int) extends Block (iMap, jMap, "Blocks/CliffTopRight.png") {
    walkable = false
}
class CliffBlockBotRight (iMap : Int, jMap : Int) extends Block (iMap, jMap, "Blocks/CliffBotRight.png") {
    walkable = false
}

class GrassBlock (iMap : Int, jMap : Int) extends Block (iMap, jMap, "Blocks/Grass.png") {
    interactable = true
    override def interact : Unit = {
        if (scala.util.Random.nextFloat() < 0.1) {
            PlayerDisplayer.mapUI.listening = false
            var b = new Battle(Player, WildCharacter)
            WildCharacter.initialise
            b.initialise
            b.start
        }
    }
}

class EmptyBlock (iMap : Int, jMap : Int) extends Block (iMap, jMap, "Empty.png") {
    override def display (g : Graphics) : Unit = {}
}