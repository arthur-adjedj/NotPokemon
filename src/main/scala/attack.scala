abstract class Attack {
    val name : String = ""
    val power : Int = 0
    val critChance : Float = 0
    val priority : Int = 0
    val accuracy : Float = 1
    val attackType : Type     
    val inducedStatus : Status = NoStatus
    val induceStatusChance : Float = 0
    val minHits = 1
    val maxHits = 1
}

object QuickAttack extends Attack {
    override val name = "quick attack"
    override val power = 40
    override val priority: Int = 1
    val attackType: Type = NormalType
}



