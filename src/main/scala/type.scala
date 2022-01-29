abstract class Type {
    def name : String 
    def multDamage (t : Type) : Float
}

object Normal extends Type {
    override def name = "Normal"
    override def multDamage(t: Type) : Float = 1
}

object Fire extends Type {
    override def name = "Fire"
    override def multDamage(t: Type) : Float = {t.name match {
        case "Fire" => 1/2
        case "Water" => 1/2
        case "Grass" => 2  
        case _ => 1 
        }
    }
}

object Water extends Type {
    override def name = "Water"
    override def multDamage(t: Type) : Float = {t.name match {
        case "Fire" => 2
        case "Water" => 1/2
        case "Grass" => 1/2 
        case _ => 1
        }
    }
}

object Electric extends Type {
    override def name = "Electric"
    override def multDamage(t: Type) : Float = {t.name match {
        case "Water" => 2
        case "Electric" => 1/2
        case "Grass" => 1/2   
        case _ => 1
        }
    }
}   

object Grass extends Type {
    override def name = "Grass"
    override def multDamage(t: Type) : Float = {t.name match {
        case "Fire" => 1/2
        case "Water" => 2
        case "Grass" => 1/2   
        case _ => 1
        }
    }
}

object Ice extends Type {
    override def name = "Ice"
    override def multDamage(t: Type) : Float = {t.name match {
        case "Fire" => 1/2
        case "Water" => 2
        case "Grass" => 1/2   
        case _ => 1
        }
    }
}   