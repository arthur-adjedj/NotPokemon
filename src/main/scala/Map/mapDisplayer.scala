import java.awt.Graphics

class MapDisplayer (frame : UI) {

    var x : Int = 0
    var y : Int = -25
    var n : Int = -1 // when there will be several maps

    var iStart : Int = 0
    var jStart : Int = 0

    var sizeMap : Int = 20
    var grid : Array[Array[List[Block]]] = Array.fill(sizeMap)(Array.fill(sizeMap)(List(new EmptyBlock)))

    var ui : UI = frame
    var sizeBlock : Int = 0

    var imgName : String = "Maps/1.png"
    var img = Utils.loadImage(imgName)



    def initialise (sizeB : Int) : Unit = {
        PlayerDisplayer.mapDisplayer = this
        SecondCharacterDisplayer.mapDisplayer = this
        SecondPlayerDisplayer.mapDisplayer = this
        sizeBlock = ui.sizeBlock
        for (i <- 0 to sizeMap - 1) {
            for (j <- 0 to sizeMap - 1) {
                grid(i)(j) foreach (b => b.initialise(i, j))
            }
        }
    }

    def update : Unit = {
        for (i <- 0 to sizeMap - 1) {
            for (j <- 0 to sizeMap - 1) {
                grid(i)(j) foreach (b => b.updateCoordinatesOnMap(i, j))
            }
        }
        Utils.characterDisplayers.foreach(x => if (x.whichMap == PlayerDisplayer.whichMap) x.update)
    }
    


    def display (g : Graphics) : Unit = {
        g.drawImage(img, x, y, null)
        for (i <- grid.indices) {
            for (j <- grid(i).indices) {
                grid(i)(j) foreach (b => b.updateCoordinates(x, y, sizeBlock))
                grid(i)(j) foreach (b => b.display(g))
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

    n = 1
    iStart = 14
    jStart = 2
    
    grid(2)(1) = List(new MultiCliff(1, 0))
    grid(3)(1) = List(new MultiCliff(1, 0))
    grid(4)(1) = List(new MultiCliff(1, 1))
    grid(4)(2) = List(new MultiCliff(0, 1))
    grid(4)(3) = List(new MultiCliff(0, 1))
    grid(4)(4) = List(new MultiCliff(-1, 1))
    grid(3)(4) = List(new MultiCliff(-1, 0))
    grid(2)(4) = List(new MultiCliff(-1, 0))

    grid(0)(2) = List(new GrassBlock)
    grid(1)(3) = List(new GrassBlock)
    grid(0)(3) = List(new GrassBlock)
    grid(1)(2) = List(new GrassBlock)

    for (i <- 7 to 13) {
        for (j <- 7 to 13) {
            grid(i)(j) = List(new IceBlock)
        }
    }
    for (i <- 7 to 13) {
        grid(i)(6) = List(new MultiCliff(-1, 0))
        grid(i)(14) = List(new MultiCliff(1, 0))

        grid(6)(i) = List(new MultiCliff(0, 1))
        grid(14)(i) = List(new MultiCliff(0, -1))
    }
    grid(7)(6) = List(new EmptyBlock)

    grid(13)(13) = List(new IceBlock,new RockBlock)
    grid(12)(8) = List(new IceBlock,new RockBlock)
    grid(8)(9) = List(new IceBlock,new RockBlock)
    grid(9)(7) = List(new IceBlock,new RockBlock)
    grid(13)(8) = List(new IceBlock,new RockBlock)
    grid(11)(10) = List(new IceBlock,new RockBlock)
    grid(11)(11) = List(new IceBlock,new RockBlock)
    grid(11)(12) = List(new IceBlock,new RockBlock)
    grid(10)(12) = List(new IceBlock,new RockBlock)
    grid(9)(12) = List(new IceBlock,new RockBlock)
    grid(9)(11) = List(new IceBlock,new RockBlock)
    grid(9)(10) = List(new IceBlock,new RockBlock)

    grid(10)(1) = List(new HealBlock)

    grid(10)(3) = List(new MapItemBlock(new Bike))
    
    grid(3)(12) = List(new Door(1))
    grid(1)(12) = List(new MapItemBlock(new Key(1)))

    grid(10)(6) = List(new Door(2))
    


}