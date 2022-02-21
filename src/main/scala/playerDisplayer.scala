import java.awt.Graphics


class PlayerDisplayer (imgNam : String) {
    var x : Int = 0
    var y : Int = 0
    var whichMap : Int = 0
    var imgName : String = imgNam
    var img = Utils.loadImage(imgName)

    def display (g : Graphics, xMap : Int, yMap : Int, n : Int) : Unit = {
        if (n == whichMap) {
            g.drawImage(img, x - xMap, y - yMap, null)
        }
    }
}


object FirstPlayerDisplayer extends PlayerDisplayer ("Players/FirstPlayer.png") {

}

object EmptyPlayerDisplayer extends PlayerDisplayer ("Empty.png") {}