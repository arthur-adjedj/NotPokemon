import swing._
import swing.event._
import java.awt.{Color,Graphics2D,BasicStroke}
import java.awt.image.BufferedImage


class Battle extends Component {
  preferredSize = new Dimension(800, 800)
  var img = javax.imageio.ImageIO.read(getClass.getResource("test.png"))

  override def paintComponent (g : Graphics2D) : Unit = {
    super.paintComponent(g)
    g.drawImage(img, 500, 100, null)
    g.drawImage(img, 200, 600, null)
  }

}



object MyApp extends SimpleSwingApplication {
  
  var battle = new Battle


  def top = new MainFrame {
    title = "MyApplication"
    contents = battle
    size = new Dimension(800, 800)
  }
}