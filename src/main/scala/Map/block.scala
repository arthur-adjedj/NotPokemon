import java.awt.Graphics
import java.util.concurrent.TimeUnit


abstract class Block (imageName_ : String) {
    var x : Int = 0
    var y : Int = 0
    var i : Int = 0
    var j : Int = 0

    var imgName : String = imageName_
    var img = Utils.loadImage(imgName)

    var originalWalkable : Boolean = true
    var walkable : Boolean = true
    var slippery : Boolean = false
    var stopsAnimation : Boolean = false

    def changingDirecton (d : Direction) : Int = -1 // if it doesn't change the direction then -1 else the index of the direction on the sprite

    def canBeWalked (c : CharacterDisplayer) : Boolean = walkable

    def initialise (iMap : Int, jMap : Int) : Unit = {
        walkable = originalWalkable
        i = iMap
        j = jMap
    }

    def display (g : Graphics) : Unit = {
        var frame = Utils.frame
        // we only draw on the screen (if swing doesn't already handle it)
        if (-frame.sizeBlock <= x && x <= frame.sizeX && -frame.sizeBlock <= y && y <= 400) {
            g.drawImage(img, x, y, null)
        }
    }
    
    def updateCoordinatesOnMap (iMap : Int, jMap : Int) : Unit = {
        walkable = originalWalkable
        i = iMap
        j = jMap
    }

    def updateCoordinates (xMap : Int, yMap : Int, sizeBlock : Int) : Unit = {
        // when the map is moving
        x = i*sizeBlock - xMap
        y = j*sizeBlock - yMap
    }
    
    def interact (player : CharacterDisplayer) : Unit = {}

    def onWalk (player : CharacterDisplayer) : Unit = {}



}

class EmptyBlock extends Block ("Empty.png") {
    override def display (g : Graphics) : Unit = {}
}

class InvisibleBlock extends Block ("Empty.png") {
    originalWalkable = false
    override def display (g : Graphics) : Unit = {}

}

//renders a "base block" given a certain orientation with updown and leftright in {-1,0,1}
class MultiBlock(updown : Int, leftright : Int, base : String) extends Block("Maps/Tile.png") {
    imgName = "Blocks/" + base + "Sprites.png"
    img = Utils.loadImage(imgName)

    override def display (g : Graphics) : Unit = {
        var frame = Utils.frame
        // we only draw on the screen (if swing doesn't already handle it)
        if (-frame.sizeBlock <= x && x <= frame.sizeX && -frame.sizeBlock <= y && y <= 400) {
            g.drawImage(img, x, y, x+40, y+40, (leftright+1)*40, (1-updown)*40, (leftright+2)*40,(2-updown)*40, null)
        }
    }

}

class RockBlock extends Block("Blocks/Rock.png") {
    originalWalkable = false
}

class MultiCliff(updown : Int, leftright : Int) extends MultiBlock(updown, leftright, "Cliff") {
    originalWalkable = false
}

class GrassBlock extends Block ("Blocks/Grass.png") {
    override def onWalk (player : CharacterDisplayer) : Unit = {
        if (player.player.name == "You") {
            if (scala.util.Random.nextFloat() < 0.1) {
                PlayerDisplayer.canInteract = false
                WildCharacter.initialise
                Utils.frame.startBattle(Player, WildCharacter)
            }
        }
    }
}

class IceBlock extends Block ("Blocks/Ice.png") {
    slippery = true
    stopsAnimation = true
}

class MultiWater(updown : Int, leftright : Int) extends MultiBlock(updown, leftright, "Water") {
    stopsAnimation = true
    override def canBeWalked (c : CharacterDisplayer) : Boolean = {
        c.currentItem.name == "Surf"
    }

    override def changingDirecton (d : Direction) : Int = {
        d match {
            case Up => 1
            case Down => 2
            case Right => 3
            case Left => 0
        }
    }
}

class HealBlock extends Block ("Blocks/Item.png") {
    originalWalkable = false

    override def interact (c : CharacterDisplayer) : Unit = {
        c.player.team.foreach(x => x.heal(x.hpMax))
    }

}

class Door (idKey_ : Int) extends Block ("Blocks/ClosedDoor.png") {
    originalWalkable = false
    var otherImgName = "Blocks/OpenDoor.png"
    var unlocked : Boolean = false
    var idKey : Int = idKey_

    override def interact (c : CharacterDisplayer) : Unit = {
        if (unlocked) {
            originalWalkable = !originalWalkable
            var temp = otherImgName
            otherImgName = imgName
            imgName = temp
            img = Utils.loadImage(imgName)
        } else if (idKey == -1 || c.notUsableMapInventy.exists(x => x.id == idKey)) {
            unlocked = true
            DiscussionLabel.changeText("You unlocked the door !")
            interact(c)
        } else {
            DiscussionLabel.changeText("The door is locked !")
        }
        walkable = originalWalkable
    }
}

class MapItemBlock (item_ : MapItem) extends Block ("Blocks/Item.png") {
    var item : MapItem = item_
    var taken : Boolean = false
    originalWalkable = false

    override def initialise (iMap : Int, jMap : Int) : Unit = {
        super.initialise(iMap, jMap)
        imgName = item.imgName
        img = Utils.loadImage(imgName)
    }

    override def interact (c : CharacterDisplayer) : Unit = {
        if (!taken) {
            taken = true
            c.getMapItem(item)
            originalWalkable = true
            walkable = originalWalkable
        }
    }

    override def display (g : Graphics) : Unit = {
        if (!taken) {
            super.display(g)
        }
    }
}

class TreeRoot extends Block ("/Blocks/Tree.png") {
    originalWalkable = false

    override def display (g : Graphics) : Unit = {
        var frame = Utils.frame
        if (-3*frame.sizeBlock <= x && x <= frame.sizeX && -3*frame.sizeBlock <= y && y <= 480) {
            //a tree is bigger than 2x2 blocks in size, we consider the root of the tree to be the downmost leftmost block, and fill in the 3 others with invisible unwalkable blocks
            g.drawImage(img, x-10, y-73, null)
        }
    }
}



class Portal (n_ : Int) extends Block ("/Blocks/Portal.png") {
    var iTarget : Int = -1
    var jTarget : Int = -1
    var n = n_
    originalWalkable = true

    override def onWalk (c : CharacterDisplayer) : Unit = {
        if (Utils.mapDisplayers(n-1) != EmptyMapDisplayer) {
            Utils.frame.changeMap(Utils.mapDisplayers(n-1), iTarget, jTarget)
            c.previousJ = jTarget + 1
            c.portalsCrossed += 1
        } else {
            Utils.mapDisplayers(n-1) = n match {
                case 1 => new MapDisplayer1(Utils.frame)
                case 2 => new MapDisplayer2(Utils.frame)
            } 
            onWalk(c)
       }
    }
}

class TargetedPortal (n_ : Int, iTarget_ : Int, jTarget_ : Int) extends Portal (n_) {
    iTarget = iTarget_
    jTarget = jTarget_
}