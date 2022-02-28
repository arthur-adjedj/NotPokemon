import java.awt.event._
import java.awt.{Color,Graphics,BasicStroke,Font}
import java.awt.event.MouseEvent
import javax.swing.{JFrame, JPanel, JLabel}
import java.io.File



class DrawPanelMap (mapDisplayer : MapDisplayer) extends MyPanel with Repaintable {

    var ready : Boolean = false

    override def initialise : Unit = {
        ready = true
    }


    override def paintComponent (g : Graphics) : Unit = {
        if (ready) {
            super.paintComponent(g)
            mapDisplayer.display(g)
            g.setColor(Color.BLACK)
            g.fillRect(0, 0, getWidth, 20)
        }
    }
}