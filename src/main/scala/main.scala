import scala.swing._
import scala.swing.event._

object ButtonApp extends SimpleSwingApplication {
  def top: Frame = new MainFrame {
    title = "My Frame"
    contents = new GridPanel(7, 100) {
      hGap = 3
      vGap = 3
      contents += new Button {
        text = "Press Me!"
        reactions += {
          case ButtonClicked(_) => text = "Hello Scala"
        }
      }
      contents += new Label("This is a label !")
    }
    size = new Dimension(800,800)
  }
}   