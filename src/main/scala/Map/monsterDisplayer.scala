import java.awt.{Color,Graphics,BasicStroke,Font}
import java.io.File
import javax.swing.{JFrame, JPanel, JLabel}
import java.awt.image.BufferedImage

class MonsterDisplayer (imageName_ : String) extends Object with Descriptable {
    var x : Int = 0
    var y : Int = 0
    var width : Int = 164
    var height : Int = 164
    var imageName : String = imageName_
    var image : BufferedImage = Utils.loadImage(imageName)
    context = "Battle"


    def display (g : Graphics) : Unit = {
        g.drawImage(image, x, y, null)
    }

    def update : Unit = {}

    override def isMouseOver (xClick : Int, yClick : Int) : Boolean = {
        context == Utils.frame.currentState && x <= xClick && xClick <= (x + width) && y <= yClick && yClick <= (y + height)
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
        
        var liString = Utils.cutString(text, 30)

        var xToShow = (xMouse).max(20) 
        var yToShow = yMouse - 10 - 20*liString.length
 
        (0 until liString.length).foreach(x => xToShow = xToShow.min(widthWindow - metrics.stringWidth(liString(x)) - 20))
        (0 until liString.length).foreach(x => g.drawString(liString(x), xToShow, yToShow + 20*x))

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

        var liString = Utils.cutString(text, 30)

        var xToShow = (xMouse).max(20) 
        var yToShow = yMouse - 10 - 20*liString.length

        (0 until liString.length).foreach(x => xToShow = xToShow.min(widthWindow - metrics.stringWidth(liString(x)) - 20))
        (0 until liString.length).foreach(x => g.drawString(liString(x), xToShow, yToShow + 20*x))

    }
}