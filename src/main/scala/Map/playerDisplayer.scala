import java.awt.Graphics
import java.awt.image.BufferedImage
import java.util.concurrent.TimeUnit
import scala.collection.mutable.Queue


abstract class Direction
case object Up extends Direction
case object Down extends Direction
case object Left extends Direction
case object Right extends Direction


class CharacterDisplayer (imgName : String) extends Object with Updatable {
    var direction : Direction = Down

    var x : Int = 0
    var y : Int = 0

    //tile number on the map
    var i : Int = 0
    var j : Int = 0

    var speed : Int = 1
    var whichMap : Int = 0
    var mover : Mover = new Mover

    // when noClip is true, the player can travel everywhere ignoring the walls and the interactions
    var noClip : Boolean = false

    /*tile number of the current sprite used, starting in the upper left corner
    the sprites are contained in such a way that the orientation depends on ny, and the animation key on nx,
    see "Characters/MainCharacter.png" for example*/
    var nx = 0
    var ny = 0

    //size of the sprite
    var height = 50
    var width = 34


    var img = Utils.loadImage(imgName)
    
    var player : Character = EmptyCharacter

    var mapDisplayer : MapDisplayer = EmptyMapDisplayer
    var isMoving : Boolean = false
    //player can't move during dialogues, battles or when the bag/pokédex is open
    var canInteract : Boolean = true

    var lastMoveX : Int = 0
    var lastMoveY : Int = 0

    // true iff the last move succeeded (no wall, no border...)
    var hasMoved : Boolean = false

    var sliding : Boolean = false

    var usableMapInventory : Queue[MapItem] = Queue()
    var notUsableMapInventy : Queue[MapItem] = Queue()
    var currentItem : MapItem = EmptyMapItem

    Utils.characterDisplayers = this :: Utils.characterDisplayers

    def initialise : Unit = {
        //mapDisplayer = Utils.frame.mapDisplayer
        mapDisplayer.grid(i)(j) foreach (b => b.walkable = false)
        alignCoordinates
    }

    def update : Unit = {
        mapDisplayer.grid(i)(j).foreach (b => b.walkable = false)
    }

    def move (moveX : Int, moveY : Int) : Unit = {
        hasMoved = false
        var uTurn = (moveX + lastMoveX, moveY + lastMoveY) == (0, 0)
        if (!isMoving && canInteract) {
            lastMoveX = moveX
            lastMoveY = moveY

            direction = (moveX, moveY) match {
                case (0, 1) => Down
                case (0, -1) => Up
                case (-1, 0) => Left
                case (1, 0) => Right
                }

            if (0 <= i+moveX && i+moveX < mapDisplayer.grid.length && 0 <= j+moveY && j+moveY < mapDisplayer.grid(i).length && !uTurn) {
                if (mapDisplayer.grid(i+moveX)(j+moveY).forall(b => b.walkable) || noClip) {

                    mover = new Mover
                    mover.characterDisplayer = this
                    isMoving = true
                    mover.move(moveX, moveY)
                    hasMoved = true
                } else {
                    sliding = false
                }
            } else {
                sliding = false
            }
            
        }
    }

    def endMove : Unit = {
        mapDisplayer.grid(i)(j) foreach (b => b.onWalk(this))
        mapDisplayer.grid(i-lastMoveX)(j-lastMoveY) foreach (b => b.walkable = mapDisplayer.grid(i)(j).head.originalWalkable)

        isMoving = false
        mapDisplayer.grid(i)(j) foreach (b => b.walkable = false)
        if (mapDisplayer.grid(i)(j).exists(b => b.slippery) && !noClip) {
            sliding = true
            move(lastMoveX, lastMoveY)
        } else {
            sliding = false
        }
    }

