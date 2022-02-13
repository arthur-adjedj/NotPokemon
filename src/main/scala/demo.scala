object Demo {
    def start : Unit = {
        var b = new Battle(FirstPlayer, SecondPlayer)
        b.initialise
        b.start

        b = new Battle(FirstPlayer, WildPlayer)
        b.initialise
        b.start

        b = new Battle(FirstPlayer, ThirdPlayer)
        b.initialise
        b.start
    }
}

object SecondPlayer extends Opponent {
    team(0) = new Squirtle
    team(0).owner = this
    team(0).gainLvl(3)

    team(1) = new Charmander
    team(1).owner = this
    team(1).gainLvl(3)
    name = "Opponent"
}

object ThirdPlayer extends Opponent {
    team(0) = new Rattata
    team(0).owner = this
    team(0).gainLvl(4)

    team(1) = new Bulbasaur
    team(1).owner = this
    team(1).gainLvl(4)
    name = "Opponent"
}

object WildPlayer extends WildOpponent {
    team(0) = new Rattata
    team(0).owner = this
    team(0).gainLvl(4)

}