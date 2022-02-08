import java.io.File
import java.awt.event.MouseEvent
import java.awt.{Color,Graphics,BasicStroke,Font}



abstract class MyButton (imageNam : String) {
    var imageName = imageNam
    var x : Int = 0
    var y : Int = 0
    var width : Int = 0
    var height : Int = 0
    var image = Utils.loadImage(imageName)
    var visible : Boolean = false
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

    def onClick (x_click : Int, y_click : Int) : Boolean = {
        if (visible && x <= x_click && x_click <= (x + width) && y <= y_click && y_click <= (y + height)) {
            isClicked
            true
        } else {
            false
        }
  }

    def isClicked : Unit = {
        println("The button is clicked")
    }

    def setVisible(b : Boolean) : Unit = {
        visible = b
        update
    }

    def update : Unit = {}
}

abstract class CastAttackButton (imageNam : String) extends MyButton (imageNam) {
    var n : Int = 0
    visible = false
    override def isClicked : Unit = {
        if (FirstPlayer.castAttack(n)) {

            CastAttackButton1.setVisible(false)
            CastAttackButton2.setVisible(false)
            CastAttackButton3.setVisible(false)
            CastAttackButton4.setVisible(false)

            AttackButton.setVisible(true)
            BagButton.setVisible(true)
            MonsterButton.setVisible(true)
            RunButton.setVisible(true)
        }
    }

    override def update : Unit = {
        if (FirstPlayer.currentMonster.attacks(n).name != "Empty") {
            imageName = FirstPlayer.currentMonster.attacks(n).attackType.imageButtonName
            text = FirstPlayer.currentMonster.attacks(n).name
        } else {
            imageName = "Buttons/EmptyButton.png"
            text = "Empty Slot"
        }
        image = Utils.loadImage(imageName)
    }
}

abstract class ChangeMonsterButton (imageNam : String) extends MyButton (imageNam) {
    var n : Int = 0
    visible = false

    override def isClicked : Unit = {
        if (FirstPlayer.changeMonster(n)) {
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

    override def update : Unit = {
        if (FirstPlayer.team(n).alive && FirstPlayer.team(n).name != "Empty" && FirstPlayer.team(n) != FirstPlayer.currentMonster) {
            imageName = FirstPlayer.team(n).monsterType.imageButtonName
        } else {
            imageName = "Buttons/EmptyButton.png"
        }
        text = FirstPlayer.team(n).name
        image = Utils.loadImage(imageName)
    }

}



object AttackButton extends MyButton ("Buttons/AttackButton.png") {
    x = 3
    y = 405
    width = 300
    height = 136
    visible = true
    text = "Attack"

    override def isClicked : Unit = {

        println("Showing the attacks")

        AttackButton.setVisible(false)
        BagButton.setVisible(false)
        MonsterButton.setVisible(false)
        RunButton.setVisible(false)

        CastAttackButton1.setVisible(true)
        CastAttackButton2.setVisible(true)
        CastAttackButton3.setVisible(true)
        CastAttackButton4.setVisible(true)
    }
}


object BagButton extends MyButton ("Buttons/BagButton.png") {
    x = 311
    y = 405
    width = 300
    height = 136
    visible = true
    text = "Bag"


    override def isClicked : Unit = {
        println("Showing the bag")
        DiscusionLabel.changeText("Your bag is empty !")
    }
}


object MonsterButton extends MyButton ("Buttons/MonstersButton.png") {
    x = 3
    y = 549
    width = 300
    height = 136
    visible = true
    text = "Pokemons"

    override def isClicked : Unit = {
        println("Showing the available monsters")

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
}


object RunButton extends MyButton ("Buttons/RunButton.png") {
    x = 311
    y = 549
    width = 300
    height = 136
    visible = true
    text = "Run"

    override def isClicked : Unit = {
        println("Run away from the battle")
        DiscusionLabel.changeText("For the moment, you cannot run !")
    }
}

object CastAttackButton1 extends CastAttackButton ("Buttons/WaterButton.png") {
    x = 3
    y = 405
    width = 300
    height = 136

    n = 0

}

object CastAttackButton2 extends CastAttackButton ("Buttons/FireButton.png") {
    x = 311
    y = 405
    width = 300
    height = 136
    n = 1
}

object CastAttackButton3 extends CastAttackButton ("Buttons/GrassButton.png") {
    x = 3
    y = 549
    width = 300
    height = 136
    n = 2
}

object CastAttackButton4 extends CastAttackButton ("Buttons/IceButton.png") {
    x = 311
    y = 549
    width = 300
    height = 136
    n = 3
}

object ChangeMonsterButton1 extends ChangeMonsterButton ("Buttons/ElectricButton.png") {
    x = 3
    y = 405
    width = 300
    height = 136
    n = 0
}

object ChangeMonsterButton2 extends ChangeMonsterButton ("Buttons/EmptyButton.png") {
    x = 311
    y = 405
    width = 300
    height = 136
    n = 1
}

object ChangeMonsterButton3 extends ChangeMonsterButton ("Buttons/EmptyButton.png") {
    x = 3
    y = 549
    width = 300
    height = 136
    n = 2
}

object ChangeMonsterButton4 extends ChangeMonsterButton ("Buttons/EmptyButton.png") {
    x = 311
    y = 549
    width = 300
    height = 136
    n = 3
}

object ChangeMonsterButton5 extends ChangeMonsterButton ("Buttons/EmptyButton.png") {
    x = 3
    y = 693
    width = 300
    height = 136
    n = 4
}

object ChangeMonsterButton6 extends ChangeMonsterButton ("Buttons/EmptyButton.png") {
    x = 311
    y = 693
    width = 300
    height = 136
    n = 5
}