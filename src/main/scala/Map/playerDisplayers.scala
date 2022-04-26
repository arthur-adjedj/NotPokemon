
object SecondPlayerDisplayer extends OpponentDisplayer ("Characters/Louis.png") {

    direction = Left
    
    interceptLength = 6
    player = LouisBattle
    speed = 1

    i = 14
    j = 4

    whichMap = 1
    speed = 10


}

object SecondCharacterDisplayer extends OpponentDisplayer ("Characters/FemaleCharacter.png") {

    player = SecondCharacter
    interceptLength = 0

    direction = Up
    whichMap = 1
    i = 12
    j = 13

}

// this one is used for tests
object MissingCharacterDisplayer extends OpponentDisplayer ("Characters/MainCharacter.png") {
    player = MissingCharacter
    whichMap = 1
    interceptLength = 0
    i = 20
    j = 20
}

object EmptyCharacterDisplayer extends CharacterDisplayer ("Empty.png") {
    override def initialise : Unit = {}
    override def update : Unit = {}
}
