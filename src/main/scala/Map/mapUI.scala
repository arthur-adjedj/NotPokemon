import java.awt.event._
import java.awt.{Color,Graphics,BasicStroke,Font}
import java.awt.event.MouseEvent
import javax.swing.{JFrame, JPanel, JLabel}
import java.io.File


class MapUI extends JFrame with KeyListener {
    var sizeBlock = 40

    var posX = 1000
    var posY = 100
    var sizeX = 800
    var sizeY = 800

    var ready : Boolean = false

    var mapDisplayer : MapDisplayer = new MapDisplayer1 (new UI)

    var pane = new DrawPanelMap(mapDisplayer)
    var listening : Boolean = true

    def initialise : Unit = {

        mapDisplayer.initialise(1, sizeBlock)
        pane.initialise

        PlayerDisplayer.mapUI = this

        setSize(sizeX, sizeY)
        addKeyListener(this)
        setLayout(null)

        setUndecorated(true)
        setContentPane(pane)

        Utils.characterDisplayers.foreach(x => x.alignCoordinates)


        setLocation(posX, posY)
        ready = true
        setVisible(true)

    }

    def keyReleased (e : KeyEvent) : Unit = {}

    def keyPressed (e : KeyEvent) : Unit = {
        if (listening) {
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

object EmptyMapUI extends MapUI {}



class DrawPanelMap (mapDisplayer : MapDisplayer) extends MyPanel with Repaintable {

    var ready : Boolean = false

    override def initialise : Unit = {
        ready = true
    }


    override def paintComponent (g : Graphics) : Unit = {
        if (ready) {
            super.paintComponent(g)
            mapDisplayer.display(g)
        }
    }
}