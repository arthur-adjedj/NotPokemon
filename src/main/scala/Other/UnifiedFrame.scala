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


    var battlePane : DrawPanelBattle = new DrawPanelBattle(EmptyCharacter, EmptyCharacter)
    var mapPane : DrawPanelMap = new DrawPanelMap(mapDisplayer)
    var pokedexPane : DrawPokedexPanel = new DrawPokedexPanel
    var currentPane : MyPanel = mapPane.asInstanceOf[MyPanel]
    var currentState : String = ""
    var listeningToKeyboard : Boolean = true

    def initialise : Unit = {

        Utils.start

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
        currentState = "Battle"
        DiscussionLabel.visible = true

        var b = new Battle(p1, p2)
        YourBar.p1 = p1
        EnnemyBar.p2 = p2
        battlePane = new DrawPanelBattle(p1, p2)
        currentPane = battlePane.asInstanceOf[MyPanel]
        currentPane.initialise
        setContentPane(currentPane)
        b.initialise
        b.start
        setVisible(true)
    }

    def backToBattle : Unit = {
        if (battlePane.ready) {
            currentState = "Battle"
            DiscussionLabel.visible = true
            currentPane = battlePane.asInstanceOf[MyPanel]
            setContentPane(currentPane)
        }
    }

    def initialiseMap : Unit = {
        currentState = "Map"
        DiscussionLabel.visible = false

        
        mapDisplayer.initialise(sizeBlock)
        currentPane = mapPane.asInstanceOf[MyPanel]
        currentPane.initialise
        setContentPane(currentPane)
        PlayerDisplayer.i = mapDisplayer.iStart
        PlayerDisplayer.j = mapDisplayer.jStart
        PlayerDisplayer.alignCoordinates
        PlayerDisplayer.whichMap = mapDisplayer.n

        Utils.characterDisplayers.foreach(x => x.initialise)
    }

    def changeMap (map : MapDisplayer) : Unit = {
        mapDisplayer = map
        initialiseMap
    }

    def backToMap : Unit = {
        if (mapPane.ready) {
            if (Player.team.forall(x => !x.alive)) {
                loseTheGame
            }
            currentState = "Map"
            DiscussionLabel.visible = false
            currentPane = mapPane.asInstanceOf[MyPanel]
            setContentPane(currentPane)
        }
    }

    def initialisePokedex : Unit = {
        currentState = "Pokedex"
        DiscussionLabel.visible = false

        currentPane = pokedexPane
        currentPane.initialise
        setContentPane(currentPane)
    }

    def backToPokedex : Unit = {
        if (pokedexPane.ready) {
            currentState = "Pokedex"
            DiscussionLabel.visible = false

            currentPane = pokedexPane
            setContentPane(currentPane)
        }
    }

    def loseTheGame : Unit = {
        Utils.print("You lost the game")
        close
    }

    def close () : Unit = {
        dispose()
    }

    def help () : Unit = {
        currentPane.showHelp = !currentPane.showHelp
    }

    def mouseClicked (e : MouseEvent) : Unit = {
        if (!DiscussionLabel.changingText && DiscussionLabel.messageQueue.isEmpty) {
            if (Player.hisTurn || Utils.frame.currentState != "Battle") {
                var clickCaught : Boolean = false
                def clickAButton (b : MyButton) : Unit = {
                    if (!clickCaught) {
                        clickCaught = b.onClick(e.getX, e.getY)
                    }
                }

                Utils.buttonList.foreach(clickAButton)
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

        Utils.descriptables.foreach(moveMouseOver)
        eventCaught = false
        currentPane.xMouse = e.getX
        currentPane.yMouse = e.getY
    }

    def keyReleased (e : KeyEvent) : Unit = {}

    def keyPressed (e : KeyEvent) : Unit = {
        if (listeningToKeyboard) {
            if (!DiscussionLabel.changingText && DiscussionLabel.messageQueue.isEmpty) {
                e.getKeyChar.toLower match {
                    case 'z' => PlayerDisplayer.move(0, -1)
                    case 's' => PlayerDisplayer.move(0, 1)
                    case 'q' => PlayerDisplayer.move(-1, 0)
                    case 'd' => PlayerDisplayer.move(1, 0)

                    case 'i' => SecondPlayerDisplayer.move(0, -1)
                    case 'k' => SecondPlayerDisplayer.move(0, 1)
                    case 'j' => SecondPlayerDisplayer.move(-1, 0)
                    case 'l' => SecondPlayerDisplayer.move(1, 0)

                    case 'e' => PlayerDisplayer.interactExplicitly
                    case 'o' => SecondPlayerDisplayer.interactExplicitly

                    case 'a' => PlayerDisplayer.changeCurrentItem

                    case '1' => if (Utils.debug) backToBattle
                    case '2' => if (Utils.debug) backToMap
                    case '3' => if (Utils.debug) backToPokedex

                    // For debugging purposes
                    case 'n' => if (Utils.debug) PlayerDisplayer.noClip = !PlayerDisplayer.noClip
                    case _ => Utils.print(e.getKeyChar.toInt)
                }
            } else {
                DiscussionLabel.skip
            }
            PlayerDisplayer.mapDisplayer.update
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
    var ready : Boolean = false

    def initialise : Unit = {
        ready = true
    }

    override def paintComponent (g : Graphics) : Unit = {
        super.paintComponent(g)
    }


    def endPaintComponent(g : Graphics) : Unit = {
        // at the end of PaintComponent, like super.paintComponent at the beggining
        g.setColor(Color.BLACK)
        g.fillRect(0, 0, getWidth, 20)
        CloseButton.display(g)
        HelpButton.display(g)
        Utils.buttonList.foreach(x => x.update)
        Utils.buttonList.foreach(x => x.display(g))

        if (showHelp) {
            underMouse.onMouseOver(g, xMouse, yMouse, getWidth, getHeight)
        }
    }
}

