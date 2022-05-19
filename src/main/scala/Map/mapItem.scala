//These items are unique, you cannot stack them
import java.awt.Graphics

abstract class MapItem extends Object with Saveable {
    var id : Int = -1
    var name : String = "Nothing"
    override def toStringSave (tabs : Int) : String = "-MapItem : " + name

    // to know if we can select this item or not
    var usable : Boolean = true
    
    def imgName : String = "Items/" + name + ".png"
    def onSelect (p : CharacterDisplayer) : Unit = {}
    def onUnselect (p : CharacterDisplayer) : Unit = {}
    def display (g : Graphics, x : Int, y : Int, c : CharacterDisplayer) : Unit = {}
}

class Key (id_ : Int) extends MapItem {
    id = id_
    usable = false
    name = "Key"

    override def toStringSave (tabs : Int) : String = "-MapItem : " + name + " " + id
}

class Bike extends MapItem {
    name = "Bike"
    override def onSelect (p : CharacterDisplayer) : Unit = {
        p.speed *= 2
    }

    override def onUnselect (p : CharacterDisplayer) : Unit = {
        p.speed /= 2
    }
}

class Surf extends MapItem {
    name = "Surf"
    def imgName (direction: Direction) : String = {
        "Items/Surfboard" + Utils.directionToString(direction) + ".png"
    }

    override def display (g : Graphics, x : Int, y : Int, c : CharacterDisplayer) : Unit = {
        g.drawImage(Utils.loadImage(imgName(c.direction)), x+10, y+20, null)
    }
}

object EmptyMapItem extends MapItem {

}