import java.awt.Graphics
import java.awt.image.BufferedImage
import java.util.concurrent.TimeUnit
import scala.collection.mutable.Queue


abstract class Direction
case object Up extends Direction
case object Down extends Direction
case object Left extends Direction
case object Right extends Direction


class CharacterDisplayer (imgName : String) extends Object with Updatable with Saveable {
    var direction : Direction = Down
    var interceptLength : Int = 0 // for opponents (we need it here for a foreach)

    var x : Int = 0
    var y : Int = 0

    //tile number on the map
    var i : Int = 0
    var j : Int = 0

    // for the zBuffer
    var previousI : Int = 0
    var previousJ : Int = 0

    var speed : Int = 10
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


    // some stats
    var traveledDistance : Int = 0
    var portalsCrossed : Int = 0
    var hasDoneSomething : Boolean = false

    Utils.characterDisplayers = this :: Utils.characterDisplayers

    def initialise : Unit = {
        //mapDisplayer = Utils.frame.mapDisplayer
        player.initialise
        mapDisplayer.grid(i)(j) foreach (b => b.walkable = false)
        alignCoordinates
    }

    def update : Unit = {
        if (mapDisplayer != null) {
            mapDisplayer.grid(i)(j).foreach (b => b.walkable = false)
        }
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
                if (mapDisplayer.grid(i+moveX)(j+moveY).reverse.forall(x => x.canBeWalked(this)) || noClip) {
                    traveledDistance += 1

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
        if (0 <= i - lastMoveX && i - lastMoveX < mapDisplayer.grid.size && 0 <= j - lastMoveY && j - lastMoveY < mapDisplayer.grid(i).size) {
            // this check is mandatory when changing map
            mapDisplayer.grid(i-lastMoveX)(j-lastMoveY) foreach (b => b.walkable = b.originalWalkable)
        }

        previousI = i
        previousJ = j

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
        var frame = Utils.frame
        /*shift the sprite on the tile such that its feet are at the right place*/
        var xshift = x + (Utils.frame.sizeBlock - width)/2 - xMap
        var yshift = y + (Utils.frame.sizeBlock - height) - yMap
        if (-frame.sizeBlock <= xshift && xshift <= frame.sizeX && -frame.sizeBlock <= yshift && yshift <= 400) {
            currentItem.display(g, xshift, yshift, this)
            g.drawImage(img,
                xshift, yshift, /*upper left corner coords on the map*/
                xshift + width, yshift +  height, /*lower right corner*/
                nx * width , ny * height,  /*upper left corner coords of the sprite on the tileset*/
                (nx + 1) * width  ,(ny + 1) * height , /*lower right corner*/
                null)
        }
    }

    //update the sprite used to render depending on its orientation
    def updateSprite() = {
        if (mapDisplayer.grid(i)(j).reverse.head.changingDirecton(direction) == -1) {
            direction match {
                case Up => ny = 3
                case Down => ny = 0
                case Right => ny = 2
                case Left => ny = 1
            }
        } else {
            ny = mapDisplayer.grid(i)(j).reverse.head.changingDirecton(direction)
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
            if (0 <= iInteracted && iInteracted < mapDisplayer.grid.length && 0 <= jInteracted && jInteracted < mapDisplayer.grid(iInteracted).length) {
                mapDisplayer.grid(iInteracted)(jInteracted) foreach (b => b.interact(this))
            }
        }
    }

    def getMapItem (item : MapItem) : Unit = {
        if (item.usable) {
            if (usableMapInventory.forall(x => x.name != item.name)) {
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

    override def toStringSave (tabs : Int) : String = {
        "\t"*tabs + "CharacterDisplayer : " + "\n" + 
        "\t"*(tabs+1) + "i : " + i + "\n" + 
        "\t"*(tabs+1) + "j : " + j + "\n" + 
        "\t"*(tabs+1) + "map : " + whichMap + "\n" + 
        player.toStringSave(tabs+2)
    }

}

abstract class OpponentDisplayer (imageName_ : String) extends CharacterDisplayer(imageName_) {
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
        var c = PlayerDisplayer
        if (player != null && !player.alreadyBeaten && !intercepting && interceptsPlayer && PlayerDisplayer != null && !PlayerDisplayer.isMoving && c.whichMap == whichMap) {
            
            c.direction = Utils.oppositeDirection(direction)

            intercepting = true
            c.canInteract = false
            new MoverToBattle(c.i, c.j, this).start
            
        }  
    }
}

object PlayerDisplayer extends CharacterDisplayer ("Characters/MainCharacter.png") {

    player = Player

    i = 0
    j = 0

    whichMap = 1
    speed = 10

    var topBox : Int = 0
    var botBox : Int = 0
    var leftBox : Int = 0
    var rightBox : Int = 0

    override def initialise : Unit = {
        super.initialise
        topBox = 3*mapDisplayer.sizeBlock
        botBox = 240
        leftBox = 4*mapDisplayer.sizeBlock
        rightBox = Utils.frame.sizeX - leftBox - mapDisplayer.sizeBlock
    }
    override def interactExplicitly : Unit = {
        var iInteracted : Int = i
        var jInteracted : Int = j

        {direction match {
            case Up => jInteracted -= 1
            case Down => jInteracted += 1
            case Left => iInteracted -= 1
            case Right => iInteracted += 1
        }}
        if (0 <= iInteracted && iInteracted < mapDisplayer.grid.length && 0 <= jInteracted && jInteracted < mapDisplayer.grid(iInteracted).length) {
            mapDisplayer.grid(iInteracted)(jInteracted) foreach (b => b.interact(this))
        }
        Utils.characterDisplayers.foreach(x => 
            if (x.i == iInteracted && x.j == jInteracted && x.whichMap == whichMap && !(x.player.alreadyBeaten)) {
                x.direction = Utils.oppositeDirection(direction); 
                x.interceptLength = 1
            })
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

    override def toStringSave (tabs : Int) : String = {
        "\t"*tabs + "PlayerDisplayer : \n" + 
        "\t"*(tabs+1) + "i : " + i + "\n" + 
        "\t"*(tabs+1) + "j : " + j + "\n" + 
        "\t"*(tabs+1) + "map : " + whichMap + "\n" + 
        player.toStringSave(tabs+2) +
        usableMapInventory.map(x => x.toStringSave(tabs+1) + "\n").foldLeft("")((x, y) => x+y) + 
        notUsableMapInventy.map(x => x.toStringSave(tabs+1) + "\n").foldLeft("")((x, y) => x+y)
    }


}