    def drawSprite (g : Graphics, xMap : Int, yMap : Int, img : BufferedImage, width : Int, height : Int, nx: Int, ny : Int ) : Unit = {
        /*shift the sprite on the tile such that its feet are at the right place*/
        var xshift = x + (Utils.frame.sizeBlock - width)/2 - xMap
        var yshift = y + (Utils.frame.sizeBlock - height) - yMap

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

    def display (g : Graphics, xMap : Int, yMap : Int, n : Int) : Unit = {
        if (n == whichMap) {
            updateSprite()
            drawSprite(g,xMap,yMap,img,width,height,nx,ny)
        }
    }

    def changeCoordinates (moveX : Int, moveY : Int) : Unit = {
        x += moveX
        y += moveY

    }

    def alignCoordinates : Unit = {
        x = i*mapDisplayer.sizeBlock
        y = j*mapDisplayer.sizeBlock
    }

    def interactExplicitly : Unit = {
        if (canInteract) {
            var iInteracted : Int = i
            var jInteracted : Int = j

            direction match {
                case Up => jInteracted -= 1
                case Down => jInteracted += 1
                case Left => iInteracted -= 1
                case Right => iInteracted += 1
            }
            mapDisplayer.grid(iInteracted)(jInteracted) foreach (b => b.interact(this))
        }
    }

    def getMapItem (item : MapItem) : Unit = {
        if (item.usable) {
            if (usableMapInventory.forall(x => x.id != item.id)) {
                usableMapInventory.enqueue(item)
            }
        } else {
            if (notUsableMapInventy.forall(x => x.id != item.id)) {
                notUsableMapInventy.enqueue(item)
            }
        }
    }

    def changeCurrentItem : Unit = {
        if (!usableMapInventory.isEmpty) {
            var item = usableMapInventory.dequeue
            usableMapInventory.enqueue(currentItem)
            currentItem.onUnselect(this)
            currentItem = item
            currentItem.onSelect(this)

            Utils.print(player.name + " is using " + item.name)
        }
    }
}

abstract class OpponentDisplayer (imageName_ : String) extends CharacterDisplayer(imageName_) {
    var interceptLength : Int = 0
    var intercepting : Boolean = false

    def interceptsPlayer : Boolean = direction match {
        case Up => 
            i == PlayerDisplayer.i && 
            PlayerDisplayer.j <= j &&
            j - interceptLength <= PlayerDisplayer.j
        case Down =>             
            i == PlayerDisplayer.i && 
            j <= PlayerDisplayer.j &&
            PlayerDisplayer.j <= j + interceptLength
        case Right => 
            j == PlayerDisplayer.j && 
            i <= PlayerDisplayer.i &&
            PlayerDisplayer.i <= i + interceptLength
        case Left => 
            j == PlayerDisplayer.j && 
            PlayerDisplayer.i <= i &&
            i - interceptLength <= PlayerDisplayer.i         
    }
    
    override def update : Unit = {
        super.update
        if (!player.alreadyBeaten && !intercepting && interceptsPlayer && !PlayerDisplayer.isMoving) {
            var c = PlayerDisplayer
            direction match {
                case Up => c.direction = Down
                case Down => c.direction = Up
                case Right => c.direction = Left
                case Left => c.direction = Right
            }
            if (c.whichMap == whichMap) {
                intercepting = true
                c.canInteract = false
                new MoverToBattle(c.i, c.j, this).start
            }
        }  
    }
}

object PlayerDisplayer extends CharacterDisplayer ("Characters/MainCharacter.png") {

    player = Player

    i = 0
    j = 0

    whichMap = 1
    speed = 10

    var topBox : Int = 120
    var botBox : Int = 280
    var leftBox : Int = 80
    var rightBox : Int = 280

    override def interactExplicitly : Unit = {
        var iInteracted : Int = i
        var jInteracted : Int = j

        {direction match {
            case Up => jInteracted -= 1
            case Down => jInteracted += 1
            case Left => iInteracted -= 1
            case Right => iInteracted += 1
        }}
        mapDisplayer.grid(iInteracted)(jInteracted) foreach (b => b.interact(this))
        Utils.characterDisplayers.foreach(x => if (x.i == iInteracted && x.j == jInteracted && x.whichMap == whichMap && !(x.player.alreadyBeaten)) Utils.frame.startBattle(player, x.player))
    }

    override def changeCoordinates (moveX : Int, moveY : Int) : Unit = {
        var xMap = mapDisplayer.x
        var yMap = mapDisplayer.y
        if ((x + moveX - xMap < leftBox && moveX < 0) || (x + moveX - xMap > rightBox && moveX > 0)) {
            mapDisplayer.x += moveX
        }

        if ((y + moveY - yMap < topBox && moveY < 0) || (y + moveY - yMap > botBox && moveY > 0)) {
            mapDisplayer.y += moveY
        }
        super.changeCoordinates(moveX, moveY)
    }

    override def display (g : Graphics, xMap : Int, yMap : Int, n : Int) : Unit = {
        if (Utils.debug) {
            g.drawRect(leftBox, topBox, rightBox - leftBox + mapDisplayer.sizeBlock, botBox - topBox + mapDisplayer.sizeBlock)
        }
        super.display(g, xMap, yMap, n)
    }


}

object SecondPlayerDisplayer extends OpponentDisplayer ("Characters/MainCharacter.png") {

    direction = Left
    
    interceptLength = 6
    player = ThirdCharacter
    speed = 1

    i = 8
    j = 0

    whichMap = 1
    speed = 10


}

object SecondCharacterDisplayer extends CharacterDisplayer ("Characters/Louis.png") {

    player = SecondCharacter

    direction = Up
    whichMap = 1
    i = 10
    j = 11


}


object EmptyCharacterDisplayer extends CharacterDisplayer ("Empty.png") {
    override def initialise : Unit = {}
    override def update : Unit = {}
}