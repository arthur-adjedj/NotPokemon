import java.util.concurrent.TimeUnit
import java.sql.Time

abstract class EasterEgg extends Thread {}

object EasterEgg1 extends EasterEgg {
    var messages : List[String] = List("Oh you are a patient player !", "What are you waiting for ?", "Don't you want to play ?",
                                       "Ok you won ! I'm giving 5 levels to your pokemons !", "Still don't wan't to play ? 5 levels again !", "This is the last time !")
    
    var secondsToSleep : Int = 30
    override def run : Unit = {
        for (i <- 0 until messages.size - 3) {
            TimeUnit.SECONDS.sleep(secondsToSleep)
            if (!PlayerDisplayer.hasDoneSomething) {
                DiscussionLabel.changeText(messages(i))
            }
        }
        for (i <- messages.size - 3 until messages.size) {
            TimeUnit.SECONDS.sleep(secondsToSleep)
            if (!PlayerDisplayer.hasDoneSomething) {
                DiscussionLabel.changeText(messages(i))
                PlayerDisplayer.player.team.foreach(x => x.gainLvl(5, false))
            }
        }
        TimeUnit.SECONDS.sleep(2*secondsToSleep)
        if (!PlayerDisplayer.hasDoneSomething) {
            DiscussionLabel.changeText("You are so patient ! I'm offering you a pokemon ! Will you accept it ?")
            Utils.askChoice("Yes No")
            Utils.waitForBooleanFunction(x => Utils.choiceDone != -1)
            if (Utils.choiceDone == 1) {
                var pokemon = new Eevee
                pokemon.gainLvl(19)
                DiscussionLabel.changeText("Which slot ?")
                Utils.askChoice("Pokemon Slot")
                Utils.waitForBooleanFunction(x => Utils.choiceDone != -1)
                Player.team(Utils.choiceDone) = pokemon
                pokemon.owner = Player
            }
        }
    }
}

object EasterEgg2 extends EasterEgg {
    var message : List[String] = List("(Time Traveler) You are a portal traveler ! Choose a slot for our gift !", "You will thank me later !")
    var loop : Boolean = true

    override def run : Unit = {
        while (loop) {
            if (PlayerDisplayer.portalsCrossed >= 5) {
                loop = false
                DiscussionLabel.changeText(message(0))
                Utils.askChoice("Pokemon Slot")
                Utils.waitForBooleanFunction(x => Utils.choiceDone != -1)
                var pokemon = new Gyarados
                Player.team(Utils.choiceDone) = pokemon
                pokemon.gainLvl(49, false)
                pokemon.owner = Player
                DiscussionLabel.changeText(message(1))
            } else {
                TimeUnit.MILLISECONDS.sleep(10)
            }

        }
    }
}

object EasterEgg3 extends EasterEgg {
    var messages : List[String] = List("Please stop !", "Really please stop !", "I said STOP !", "Will you stop if I give you a good pokemon ?", "I'm exhauted !",
                                      "This is your last chance ! If you stop I give you my best pokemon !")

    var count : Int = 0
    var i : Int = 0
    var loop : Boolean = true
    var trusted : Boolean = false

    override def run : Unit = {
        while (loop) {
            if (count == 10) {
                count = 0
                DiscussionLabel.changeText(messages(i))
                if (List(3, 5).contains(i)) {
                    askForStop(i)
                }
                if (i == messages.size - 1) {
                    loop = false
                }
                i += 1
            }
            TimeUnit.MILLISECONDS.sleep(10)
        }
    }

    def press : Unit = {
        count += 1
        if (trusted) {
            DiscussionLabel.changeText("I trusted you ! You deceived me ! I will annoy you every time you press this f*****g button !")
        }
    }

    def askForStop (n : Int) : Unit = {
        var pokemon : Monster = n match {
            case 3 => new Magikarp
            case 5 => new Snorlax
        }
        pokemon.gainLvl(10*n-1, false)
        Utils.askChoice("Yes No")
        Utils.waitForBooleanFunction(x => Utils.choiceDone != -1)
        if (Utils.choiceDone == 1) {
            DiscussionLabel.changeText("Which slot ?")
            Utils.askChoice("Pokemon Slot")
            Utils.waitForBooleanFunction(x => Utils.choiceDone != -1)
            Player.team(Utils.choiceDone) = pokemon
            pokemon.owner = Player
            DiscussionLabel.changeText("I hope you're honest !")
            loop = false
            trusted = true
        }

    }
}