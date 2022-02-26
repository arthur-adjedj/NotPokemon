import java.awt.{Color,Graphics,BasicStroke,Font}
import java.io.File
import javax.swing.{JFrame, JPanel, JLabel}
import java.awt.image.BufferedImage

class MonsterDisplayer (imageNam : String) extends Object with Descriptable {
    var x : Int = 0
    var y : Int = 0
    var width : Int = 164
    var height : Int = 164
    var imageName : String = imageNam
    var image : BufferedImage = Utils.loadImage(imageName)


    def display (g : Graphics) : Unit = {
        g.drawImage(image, x, y, null)
    }

    def update : Unit = {}

    override def isMouseOver (xClick : Int, yClick : Int) : Boolean = {
        x <= xClick && xClick <= (x + width) && y <= yClick && yClick <= (y + height)
    }
}

object PlayerMonsterDisplayer extends MonsterDisplayer ("Empty.png") {
    x = 75
    y = 141
    
    override def update : Unit = {
        imageName = Player.currentMonster.imgNameBack
        image = Utils.loadImage(imageName)
        y = 141 + Player.currentMonster.uiYShift
    }

    override def onMouseOver (g : Graphics, xMouse : Int, yMouse : Int, widthWindow : Int, heightWindow : Int) : Unit = {
        var metrics = g.getFontMetrics
        var statusString = Player.currentMonster.status.map(x => x.name).fold("")((x, y) => x + ", " + y)
        var text = ""

        if (statusString != "") {
            text = Player.currentMonster.toString + "His status are : " + statusString.substring(1, statusString.length)
        } else {
            text = Player.currentMonster.toString
        }
        var (t1, t2, t3) = Utils.cutString(text, 30)

        var xToShow = xMouse
        var yToShow = yMouse - 10

        xToShow = (xMouse).min(widthWindow - metrics.stringWidth(t1) - 20).min(widthWindow - metrics.stringWidth(t2) - 20).max(20)

        if (t2 != "") {
            yToShow -= 20
        } 
        if (t3 != "") {
            yToShow -= 20
        }

        g.drawString(t1, xToShow, yToShow)
        g.drawString(t2, xToShow, yToShow + 20)
        g.drawString(t3, xToShow, yToShow + 40)

    }
}

object OpponentMonsterDisplayer extends MonsterDisplayer ("Empty.png") {
    x = 370
    y = 35

    override def update : Unit = {
        imageName = Player.opponent.currentMonster.imgNameFront
        image = Utils.loadImage(imageName)
        y = 35
    }

    override def onMouseOver (g : Graphics, xMouse : Int, yMouse : Int, widthWindow : Int, heightWindow : Int) : Unit = {
        var metrics = g.getFontMetrics
        var statusString = Player.opponent.currentMonster.status.map(x => x.name).fold("")((x, y) => x + ", " + y)
        var text = ""
        
        if (statusString != "") {
            text = Player.opponent.currentMonster.toString + "His status are : " + statusString.substring(1, statusString.length)
        } else {
            text = Player.opponent.currentMonster.toString
        }

        var (t1, t2, t3) = Utils.cutString(text, 30)

        var xToShow = xMouse
        var yToShow = yMouse - 10

        xToShow = (xMouse).min(widthWindow - metrics.stringWidth(t1) - 20).min(widthWindow - metrics.stringWidth(t2) - 20).max(20)

        if (t2 != "") {
            yToShow -= 20
        } 
        if (t3 != "") {
            yToShow -= 20
        }

        g.drawString(t1, xToShow, yToShow)
        g.drawString(t2, xToShow, yToShow + 20)
        g.drawString(t3, xToShow, yToShow + 40)

    }
}