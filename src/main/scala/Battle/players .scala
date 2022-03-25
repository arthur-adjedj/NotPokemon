object SecondCharacter extends Opponent {
    team(0) = new Magikarp
    team(0).owner = this
    team(0).gainLvl(3,false)

    name = "Opponent"

    override def lose : Unit = {
        super.lose
        PlayerDisplayer.getMapItem(new Key(2))
        PlayerDisplayer.mapDisplayer.update
    }

    override def printLosingMessage : Unit = {
        DiscussionLabel.changeText(List("How did you beat me ?", "Anyway ! I'm opening the door so you can exit the maze !", "Do not thank me for that !"))
    }
}

object ThirdCharacter extends Opponent {
    team(0) = new Rattata
    team(0).owner = this
    team(0).gainLvl(4,false)

    team(1) = new Bulbasaur
    team(1).owner = this
    team(1).gainLvl(4,false)

    strat = Utils.controlStrat
    name = "Opponent"
}

object WildCharacter extends WildOpponent {
    team(0) = new Rattata
    team(0).owner = this
    team(0).gainLvl(4,false)

    def initialise : Unit = {
        team(0) = new Rattata
        team(0).owner = this
        team(0).gainLvl(4,false)
    }

}