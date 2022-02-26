object BattleTest {
    def start : Unit = {
        var b = new Battle(Player, SecondCharacter)
        b.initialise
        b.start

        // b = new Battle(Player, WildCharacter)
        // b.initialise
        // b.start

        // b = new Battle(Player, ThirdCharacter)
        // b.initialise
        // b.start
    }
}

object SecondCharacter extends Opponent {
    team(0) = new Squirtle
    team(0).owner = this
    team(0).gainLvl(3)

    name = "Opponent"
}

object ThirdCharacter extends Opponent {
    team(0) = new Rattata
    team(0).owner = this
    team(0).gainLvl(4)

    team(1) = new Bulbasaur
    team(1).owner = this
    team(1).gainLvl(4)
    name = "Opponent"
}

object WildCharacter extends WildOpponent {
    team(0) = new Rattata
    team(0).owner = this
    team(0).gainLvl(4)

    def initialise : Unit = {
        team(0) = new Rattata
        team(0).owner = this
        team(0).gainLvl(4)
    }

}