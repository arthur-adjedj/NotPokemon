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



class UI extends JFrame with MouseListener with MouseMotionListener with KeyListener {

    var sizeBlock = 40
    var mapDisplayer : MapDisplayer = new MapDisplayer1 (this)

    var posX : Int = 1000
    var posY : Int = 100

    var sizeX : Int = 614
    var sizeY : Int = 858

    var xClick : Int = -1
    var yClick : Int = -1

    var closeButton = new CloseButton ("Close.png", close, sizeX - 20, 2)
    var helpButton = new HelpButton ("Help.png", help)


    // cannot create the list and adding buttons during their creation because of a weird behaviour with 'object'
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
                                                PlayerMonsterDisplayer, OpponentMonsterDisplayer)


    var battlePane : DrawPanelBattle = new DrawPanelBattle(buttonList, EmptyCharacter, EmptyCharacter)
    var mapPane : DrawPanelMap = new DrawPanelMap(mapDisplayer)
    var currentPane : MyPanel = mapPane.asInstanceOf[MyPanel]
    var listeningToKeyboard : Boolean = true

    def initialise : Unit = {

        setSize(sizeX, sizeY)

        addMouseListener(this)
        addMouseMotionListener(this)
        addKeyListener(this)
        setLayout(null)

        setUndecorated(true)
        setContentPane(currentPane)


        setLocation(posX, posY)
        setVisible(true)
    }

    def startBattle (p1 : Character, p2 : Character) : Unit = {
        var b = new Battle(p1, p2)
        YourBar.p1 = p1
        EnnemyBar.p2 = p2
        battlePane = new DrawPanelBattle(buttonList, p1, p2)
        currentPane = battlePane.asInstanceOf[MyPanel]
        currentPane.initialise
        setContentPane(currentPane)
        b.initialise
        b.start
        setVisible(true)

    }

    def initialiseMap : Unit = {
        mapDisplayer.initialise(1, sizeBlock)
        currentPane = mapPane.asInstanceOf[MyPanel]
        currentPane.initialise
        setContentPane(currentPane)
    }

    def backToMap : Unit = {
        currentPane = mapPane.asInstanceOf[MyPanel]
        setContentPane(currentPane)
    }

    def close () : Unit = {
        dispose()
    }

    def help () : Unit = {
        currentPane.showHelp = !currentPane.showHelp
    }

    def mouseClicked (e : MouseEvent) : Unit = {
        if (!DiscussionLabel.changingText && DiscussionLabel.messageQueue.isEmpty) {
            if (Player.hisTurn) {
                var clickCaught : Boolean = false
                def clickAButton (b : MyButton) : Unit = {
                    if (!clickCaught) {
                        clickCaught = b.onClick(e.getX, e.getY)
                    }
                }

                buttonList.foreach(clickAButton)
                clickCaught = false
            }
        } else {
            DiscussionLabel.skip
        }
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
        currentPane.underMouse = EmptyDescriptable
        var eventCaught : Boolean = false
        def moveMouseOver (b : Descriptable) : Unit = {
            if (!eventCaught) {
                if (b.isMouseOver(e.getX, e.getY)) {
                    eventCaught = true
                    currentPane.underMouse = b
                }
            }
        }

        descriptables.foreach(moveMouseOver)
        eventCaught = false
        currentPane.xMouse = e.getX
        currentPane.yMouse = e.getY
    }

    def keyReleased (e : KeyEvent) : Unit = {}

    def keyPressed (e : KeyEvent) : Unit = {
        if (listeningToKeyboard) {
            e.getKeyChar match {
                case 'z' => PlayerDisplayer.move(0, -1)
                case 's' => PlayerDisplayer.move(0, 1)
                case 'q' => PlayerDisplayer.move(-1, 0)
                case 'd' => PlayerDisplayer.move(1, 0)
                case _ => Utils.print(e.getKeyChar)
            }
        }        
    }

    def keyTyped (e : KeyEvent) : Unit = {}
}

object EmptyUI extends UI {}

class MyPanel extends JPanel with Repaintable {
    var xMouse : Int = -1
    var yMouse : Int = -1
    var underMouse : Descriptable = EmptyDescriptable
    var showHelp : Boolean = false

    def initialise : Unit = {}
}

