import java.awt.event._
import java.awt.{Color,Graphics,BasicStroke,Font}
import java.awt.event.MouseEvent
import javax.swing.{JFrame, JPanel, JLabel}
import java.io.File



class DrawPanelMap (mapDisplayer_ : MapDisplayer) extends MyPanel {

    var mapDisplayer : MapDisplayer = mapDisplayer_


    override def paintComponent (g : Graphics) : Unit = {
        if (ready) {
            super.paintComponent(g)
            mapDisplayer.display(g)
            DiscussionLabel.display(g)
            endPaintComponent(g)
        }
    }
}