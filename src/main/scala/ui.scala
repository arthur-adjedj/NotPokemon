import java.awt.event._
import java.awt.{Color,Graphics,BasicStroke,Font}
import java.awt.event.MouseEvent
import javax.swing.{JFrame, JPanel, JLabel}
import java.io.File
import java.awt.font.TextAttribute
import collection.JavaConverters._


import java.util.concurrent.TimeUnit
import scala.runtime.EmptyMethodCache
import javax.swing.DebugGraphics
import java.awt.event.MouseMotionListener
import java.awt.Toolkit


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

object DiscussionLabel {
    var text1 : String = ""
    var text2 : String = ""
    var battleUi : BattleUI = EmptyBattleUI
    var charPerLine : Int = 27
    var x : Int = 40
    var y : Int = 330
    var font : Font = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream("PokemonPixelFont.ttf"))
    font = font.deriveFont(Font.PLAIN,40)
    val attributes = (collection.Map(TextAttribute.TRACKING -> 0.05)).asJava
    font = font.deriveFont(attributes)
    font = font.deriveFont(font.getStyle() | Font.BOLD)


    def display (g : Graphics) : Unit = {
        g.setFont(font)
        g.drawString(text1, x, y)
        g.drawString(text2, x, y+30)
    }

    def changeText (s : String) : Unit = {
        println(s)
        var (t1, t2, t3) = Utils.cutString(s, charPerLine)
        text1 = t1
        text2 = t2
        battleUi.refresh
    }
}

trait Descriptable {

    def onMouseOver (g : Graphics, xMouse : Int, yMouse : Int, width : Int, height : Int) : Unit = {}
    def isMouseOver (x : Int, y : Int) : Boolean = false
}

object EmptyDescriptable extends Object with Descriptable {
    override def isMouseOver (x : Int, y : Int) = true
}

class BattleUI (p1 : Player, p2 : Player, battle : Battle) extends JFrame with MouseListener with MouseMotionListener {

    var posX : Int = 1000
    var posY : Int = 100

    var sizeX : Int = 614
    var sizeY : Int = 858

    var xClick : Int = -1
    var yClick : Int = -1


    var closeButton = new CloseButton ("Close.png", close, sizeX - 20, 2)
    var helpButton = new HelpButton ("Help.png", help)

    var buttonList : List[MyButton] = List(AttackButton, BagButton, MonsterButton, RunButton, BackButton, NextPageItemButton, closeButton, helpButton,
                                            CastAttackButton1, CastAttackButton2, CastAttackButton3, CastAttackButton4,
                                            ChangeMonsterButton1, ChangeMonsterButton2, ChangeMonsterButton3, 
                                            ChangeMonsterButton4, ChangeMonsterButton5, ChangeMonsterButton6,
                                            UseItemButton1, UseItemButton2, UseItemButton3, UseItemButton4)

    var descriptables : List[Descriptable] = List(AttackButton, BagButton, MonsterButton, RunButton, BackButton, NextPageItemButton,
                                                CastAttackButton1, CastAttackButton2, CastAttackButton3, CastAttackButton4,
                                                ChangeMonsterButton1, ChangeMonsterButton2, ChangeMonsterButton3, 
                                                ChangeMonsterButton4, ChangeMonsterButton5, ChangeMonsterButton6,
                                                UseItemButton1, UseItemButton2, UseItemButton3, UseItemButton4,
                                                FirstPlayerMonsterDisplayer, OpponentMonsterDisplayer)
    
    var pane = new DrawPanel(buttonList, p1, p2, this)
    var lastMonsterSelected : Monster = EmptyMonster

    

    def initialise : Unit = {
        YourBar.p1 = p1
        EnnemyBar.p2 = p2

        setSize(sizeX, sizeY)
        
        addMouseListener(this)
        addMouseMotionListener(this)
        setLayout(null)

        setUndecorated(true)
        setContentPane(pane)


        setLocation(posX, posY)
        setVisible(true)


    }

    def close () : Unit = {
        dispose()
    }

    def help () : Unit = {
        pane.showHelp = !pane.showHelp
    }

