import java.awt.Graphics
import java.util.concurrent.TimeUnit


class PlayerDisplayer (imgNam : String) {
    var x : Int = 0
    var y : Int = 0

    var i : Int = 0
    var j : Int = 0

    var speed : Int = 1
    var whichMap : Int = 0
    var mover : Mover = new Mover

    var imgName : String = imgNam
    var img = Utils.loadImage(imgName)

    var mapDisplayer : MapDisplayer = EmptyMapDisplayer
    var mapUI : MapUI = EmptyMapUI
    var isMoving : Boolean = false
    var canMove : Boolean = true

    Utils.playersDisplayers = this :: Utils.playersDisplayers

    def move (moveX : Int, moveY : Int) : Unit = {
        if (!isMoving && canMove) {
            if (0 <= i+moveX && i+moveX < mapDisplayer.grid.length && 0 <= j+moveY && j+moveY < mapDisplayer.grid(i).length) {
                if (mapDisplayer.grid(i+moveX)(j+moveY).walkable) {
                    mover = new Mover
                    mover.playerdisplayer = this
                    isMoving = true
                    mover.move(moveX, moveY)
                }
                if (mapDisplayer.grid(i+moveX)(j+moveY).interactable) {
                    mapDisplayer.grid(i+moveX)(j+moveY).interact
                }
            }
            
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

        i = x/mapDisplayer.sizeBlock
        j = y/mapDisplayer.sizeBlock
    
    }
}


object FirstPlayerDisplayer extends PlayerDisplayer ("Players/FirstPlayer.png") {

    whichMap = 1
    speed = 100
}

object EmptyPlayerDisplayer extends PlayerDisplayer ("Empty.png") {}