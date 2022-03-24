class Pikachu extends Monster {
    catchRate = 190
    
    baseHpStat = 35
    baseAttackStat = 55
    baseDefenseStat = 40
    baseSpeedStat = 90

    xpGraph = "Medium Fast"
    baseXp = 112

    monsterType = Electric
    name = "Pikachu"
    originalName = "Pikachu"
    attacks(0) = QuickAttack
    attacks(1) = DoubleSlap
    attacks(2) = ThunderWave
    attacks(3) = Thunder

    description = "Whenever PIKACHU comes across something new, it blasts it with a jolt of electricity. If you come across a blackened berry, it's evidence that this POKEMON mistook the intensity of its charge."

}

class Squirtle extends Monster {
    baseHpStat = 44
    baseAttackStat = 48
    baseDefenseStat = 65
    baseSpeedStat = 50

    xpGraph = "Medium Slow"
    baseXp = 63

    monsterType = Water
    name = "Carapuuuce"
    originalName = "Squirtle"

    attacks(0) = Tackle
    attacks(1) = WaterGun
    attacks(2) = AquaTail
    attacks(3) = TailWhip

    uiYShift = 18

    description = "Its shell is not just for protection. Its rounded shape and the grooves on its surface minimize resistance in water, enabling SQUIRTLE to swim at high speeds."

}


class Bulbasaur extends Monster {
    baseHpStat = 45
    baseAttackStat = 49
    baseDefenseStat = 49
    baseSpeedStat = 45

    xpGraph = "Medium Slow"
    baseXp = 64

    monsterType = Grass
    name = "Bulbizare"
    originalName = "Bulbasaur"

    attacks(0) = Growl
    attacks(1) = Tackle
    attacks(2) = VineWhip
    attacks(3) = Growth

    uiYShift = 23

    description =  "BULBASAUR can be seen napping in bright sunlight. There is a seed on its back. By soaking up the sun's rays, the seed grows progressively larger."
}

class Charmander extends Monster {
    baseHpStat = 39
    baseAttackStat = 52
    baseDefenseStat = 43
    baseSpeedStat = 65

    xpGraph = "Medium Slow"
    baseXp = 62

    monsterType = Fire
    name = "Salameche"
    originalName = "Charmander"

    attacks(0) = Growl
    attacks(1) = Scratch
    attacks(2) = Ember
    attacks(3) = Flamethrower
    uiYShift = 18

    description = "The flame that burns at the tip of its tail is an indication of its emotions. The flame wavers when CHARMANDER is happy, and blazes when it is enraged."
}

class Rattata extends Monster {
    catchRate = 255

    baseHpStat = 30
    baseAttackStat = 56
    baseDefenseStat = 35
    baseSpeedStat = 72

    xpGraph = "Medium Fast"
    baseXp = 51

    monsterType = Normal
    name = "Ratatata"
    originalName = "Rattata"

    attacks(0) = Tackle
    attacks(1) = TailWhip
    attacks(2) = QuickAttack
    attacks(3) = Crunch
    uiYShift = 15

    description = "RATTATA is cautious in the extreme. Even while it is asleep, it constantly listens by moving its ears around. It is not picky about where it lives - it will make its nest anywhere."

}

