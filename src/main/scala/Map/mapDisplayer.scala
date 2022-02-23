import java.awt.Graphics

class MapDisplayer (mapUI : MapUI, sizeB : Int){

    var x : Int = 0
    var y : Int = 0
    var n : Int = 1

    var grid : Array[Array[Int]] = Array.fill(10){Array.fill(10){0}}

    var sizeBlock : Int = sizeB

    var imgName : String = "Maps/1.png"
    var img = Utils.loadImage(imgName)

    def initialise (nMap : Int, sizeB : Int) : Unit = {
        n = nMap
        FirstPlayerDisplayer.mapDisplayer = this
    }
    


    def display (g : Graphics) : Unit = {
        g.drawImage(img, x, y, null)
        Utils.playersDisplayers.foreach(p => p.display(g, x, y, n))
        for (i <- 0 to 9) {
            for (j <- 0 to 9) {
                if (grid(i)(j) != 0) {
                    g.fillRect(i*sizeBlock, j*sizeBlock, sizeBlock, sizeBlock)
                }
            }
        }
    }


    def changeCoordinates (moveX : Int, moveY : Int) : Unit = {
        x += moveX
        y += moveY
    }
}

object EmptyMapDisplayer extends MapDisplayer (EmptyMapUI, 0) {

}