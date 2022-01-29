import java.text.Normalizer
abstract class Monster {
    var hpMax : Int = 100
    var hp : Int = 100
    var level : Int = 1
    var accuracy : Float = 1
    var attackStat : Int = 1
    var defenseStat : Int = 1

    val monsterType : Type
    def name : String

    def castAttack (attack : Attack, other : Monster) : Unit = {}
    def receiveAttack (attack : Attack, other : Monster) : Unit = {}
    def heal (amount : Int) : Unit = {
        hp += amount
        if (hp > hpMax) {
            hp = hpMax
        }
    }

    def takeDamage (amount : Int) : Unit = {
        hp -= amount
        if (hp <= 0) {
            die
        }
    }

    def die = {
        println("I'm dying")
    }
}

object Pikachu extends Monster {
    accuracy = 19/20
    override val monsterType = NormalType
    override def name = "It's Pikachuuuuuu"

}

