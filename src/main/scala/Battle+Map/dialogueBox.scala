import java.awt.{Color,Graphics,BasicStroke,Font}
import java.awt.font.TextAttribute
import collection.JavaConverters._


object DiscussionLabel {
    var text1 : String = ""
    var text2 : String = ""
    var battleUi : BattleUI = EmptyBattleUI
    var charPerLine : Int = 27
    var x : Int = 40
    var y : Int = 330
    var font : Font = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream("PokemonPixelFont.ttf"))
    font = font.deriveFont(Font.PLAIN,40)
    val attributes = (collection.Map(TextAttribute.TRACKING -> 0.05)).asJava
    font = font.deriveFont(attributes)
    font = font.deriveFont(font.getStyle() | Font.BOLD)


    def display (g : Graphics) : Unit = {
        g.setFont(font)
        g.drawString(text1, x, y)
        g.drawString(text2, x, y+30)
    }

    def changeText (s : String) : Unit = {
        Utils.print(s)
        var (t1, t2, t3) = Utils.cutString(s, charPerLine)
        text1 = t1
        text2 = t2
        battleUi.pane.repaint()
    }
}