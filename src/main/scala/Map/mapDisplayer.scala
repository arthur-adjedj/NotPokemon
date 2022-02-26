import java.awt.Graphics

class MapDisplayer (mapUI : MapUI, sizeB : Int) {

    var x : Int = 0
    var y : Int = 0
    var n : Int = 1

    var grid : Array[Array[Block]] = Array.ofDim[Block](20, 20)

    var sizeBlock : Int = sizeB

    var imgName : String = "Maps/1.png"
    var img = Utils.loadImage(imgName)

    def initialise (nMap : Int, sizeB : Int) : Unit = {
        n = nMap
        PlayerDisplayer.mapDisplayer = this
        SecondCharacterDisplayer.mapDisplayer = this
    }
    


    def display (g : Graphics) : Unit = {
        g.drawImage(img, x, y, null)
        for (i <- grid.indices) {
            for (j <- grid(i).indices) {
                grid(i)(j).updateCoordinates(x, y, sizeBlock)
                grid(i)(j).display(g)
            }
        }
        //sorts the character rendering order in respect to their depth on screen
        Utils.characterDisplayers = Utils.characterDisplayers.sortWith((p1,p2) => p1.j < p2.j)
        Utils.characterDisplayers.foreach(p => p.display(g, x, y, n))
    }


    def changeCoordinates (moveX : Int, moveY : Int) : Unit = {
        x += moveX
        y += moveY
    }
}

object EmptyMapDisplayer extends MapDisplayer (EmptyMapUI, 0) {

}

class MapDisplayer1 (mapUI : MapUI, sizeB : Int) extends MapDisplayer (mapUI, sizeB) {
    for (i <- grid.indices) {
        for (j <- grid(i).indices) {
            grid(i)(j) = new EmptyBlock(i, j)
        }
    }
    grid(2)(1) = new WallBlock(2, 1)
    grid(3)(1) = new WallBlock(3, 1)
    grid(4)(1) = new WallBlock(4, 1)
    grid(4)(2) = new WallBlock(4, 2)
    grid(4)(3) = new WallBlock(4, 3)
    grid(4)(4) = new WallBlock(4, 4)
    grid(3)(4) = new WallBlock(3, 4)
    grid(2)(4) = new WallBlock(2, 4)


    grid(10)(10) = new GrassBlock(10, 10)
    grid(11)(11) = new GrassBlock(11, 11)
    grid(10)(11) = new GrassBlock(10, 11)
    grid(11)(10) = new GrassBlock(11, 10)
}