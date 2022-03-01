import java.awt.Graphics

class MapDisplayer (frame : UI) {

    var x : Int = 0
    var y : Int = -25
    var n : Int = 1 // when there will be several maps

    var grid : Array[Array[Block]] = Array.ofDim[Block](20, 20)

    var ui : UI = frame
    var sizeBlock : Int = 0

    var imgName : String = "Maps/1.png"
    var img = Utils.loadImage(imgName)


    def initialise (nMap : Int, sizeB : Int) : Unit = {
        n = nMap
        PlayerDisplayer.mapDisplayer = this
        SecondCharacterDisplayer.mapDisplayer = this
        sizeBlock = ui.sizeBlock
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

object EmptyMapDisplayer extends MapDisplayer (EmptyUI) {

}

class MapDisplayer1 (frame : UI) extends MapDisplayer (frame : UI) {
    for (i <- grid.indices) {
        for (j <- grid(i).indices) {
            grid(i)(j) = new EmptyBlock(i, j)
        }
    }
    grid(2)(1) = new MultiCliff(2, 1, 1, 0)
    grid(3)(1) = new MultiCliff(3, 1, 1, 0)
    grid(4)(1) = new MultiCliff(4, 1, 1, 1)
    grid(4)(2) = new MultiCliff(4, 2, 0, 1)
    grid(4)(3) = new MultiCliff(4, 3, 0, 1)
    grid(4)(4) = new MultiCliff(4, 4, -1, 1)
    grid(3)(4) = new MultiCliff(3, 4, -1, 0)
    grid(2)(4) = new MultiCliff(2, 4, -1, 0)

    grid(0)(2) = new GrassBlock(0, 2)
    grid(1)(3) = new GrassBlock(1, 3)
    grid(0)(3) = new GrassBlock(0, 3)
    grid(1)(2) = new GrassBlock(1, 2)

    for (i <- 7 to 13) {
        for (j <- 7 to 13) {
            grid(i)(j) = new IceBlock(i, j)
        }
    }
    for (i <- 7 to 13) {
        grid(i)(6) = new MultiCliff(i, 6, -1, 0)
        grid(i)(14) = new MultiCliff(i, 14, 1, 0)

        grid(6)(i) = new MultiCliff(6, i, 0, 1)
        grid(14)(i) = new MultiCliff(14, i, 0, -1)
    }
    grid(7)(6) = new EmptyBlock(7, 6)
    grid(10)(10) = new EmptyBlock(10, 10)
    grid(11)(10) = new EmptyBlock(11, 10)
    grid(10)(11) = new EmptyBlock(10, 11)


    grid(13)(13) = new RockBlock(13, 13)
    grid(12)(8) = new RockBlock(12, 8)
    grid(8)(9) = new RockBlock(8, 9)
    grid(9)(7) = new RockBlock(9, 7)
    grid(13)(8) = new RockBlock(13, 8)
    grid(11)(10) = new RockBlock(11, 10)
    grid(11)(11) = new RockBlock(11, 11)
    grid(11)(12) = new RockBlock(11, 12)
    grid(10)(12) = new RockBlock(10, 12)
    grid(9)(12) = new RockBlock(9, 12)
    grid(9)(11) = new RockBlock(9, 11)
    grid(9)(10) = new RockBlock(9, 10)


}