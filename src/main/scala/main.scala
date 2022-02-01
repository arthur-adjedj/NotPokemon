import swing._
import swing.event._
import java.awt.{Color,Graphics2D,BasicStroke}
import java.awt.image.BufferedImage


class Battle extends Component {
  preferredSize = new Dimension(800, 800)
  var battleBackgroundImg = javax.imageio.ImageIO.read(getClass.getResource("BattleBackground.png"))
  var pokemonFrontImg = javax.imageio.ImageIO.read(getClass.getResource("PikachuFront.png"))
  var pokemonBackImg = javax.imageio.ImageIO.read(getClass.getResource("SquirtleBack.png"))
  override def paintComponent (g : Graphics2D) : Unit = {
    super.paintComponent(g)
    g.drawImage(battleBackgroundImg, 0, 0, null)
    g.drawImage(pokemonFrontImg,375,20,null)
    g.drawImage(pokemonBackImg,85,159,null)
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