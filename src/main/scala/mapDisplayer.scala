import java.awt.Graphics

class MapDisplayer (mapUI : MapUI, sizeBlock : Int){

    var x : Int = 0
    var y : Int = 0
    var n : Int = 1

    var sizeBlock : Int = 1

    var imgName : String = "Maps/1.png"
    var img = Utils.loadImage(imgName)

    def initialise (nMap : Int, sizeB : Int) : Unit = {
        n = nMap
        FirstPlayerDisplayer.mapDisplayer = this
    }
    


    def display (g : Graphics) : Unit = {
        g.drawImage(img, x, y, null)
        Utils.playersDisplayers.foreach(p => p.display(g, x, y, n))
    }


    def move (moveX : Int, moveY : Int) : Unit = {
        x += moveX
        y += moveY
    }
}

object EmptyMapDisplayer extends MapDisplayer {

}