import swing._
import java.awt.event._
import java.awt.{Color,Graphics,BasicStroke,Font}
import java.awt.image.BufferedImage
import java.awt.event.MouseEvent
import javax.swing.{JFrame, JPanel, JLabel}
import java.io.File

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


abstract class HpBar {
    var hpRate : Float = 1f
    var x : Int = 0
    var y : Int = 0
    var width : Int = 136
    var height : Int = 10

    def setRatio(value : Float) : Unit = {
        hpRate = value
    }


    def setColor(g : Graphics) : Unit = {
        if ( hpRate < 0.25 ) {
            g.setColor(java.awt.Color.red)
        } else {if ( hpRate < 0.75 ) {
            g.setColor(java.awt.Color.orange)
        } else {
            g.setColor(java.awt.Color.green)}
        }
    }

    def display (g : Graphics) : Unit = {
        g.setColor(java.awt.Color.white)
        g.fillRect( x, y ,width,height)       
        setColor(g)
        g.fillRect( x, y ,(width.toFloat*hpRate).toInt,height)       
    }
}








class BattleUI (p1 : Player, p2 : Player, battle : Battle) extends JFrame with MouseListener {
    
    var buttonList : List[MyButton] = List(AttackButton, BagButton, MonsterButton, RunButton,
                                            CastAttackButton1, CastAttackButton2, CastAttackButton3, CastAttackButton4,
                                            ChangeMonsterButton1, ChangeMonsterButton2, ChangeMonsterButton3, 
                                            ChangeMonsterButton4, ChangeMonsterButton5, ChangeMonsterButton6)

    
    var pane = new DrawPanel(buttonList, p1, p2)

    def initialise : Unit = {
        setSize(614, 828 + 30)
        
        addMouseListener(this)
        setLayout(null)

        setUndecorated(true)
        setContentPane(pane)


        setLocation(100, 100)
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



class DrawPanel (buttonList : List[MyButton], p1 : Player, p2 : Player) extends JPanel {
    var toShow : Boolean = false
    var battleBackgroundImg = javax.imageio.ImageIO.read(getClass.getResource("BattleBackground.png"))
    var pokemonFrontImg = javax.imageio.ImageIO.read(getClass.getResource("EmptyFront.png"))
    var pokemonBackImg = javax.imageio.ImageIO.read(getClass.getResource("EmptyBack.png"))
    var ennemyBarImg = javax.imageio.ImageIO.read(getClass.getResource("EnnemyBar.png"))
    var yourBarImg = javax.imageio.ImageIO.read(getClass.getResource("YourBar.png"))
    var buttonImg = javax.imageio.ImageIO.read(getClass.getResource("Button.png"))
    var textBarImg = javax.imageio.ImageIO.read(getClass.getResource("TextBar.png"))
    var poke_font : Font = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream("PokemonPixelFont.ttf"))
    poke_font = poke_font.deriveFont(Font.PLAIN,30)

    object EnnemyBar{
        object EnnemyHpBar extends HpBar {
            x =  112
            y = 56
        }
        def display (g : Graphics) : Unit = {
            EnnemyHpBar.setRatio(p2.currentMonster.hpRate)
            EnnemyHpBar.display(g)
            g.drawImage(ennemyBarImg, 10, 20, null)
            g.setColor(new Color(68,64,69))
            g.drawString(p2.currentMonster.name,22,48)
            g.drawString("Lv:" + p2.currentMonster.level.toString,180,48)
        }
    }

    object YourBar{
        object YourHpBar extends HpBar {
            x = 443
            y = 226
            hpRate = 0.2f
        }

        object YourExpBar extends HpBar {
            x = 402
            y = 264
            width = 177
            height = 8
            hpRate = 1f

            val color : Color = new Color(250,244,215)
            override def setColor(g: Graphics): Unit = {
                g.setColor(color)
            }
        }

        def display (g : Graphics) : Unit = {
            YourHpBar.setRatio(p1.currentMonster.hpRate)
            YourExpBar.setRatio(p1.currentMonster.xpRate)
            YourHpBar.display(g)
            YourExpBar.display(g)
            g.drawImage(yourBarImg,320, 190, null)
            g.setColor(new Color(68,64,69))
            g.drawString(p1.currentMonster.name,355,218)
            g.drawString("Lv: " + p1.currentMonster.level.toString,513,218)
            g.drawString(p1.currentMonster.hp + " / "+ p1.currentMonster.hpMax,500,257)
        }
    }



  
    override def paintComponent (g : Graphics) : Unit = {
        super.paintComponent(g)
        g.setFont(poke_font)
        g.drawImage(battleBackgroundImg, 0, 0, null)
        g.drawImage(pokemonFrontImg, 370, 35- p2.currentMonster.uiYShift, null)
        g.drawImage(pokemonBackImg, 75, 141 - p1.currentMonster.uiYShift, null)
        
        EnnemyBar.display(g)
        YourBar.display(g)


        buttonList.foreach(x => x.display(g))
        g.drawImage(textBarImg,0,287,null)

    }

    def updateImages : Unit = {
        pokemonFrontImg = javax.imageio.ImageIO.read(getClass.getResource(p2.currentMonster.imgNameFront))
        pokemonBackImg = javax.imageio.ImageIO.read(getClass.getResource(p1.currentMonster.imgNameBack))
        repaint(0, 0, 900, 900)
    }
}


