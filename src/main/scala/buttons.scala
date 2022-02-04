import java.io.File
import java.awt.event.MouseEvent
import java.awt.{Color,Graphics,BasicStroke,Font}



abstract class MyButton (imageName : String) {
    var x : Int = 0
    var y : Int = 0
    var width : Int = 0
    var height : Int = 0
    var image = javax.imageio.ImageIO.read(getClass.getResource(imageName))
    var visible : Boolean = false

    def display (g : Graphics) : Unit = {
        if (visible) {
            g.drawImage(image, x, y, null)
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
    }
}

abstract class CastAttackButton (imageName : String) extends MyButton (imageName) {
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

    override def setVisible (b : Boolean) : Unit = {
        visible = b && FirstPlayer.currentMonster.attacks(n).name != "Empty"
    }
}

abstract class ChangeMonsterButton (imageName : String) extends MyButton (imageName) {
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

    override def setVisible (b : Boolean) : Unit = {
        visible = b && FirstPlayer.team(n).alive && FirstPlayer.team(n).name != "Empty" && FirstPlayer.team(n) != FirstPlayer.currentMonster
    }

}



object AttackButton extends MyButton ("Button.png") {
    x = 3
    y = 405
    width = 300
    height = 136
    visible = true

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


object BagButton extends MyButton ("Button.png") {
    x = 311
    y = 405
    width = 300
    height = 136
    visible = true

    override def isClicked : Unit = {
        println("Showing the bag")
    }
}


object MonsterButton extends MyButton ("Button.png") {
    x = 3
    y = 549
    width = 300
    height = 136
    visible = true

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


object RunButton extends MyButton ("Button.png") {
    x = 311
    y = 549
    width = 300
    height = 136
    visible = true

    override def isClicked : Unit = {
        println("Run away from the battle")
    }
}

object CastAttackButton1 extends CastAttackButton ("Button.png") {
    x = 3
    y = 405
    width = 300
    height = 136

    n = 0
}

object CastAttackButton2 extends CastAttackButton ("Button.png") {
    x = 311
    y = 405
    width = 300
    height = 136
    n = 1
}

object CastAttackButton3 extends CastAttackButton ("Button.png") {
    x = 3
    y = 549
    width = 300
    height = 136
    n = 2
}

object CastAttackButton4 extends CastAttackButton ("Button.png") {
    x = 311
    y = 549
    width = 300
    height = 136
    n = 3
}

object ChangeMonsterButton1 extends ChangeMonsterButton ("Button.png") {
    x = 3
    y = 405
    width = 300
    height = 136
    n = 0
}

object ChangeMonsterButton2 extends ChangeMonsterButton ("Button.png") {
    x = 311
    y = 405
    width = 300
    height = 136
    n = 1
}

object ChangeMonsterButton3 extends ChangeMonsterButton ("Button.png") {
    x = 3
    y = 549
    width = 300
    height = 136
    n = 2
}

object ChangeMonsterButton4 extends ChangeMonsterButton ("Button.png") {
    x = 311
    y = 549
    width = 300
    height = 136
    n = 3
}

object ChangeMonsterButton5 extends ChangeMonsterButton ("Button.png") {
    x = 3
    y = 693
    width = 300
    height = 136
    n = 4
}

object ChangeMonsterButton6 extends ChangeMonsterButton ("Button.png") {
    x = 311
    y = 693
    width = 300
    height = 136
    n = 5
}