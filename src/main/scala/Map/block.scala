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

    def initialise (iMap : Int, jMap : Int) : Unit = {
        walkable = originalWalkable
        i = iMap
        j = jMap
    }

    def display (g : Graphics) : Unit = {
        g.drawImage(img, x, y, null)
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
//renders a "base block" given a certain orientation with updown and leftright in {-1,0,1}
class MultiBlock(updown : Int, leftright : Int, base : String) extends Block("Maps/Tile.png") {
    var upDownStr : String = updown match {
        case (-1) => "Bot"
        case 1 => "Top"
        case _ => ""
    }
    var leftRightStr : String = leftright match {
        case (-1) => "Left"
        case 1 => "Right"
        case _ => ""
    }

    imgName = "Blocks/" + base + upDownStr + leftRightStr + ".png"
    img = Utils.loadImage(imgName)
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
}

class HealBlock extends Block ("Blocks/Heal.png") {
    originalWalkable = false

    override def interact (c : CharacterDisplayer) : Unit = {
        c.player.team.foreach(x => x.heal(x.hpMax))
    }

}

class EmptyBlock extends Block ("Empty.png") {
    override def display (g : Graphics) : Unit = {}
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
    }
}

class MapItemBlock (item_ : MapItem) extends Block ("Empty.png") {
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
        }
    }

    override def display (g : Graphics) : Unit = {
        if (!taken) {
            super.display(g)
        }
    }
}