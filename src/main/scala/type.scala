abstract class Type {
    var name : String = "" 
    def multDamage (t : Type) : Float = 1f
    def imageButtonName : String = {"Buttons/" + name + "Button.png"}

    override def toString : String = name
}

object Normal extends Type {
    name = "Normal"
}

object Fire extends Type {
    name = "Fire"
    override def multDamage(t: Type) : Float = {t.name match {
        case "Fire" => 1f/2f
        case "Water" => 1f/2f
        case "Grass" => 2f  
        case "Ice" => 2f
        case _ => 1f
        }
    }
}

object Water extends Type {
    name = "Water"
    override def multDamage(t: Type) : Float = {t.name match {
        case "Fire" => 2f
        case "Water" => 1f/2f
        case "Grass" => 1f/2f 
        case _ => 1f
        }
    }
}

object Electric extends Type {
    name = "Electric"
    override def multDamage(t: Type) : Float = {t.name match {
        case "Water" => 2f
        case "Electric" => 1f/2f
        case "Grass" => 1f/2f   
        case _ => 1f
        }
    }
}   

object Grass extends Type {
    name = "Grass"
    override def multDamage(t: Type) : Float = {t.name match {
        case "Fire" => 1f/2f
        case "Water" => 2f
        case "Grass" => 1f/2f   
        case _ => 1f
        }
    }
}

object Ice extends Type {
    name = "Ice"
    override def multDamage(t: Type) : Float = {t.name match {
        case "Water" => 1f/2f
        case "Grass" => 2f   
        case "Ice" => 1f/2f
        case _ => 1f
        }
    }
}   

object EmptyType extends Type {
    
}