    def nullPointerException : Unit = {
        getClass.getResource("")
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
    def mouseEntered (e : MouseEvent) : Unit = {}
    def mouseExited (e : MouseEvent) : Unit = {}
    def mousePressed (e : MouseEvent) : Unit = {
        if (xClick == -1 && yClick == -1) {
            if (e.getY >= 0 && e.getY < 20 && e.getX >= 0 && e.getX <= getWidth) {
                xClick = e.getX
                yClick = e.getY
            }
        }
    }

    def mouseReleased (e : MouseEvent) : Unit = {
        xClick = -1
        yClick = -1
    }

    def mouseDragged (e : MouseEvent) : Unit = {
        if (xClick != -1 && yClick != -1) {
            var movX = xClick - e.getX
            var movY = yClick - e.getY
            posX = (posX - movX).max(0).min(Toolkit.getDefaultToolkit.getScreenSize.getWidth.toInt-getWidth)
            posY = (posY - movY).max(0).min(Toolkit.getDefaultToolkit.getScreenSize.getHeight.toInt-getHeight)
            setLocation(posX, posY)
        }
    }

    def mouseMoved (e : MouseEvent) : Unit = {
        pane.underMouse = EmptyDescriptable
        var eventCaught : Boolean = false
        def moveMouseOver (b : Descriptable) : Unit = {
            if (!eventCaught) {
                if (b.isMouseOver(e.getX, e.getY)) {
                    eventCaught = true
                    pane.underMouse = b
                }
            }
        }

        descriptables.foreach(moveMouseOver)
        eventCaught = false
        updateImages
        pane.xMouse = e.getX
        pane.yMouse = e.getY
    }

    def refresh : Unit = {
        pane.refresh
    }

    def updateImages : Unit = {
        pane.updateImages
    }

}

object EnnemyBar{
    def ennemyBarImg = Utils.loadImage("EnnemyBar.png")
    var p2 : Player = EmptyPlayer
    object EnnemyHpBar extends HpBar {
        x = 112
        y = 76
    }
    def display (g : Graphics) : Unit = {
        EnnemyHpBar.setRatio(p2.currentMonster.hpRate)
        EnnemyHpBar.display(g)
        g.drawImage(ennemyBarImg, 10, 40, null)
        g.setColor(new Color(68,64,69))
        g.drawString(p2.currentMonster.name,22,68)
        g.drawString("Lv:" + p2.currentMonster.level.toString,180,68)
    }
}

object YourBar{
    def yourBarImg = Utils.loadImage("YourBar.png")
    var p1 : Player = EmptyPlayer

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

        val color : Color = new Color(0,244,215)
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


object EmptyBattleUI extends BattleUI (EmptyPlayer, EmptyPlayer, EmptyBattle) {}

class DrawPanel (buttonList : List[MyButton], p1 : Player, p2 : Player, ui : BattleUI) extends JPanel {
    var toShow : Boolean = false
    var battleBackgroundImg = Utils.loadImage("BattleBackground.png")
    var pokemonFrontImg = Utils.loadImage("Monsters/EmptyFront.png")
    var pokemonBackImg = Utils.loadImage("Monsters/EmptyBack.png")
    var textBarImg = Utils.loadImage("TextBar.png")
    var poke_font : Font = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream("PokemonPixelFont.ttf"))
    var underMouse : Descriptable = EmptyDescriptable
    var xMouse : Int = 0
    var yMouse : Int = 0
    var showHelp : Boolean = false

    poke_font = poke_font.deriveFont(Font.PLAIN,30)
  
    override def paintComponent (g : Graphics) : Unit = {
        super.paintComponent(g)
        buttonList.foreach(x => x.update)
        g.setFont(poke_font)
        g.drawImage(battleBackgroundImg, 0, 0, null)
        //g.drawImage(pokemonFrontImg, 370, 35 + p2.currentMonster.uiYShift, null)
        //g.drawImage(pokemonBackImg, 75, 141 + p1.currentMonster.uiYShift, null)

        FirstPlayerMonsterDisplayer.display(g)
        OpponentMonsterDisplayer.display(g)
        
        EnnemyBar.display(g)
        YourBar.display(g)

        g.drawImage(textBarImg,0,287,null)
        DiscussionLabel.display(g)
        g.setColor(Color.BLACK)
        g.fillRect(0, 0, getWidth, 20)
        buttonList.foreach(x => x.display(g))
        if (showHelp) {
            underMouse.onMouseOver(g, xMouse, yMouse, getWidth, getHeight)
        }
        
    }

    def updateImages : Unit = {
        pokemonFrontImg = Utils.loadImage(p2.currentMonster.imgNameFront)
        pokemonBackImg = Utils.loadImage(p1.currentMonster.imgNameBack)

        FirstPlayerMonsterDisplayer.update
        OpponentMonsterDisplayer.update
        
        repaint()
    }

    def refresh : Unit = {
        repaint()
    }
}
