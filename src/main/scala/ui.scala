import swing._
import java.awt.event._
import java.awt.{Color,Graphics,BasicStroke}
import java.awt.image.BufferedImage
import java.awt.event.MouseEvent
import javax.swing.{JFrame, JPanel}

import java.util.concurrent.TimeUnit

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
}

abstract class CastAttackButton (imageName : String) extends MyButton (imageName) {
    var n : Int = 0
    visible = false
    override def isClicked : Unit = {
        if (FirstPlayer.castAttack(n)) {

            CastAttackButton1.visible = false
            CastAttackButton2.visible = false
            CastAttackButton3.visible = false
            CastAttackButton4.visible = false

            AttackButton.visible = true
            BagButton.visible = true
            MonsterButton.visible = true
            LeaveButton.visible = true
        }
    }
}



object AttackButton extends MyButton ("Button.png") {
    x = 320
    y = 300
    width = 130
    height = 40
    visible = true

    override def isClicked : Unit = {

        println("Showing the attacks")

        AttackButton.visible = false
        BagButton.visible = false
        MonsterButton.visible = false
        LeaveButton.visible = false

        CastAttackButton1.visible = true
        CastAttackButton2.visible = true
        CastAttackButton3.visible = true
        CastAttackButton4.visible = true
    }
}


object BagButton extends MyButton ("Button.png") {
    x = 460
    y = 300
    width = 130
    height = 40
    visible = true

    override def isClicked : Unit = {
        println("Showing the bag")
    }
}


object MonsterButton extends MyButton ("Button.png") {
    x = 320
    y = 350
    width = 130
    height = 40
    visible = true

    override def isClicked : Unit = {
        println("Showing the available monsters")
    }
}


object LeaveButton extends MyButton ("Button.png") {
    x = 460
    y = 350
    width = 130
    height = 40
    visible = true

    override def isClicked : Unit = {
        println("Leave the battle")
    }
}

object CastAttackButton1 extends CastAttackButton ("Button.png") {
    x = 320
    y = 300
    width = 130
    height = 40
    n = 0
}

object CastAttackButton2 extends CastAttackButton ("Button.png") {
    x = 460
    y = 300
    width = 130
    height = 40
    n = 1
}

object CastAttackButton3 extends CastAttackButton ("Button.png") {
    x = 320
    y = 350
    width = 130
    height = 40
    n = 2
}

object CastAttackButton4 extends CastAttackButton ("Button.png") {
    x = 460
    y = 350
    width = 130
    height = 40
    n = 3
}





class BattleUI (p1 : Player, p2 : Player, battle : Battle) extends JFrame with MouseListener {
    var buttonList : List[MyButton] = List(AttackButton, BagButton, MonsterButton, LeaveButton,
                                            CastAttackButton1, CastAttackButton2, CastAttackButton3, CastAttackButton4)
    var pane = new DrawPanel(buttonList, p1, p2)

    def initialise : Unit = {
        setSize(614, 420)
        
        addMouseListener(this)
        setLayout(null)

        setUndecorated(true)
        setContentPane(pane)
        setVisible(true)
    }
    
  

    def mouseClicked (e : MouseEvent) : Unit = {
        if (FirstPlayer.hisTurn) {
            var clickCaught : Boolean = false
            def clickAButton (b : MyButton) : Unit = {
                if (!clickCaught) {
                    clickCaught = b.onClick(e.getX, e.getY)
                }
            }

            buttonList.foreach(clickAButton)
            clickCaught = false
        }    
        updateImages

    }
    def mouseEntered (e : MouseEvent) : Unit = {

    }
    def mouseExited (e : MouseEvent) : Unit = {

    }
    def mousePressed (e : MouseEvent) : Unit = {

    }
    def mouseReleased (e : MouseEvent) : Unit = {

    }

    def updateImages : Unit = {
        pane.updateImages
    }

}

abstract class HpBar {
    var maxHp: Int = 0
    var currentHp : Int = 0
    var x : Int = 0
    var y : Int = 0
    var width : Int = 0
    var height : Int = 0

    def setMaxHp(value : Int) {
        maxHp = value
    }

    def setCurrentHp(value : Int) {
        currentHp = value
    }

    def setColor(g : Graphics) : Unit = {
        if ( currentHp.toFloat / maxHp.toFloat < 0.25 ) {
            g.setColor(java.awt.Color.red)
        } else {if ( currentHp.toFloat / maxHp.toFloat < 0.75 ) {
            g.setColor(java.awt.Color.orange)
        } else {
            g.setColor(java.awt.Color.green)}
        }
    }

    def display (g : Graphics) : Unit = {
        setColor(g)
        g.fillRect( x, y ,(width.toFloat*(currentHp.toFloat/maxHp.toFloat)).toInt,height)       
    }
}

class EnnemyHpBar extends HpBar {
    x = 112
    y = 56
    width = 136
    height = 10
    maxHp = 10
    currentHp = 10
}
class YourHpBar extends HpBar {
    x = 444
    y = 226
    width = 135
    height = 10
    maxHp = 150
    currentHp = 100
}

class DrawPanel (buttonList : List[MyButton], p1 : Player, p2 : Player) extends JPanel {
    var toShow : Boolean = false
    var battleBackgroundImg = javax.imageio.ImageIO.read(getClass.getResource("BattleBackground.png"))
    var pokemonFrontImg = javax.imageio.ImageIO.read(getClass.getResource("EmptyFront.png"))
    var pokemonBackImg = javax.imageio.ImageIO.read(getClass.getResource("EmptyBack.png"))
    var ennemyBarImg = javax.imageio.ImageIO.read(getClass.getResource("EnnemyBar.png"))
    var yourBarImg = javax.imageio.ImageIO.read(getClass.getResource("YourBar.png"))
    var buttonImg = javax.imageio.ImageIO.read(getClass.getResource("Button.png"))
    var ennemyHpBar = new EnnemyHpBar
    var yourHpBar = new YourHpBar

  
    override def paintComponent (g : Graphics) : Unit = {
        super.paintComponent(g)
        g.drawImage(battleBackgroundImg, 0, 0, null)
        g.drawImage(pokemonFrontImg, 370, 35, null)
        g.drawImage(pokemonBackImg, 75, 141, null)
        g.drawImage(ennemyBarImg, 10, 20, null)
        g.drawImage(yourBarImg, 320, 190, null)
        ennemyHpBar.display(g)
        yourHpBar.display(g)

        buttonList.foreach(x => x.display(g))
    }

    def updateImages : Unit = {
        pokemonFrontImg = javax.imageio.ImageIO.read(getClass.getResource(p2.currentMonster.imgNameFront))
        pokemonBackImg = javax.imageio.ImageIO.read(getClass.getResource(p1.currentMonster.imgNameBack))
        repaint(0, 0, 800, 800)
    }
}


