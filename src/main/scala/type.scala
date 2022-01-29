abstract class Type {
    def name : String 
    def multDamage (t : Type) : Float
}

object NormalType extends Type {
    override def name = "normal"
    override def multDamage(t: Type) : Float = 1
}

object FireType extends Type {
    override def name = "fire"
    override def multDamage(t: Type) : Float = {t.name match {
        case "normal" => 1
        case "fire" => 1/2
        case "water" => 1/2
        case "grass" => 2   
        }
    }
}

object WaterType extends Type {
    override def name = "water"
    override def multDamage(t: Type) : Float = {t.name match {
        case "normal" => 1
        case "fire" => 2
        case "water" => 1/2
        case "grass" => 1/2   
        }
    }
}

object GrassType extends Type {
    override def name = "grass"
    override def multDamage(t: Type) : Float = {t.name match {
        case "normal" => 1
        case "fire" => 1/2
        case "water" => 2
        case "grass" => 1/2   
        }
    }
}