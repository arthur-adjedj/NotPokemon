case class Strategy (attack : Int, buff : Int, debuff : Int, status : Int, swap : Int)

trait ScoreForStrategy {
    def scoreForStrategy (self : Monster, ennemy : Monster) : Int
    var action : String
}


trait Intelligence {
    var strat : Strategy = Utils.aggroStrat
    var team : Array[Monster]
    var opponent : Character
    var currentMonster : Monster
    var availableAttacks : Array[Attack]
    def changeMonster (monster : Monster) : Boolean
    def chooseAttack (attack : Attack) : Unit

    def chooseAction : Unit = {
        var classicAttacks = availableAttacks.filter(x => x.action == "attack")
        var classicAttacksScore = classicAttacks.map(x => x.scoreForStrategy(currentMonster, opponent.currentMonster)*strat.attack)

        var buffAttacks = availableAttacks.filter(x => x.action == "buff")
        var buffAttacksScore = buffAttacks.map(x => x.scoreForStrategy(currentMonster, opponent.currentMonster)*strat.buff)

        var debuffAttacks = availableAttacks.filter(x => x.action == "debuff")
        var debuffAttacksScore = debuffAttacks.map(x => x.scoreForStrategy(currentMonster, opponent.currentMonster)*strat.debuff)

        var statusAttacks = availableAttacks.filter(x => x.action == "status")
        var statusAttacksScore = statusAttacks.map(x => x.scoreForStrategy(currentMonster, opponent.currentMonster)*strat.status)

        var availableTeam = team.filter(x => x.alive && x != currentMonster && 
            x.scoreForStrategy(x, opponent.currentMonster) >= currentMonster.scoreForStrategy(currentMonster, opponent.currentMonster)).toList
        var availableTeamScore = availableTeam.map(x => 
            (x.scoreForStrategy(x, opponent.currentMonster) - currentMonster.scoreForStrategy(currentMonster, opponent.currentMonster))*strat.swap)

        var allActions = List.concat(classicAttacks, buffAttacks, debuffAttacks, statusAttacks, availableTeam)
        var allActionsScore = List.concat(classicAttacksScore, buffAttacksScore, debuffAttacksScore, statusAttacksScore, availableTeamScore)

        var sumScores = allActionsScore.fold(0)((x, y) => x + y)
        var random = scala.util.Random.nextInt(sumScores)

        for (i <- 0 until allActions.length) {
            Utils.print(allActions(i), allActionsScore(i))
        }

        var i = -1
        while (random > 0) {
            i += 1
            random -= allActionsScore(i)
        }
        var actionToDo = allActions(i)
        actionToDo.action match {
            case "monster" => changeMonster(actionToDo.asInstanceOf[Monster])
            case _ => chooseAttack(actionToDo.asInstanceOf[Attack])
        }

    }
}