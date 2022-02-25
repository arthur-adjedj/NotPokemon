import javax.swing.{JFrame, JPanel, JLabel}
import java.io.File
import java.awt.{Color,Graphics,BasicStroke,Font}
import java.awt.image.BufferedImage
import java.util.concurrent.TimeUnit

object Utils {

    var playerDisplayers : List[PlayerDisplayer] = List()
    Repainter.start

    var debug = false

    def print[T] (s : T) : Unit = {
        if (debug) {
            println(s)
        }
    }


    def loadImage (name : String) : BufferedImage = {
        try {
            javax.imageio.ImageIO.read(getClass.getResource(name))
        }
        catch {
            case _ : Throwable => Utils.print("Issues while importing " + name); javax.imageio.ImageIO.read(getClass.getResource("Empty.png"))
        }
    }

    def findFirstOccurenceArray [T](array : Array[T], element : T) : Int = {
        var i : Int = 0
        var found : Boolean = false
        while (!found && i < array.length) {
            found = array(i) == element
            i += 1
        }
        if (found) {
            i - 1 
        } else {
            -1
        }
    }

    def cutString (s : String, charPerLine : Int) : (String, String, String) = {
        var text1 = ""
        var text2 = ""
        var text3 = ""
        if (s.length <= charPerLine) {
            text1 = s
        } else {
            var l = s.substring(0, charPerLine).lastIndexOf(" ")
            text1 = s.substring(0, l)
            text2 = s.substring(l+1)
            if (text2.length > charPerLine) {
                var s = text2
                l = s.substring(0, charPerLine).lastIndexOf(" ")
                text2 = s.substring(0, l)
                text3 = s.substring(l+1)
            }
        }
        (text1, text2, text3)
    }

    var repaintables : List[Repaintable] = List()

}

trait Repaintable {
    Utils.repaintables = this :: Utils.repaintables
    def repaint() : Unit
}

class Mover extends Thread {
    var lastMoveX : Int = 0
    var lastMoveY : Int = 0
    var playerdisplayer : PlayerDisplayer = EmptyPlayerDisplayer

    
    def move (moveX : Int, moveY : Int) : Unit = {
        lastMoveX = moveX
        lastMoveY = moveY
        start
    }

    override def run : Unit = {
        for (i <- 0 to playerdisplayer.mapDisplayer.sizeBlock - 1) {
            playerdisplayer.mapUI.pane.repaint()
            playerdisplayer.changeCoordinates(lastMoveX, lastMoveY)
            playerdisplayer.mapUI.pane.repaint()
            TimeUnit.MILLISECONDS.sleep(100/playerdisplayer.speed)
            playerdisplayer.mapUI.pane.repaint()
            if (i % (playerdisplayer.mapDisplayer.sizeBlock/4) == 0) {
                playerdisplayer.nx = (playerdisplayer.nx + 1) % 4
            }
        }
        playerdisplayer.endMove
    }
}

object Repainter extends Thread {
    override def run : Unit = {
        while (true) {
            Utils.repaintables.foreach(x => x.repaint())
            TimeUnit.MILLISECONDS.sleep(100/6)
        }
    }
}