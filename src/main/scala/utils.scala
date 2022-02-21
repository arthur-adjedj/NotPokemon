import javax.swing.{JFrame, JPanel, JLabel}
import java.io.File
import java.awt.{Color,Graphics,BasicStroke,Font}
import java.awt.image.BufferedImage
import javax.swing.DebugGraphics

object Utils {

    var playersDisplayers : List[PlayerDisplayer] = List()




    def loadImage (name : String) : BufferedImage = {
        try {
            javax.imageio.ImageIO.read(getClass.getResource(name))
        }
        catch {
            case _ : Throwable => println("Issues while importing " + name); javax.imageio.ImageIO.read(getClass.getResource("Empty.png"))
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




}