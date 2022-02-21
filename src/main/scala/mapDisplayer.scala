import java.awt.Graphics

class MapDisplayer {

    var x : Int = 0
    var y : Int = 0
    var n : Int = 0

    var imgName : String = "Maps/1.png"
    var img = Utils.loadImage(imgName)

    def initialise (nMap : Int) : Unit = {
        n = nMap
    }
    


    def display (g : Graphics) : Unit = {
        g.drawImage(img, x, y, null)
        Utils.players.foreach(p => p.displayer.display(g, x, y, n))
    }


    def move (moveX : Int, moveY : Int) : Unit = {
        x += moveX
        y += moveY
    }
}