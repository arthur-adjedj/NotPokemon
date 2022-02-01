class Battle (p1 : Player, p2 : Player) {
    
    def initialise : Unit = {
        p1.battle = this
        p2.battle = this

        p1.enterBattle
        p2.enterBattle

    }

    def start : Unit = {
        while (p1.playing && p2.playing) {
            p1.newTurn
            if (p2.playing) {
                p2.newTurn
            }
        }
    }




    
}

object EmptyBattle extends Battle (EmptyPlayer, EmptyPlayer) {

}