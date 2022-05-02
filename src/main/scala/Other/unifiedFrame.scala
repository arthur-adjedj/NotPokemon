// package org.interactivemesh.scala.swing.j3d


// import javax.media

import java.awt.event._
import java.awt.{Color,Graphics,BasicStroke,Font}
import javax.swing.{JFrame, JPanel, JLabel}
import java.io.File
import java.awt.font.TextAttribute
import collection.JavaConverters._


import java.util.concurrent.TimeUnit
import scala.runtime.EmptyMethodCache
import javax.swing.DebugGraphics
import java.awt.Toolkit





class MyUI extends JFrame with MouseListener with MouseMotionListener with KeyListener with MouseWheelListener {

    var sizeBlock = 40
    var mapDisplayer : MapDisplayer = new MapDisplayer1 (this)

    var posX : Int = 1000
    var posY : Int = 100

    var sizeX : Int = 614
    var sizeY : Int = 858

    var xClick : Int = -1
    var yClick : Int = -1


    var battlePane : DrawPanelBattle = new DrawPanelBattle(EmptyCharacter, EmptyCharacter)
    var mapPane : DrawPanelMap = new DrawPanelMap
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
        addMouseWheelListener(this)
        setLayout(null)
        setUndecorated(true)
        setContentPane(currentPane)


        setLocation(posX, posY)
        setVisible(true)
        Utils.easterEggs.foreach(x => x.start)
    }

    def startBattle (p1 : Character, p2 : Character) : Unit = {

        DiscussionLabel.visible = true
        DiscussionLabel.changeText(p1.enteringBattleMessage)
        DiscussionLabel.changeText(p2.enteringBattleMessage)
        Utils.waitDiscussionLabel

        currentState = "Battle"

        var b = new Battle(p1, p2)
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

    def initialiseMap : Unit = initialiseMap(-1, -1)

    def initialiseMap (i : Int, j : Int): Unit = {
        currentState = "Map"
        DiscussionLabel.visible = false

        
        mapDisplayer.initialise(sizeBlock, i, j)
        mapPane.changeMap(mapDisplayer)
        currentPane = mapPane.asInstanceOf[MyPanel]
        currentPane.initialise
        setContentPane(currentPane)

        Utils.characterDisplayers.foreach(x => x.initialise)
    }

    def changeMap (map : MapDisplayer) : Unit = changeMap(map, -1, -1)

    def changeMap (map : MapDisplayer, i : Int, j : Int) : Unit = {
        mapDisplayer = map
        initialiseMap(i, j)
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

    def backToMap (s : String) : Unit = {
        backToMap
        DiscussionLabel.changeText(s)
    }

    def backToMap (s : List[String]) : Unit = {
        backToMap
        DiscussionLabel.changeText(s)
    }

    def initialisePokedex : Unit = {
        currentState = "Pokedex"
        DiscussionLabel.visible = false

        currentPane = pokedexPane.asInstanceOf[MyPanel]
        currentPane.initialise
        setContentPane(currentPane)
        setVisible(true)
    }

    def backToPokedex : Unit = {
        if (pokedexPane.ready) {
            currentState = "Pokedex"
            DiscussionLabel.visible = false

            currentPane = pokedexPane.asInstanceOf[MyPanel]
            setContentPane(currentPane)
        } else {
            initialisePokedex
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

    def save : Unit = {
        Utils.print(Utils.characterDisplayers.map(x => x.toStringSave(0)).foldLeft("")((x, y) => x+y))
    }

    def mouseClicked (e : MouseEvent) : Unit = {
        PlayerDisplayer.hasDoneSomething = true
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
        PlayerDisplayer.hasDoneSomething = true
        if (xClick == -1 && yClick == -1) {
            if (e.getY >= 0 && e.getY < 20 && e.getX >= 0 && e.getX <= getWidth) {
                xClick = e.getX
                yClick = e.getY
            }
        }
        currentPane.onMousePressed(e)
    }

    def mouseReleased (e : MouseEvent) : Unit = {
        xClick = -1
        yClick = -1
        currentPane.onMouseRealeased(e)
    }

    def mouseDragged (e : MouseEvent) : Unit = {
        if (xClick != -1 && yClick != -1) {
            var movX = xClick - e.getX
            var movY = yClick - e.getY
            posX = (posX - movX).max(0).min(Toolkit.getDefaultToolkit.getScreenSize.getWidth.toInt-getWidth)
            posY = (posY - movY).max(0).min(Toolkit.getDefaultToolkit.getScreenSize.getHeight.toInt-getHeight)
            setLocation(posX, posY)

        }
        currentPane.onMouseDragged(e)
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

        currentPane.onMouseMoved(e)
    }

    def keyReleased (e : KeyEvent) : Unit = {}

    def keyPressed (e : KeyEvent) : Unit = {
        PlayerDisplayer.hasDoneSomething = true
        if (!DiscussionLabel.changingText && DiscussionLabel.messageQueue.isEmpty && listeningToKeyboard) {
            e.getKeyChar.toLower match {
                
                // For debugging purposes                    
                case '1' => if (Utils.debug) backToBattle
                case '2' => if (Utils.debug) backToMap
                case '3' => if (Utils.debug) backToPokedex
                case '4' => if (Utils.debug) DiscussionLabel.changeText("This is a very long message ! It is supposed to be displayed on several lines ! I hope it will work correctly ! And of course it works correctly !")
                case ' ' => EasterEgg3.press
                case _ => currentPane.onKeyPressed(e)
            }
        } else if (e.getKeyChar.toLower != ' '){
            DiscussionLabel.skip
        }
    }

    def keyTyped (e : KeyEvent) : Unit = {}

    def mouseWheelMoved (e : MouseWheelEvent) : Unit = {
        if (currentState == "Pokedex") {
            pokedexPane.changeCurrentPokemonBrut(pokedexPane.currentPokemonIndex + e.getWheelRotation)
        }
    }
}

object EmptyUI extends MyUI {}

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
        //Utils.print(scala.util.Random.nextFloat)
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

    def onKeyPressed (e : KeyEvent) : Unit = {}

    def onMousePressed (e : MouseEvent) : Unit = {}

    def onMouseRealeased (e : MouseEvent) : Unit = {}

    def onMouseMoved (e : MouseEvent) : Unit = {}

    def onMouseDragged (e : MouseEvent) : Unit = {}
}

