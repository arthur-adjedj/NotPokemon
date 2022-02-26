import java.awt.Graphics
import java.awt.image.BufferedImage
import java.util.concurrent.TimeUnit


abstract class Direction
case object Up extends Direction
case object Down extends Direction
case object Left extends Direction
case object  Right extends Direction


class PlayerDisplayer (imgName : String) {
    var direction : Direction = Down

    var x : Int = 0
    var y : Int = 0

    //tile number on the map
    var i : Int = -1
    var j : Int = -1

    var speed : Int = 1
    var whichMap : Int = 0
    var mover : Mover = new Mover

    /*tile number of the current sprite used, starting in the upper left corner
    the sprites are contained in such a way that the orientation depends on ny, and the animation key on nx,
    see "Players/MainCharacter.png" for example*/
    var nx = 0
    var ny = 0

    //size of the sprite
    var height = 50
    var width = 34


    var img = Utils.loadImage(imgName)
    
    var player : Player = EmptyPlayer

    var mapDisplayer : MapDisplayer = EmptyMapDisplayer
    var mapUI : MapUI = EmptyMapUI
    var isMoving : Boolean = false
    //players can't move during dialogues, battles or when the bag/pokédex is open
    var canInteract : Boolean = true

    Utils.playerDisplayers = this :: Utils.playerDisplayers

    def drawSprite (g : Graphics, xMap : Int, yMap : Int, img : BufferedImage, width : Int, height : Int, nx: Int, ny : Int ) : Unit = {
        /*shift the sprite on the tile such that its feet are at the right place*/
        var xshift = x + (mapUI.sizeBlock - width)/2 - xMap
        var yshift = y + (mapUI.sizeBlock - height) - yMap

        g.drawImage(img,
            xshift, yshift, /*upper left corner coords on the map*/
            xshift + width, yshift +  height, /*lower right corner*/
            nx * width , ny * height,  /*upper left corner coords of the sprite on the tileset*/
            (nx + 1) * width  ,(ny + 1) * height , /*lower right corner*/
            null)
    }

    //update the sprite used to render depending on its orientation
    def updateSprite() = {
         direction match {
             case Up => ny = 3
             case Down => ny = 0
             case Right => ny = 2
            case Left => ny = 1
         }
        }

    def move (moveX : Int, moveY : Int) : Unit = {
        if (!isMoving && canInteract) {
            Utils.print(x, y)

            direction = (moveX, moveY) match {
                case (0, 1) => Down
                case (0, -1) => Up
                case (-1, 0) => Left
                case (1, 0) => Right
                }

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
            updateSprite()
            drawSprite(g,xMap,yMap,img,width,height,nx,ny)
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


object FirstPlayerDisplayer extends PlayerDisplayer ("Players/MainCharacter.png") {

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

object SecondPlayerDisplayer extends PlayerDisplayer ("Players/Louis.png") {

    player = SecondPlayer

    whichMap = 1
    i = 7
    j = 7

    override def display (g : Graphics, xMap : Int, yMap : Int, n : Int) : Unit = {
        super.display(g, xMap, yMap, n)
    }

}

object EmptyPlayerDisplayer extends PlayerDisplayer ("Empty.png") {}