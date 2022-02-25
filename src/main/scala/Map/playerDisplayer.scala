import java.awt.Graphics
import java.util.concurrent.TimeUnit


class PlayerDisplayer (imgNams : Array[String]) {
    var x : Int = 0
    var y : Int = 0

    var i : Int = -1
    var j : Int = -1

    var speed : Int = 1
    var whichMap : Int = 0
    var mover : Mover = new Mover

    var imgs = imgNams.map(x => Utils.loadImage(x)) // [Up, Right, Down, Left]
    var img = imgs(0)
    
    var player : Player = EmptyPlayer

    var mapDisplayer : MapDisplayer = EmptyMapDisplayer
    var mapUI : MapUI = EmptyMapUI
    var isMoving : Boolean = false
    var canMove : Boolean = true

    Utils.playerDisplayers = this :: Utils.playerDisplayers

    def move (moveX : Int, moveY : Int) : Unit = {
        if (!isMoving)
            {(moveX, moveY) match {
            case (0, 1) => img = imgs(2)
            case (0, -1) => img = imgs(0)
            case (-1, 0) => img = imgs(3)
            case (1, 0) => img = imgs(1)

            }
        }

        if (!isMoving && canMove) {
            if (0 <= i+moveX && i+moveX < mapDisplayer.grid.length && 0 <= j+moveY && j+moveY < mapDisplayer.grid(i).length) {
                if (mapDisplayer.grid(i+moveX)(j+moveY).walkable) {
                    mover = new Mover
                    mover.playerdisplayer = this
                    isMoving = true
                    mover.move(moveX, moveY)
                    alignCoordinates
                }
                if (mapDisplayer.grid(i+moveX)(j+moveY).interactable) {
                    mapDisplayer.grid(i+moveX)(j+moveY).interact
                }
            }
            
        }
    }

    def endMove : Unit = {
        isMoving = false
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

    def alignCoordinates : Unit = {
        x = i*mapDisplayer.sizeBlock
        y = j*mapDisplayer.sizeBlock
    }
}


object FirstPlayerDisplayer extends PlayerDisplayer (Array("Players/FirstPlayerUp.png", "Players/FirstPlayerRight.png", "Players/FirstPlayerDown.png", "Players/FirstPlayerLeft.png")) {

    player = FirstPlayer

    i = 0
    j = 0

    whichMap = 1
    speed = 10


    override def endMove : Unit = {
        super.endMove
        var sameCase = Utils.playerDisplayers.filter(opp => opp != this && opp.i == i && opp.j == j && !opp.player.alreadyBeaten)
        for (i <- sameCase.indices) {
            var b = new Battle(FirstPlayer, sameCase(i).player)
            b.initialise
            b.start
        }
    }


}

object SecondPlayerDisplayer extends PlayerDisplayer (Array("Players/FirstPlayerUp.png", "Players/FirstPlayerRight.png", "Players/FirstPlayerDown.png", "Players/FirstPlayerLeft.png")) {
    
    player = SecondPlayer

    whichMap = 1
    i = 7
    j = 7

    override def display (g : Graphics, xMap : Int, yMap : Int, n : Int) : Unit = {
        super.display(g, xMap, yMap, n)
        println(i, j)
    }

}

object EmptyPlayerDisplayer extends PlayerDisplayer (Array("Empty.png")) {}