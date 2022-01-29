abstract class Type {
    var name : String = "" 
    def multDamage (t : Type) : Float = 1
}

object Normal extends Type {
    name = "Normal"
}

object Fire extends Type {
    name = "Fire"
    override def multDamage(t: Type) : Float = {t.name match {
        case "Fire" => 1/2
        case "Water" => 1/2
        case "Grass" => 2  
        case "Ice" => 2
        case _ => 1 
        }
    }
}

object Water extends Type {
    name = "Water"
    override def multDamage(t: Type) : Float = {t.name match {
        case "Fire" => 2
        case "Water" => 1/2
        case "Grass" => 1/2 
        case _ => 1
        }
    }
}

object Electric extends Type {
    name = "Electric"
    override def multDamage(t: Type) : Float = {t.name match {
        case "Water" => 2
        case "Electric" => 1/2
        case "Grass" => 1/2   
        case _ => 1
        }
    }
}   

object Grass extends Type {
    name = "Grass"
    override def multDamage(t: Type) : Float = {t.name match {
        case "Fire" => 1/2
        case "Water" => 2
        case "Grass" => 1/2   
        case _ => 1
        }
    }
}

object Ice extends Type {
    name = "Ice"
    override def multDamage(t: Type) : Float = {t.name match {
        case "Water" => 1/2
        case "Grass" => 2   
        case "Ice" => 1/2
        case _ => 1
        }
    }
}   