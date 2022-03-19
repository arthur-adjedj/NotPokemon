import java.awt.event._
import java.awt.{Color,Graphics,BasicStroke,Font}
import java.awt.event.MouseEvent
import javax.swing.{JFrame, JPanel, JLabel}
import java.io.File



class DrawPanelMap (mapDisplaye : MapDisplayer) extends MyPanel with Repaintable {

    var mapDisplayer : MapDisplayer = mapDisplaye
    var ready : Boolean = false

    override def initialise : Unit = {
        ready = true
    }


    override def paintComponent (g : Graphics) : Unit = {
        if (ready) {
            super.paintComponent(g)
            mapDisplayer.display(g)
            DiscussionLabel.display(g)
            endPaintComponent(g)
        }
    }
}