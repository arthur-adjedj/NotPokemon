import java.awt.{Color,Graphics,BasicStroke,Font}
import java.awt.font.TextAttribute
import collection.JavaConverters._
import java.util.concurrent.TimeUnit
import org.w3c.dom.Text
import scala.collection.mutable.Queue


object DiscussionLabel {
    var text1 : String = ""
    var text2 : String = ""
    var battleUi : BattleUI = EmptyBattleUI
    var charPerLine : Int = 27
    var x : Int = 40
    var y : Int = 330

    var visible = true

    var textBarImg = Utils.loadImage("TextBar.png")
    
    var font : Font = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream("PokemonPixelFont.ttf"))
    font = font.deriveFont(Font.PLAIN,40)
    val attributes = (collection.Map(TextAttribute.TRACKING -> 0.05)).asJava
    font = font.deriveFont(attributes)
    font = font.deriveFont(font.getStyle() | Font.BOLD)
    var textChanger : TextChanger = new TextChanger("", "")
    var changingText : Boolean = false
    var messageQueue : Queue[String] = Queue()


    def display (g : Graphics) : Unit = {
        if (visible) {
            g.drawImage(textBarImg,0,287,null)
            g.setFont(font)
            g.drawString(text1, x, y)
            g.drawString(text2, x, y+30)
        }
    }

    def changeText (s : String) : Unit = {
        Utils.print(s)
        if (!changingText) {
            changingText = true
            var (t1, t2, t3) = Utils.cutString(s, charPerLine)

            text1 = ""
            text2 = ""

            textChanger = new TextChanger(t1, t2)
            textChanger.start
        } else {
            messageQueue.enqueue(s)
        }
    }

    def skip : Unit = {
        textChanger.skip
    }
}

class TextChanger (t1 : String, t2 : String) extends Thread {
    var text1 : String = t1
    var text2 : String = t2

    var pausing : Boolean = true
    var waitTime : Int = 50
    var pauseTime : Int = 400
    override def run : Unit = {
        for (i <- text1.indices) {
            DiscussionLabel.text1 += text1(i)
            if (List('.','!').contains(text1(i))) TimeUnit.MILLISECONDS.sleep(pauseTime)
            else TimeUnit.MILLISECONDS.sleep(waitTime)

        }
        for (i <- text2.indices) {
            DiscussionLabel.text2 += text2(i)

            if (List('.','!').contains(text2(i))) TimeUnit.MILLISECONDS.sleep(pauseTime)
            else TimeUnit.MILLISECONDS.sleep(waitTime)

        }
        DiscussionLabel.changingText = false
        if (!DiscussionLabel.messageQueue.isEmpty) {
            DiscussionLabel.changeText(DiscussionLabel.messageQueue.dequeue)
        }
    }

    def skip : Unit = {
        waitTime = 10
    }
}