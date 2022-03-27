import java.util.concurrent.TimeUnit
import java.sql.Time

class Battle (p1 : Character, p2 : Character) extends Thread {
    var winner : Character = EmptyCharacter
    var loser : Character = EmptyCharacter


    def initialise : Unit = {
        //character can't move or interact on the map during battle
        PlayerDisplayer.canInteract = false

        YourBar.p1 = p1
        EnnemyBar.p2 = p2

        p1.battle = this
        p2.battle = this

        p1.opponent = p2
        p2.opponent = p1

        p1.enterBattle
        p2.enterBattle

        Player.updateInventory
        DiscussionLabel.changeText("")
    }

    override def run : Unit = {
        // while both players are able to play, they choose an attack and cast it
        while (p1.playing && p2.playing) {
            Player.updateInventory
            p1.newTurn
            p2.newTurn
            var monsterP1 = p1.currentMonster
            var monsterP2 = p2.currentMonster
            if (p1.currentAttack.priority > p2.currentAttack.priority || 
                (p1.currentAttack.priority == p2.currentAttack.priority && p1.currentMonster.getSpeed >= p2.currentMonster.getSpeed)) {
                p1.castAttack(p1.currentAttack)
                TimeUnit.SECONDS.sleep(1)
                if (monsterP2.alive && p2.playing) {
                    p2.castAttack(p2.currentAttack)
                }
            } else {
                p2.castAttack(p2.currentAttack)
                TimeUnit.SECONDS.sleep(1)
                if (monsterP1.alive && p1.playing) {
                    p1.castAttack(p1.currentAttack)
                }
            }
            p1.currentAttack = EmptyAttack
            p2.currentAttack = EmptyAttack
        }
        Utils.waitDiscussionLabel(true)
        Utils.frame.backToMap(loser.losingMessage)
        Utils.frame.battlePane.ready = false
        PlayerDisplayer.canInteract = true
    }
}

object EmptyBattle extends Battle (EmptyCharacter, EmptyCharacter) {
    
}