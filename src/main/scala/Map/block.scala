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


class WallBlock (iMap : Int, jMap : Int) extends Block (iMap, jMap, "Blocks/Wall.png") {
    walkable = false
}

class GrassBlock (iMap : Int, jMap : Int) extends Block (iMap, jMap, "Blocks/Grass.png") {
    interactable = true
    override def interact : Unit = {
        if (scala.util.Random.nextFloat() < 0.1 || true) {
            FirstPlayerDisplayer.mapUI.listening = false
            var b = new Battle(FirstPlayer, WildPlayer)
            WildPlayer.initialise
            b.initialise
            b.start
        }
    }
}

class EmptyBlock (iMap : Int, jMap : Int) extends Block (iMap, jMap, "Empty.png") {
    override def display (g : Graphics) : Unit = {}
}