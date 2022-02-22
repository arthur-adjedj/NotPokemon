import java.awt.event._
import java.awt.{Color,Graphics,BasicStroke,Font}
import java.awt.event.MouseEvent
import javax.swing.{JFrame, JPanel, JLabel}
import java.io.File


class MapUI extends JFrame with KeyListener {
    var sizeBlock = 50

    var posX = 1000
    var posY = 100
    var sizeX = 800
    var sizeY = 800

    var ready : Boolean = false

    var mapDisplayer : MapDisplayer = new MapDisplayer(this, sizeBlock)

    var pane = new DrawPanelMap(mapDisplayer)

    def initialise : Unit = {

        mapDisplayer.initialise(1, sizeBlock)
        pane.initialise

        FirstPlayerDisplayer.mapUI = this

        setSize(sizeX, sizeY)
        addKeyListener(this)
        setLayout(null)

        setUndecorated(true)
        setContentPane(pane)


        setLocation(posX, posY)
        ready = true
        setVisible(true)

    }

    def keyReleased (e : KeyEvent) : Unit = {}

    def keyPressed (e : KeyEvent) : Unit = {
        {e.getKeyChar match {
            case 'z' => FirstPlayerDisplayer.move(0, -1)
            case 's' => FirstPlayerDisplayer.move(0, 1)
            case 'q' => FirstPlayerDisplayer.move(-1, 0)
            case 'd' => FirstPlayerDisplayer.move(1, 0)
            case _ => println(e.getKeyChar)
        }}
        pane.repaint()
        
    }

    def keyTyped (e : KeyEvent) : Unit = {}
}

object EmptyMapUI extends MapUI {}



class DrawPanelMap (mapDisplayer : MapDisplayer) extends JLabel with Repaintable {

    var ready : Boolean = false

    def initialise : Unit = {
        ready = true
    }


    override def paintComponent (g : Graphics) : Unit = {
        if (ready) {
            super.paintComponent(g)
            mapDisplayer.display(g)
        }
    }
}