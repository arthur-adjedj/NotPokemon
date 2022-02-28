import java.awt.event._
import java.awt.{Color,Graphics,BasicStroke,Font}
import java.awt.event.MouseEvent
import javax.swing.{JFrame, JPanel, JLabel}
import java.io.File
import java.awt.font.TextAttribute
import collection.JavaConverters._


import java.util.concurrent.TimeUnit
import scala.runtime.EmptyMethodCache
import javax.swing.DebugGraphics
import java.awt.event.MouseMotionListener
import java.awt.Toolkit


abstract class HpBar {
    var hpRate : Float = 1f
    var x : Int = 0
    var y : Int = 0
    var width : Int = 136
    var height : Int = 10

    def setRatio(value : Float) : Unit = {
        hpRate = value
    }


    def setColor(g : Graphics) : Unit = {
        if ( hpRate < 0.25 ) {
            g.setColor(java.awt.Color.red)
        } else {if ( hpRate < 0.75 ) {
            g.setColor(java.awt.Color.orange)
        } else {
            g.setColor(java.awt.Color.green)}
        }
    }

    def display (g : Graphics) : Unit = {
        g.setColor(java.awt.Color.white)
        g.fillRect( x, y ,width,height)       
        setColor(g)
        g.fillRect( x, y ,(width.toFloat*hpRate).toInt,height)       
    }
}


object EnnemyBar{
    def ennemyBarImg = Utils.loadImage("EnnemyBar.png")
    var p2 : Character = EmptyCharacter
    object EnnemyHpBar extends HpBar {
        x = 112
        y = 76
    }
    def display (g : Graphics) : Unit = {
        EnnemyHpBar.setRatio(p2.currentMonster.hpRate)
        EnnemyHpBar.display(g)
        g.drawImage(ennemyBarImg, 10, 40, null)
        g.setColor(new Color(68,64,69))
        g.drawString(p2.currentMonster.name,22,68)
        g.drawString("Lv:" + p2.currentMonster.level.toString,180,68)
    }
}

object YourBar{
    def yourBarImg = Utils.loadImage("YourBar.png")
    var p1 : Character = EmptyCharacter

    object YourHpBar extends HpBar {
        x = 443
        y = 226
        hpRate = 0.2f
    }

    object YourExpBar extends HpBar {
        x = 402
        y = 264
        width = 177
        height = 8
        hpRate = 1f

        val color : Color = new Color(0,244,215)
        override def setColor(g: Graphics): Unit = {
            g.setColor(color)
        }
    }

    def display (g : Graphics) : Unit = {
        YourHpBar.setRatio(p1.currentMonster.hpRate)
        YourExpBar.setRatio(p1.currentMonster.xpRate)
        YourHpBar.display(g)
        YourExpBar.display(g)
        g.drawImage(yourBarImg,320, 190, null)
        g.setColor(new Color(68,64,69))
        g.drawString(p1.currentMonster.name,355,218)
        g.drawString("Lv: " + p1.currentMonster.level.toString,513,218)
        g.drawString(p1.currentMonster.hp + " / "+ p1.currentMonster.hpMax,500,257)
    }
}


// object EmptyBattleUI extends BattleUI (EmptyCharacter, EmptyCharacter, EmptyBattle) {}

class DrawPanelBattle (buttonList : List[MyButton], p1 : Character, p2 : Character) extends MyPanel with Repaintable {
    var battleBackgroundImg = Utils.loadImage("BattleBackground.png")
    var pokemonFrontImg = Utils.loadImage("Monsters/EmptyFront.png")
    var pokemonBackImg = Utils.loadImage("Monsters/EmptyBack.png")
    var poke_font : Font = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream("PokemonPixelFont.ttf"))

    poke_font = poke_font.deriveFont(Font.PLAIN,30)
  
    override def paintComponent (g : Graphics) : Unit = {
        super.paintComponent(g)
        buttonList.foreach(x => x.update)
        g.setFont(poke_font)
        g.drawImage(battleBackgroundImg, 0, 0, null)

        PlayerMonsterDisplayer.update
        OpponentMonsterDisplayer.update

        PlayerMonsterDisplayer.display(g)
        OpponentMonsterDisplayer.display(g)
        
        EnnemyBar.display(g)
        YourBar.display(g)

        DiscussionLabel.display(g)
        g.setColor(Color.BLACK)
        g.fillRect(0, 0, getWidth, 20)
        buttonList.foreach(x => x.display(g))
        if (showHelp) {
            underMouse.onMouseOver(g, xMouse, yMouse, getWidth, getHeight)
        }
        
    }

}
