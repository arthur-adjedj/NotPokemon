import javax.swing.{JFrame, JPanel, JLabel}
import java.io.File
import java.awt.{Color,Graphics,BasicStroke,Font}
import java.awt.image.BufferedImage

object Utils {
    def loadImage (name : String) : BufferedImage = {
        try {
            javax.imageio.ImageIO.read(getClass.getResource(name))
        }
        catch {
            case _ : Throwable => println("Issues while importing " + name); javax.imageio.ImageIO.read(getClass.getResource("Empty.png"))
        }
    }




}