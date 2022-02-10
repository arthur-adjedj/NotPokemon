import java.util.concurrent.TimeUnit

class Battle (p1 : Player, p2 : Player) {
    var ui : BattleUI = new BattleUI(p1, p2, this)
    def initialise : Unit = {
        ui.initialise

        p1.battle = this
        p2.battle = this

        p1.opponent = p2
        p2.opponent = p1

        p1.enterBattle
        p2.enterBattle

        ui.updateImages
    }

    def start : Unit = {
        while (p1.playing && p2.playing) {
            p1.newTurn
            ui.updateImages
            TimeUnit.SECONDS.sleep(1)
            if (p2.playing) {
                p2.newTurn
                ui.updateImages
                //TimeUnit.SECONDS.sleep(1)
            }
        }
    }
}

object EmptyBattle extends Battle (EmptyPlayer, EmptyPlayer) {
    
}