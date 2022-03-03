import java.io.File
import java.awt.event.MouseEvent
import java.awt.{Color,Graphics,BasicStroke,Font}
import java.util.concurrent.TimeUnit
import javax.swing.{JFrame, JPanel, JLabel}



abstract class MyButton (imageName_ : String) extends Object with Descriptable {
    var originalImageName = imageName_
    var imageName = imageName_
    var x : Int = 0
    var y : Int = 0
    var width : Int = 300
    var height : Int = 136
    var image = Utils.loadImage(imageName)
    var visible : Boolean = false
    var clickable : Boolean = true
    var poke_font : Font = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream("PokemonPixelFont.ttf"))
    poke_font = poke_font.deriveFont(Font.PLAIN,30)
    var text : String = ""
    var xtext  = 0
    var ytext = 0
    def display (g : Graphics) : Unit = {
        if (visible) {
            g.setFont(poke_font)
            var metrics = g.getFontMetrics(poke_font);
            // Coordonées du texte
            xtext = x + (width - metrics.stringWidth(text)) / 2;
            ytext = y + ((height - metrics.getHeight()) / 2) + metrics.getAscent();
            g.drawImage(image, x, y, null)
            g.drawString(text,xtext,ytext)
        }
    }

    def onClick (xClick : Int, yClick : Int) : Boolean = {
        if (visible && x <= xClick && xClick <= (x + width) && y <= yClick && yClick <= (y + height) && clickable) {
            isClicked
            true // tells the listener that the button is clicked
        } else {
            false
        }
  }

    def isClicked : Unit = {
        // function called when the button is clicked
        Utils.print("The button is clicked")
    }

    def setVisible(b : Boolean) : Unit = {
        visible = b
        update
    }

    def update : Unit = {
        clickable = visible
    }

    override def isMouseOver (x_click : Int, y_click : Int) : Boolean = {
        visible && x <= x_click && x_click <= (x + width) && y <= y_click && y_click <= (y + height) && clickable
    }
}

class CloseButton (imageName_ : String, closeFunction : () => Unit, posX : Int, posY : Int) extends MyButton (imageName_) {
    x = posX
    y = posY
    width = 15
    height = 15
    visible = true
    override def isClicked : Unit = {
        closeFunction()
    }
}

class HelpButton (imageName_ : String, helpFunction : () => Unit) extends MyButton (imageName_) {
    x = 0
    y = 0
    width = 15
    height = 15
    visible = true
    override def isClicked : Unit = {
        helpFunction()
    }
}

abstract class CastAttackButton (imageName_ : String) extends MyButton (imageName_) {
    var n : Int = 0
    visible = false
    override def isClicked : Unit = {
        if (Player.chooseAttack(n)) {

            CastAttackButton1.setVisible(false)
            CastAttackButton2.setVisible(false)
            CastAttackButton3.setVisible(false)
            CastAttackButton4.setVisible(false)

            BackButton.setVisible(false)

            AttackButton.setVisible(true)
            BagButton.setVisible(true)
            MonsterButton.setVisible(true)
            RunButton.setVisible(true)
        }
    }

    override def update : Unit = {
        if (Player.currentMonster.attacks(n).name != "Empty") {
            imageName = Player.currentMonster.attacks(n).attackType.imageButtonName
            text = Player.currentMonster.attacks(n).name
            clickable = visible
        } else {
            imageName = "Buttons/EmptyButton.png"
            text = "Empty"
            clickable = false
        }
        image = Utils.loadImage(imageName)
    }

