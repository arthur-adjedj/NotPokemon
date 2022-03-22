import javax.swing.{JFrame, JPanel, JLabel}
import java.io.File
import java.awt.{Color,Graphics,BasicStroke,Font}
import java.awt.image.BufferedImage
import java.util.concurrent.TimeUnit

import java.lang.Math

object Utils {

    // some global variables

    var characterDisplayers : List[CharacterDisplayer] = List()
    var repaintables : List[Repaintable] = List()
    var updatable : List[Updatable] = List()
    var frame : UI = EmptyUI

    var castAttackButtonList : List[CastAttackButton] = (0 to 3).map(x => new CastAttackButton(x)).toList
    var useItemButtonList : List[UseItemButton] = (0 to 3).map(x => new UseItemButton(x)).toList
    var changeMonsterButtonList : List[ChangeMonsterButton] = (0 to 5).map(x => new ChangeMonsterButton(x)).toList

    // cannot create the list and adding buttons during their creation because of a weird behaviour with 'object'
    var buttonList : List[MyButton] = List.concat(List(AttackButton, BagButton, MonsterButton, RunButton, BackButton, NextPageItemButton, CloseButton, HelpButton),
                                    castAttackButtonList, useItemButtonList, changeMonsterButtonList)

    var descriptables : List[Descriptable] = List.concat(List(PlayerMonsterDisplayer, OpponentMonsterDisplayer), buttonList)

    var debug = false

    // some strategies
    var aggroStrat = Strategy(1, 0, 0, 0, 0)
    var tempoStrat = Strategy(1, 1, 1, 0, 0)
    var controlStrat = Strategy(1, 1, 1, 0, 1)

    def start : Unit = {
        Repainter.start
        Updater.start
    }

    def print[T] (s : T) : Unit = {
        if (debug) {
            println(s)
        }
    }


    def loadImage (name : String) : BufferedImage = {
        // if the image doesn't load, it returns a wrong image but doesn't crash
        // sometimes, it's the JVM's fault
        try {
            javax.imageio.ImageIO.read(getClass.getResource(name))
        }
        catch {
            case _ : Throwable => Utils.print("Issues while importing " + name); javax.imageio.ImageIO.read(getClass.getResource("Empty.png"))
        }
    }

    def findFirstOccurenceArray [T](array : Array[T], element : T) : Int = {
        var i : Int = 0
        var found : Boolean = false
        while (!found && i < array.length) {
            found = array(i) == element
            i += 1
        }
        if (found) {
            i - 1 
        } else {
            -1
        }
    }

    def cutString (s : String, charPerLine : Int) : (String, String, String) = {
        var text1 = ""
        var text2 = ""
        var text3 = ""
        if (s.length <= charPerLine) {
            text1 = s
        } else {
            var l = s.substring(0, charPerLine).lastIndexOf(" ")
            text1 = s.substring(0, l)
            text2 = s.substring(l+1)
            if (text2.length > charPerLine) {
                var s = text2
                l = s.substring(0, charPerLine).lastIndexOf(" ")
                text2 = s.substring(0, l)
                text3 = s.substring(l+1)
            }
        }
        (text1, text2, text3)
    }

    def waitDiscussionLabel : Unit = {
        waitDiscussionLabel(false)
    }

    def waitDiscussionLabel (waitLonger : Boolean): Unit = {
        while (!DiscussionLabel.messageQueue.isEmpty || DiscussionLabel.changingText) {
            TimeUnit.MILLISECONDS.sleep(10)    
        }
        if (waitLonger) {
            TimeUnit.MILLISECONDS.sleep(200)
        }
    }

    def distance (i1 : Int, j1 : Int, i2 : Int, j2 : Int) : Double = {
        Math.sqrt(Math.pow(i2-i1, 2) + Math.pow(j2-j1, 2))
    }

    def bestMove (iCurrent : Int, jCurrent : Int, iGoal : Int, jGoal : Int) : (Int, Int) = {
        if (iCurrent > iGoal) {
            (-1, 0)
        } else if (iCurrent < iGoal) {
            (1, 0)
        } else if (jCurrent > jGoal) {
            (0, -1)
        } else if (jCurrent < jGoal) {
            (0, 1)
        } else {
            (0, 0)
        }

    }
}

trait Repaintable {
    Utils.repaintables = this :: Utils.repaintables
    def repaint() : Unit
}

trait Updatable {
    Utils.updatable = this :: Utils.updatable
    def update : Unit
}

class Mover extends Thread {
    // used to move the image of a character
    var lastMoveX : Int = 0
    var lastMoveY : Int = 0
    var characterDisplayer : CharacterDisplayer = EmptyCharacterDisplayer

    
    def move (moveX : Int, moveY : Int) : Unit = {
        characterDisplayer.i += moveX
        characterDisplayer.j += moveY
        lastMoveX = moveX
        lastMoveY = moveY
        start
    }

    override def run : Unit = {
        for (i <- 0 to characterDisplayer.mapDisplayer.sizeBlock - 1) {
            characterDisplayer.changeCoordinates(lastMoveX, lastMoveY)
            if (i % (characterDisplayer.mapDisplayer.sizeBlock/4) == 0 && !characterDisplayer.sliding) {
                characterDisplayer.nx = (characterDisplayer.nx + 1) % 4
            }
            if (i != characterDisplayer.mapDisplayer.sizeBlock - 1) {
                TimeUnit.MILLISECONDS.sleep(100/characterDisplayer.speed)
            }
        }
        characterDisplayer.endMove
    }
}

class MoverToBattle (i : Int, j : Int, c : CharacterDisplayer) extends Thread {
    // be careful to have a cleared path to avoid softlock
    var iTarget : Int = i
    var jTarget : Int = j
    var characterDisplayer : CharacterDisplayer = c

    override def run : Unit = {
        while (Utils.distance(characterDisplayer.i, characterDisplayer.j, iTarget, jTarget) > 1 || characterDisplayer.isMoving) {
            var (i, j) = Utils.bestMove(characterDisplayer.i, characterDisplayer.j, iTarget, jTarget)
            characterDisplayer.move(i, j)
        }
        Utils.frame.startBattle(Player, characterDisplayer.player)

    }
}

object Repainter extends Thread {
    override def run : Unit = {
        while (true) {
            Utils.repaintables.foreach(x => x.repaint())
            TimeUnit.MILLISECONDS.sleep(100/6)
        }
    }
}

object Updater extends Thread {
    override def run : Unit = {
        while (true) {
            Utils.updatable.foreach(x => x.update)
            //Utils.print(Utils.updatable)
            TimeUnit.MILLISECONDS.sleep(100/6)
        }
    }
}

trait Descriptable {

    def onMouseOver (g : Graphics, xMouse : Int, yMouse : Int, width : Int, height : Int) : Unit = {}
    def isMouseOver (x : Int, y : Int) : Boolean = false
    var context : String = "All"
}

object EmptyDescriptable extends Object with Descriptable {
    override def isMouseOver (x : Int, y : Int) = true
}