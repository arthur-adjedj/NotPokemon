import java.awt.Graphics
import java.util.concurrent.TimeUnit


class PlayerDisplayer (imgNams : Array[String]) {
    var x : Int = 0
    var y : Int = 0

    var i : Int = 0
    var j : Int = 0

    var speed : Int = 1
    var whichMap : Int = 0
    var mover : Mover = new Mover

    var imgs = imgNams.map(x => Utils.loadImage(x)) // [Up, Right, Down, Left]
    var img = imgs(0)

    var mapDisplayer : MapDisplayer = EmptyMapDisplayer
    var mapUI : MapUI = EmptyMapUI
    var isMoving : Boolean = false
    var canMove : Boolean = true

    Utils.playersDisplayers = this :: Utils.playersDisplayers

    def move (moveX : Int, moveY : Int) : Unit = {
        {(moveX, moveY) match {
            case (0, 1) => img = imgs(2)
            case (0, -1) => img = imgs(0)
            case (-1, 0) => img = imgs(3)
            case (1, 0) => img = imgs(1)

        }}

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


object FirstPlayerDisplayer extends PlayerDisplayer (Array("Players/FirstPlayerUp.png", "Players/FirstPlayerRight.png", "Players/FirstPlayerDown.png", "Players/FirstPlayerLeft.png")) {

    whichMap = 1
    speed = 100
}

object EmptyPlayerDisplayer extends PlayerDisplayer (Array("Empty.png")) {}