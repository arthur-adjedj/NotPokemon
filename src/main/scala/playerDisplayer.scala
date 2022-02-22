import java.awt.Graphics
import java.util.concurrent.TimeUnit


class PlayerDisplayer (imgNam : String) {
    var x : Int = 0
    var y : Int = 0
    var speed : Int = 1
    var whichMap : Int = 0
    var mover : Mover = new Mover

    var imgName : String = imgNam
    var img = Utils.loadImage(imgName)

    var mapDisplayer : MapDisplayer = EmptyMapDisplayer
    var mapUI : MapUI = EmptyMapUI
    var isMoving : Boolean = false

    Utils.playersDisplayers = this :: Utils.playersDisplayers

    def move (moveX : Int, moveY : Int) : Unit = {
        mover = new Mover
        mover.playerdisplayer = this
        if (!isMoving) {
            isMoving = true
            mover.move(moveX, moveY)
        }
    }

    def display (g : Graphics, xMap : Int, yMap : Int, n : Int) : Unit = {
        if (n == whichMap) {
            g.drawImage(img, x - xMap, y - yMap, null)
        }
    }

    def changeCoordinates (moveX : Int, moveY : Int) : Unit = {
        x += moveX
        y += moveY
        println(x, y)
    }
}


object FirstPlayerDisplayer extends PlayerDisplayer ("Players/FirstPlayer.png") {

    whichMap = 1
    speed = 10
}

object EmptyPlayerDisplayer extends PlayerDisplayer ("Empty.png") {}