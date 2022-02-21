import java.awt.Graphics
import java.util.concurrent.TimeUnit


class PlayerDisplayer (imgNam : String) {
    var x : Int = 0
    var y : Int = 0
    var speed : Int = 1
    var whichMap : Int = 0
    var imgName : String = imgNam
    var img = Utils.loadImage(imgName)

    Utils.playersDisplayers = this :: Utils.playersDisplayers

    def display (g : Graphics, xMap : Int, yMap : Int, n : Int) : Unit = {
        if (n == whichMap) {
            g.drawImage(img, x - xMap, y - yMap, null)
        }
    }

    def move (moveX : Int, moveY : Int) : Unit = {
        x += moveX
        y += moveY
    }
}


object FirstPlayerDisplayer extends PlayerDisplayer ("Players/FirstPlayer.png") {

    whichMap = 1
    var mapDisplayer : MapDisplayer = EmptyMapDisplayer
    var mapUI : MapUI = EmptyMapUI
    override def move (moveX : Int, moveY : Int) : Unit = {
        for (i <- 0 to mapDisplayer.sizeBlock - 1) {
            x += moveX
            y += moveY
        }
        println(x, y)


    }
}

object EmptyPlayerDisplayer extends PlayerDisplayer ("Empty.png") {}