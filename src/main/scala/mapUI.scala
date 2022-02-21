import java.awt.event._
import java.awt.{Color,Graphics,BasicStroke,Font}
import java.awt.event.MouseEvent
import javax.swing.{JFrame, JPanel, JLabel}
import java.io.File


class MapUI extends JFrame with KeyListener {
    var sizeBlock = 10

    var posX = 1000
    var posY = 100
    var sizeX = 800
    var sizeY = 800

    var ready : Boolean = false

    var mapDisplayer : MapDisplayer = new MapDisplayer

    var pane = new DrawPanelMap(mapDisplayer)

    def initialise : Unit = {

        mapDisplayer.initialise(1)
        pane.initialise

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
        {e.getKeyChar.toString match {
            case "z" => println("C'est z")
            case _ => println(e.getKeyChar)
        }}
        
    }

    def keyTyped (e : KeyEvent) : Unit = {}
}



class DrawPanelMap (mapDisplayer : MapDisplayer) extends JLabel {

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