    override def onMouseOver (g : Graphics, x : Int, y : Int, width : Int, height : Int) : Unit = {
        var metrics = g.getFontMetrics
        var text = Player.currentMonster.attacks(n).toString
        var (t1, t2, t3) = Utils.cutString(text, 40)

        var xToShow = x
        var yToShow = y - 10
        if (n % 2 == 1) {
            xToShow = (x - 200).min(width - metrics.stringWidth(t1) - 20).min(width - metrics.stringWidth(t2) - 20)
        } else {
            xToShow = (x).min(width - metrics.stringWidth(t1) - 20).min(width - metrics.stringWidth(t2) - 20).max(20)
        }

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

abstract class ChangeMonsterButton (imageName_ : String) extends MyButton (imageName_) {
    var n : Int = 0
    visible = false

    override def isClicked : Unit = {
        if (Player.changeMonster(n)) {
            ChangeMonsterButton1.setVisible(false)
            ChangeMonsterButton2.setVisible(false)
            ChangeMonsterButton3.setVisible(false)
            ChangeMonsterButton4.setVisible(false)
            ChangeMonsterButton5.setVisible(false)
            ChangeMonsterButton6.setVisible(false)

            
            AttackButton.setVisible(true)
            BagButton.setVisible(true)
            MonsterButton.setVisible(true)
            RunButton.setVisible(true)
        }        
    }

    override def isMouseOver (x_click : Int, y_click : Int) : Boolean = {
        visible && x <= x_click && x_click <= (x + width) && y <= y_click && y_click <= (y + height) && (Player.team(n).name != "Empty") 
    }

    override def onMouseOver (g : Graphics, x : Int, y : Int, width : Int, height : Int) : Unit = {
        var metrics = g.getFontMetrics
        var text = Player.team(n).toString
        var (t1, t2, t3) = Utils.cutString(text, 40)

        var xToShow = x
        var yToShow = y - 10
        if (n % 2 == 1) {
            xToShow = (x - 200).min(width - metrics.stringWidth(t1) - 20).min(width - metrics.stringWidth(t2) - 20)
        } else {
            xToShow = (x).min(width - metrics.stringWidth(t1) - 20).min(width - metrics.stringWidth(t2) - 20).max(20)
        }

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

    override def update : Unit = {
        if (Player.team(n).alive && Player.team(n).name != "Empty") {
            imageName = Player.team(n).monsterType.imageButtonName
            clickable = visible
        } else {
            imageName = "Buttons/EmptyButton.png"
            clickable = false
        }
        text = Player.team(n).name
        image = Utils.loadImage(imageName)
    }

}

abstract class UseItemButton (imageName_ : String) extends MyButton (imageName_) {
    var n : Int = 0
    visible = false

    override def isClicked : Unit = {
        if (Player.useItem(n)) {
            UseItemButton1.setVisible(false)
            UseItemButton2.setVisible(false)
            UseItemButton3.setVisible(false)
            UseItemButton4.setVisible(false)

            BackButton.setVisible(false)
            NextPageItemButton.setVisible(false)
            
            AttackButton.setVisible(true)
            BagButton.setVisible(true)
            MonsterButton.setVisible(true)
            RunButton.setVisible(true)
        }        
    }

    override def onMouseOver (g : Graphics, xMouse : Int, yMouse : Int, widthWindow : Int, heightWindow : Int) : Unit = {
        var metrics = g.getFontMetrics
        var text = Player.usableInventory(n).toString
        var (t1, t2, t3) = Utils.cutString(text, 40)

        var xToShow = xMouse
        var yToShow = yMouse - 10
        if (n % 2 == 1) {
            xToShow = (xMouse - 200).min(widthWindow - metrics.stringWidth(t1) - 20).min(widthWindow - metrics.stringWidth(t2) - 20)
        } else {
            xToShow = (xMouse).min(widthWindow - metrics.stringWidth(t1) - 20).min(widthWindow - metrics.stringWidth(t2) - 20).max(20)
        }

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

    override def update : Unit = {
        if (Player.usableInventory(n).name != "Empty" && Player.usableInventory(n).usable) {
            imageName = "Buttons/BagButton.png"
            clickable = visible
        } else {
            imageName = "Buttons/EmptyButton.png"
            clickable = false
        }
        text = Player.usableInventory(n).name + "(" + Player.usableInventory(n).amount + ")"
        image = Utils.loadImage(imageName)
    }
}



object AttackButton extends MyButton ("Buttons/AttackButton.png") {
    x = 3
    y = 405
    visible = true
    text = "Attack"

    override def isClicked : Unit = {


        AttackButton.setVisible(false)
        BagButton.setVisible(false)
        MonsterButton.setVisible(false)
        RunButton.setVisible(false)

        CastAttackButton1.setVisible(true)
        CastAttackButton2.setVisible(true)
        CastAttackButton3.setVisible(true)
        CastAttackButton4.setVisible(true)

        BackButton.x = 154
        BackButton.y = 693
        BackButton.setVisible(true)
    }

    override def update : Unit = {
        if (Player.currentMonster.alive) {
            imageName = originalImageName
            clickable = visible
        } else {
            imageName = "Buttons/EmptyButton.png"
            clickable = false
        }
        image = Utils.loadImage(imageName)
    }
}


object BagButton extends MyButton ("Buttons/BagButton.png") {
    x = 311
    y = 405
    visible = true
    text = "Bag"


    override def isClicked : Unit = {
        AttackButton.setVisible(false)
        BagButton.setVisible(false)
        MonsterButton.setVisible(false)
        RunButton.setVisible(false)

        UseItemButton1.setVisible(true)
        UseItemButton2.setVisible(true)
        UseItemButton3.setVisible(true)
        UseItemButton4.setVisible(true)
        NextPageItemButton.setVisible(true)

        NextPageItemButton.currentPage = 0
        UseItemButton1.n = 4*NextPageItemButton.currentPage
        UseItemButton2.n = 4*NextPageItemButton.currentPage + 1
        UseItemButton3.n = 4*NextPageItemButton.currentPage + 2
        UseItemButton4.n = 4*NextPageItemButton.currentPage + 3

        BackButton.x = 3
        BackButton.y = 693
        BackButton.setVisible(true)
    }

    override def update : Unit = {
        if (Player.currentMonster.alive && Player.usableInventory.filter(x => x.name != "Empty").length > 0) {
            imageName = originalImageName
            clickable = visible
        } else {
            imageName = "Buttons/EmptyButton.png"
            clickable = false
        }
        image = Utils.loadImage(imageName)
    }
}


object MonsterButton extends MyButton ("Buttons/MonstersButton.png") {
    x = 3
    y = 549
    visible = true
    text = "Pokemons"

    override def isClicked : Unit = {

        AttackButton.setVisible(false)
        BagButton.setVisible(false)
        MonsterButton.setVisible(false)
        RunButton.setVisible(false)

        ChangeMonsterButton1.setVisible(true)
        ChangeMonsterButton2.setVisible(true)
        ChangeMonsterButton3.setVisible(true)
        ChangeMonsterButton4.setVisible(true)
        ChangeMonsterButton5.setVisible(true)
        ChangeMonsterButton6.setVisible(true)
    }

    override def update : Unit = {
        if (Player.team.exists(x => (x != Player.currentMonster) && (x.name != "Empty") && (x.alive))) {
            imageName = originalImageName
            clickable = visible
        } else {
            imageName = "Buttons/EmptyButton.png"
            clickable = false
        }
        image = Utils.loadImage(imageName)
    }

}


object RunButton extends MyButton ("Buttons/RunButton.png") {
    x = 311
    y = 549
    visible = true
    text = "Run"

    override def isClicked : Unit = {
        DiscussionLabel.changeText("You run, far, really far !")
        Player.runningAway = true
        Player.lose
    }

    override def update : Unit = {
        if (Player.currentMonster.alive) {
            imageName = originalImageName
            clickable = visible
        } else {
            imageName = "Buttons/EmptyButton.png"
            clickable = false
        }
        image = Utils.loadImage(imageName)
    }
}

object BackButton extends MyButton ("Buttons/AttackButton.png") {
    visible = false
    text = "Back"

    override def isClicked : Unit = {
        ChangeMonsterButton1.setVisible(false)
        ChangeMonsterButton2.setVisible(false)
        ChangeMonsterButton3.setVisible(false)
        ChangeMonsterButton4.setVisible(false)
        ChangeMonsterButton5.setVisible(false)
        ChangeMonsterButton6.setVisible(false)

        CastAttackButton1.setVisible(false)
        CastAttackButton2.setVisible(false)
        CastAttackButton3.setVisible(false)
        CastAttackButton4.setVisible(false)

        UseItemButton1.setVisible(false)
        UseItemButton2.setVisible(false)
        UseItemButton3.setVisible(false)
        UseItemButton4.setVisible(false)

        NextPageItemButton.setVisible(false)
        setVisible(false)

        AttackButton.setVisible(true)
        BagButton.setVisible(true)
        MonsterButton.setVisible(true)
        RunButton.setVisible(true)
    }
}

object CastAttackButton1 extends CastAttackButton ("Buttons/WaterButton.png") {
    x = 3
    y = 405
    n = 0

}

object CastAttackButton2 extends CastAttackButton ("Buttons/FireButton.png") {
    x = 311
    y = 405
    n = 1
}

object CastAttackButton3 extends CastAttackButton ("Buttons/GrassButton.png") {
    x = 3
    y = 549
    n = 2
}

object CastAttackButton4 extends CastAttackButton ("Buttons/IceButton.png") {
    x = 311
    y = 549
    n = 3
}

object ChangeMonsterButton1 extends ChangeMonsterButton ("Buttons/ElectricButton.png") {
    x = 3
    y = 405
    n = 0
}

object ChangeMonsterButton2 extends ChangeMonsterButton ("Buttons/EmptyButton.png") {
    x = 311
    y = 405
    n = 1
}

object ChangeMonsterButton3 extends ChangeMonsterButton ("Buttons/EmptyButton.png") {
    x = 3
    y = 549
    n = 2
}

object ChangeMonsterButton4 extends ChangeMonsterButton ("Buttons/EmptyButton.png") {
    x = 311
    y = 549
    n = 3
}

object ChangeMonsterButton5 extends ChangeMonsterButton ("Buttons/EmptyButton.png") {
    x = 3
    y = 693
    n = 4
}

object ChangeMonsterButton6 extends ChangeMonsterButton ("Buttons/EmptyButton.png") {
    x = 311
    y = 693
    n = 5
}

object UseItemButton1 extends UseItemButton ("Buttons/EmptyButton.png") {
    x = 3
    y = 405
    n = 0

}

object UseItemButton2 extends UseItemButton ("Buttons/EmptyButton.png") {
    x = 311
    y = 405
    n = 1
}

object UseItemButton3 extends UseItemButton ("Buttons/EmptyButton.png") {
    x = 3
    y = 549
    n = 2
}
object UseItemButton4 extends UseItemButton ("Buttons/EmptyButton.png") {
    x = 311
    y = 549
    n = 3
}

object NextPageItemButton extends MyButton ("Buttons/EmptyButton.png") {
    x = 311
    y = 693
    text = "Next"
    var currentPage = 0
    override def isClicked : Unit = {
        var numberOfPage : Int = 1 + Player.usableInventory.filter(x => x.name != "Empty").length / 4
        currentPage = (currentPage + 1) % numberOfPage
        UseItemButton1.n = 4*currentPage
        UseItemButton2.n = 4*currentPage + 1
        UseItemButton3.n = 4*currentPage + 2
        UseItemButton4.n = 4*currentPage + 3

        UseItemButton1.update
        UseItemButton2.update
        UseItemButton3.update
        UseItemButton4.update
    }

    override def update : Unit = {
        if (Player.usableInventory.filter(x => x.name != "Empty").length > 4) {
            imageName = "Buttons/AttackButton.png"
            clickable = visible
        } else {
            imageName = "Buttons/EmptyButton.png"
            clickable = false
        }
        image = Utils.loadImage(imageName)
    }
}

