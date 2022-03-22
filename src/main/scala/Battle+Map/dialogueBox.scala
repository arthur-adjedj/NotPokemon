import java.awt.{Color,Graphics,BasicStroke,Font}
import java.awt.font.TextAttribute
import collection.JavaConverters._
import java.util.concurrent.TimeUnit
import org.w3c.dom.Text
import scala.collection.mutable.Queue


object DiscussionLabel {
    var text1 : String = ""
    var text2 : String = ""
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
    var textChanger : TextChanger = new TextChanger(List())
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
        // if the text is changing then queue the message for later else write it
        Utils.print(s)
        visible = true
        if (!changingText) {
            changingText = true
            var liString = Utils.cutString(s, charPerLine)

            text1 = ""
            text2 = ""

            textChanger = new TextChanger(liString)
            textChanger.start
        } else {
            messageQueue.enqueue(s)
        }
    }

    def changeText (s : List[String]) : Unit = {
        s.foreach(x => changeText(x))
    }

    def skip : Unit = {
        textChanger.skip
    }
}

class TextChanger (liString_ : List[String]) extends Thread {
    var liString : List[String] = liString_

    var pausing : Boolean = true
    var waitTime : Int = 50
    var pauseTime : Int = 400
    override def run : Unit = {
        (0 until liString.length).foreach(x => showText(x))

        TimeUnit.MILLISECONDS.sleep(100)


        DiscussionLabel.changingText = false
        if (!DiscussionLabel.messageQueue.isEmpty) {
            DiscussionLabel.changeText(DiscussionLabel.messageQueue.dequeue)
        } else {
            DiscussionLabel.visible = Utils.frame.currentState == "Battle"
        }
    }

    def showText(n : Int) : Unit = {
        if (n < 2) {
            for (i <- liString(n).indices) {
                if (n == 0) {
                    DiscussionLabel.text1 += liString(n)(i)
                } else {
                    DiscussionLabel.text2 += liString(n)(i)
                }
                if (List('.','!').contains(liString(n)(i))) TimeUnit.MILLISECONDS.sleep(pauseTime)
                else TimeUnit.MILLISECONDS.sleep(waitTime)
            }
        } else {
            DiscussionLabel.text1 = DiscussionLabel.text2
            DiscussionLabel.text2 = ""
            for (i <- liString(n).indices) {
                DiscussionLabel.text2 += liString(n)(i)

                if (List('.','!').contains(liString(n)(i))) TimeUnit.MILLISECONDS.sleep(pauseTime)
                else TimeUnit.MILLISECONDS.sleep(waitTime)

            }
        }
    }

    def skip : Unit = {
        waitTime = 10
        pauseTime = 80
    }
}