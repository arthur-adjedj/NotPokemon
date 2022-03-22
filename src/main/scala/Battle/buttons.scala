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
    var alwaysVisible : Boolean = false
    context = "" // the value of Utils.frame.currentState to decide if we display
    var clickable : Boolean = true
    var poke_font : Font = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream("PokemonPixelFont.ttf"))
    poke_font = poke_font.deriveFont(Font.PLAIN,30)
    var text : String = ""
    var xtext  = 0
    var ytext = 0
    def display (g : Graphics) : Unit = {
        if (visible && (context == Utils.frame.currentState || context == "All") || alwaysVisible) {
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
        if ((visible && clickable || alwaysVisible) && x <= xClick && xClick <= (x + width) && y <= yClick && yClick <= (y + height)) {
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

abstract class BattleButton (imageName_ : String) extends MyButton(imageName_) {
    context = "Battle"
    def resetOnMainMenu : Unit = {
        Utils.buttonList.foreach(x => x.setVisible(false))
        AttackButton.setVisible(true)
        BagButton.setVisible(true)
        MonsterButton.setVisible(true)
        RunButton.setVisible(true)
    }
}

object CloseButton extends MyButton ("Close.png") {
    context = "All"
    x = 594
    y = 2
    width = 15
    height = 15
    alwaysVisible = true
    override def isClicked : Unit = {
        Utils.frame.close
    }
}

object HelpButton extends MyButton ("Help.png") {
    context = "All"
    x = 0
    y = 0
    width = 15
    height = 15
    alwaysVisible = true
    override def isClicked : Unit = {
        Utils.frame.help
    }
}

class CastAttackButton (n_ : Int) extends BattleButton ("Buttons/EmptyButton.png") {
    var n : Int = n_
    x = 3 + 308*(n%2)
    y = 405 + 144*(n/2)
    override def isClicked : Unit = {
        if (Player.chooseAttack(n)) {
            resetOnMainMenu
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
        var liString = Utils.cutString(text, 30)

        var xToShow = x.max(20)
        var yToShow = y - 10 - 20*liString.length

        
        (0 until liString.length).foreach(x => xToShow = xToShow.min(width - metrics.stringWidth(liString(x)) - 20))

        (0 until liString.length).foreach(x => g.drawString(liString(x), xToShow, yToShow + 20*x))
    }
}

class ChangeMonsterButton (n_ : Int) extends BattleButton ("Buttons/EmptyButton.png") {
    var n : Int = n_
    x = 3 + 308*(n%2)
    y = 405 + 144*(n/2)

    override def isClicked : Unit = {
        if (Player.changeMonster(n)) {
            resetOnMainMenu
        }        
    }

    override def isMouseOver (x_click : Int, y_click : Int) : Boolean = {
        visible && x <= x_click && x_click <= (x + width) && y <= y_click && y_click <= (y + height) && (Player.team(n).name != "Empty") 
    }

    override def onMouseOver (g : Graphics, x : Int, y : Int, width : Int, height : Int) : Unit = {
        var metrics = g.getFontMetrics
        var text = Player.team(n).toString
        var liString = Utils.cutString(text, 30)

        var xToShow = x.max(20)
        var yToShow = y - 10 - 20*liString.length

        
        (0 until liString.length).foreach(x => xToShow = xToShow.min(width - metrics.stringWidth(liString(x)) - 20))

        (0 until liString.length).foreach(x => g.drawString(liString(x), xToShow, yToShow + 20*x))
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

class UseItemButton (n_ : Int) extends BattleButton ("Buttons/EmptyButton.png") {
    var n : Int = n_
    var indexOfObject : Int = 0
    x = 3 + 308*(n%2)
    y = 405 + 144*(n/2)

    override def isClicked : Unit = {
        if (Player.useItem(indexOfObject)) {
            resetOnMainMenu
        }        
    }

    override def onMouseOver (g : Graphics, xMouse : Int, yMouse : Int, widthWindow : Int, heightWindow : Int) : Unit = {
        var metrics = g.getFontMetrics
        var text = Player.usableInventory(n).toString


        var liString = Utils.cutString(text, 30)

        var xToShow = xMouse.max(20)
        var yToShow = yMouse - 10 - 20*liString.length

        
        (0 until liString.length).foreach(x => xToShow = xToShow.min(widthWindow - metrics.stringWidth(liString(x)) - 20))

        (0 until liString.length).foreach(x => g.drawString(liString(x), xToShow, yToShow + 20*x))
    }

    override def update : Unit = {
        indexOfObject = 4*NextPageItemButton.currentPage + n
        if (Player.usableInventory(indexOfObject).name != "Empty" && Player.usableInventory(indexOfObject).usable) {
            imageName = "Buttons/BagButton.png"
            clickable = visible
        } else {
            imageName = "Buttons/EmptyButton.png"
            clickable = false
        }
        text = Player.usableInventory(indexOfObject).name + "(" + Player.usableInventory(indexOfObject).amount + ")"
        image = Utils.loadImage(imageName)
    }
}



object AttackButton extends BattleButton ("Buttons/AttackButton.png") {
    x = 3
    y = 405
    visible = true
    text = "Attack"

    override def isClicked : Unit = {


        AttackButton.setVisible(false)
        BagButton.setVisible(false)
        MonsterButton.setVisible(false)
        RunButton.setVisible(false)

        Utils.castAttackButtonList.foreach(x => x.setVisible(true))

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


object BagButton extends BattleButton ("Buttons/BagButton.png") {
    x = 311
    y = 405
    visible = true
    text = "Bag"


    override def isClicked : Unit = {
        AttackButton.setVisible(false)
        BagButton.setVisible(false)
        MonsterButton.setVisible(false)
        RunButton.setVisible(false)

        Utils.useItemButtonList.foreach(x => x.setVisible(true))
        NextPageItemButton.setVisible(true)

        NextPageItemButton.currentPage = 0

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


object MonsterButton extends BattleButton ("Buttons/MonstersButton.png") {
    x = 3
    y = 549
    visible = true
    text = "Pokemons"

    override def isClicked : Unit = {

        AttackButton.setVisible(false)
        BagButton.setVisible(false)
        MonsterButton.setVisible(false)
        RunButton.setVisible(false)

        Utils.changeMonsterButtonList.foreach(x => x.setVisible(true))
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


object RunButton extends BattleButton ("Buttons/RunButton.png") {
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
        if (Player.currentMonster.alive && Player.opponent.name == "Wild") {
            imageName = originalImageName
            clickable = visible
        } else {
            imageName = "Buttons/EmptyButton.png"
            clickable = false
        }
        image = Utils.loadImage(imageName)
    }
}

object BackButton extends BattleButton ("Buttons/AttackButton.png") {
    visible = false
    text = "Back"

    override def isClicked : Unit = {
       resetOnMainMenu
    }
}

object NextPageItemButton extends BattleButton ("Buttons/EmptyButton.png") {
    x = 311
    y = 693
    text = "Next"
    var currentPage = 0
    override def isClicked : Unit = {
        var numberOfPage : Int = 1 + Player.usableInventory.filter(x => x.name != "Empty").length / 4
        currentPage = (currentPage + 1) % numberOfPage
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

