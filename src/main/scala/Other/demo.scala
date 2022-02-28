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