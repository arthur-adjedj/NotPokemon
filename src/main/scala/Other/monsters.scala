class Pikachu extends Monster {
    height = "0.4m"
    weight = "6.0kg"

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

    description = "When several of these POKEMON gather, their electricity could build and cause lightning storms."

    uiYShift = -8
}

class Raichu extends Monster {
    height = "0.8m"
    weight = "30.0kg"

    catchRate = 75
    
    baseHpStat = 60
    baseAttackStat = 90
    baseDefenseStat = 55
    baseSpeedStat = 110

    xpGraph = "Medium Fast"
    baseXp = 218

    monsterType = Electric
    name = "Raichu"
    originalName = "Raichu"
    
    attacks(0) = QuickAttack
    attacks(1) = DoubleSlap
    attacks(2) = ThunderWave
    attacks(3) = Thunder

    description = "Its long tail serves as a ground to protect itself from its own high voltage power."

    uiYShift = -4
}

class Voltorb extends Monster {
    height = "0.5m"
    weight = "10.4kg"

    catchRate = 190
    
    baseHpStat = 40
    baseAttackStat = 30
    baseDefenseStat = 50
    baseSpeedStat = 100

    xpGraph = "Medium Fast"
    baseXp = 66

    monsterType = Electric
    name = "Voltorb"
    originalName = "Voltorb"
    
    attacks(0) = QuickAttack
    attacks(1) = DoubleSlap
    attacks(2) = ThunderWave
    attacks(3) = Thunder

    description = "Usually found in power plants. Easily mistaken for a POKEBALL, they have zapped many people."
    uiYShift = 23
}

class Electrode extends Monster {
    height = "1.2m"
    weight = "66.6kg"

    catchRate = 60
    
    baseHpStat = 60
    baseAttackStat = 50
    baseDefenseStat = 70
    baseSpeedStat = 150

    xpGraph = "Medium Fast"
    baseXp = 172

    monsterType = Electric
    name = "Electrode"
    originalName = "Electrode"
    
    attacks(0) = QuickAttack
    attacks(1) = DoubleSlap
    attacks(2) = ThunderWave
    attacks(3) = Thunder

    description = "It stores electric energy under very high pressure. It often explodes with little or no provocation."
    uiYShift = 15
}

class Electabuzz extends Monster {
    height = "1.1m"
    weight = "30.0kg"

    catchRate = 45
    
    baseHpStat = 65
    baseAttackStat = 83
    baseDefenseStat = 57
    baseSpeedStat = 105

    xpGraph = "Medium Fast"
    baseXp = 172

    monsterType = Electric
    name = "Electabuzz"
    originalName = "Electabuzz"
    
    attacks(0) = QuickAttack
    attacks(1) = DoubleSlap
    attacks(2) = ThunderWave
    attacks(3) = Thunder

    description = "Normally found near power plants, they can wander away and cause major blackouts in cities."
    uiYShift = -8

}

class Jolteon extends Monster {
    height = "0.8m"
    weight = "24.5kg"

    catchRate = 45
    
    baseHpStat = 65
    baseAttackStat = 65
    baseDefenseStat = 60
    baseSpeedStat = 130

    xpGraph = "Medium Fast"
    baseXp = 184

    monsterType = Electric
    name = "Jolteon"
    originalName = "Jolteon"
    
    attacks(0) = QuickAttack
    attacks(1) = DoubleSlap
    attacks(2) = ThunderWave
    attacks(3) = Thunder

    description = "It accumulates negative ions in the atmosphere to blast out 10000-volt lightning bolts."
    uiYShift = -4

}

class Squirtle extends Monster {
    height = "0.5m"
    weight = "9.0kg"

    catchRate = 45

    baseHpStat = 44
    baseAttackStat = 48
    baseDefenseStat = 65
    baseSpeedStat = 50

    xpGraph = "Medium Slow"
    baseXp = 63

    monsterType = Water
    name = "Squirtle"
    originalName = "Squirtle"

    attacks(0) = Tackle
    attacks(1) = WaterGun
    attacks(2) = AquaTail
    attacks(3) = TailWhip

    uiYShift = 7

    description = "After birth, its back swells and hardens into a shell. Powerfully sprays foam from its mouth."

}

class Wartortle extends Monster {
    height = "1.0m"
    weight = "22.5kg"


    catchRate = 45

    baseHpStat = 59
    baseAttackStat = 63
    baseDefenseStat = 80
    baseSpeedStat = 58

    xpGraph = "Medium Slow"
    baseXp = 142
    
    monsterType = Water
    name = "Wartortle"
    originalName = "Wartortle"

    attacks(0) = Tackle
    attacks(1) = TailWhip
    attacks(2) = WaterGun

    description = "Often hides in water to stalk unwary prey. For swimming fast, it moves its ears to maintain balance."
    uiYShift = -2

}

class Blastoise extends Monster {
    height = "1.6m"
    weight = "85.5kg"

    catchRate = 45

    baseHpStat = 79
    baseAttackStat = 83
    baseDefenseStat = 100
    baseSpeedStat = 78

    xpGraph = "Medium Slow"
    baseXp = 239
    
    monsterType = Water
    name = "Blastoise"
    originalName = "Blastoise"

    attacks(0) = Tackle
    attacks(1) = TailWhip
    attacks(2) = WaterGun

    description = "A brutal POKEMON with pressurized water jets on its shell. They are used for high speed tackles."

    uiYShift = 0
}

class Psyduck extends Monster {
    height = "0.8m"
    weight = "19.6kg"

    catchRate = 190

    baseHpStat = 50
    baseAttackStat = 52
    baseDefenseStat = 48
    baseSpeedStat = 55

    xpGraph = "Medium Fast"
    baseXp = 64
    
    monsterType = Water
    name = "Psyduck"
    originalName = "Psyduck"

    attacks(0) = Tackle
    attacks(1) = TailWhip
    attacks(2) = WaterGun

    description = "While lulling its enemies with its vacant look, this wily POKEMON will use psychokinetic powers."
    uiYShift = 5

}

class Golduck extends Monster {
    height = "1.7m"
    weight = "76.6kg"

    catchRate = 75

    baseHpStat = 80
    baseAttackStat = 82
    baseDefenseStat = 78
    baseSpeedStat = 85

    xpGraph = "Medium Fast"
    baseXp = 175
    
    monsterType = Water
    name = "Golduck"
    originalName = "Golduck"

    attacks(0) = Tackle
    attacks(1) = TailWhip
    attacks(2) = WaterGun

    description = "Often seen swimming elegantly by lake shores. It is often mistaken for the Japanese monster, Kappa."
    uiYShift = -10

}

class Poliwag extends Monster {
    height = "0.6m"
    weight = "12.4kg"

    catchRate = 255

    baseHpStat = 40
    baseAttackStat = 50
    baseDefenseStat = 40
    baseSpeedStat = 90

    xpGraph = "Medium Slow"
    baseXp = 60
    
    monsterType = Water
    name = "Poliwag"
    originalName = "Poliwag"

    attacks(0) = Tackle
    attacks(1) = TailWhip
    attacks(2) = WaterGun

    description = "Its newly grown legs prevent it from running. It appears to prefer swimming than trying to stand."
    uiYShift = 31

}

class Poliwhirl extends Monster {
    height = "1.0m"
    weight = "20.0kg"

    catchRate = 120

    baseHpStat = 65
    baseAttackStat = 65
    baseDefenseStat = 65
    baseSpeedStat = 90

    xpGraph = "Medium Slow"
    baseXp = 135
    
    monsterType = Water
    name = "Poliwhirl"
    originalName = "Poliwhirl"

    attacks(0) = Tackle
    attacks(1) = TailWhip
    attacks(2) = WaterGun

    description = "Capable of living in or out of water. When out of water, it sweats to keep its body slimy."
    uiYShift = 15

}

class Seel extends Monster {
    height = "1.1m"
    weight = "90.0kg"

    catchRate = 190

    baseHpStat = 65
    baseAttackStat = 45
    baseDefenseStat = 55
    baseSpeedStat = 45

    xpGraph = "Medium Fast"
    baseXp = 65
    
    monsterType = Water
    name = "Seel"
    originalName = "Seel"

    attacks(0) = Tackle
    attacks(1) = TailWhip
    attacks(2) = WaterGun

    description = "The protruding horn on its head is very hard. It is used for bashing through thick ice."
    uiYShift = 15

}

class Shellder extends Monster {
    height = "0.3m"
    weight = "4.0kg"

    catchRate = 190

    baseHpStat = 30
    baseAttackStat = 65
    baseDefenseStat = 100
    baseSpeedStat = 40

    xpGraph = "Slow"
    baseXp = 61
    
    monsterType = Water
    name = "Shellder"
    originalName = "Shellder"

    attacks(0) = Tackle
    attacks(1) = TailWhip
    attacks(2) = WaterGun

    description = "Its hard shell repels any kind of attack. It is vulnerable only when its shell is open."
    uiYShift = 39
}

class Krabby extends Monster {
    height = "0.4m"
    weight = "6.5kg"

    catchRate = 225

    baseHpStat = 30
    baseAttackStat = 105
    baseDefenseStat = 90
    baseSpeedStat = 50

    xpGraph = "Medium Fast"
    baseXp = 65
    
    monsterType = Water
    name = "Krabby"
    originalName = "Krabby"

    attacks(0) = Tackle
    attacks(1) = TailWhip
    attacks(2) = WaterGun

    description = "Its pincers are not only powerful weapons, they are used for balance when walking sideways."
    uiYShift = 21

}

class Kingler extends Monster {
    height = "1.3m"
    weight = "60.0kg"

    catchRate = 60

    baseHpStat = 55
    baseAttackStat = 130
    baseDefenseStat = 115
    baseSpeedStat = 75

    xpGraph = "Medium Fast"
    baseXp = 166
    
    monsterType = Water
    name = "Kingler"
    originalName = "Kingler"

    attacks(0) = Tackle
    attacks(1) = TailWhip
    attacks(2) = WaterGun

    description = "The large pincer has 10000 hp of crushing power. However, its huge size makes it unwieldy to use."
    uiYShift = -6

}

class Horsea extends Monster {
    height = "0.4m"
    weight = "8.0kg"

    catchRate = 225

    baseHpStat = 30
    baseAttackStat = 40
    baseDefenseStat = 70
    baseSpeedStat = 60

    xpGraph = "Medium Fast"
    baseXp = 59
    
    monsterType = Water
    name = "Horsea"
    originalName = "Horsea"

    attacks(0) = Tackle
    attacks(1) = TailWhip
    attacks(2) = WaterGun

    description = "Known to shoot down flying bugs with precision blasts of ink from the surface of the water."
    uiYShift = 17

}

class Seadra extends Monster {
    height = "1.2m"
    weight = "25.0kg"

    catchRate = 75

    baseHpStat = 55
    baseAttackStat = 65
    baseDefenseStat = 95
    baseSpeedStat = 85

    xpGraph = "Medium Fast"
    baseXp = 154
    
    monsterType = Water
    name = "Seadra"
    originalName = "Seadra"

    attacks(0) = Tackle
    attacks(1) = TailWhip
    attacks(2) = WaterGun

    description = "Capable of swimming backwards by rapidly flapping its wing-like pectoral fins and stout tail."
    uiYShift = -4

}

class Goldeen extends Monster {
    height = "0.6m"
    weight = "15.0kg"

    catchRate = 225

    baseHpStat = 45
    baseAttackStat = 67
    baseDefenseStat = 60
    baseSpeedStat = 63

    xpGraph = "Medium Fast"
    baseXp = 64
    
    monsterType = Water
    name = "Goldeen"
    originalName = "Goldeen"

    attacks(0) = Tackle
    attacks(1) = TailWhip
    attacks(2) = WaterGun

    description = "Its tail fin billows like an elegant ballroom dress, giving it the nickname of the Water Queen."
    uiYShift = 5

}

class Seaking extends Monster {
    height = "1.3m"
    weight = "39.0kg"

    catchRate = 60

    baseHpStat = 80
    baseAttackStat = 92
    baseDefenseStat = 65
    baseSpeedStat = 68

    xpGraph = "Medium Fast"
    baseXp = 158
    
    monsterType = Water
    name = "Seaking"
    originalName = "Seaking"

    attacks(0) = Tackle
    attacks(1) = TailWhip
    attacks(2) = WaterGun

    description = "In the autumn spawning season, they can be seen swimming powerfully up rivers and creeks."
    uiYShift = -2

}

class Staryu extends Monster {
    height = "0.8m"
    weight = "34.5kg"

    catchRate = 225

    baseHpStat = 30
    baseAttackStat = 45
    baseDefenseStat = 55
    baseSpeedStat = 85

    xpGraph = "Slow"
    baseXp = 68
    
    monsterType = Water
    name = "Staryu"
    originalName = "Staryu"

    attacks(0) = Tackle
    attacks(1) = TailWhip
    attacks(2) = WaterGun

    description = "An enigmatic POKEMON that can effortlessly regenerate any appendage it loses in battle."
    uiYShift = -2

}

class Magikarp extends Monster {
    height = "0.9m"
    weight = "10.0kg"

    catchRate = 255

    baseHpStat = 20
    baseAttackStat = 10
    baseDefenseStat = 55
    baseSpeedStat = 80

    xpGraph = "Slow"
    baseXp = 40
    
    monsterType = Water
    name = "Magikarp"
    originalName = "Magikarp"

    attacks(0) = Splash

    description = "In the distant past, it was somewhat stronger than the horribly weak descendants that exist today."
    uiYShift = -4

}

class Gyarados extends Monster {
    height = "6.5m"
    weight = "235kg"

    catchRate = 45

    baseHpStat = 95
    baseAttackStat = 125
    baseDefenseStat = 79
    baseSpeedStat = 81

    xpGraph = "Slow"
    baseXp = 189
    
    monsterType = Water
    name = "Gyarados"
    originalName = "Gyarados"

    attacks(0) = Tackle
    attacks(1) = TailWhip
    attacks(2) = WaterGun

    description = "Rarely seen in the wild. Huge and vicious, it is capable of destroying entire cities in a rage."
    uiYShift = -6

}

class Vaporeon extends Monster {
    height = "1.0m"
    weight = "29.0kg"

    catchRate = 45

    baseHpStat = 130
    baseAttackStat = 65
    baseDefenseStat = 60
    baseSpeedStat = 65

    xpGraph = "Medium Fast"
    baseXp = 184
    
    monsterType = Water
    name = "Vaporeon"
    originalName = "Vaporeon"

    attacks(0) = Tackle
    attacks(1) = TailWhip
    attacks(2) = WaterGun

    description = "Lives close to water. Its long tail is ridged with a fin which is often mistaken for a mermaid’s."
    uiYShift = -6

}




class Bulbasaur extends Monster {
    height = "0.7m"
    weight = "6.9kg"

    catchRate = 45

    baseHpStat = 45
    baseAttackStat = 49
    baseDefenseStat = 49
    baseSpeedStat = 45

    xpGraph = "Medium Slow"
    baseXp = 64

    monsterType = Grass
    name = "Bulbasaur"
    originalName = "Bulbasaur"

    attacks(0) = Growl
    attacks(1) = Tackle
    attacks(2) = VineWhip
    attacks(3) = Growth

    uiYShift = 19

    description = "A strange seed was planted on its back at birth. The plant sprouts and grows with this POKEMON."
}

class Ivysaur extends Monster {
    height = "1.0m"
    weight = "13.0kg"

    catchRate = 45

    baseHpStat = 60
    baseAttackStat = 62
    baseDefenseStat = 63
    baseSpeedStat = 60

    xpGraph = "Medium Slow"
    baseXp = 142

    monsterType = Grass
    name = "Ivysaur"
    originalName = "Ivysaur"

    attacks(0) = Growl
    attacks(1) = Tackle
    attacks(2) = VineWhip
    attacks(3) = Growth

    description = "When the bulb on its back grows large, it appears to lose the ability to stand on its hind legs."

    uiYShift = 2
}

class Venusaur extends Monster {
    height = "2.0m"
    weight = "100kg"

    catchRate = 45

    baseHpStat = 80
    baseAttackStat = 82
    baseDefenseStat = 83
    baseSpeedStat = 80

    xpGraph = "Medium Slow"
    baseXp = 236

    monsterType = Grass
    name = "Venusaur"
    originalName = "Venusaur"

    attacks(0) = Growl
    attacks(1) = Tackle
    attacks(2) = VineWhip
    attacks(3) = Growth

    description = "The plant blooms when it is absorbing solar energy. It stays on the move to seek sunlight."

    uiYShift = 9
}

class Oddish extends Monster {
    height = "0.5m"
    weight = "5.4kg"

    catchRate = 255

    baseHpStat = 45
    baseAttackStat = 50
    baseDefenseStat = 55
    baseSpeedStat = 30

    xpGraph = "Medium Slow"
    baseXp = 64

    monsterType = Grass
    name = "Oddish"
    originalName = "Oddish"

    attacks(0) = Growl
    attacks(1) = Tackle
    attacks(2) = VineWhip
    attacks(3) = Growth

    description = "During the day, it keeps its face buried in the ground. At night, it wanders around sowing its seeds."
    uiYShift = 5

}

class Gloom extends Monster {
    height = "0.8m"
    weight = "8.6kg"

    catchRate = 120

    baseHpStat = 60
    baseAttackStat = 65
    baseDefenseStat = 70
    baseSpeedStat = 40

    xpGraph = "Medium Slow"
    baseXp = 138

    monsterType = Grass
    name = "Gloom"
    originalName = "Gloom"

    attacks(0) = Growl
    attacks(1) = Tackle
    attacks(2) = VineWhip
    attacks(3) = Growth

    description = "The fluid that oozes from its mouth isn’t drool. It is a nectar that is used to attract prey."
    uiYShift = 11

}

class Vileplume extends Monster {
    height = "1.2m"
    weight = "18.6kg"

    catchRate = 45

    baseHpStat = 75
    baseAttackStat = 80
    baseDefenseStat = 85
    baseSpeedStat = 50

    xpGraph = "Medium Slow"
    baseXp = 221

    monsterType = Grass
    name = "Vileplume"
    originalName = "Vileplume"

    attacks(0) = Growl
    attacks(1) = Tackle
    attacks(2) = VineWhip
    attacks(3) = Growth
    uiYShift = -4

    description = "The larger its petals, the more toxic pollen it contains. Its big head is heavy and hard to hold up."
}

class Bellsprout extends Monster {
    height = "0.7m"
    weight = "4.0kg"
    
    catchRate = 255

    baseHpStat = 50
    baseAttackStat = 75
    baseDefenseStat = 35
    baseSpeedStat = 40

    xpGraph = "Medium Slow"
    baseXp = 60

    monsterType = Grass
    name = "Bellsprout"
    originalName = "Bellsprout"

    attacks(0) = Growl
    attacks(1) = Tackle
    attacks(2) = VineWhip
    attacks(3) = Growth
    uiYShift = 11

    description = "A carnivorous POKEMON that traps and eats bugs. It uses its root feet to soak up needed moisture."
}

class Weepinbell extends Monster {
    height = "1.0m"
    weight = "6.4kg"
    
    catchRate = 120

    baseHpStat = 65
    baseAttackStat = 90
    baseDefenseStat = 50
    baseSpeedStat = 55

    xpGraph = "Medium Slow"
    baseXp = 137

    monsterType = Grass
    name = "Weepinbell"
    originalName = "Weepinbell"

    attacks(0) = Growl
    attacks(1) = Tackle
    attacks(2) = VineWhip
    attacks(3) = Growth
    uiYShift = 11

    description = "It spits out POISONPOWDER to immobilize the enemy and then finishes it with a spray of ACID."
}

class Victreebel extends Monster {
    height = "1.7m"
    weight = "15.5kg"
    
    catchRate = 45

    baseHpStat = 80
    baseAttackStat = 105
    baseDefenseStat = 65
    baseSpeedStat = 70

    xpGraph = "Medium Slow"
    baseXp = 221

    monsterType = Grass
    name = "Victreebel"
    originalName = "Victreebel"

    attacks(0) = Growl
    attacks(1) = Tackle
    attacks(2) = VineWhip
    attacks(3) = Growth
    uiYShift = 2

    description = "Said to live in huge colonies deep in jungles, although no one has ever returned from there."
}

class Exeggcute extends Monster {
    height = "0.4m"
    weight = "2.5kg"
    
    catchRate = 90

    baseHpStat = 60
    baseAttackStat = 40
    baseDefenseStat = 80
    baseSpeedStat = 40

    xpGraph = "Slow"
    baseXp = 65

    monsterType = Grass
    name = "Exeggcute"
    originalName = "Exeggcute"

    attacks(0) = Growl
    attacks(1) = Tackle
    attacks(2) = VineWhip
    attacks(3) = Growth
    uiYShift = 29

    description = "Often mistaken for eggs. When disturbed, they quickly gather and attack in swarms."
}

class Exeggutor extends Monster {
    height = "2.0m"
    weight = "120kg"
    
    catchRate = 45

    baseHpStat = 95
    baseAttackStat = 95
    baseDefenseStat = 85
    baseSpeedStat = 55

    xpGraph = "Slow"
    baseXp = 186

    monsterType = Grass
    name = "Exeggutor"
    originalName = "Exeggutor"

    attacks(0) = Growl
    attacks(1) = Tackle
    attacks(2) = VineWhip
    attacks(3) = Growth
    uiYShift = 2

    description = "Legend has it that on rare occasions, one of its heads will drop off and continue on as an EXEGGCUTE."
}

class Tangela extends Monster {
    height = "1.0m"
    weight = "35.0kg"
    
    catchRate = 45

    baseHpStat = 65
    baseAttackStat = 55
    baseDefenseStat = 115
    baseSpeedStat = 60

    xpGraph = "Medium Fast"
    baseXp = 87

    monsterType = Grass
    name = "Tangela"
    originalName = "Tangela"

    attacks(0) = Growl
    attacks(1) = Tackle
    attacks(2) = VineWhip
    attacks(3) = Growth
    uiYShift = 33

    description = "The whole body is swathed with wide vines that are similar to seaweed. Its vines shake as it walks."
}

class Charmander extends Monster {
    height = "0.6m"
    weight = "8.5kg"

    catchRate = 45

    baseHpStat = 39
    baseAttackStat = 52
    baseDefenseStat = 43
    baseSpeedStat = 65

    xpGraph = "Medium Slow"
    baseXp = 62

    monsterType = Fire
    name = "Charmander"
    originalName = "Charmander"

    attacks(0) = Growl
    attacks(1) = Scratch
    attacks(2) = Ember
    attacks(3) = Flamethrower
    uiYShift = 5

    description = "Obviously prefers hot places. When it rains, steam is said to spout from the tip of its tail."
}

class Charmeleon extends Monster {
    height = "1.1m"
    weight = "19.0kg"

    catchRate = 45

    baseHpStat = 58
    baseAttackStat = 64
    baseDefenseStat = 58
    baseSpeedStat = 80

    xpGraph = "Medium Slow"
    baseXp = 142

    monsterType = Fire
    name = "Charmeleon"
    originalName = "Charmeleon"

    attacks(0) = Growl
    attacks(1) = Scratch
    attacks(2) = Ember
    attacks(3) = Flamethrower
    uiYShift = -2

    description = "When it swings its burning tail, it elevates the temperature to unbearably high levels."
}

class Charizard extends Monster {
    height = "1.7m"
    weight = "90.5kg"

    catchRate = 45

    baseHpStat = 78
    baseAttackStat = 84
    baseDefenseStat = 78
    baseSpeedStat = 100

    xpGraph = "Medium Slow"
    baseXp = 240

    monsterType = Fire
    name = "Charizard"
    originalName = "Charizard"

    attacks(0) = Growl
    attacks(1) = Scratch
    attacks(2) = Ember
    attacks(3) = Flamethrower
    uiYShift = -16

    description = "Spits fire that is hot enough to melt boulders. Known to cause forest fires unintentionally."
}

class Vulpix extends Monster {
    height = "0.6m"
    weight = "9.9kg"

    catchRate = 190

    baseHpStat = 38
    baseAttackStat = 41
    baseDefenseStat = 40
    baseSpeedStat = 65

    xpGraph = "Medium Fast"
    baseXp = 60

    monsterType = Fire
    name = "Vulpix"
    originalName = "Vulpix"

    attacks(0) = Growl
    attacks(1) = Scratch
    attacks(2) = Ember
    attacks(3) = Flamethrower
    uiYShift = 5

    description = "At the time of birth, it has just one tail. The tail splits from its tip as it grows older."
}

class Ninetales extends Monster {
    height = "1.1m"
    weight = "19.9kg"

    catchRate = 75

    baseHpStat = 73
    baseAttackStat = 76
    baseDefenseStat = 75
    baseSpeedStat = 100

    xpGraph = "Medium Fast"
    baseXp = 177

    monsterType = Fire
    name = "Ninetales"
    originalName = "Ninetales"

    attacks(0) = Growl
    attacks(1) = Scratch
    attacks(2) = Ember
    attacks(3) = Flamethrower
    uiYShift = -2

    description = "Very smart and very vengeful. Grabbing one of its many tails could result in a 1000-year curse."
}

class Growlithe extends Monster {
    height = "0.7m"
    weight = "19.0kg"

    catchRate = 190

    baseHpStat = 55
    baseAttackStat = 70
    baseDefenseStat = 45
    baseSpeedStat = 60

    xpGraph = "Slow"
    baseXp = 70

    monsterType = Fire
    name = "Growlithe"
    originalName = "Growlithe"

    attacks(0) = Growl
    attacks(1) = Scratch
    attacks(2) = Ember
    attacks(3) = Flamethrower
    uiYShift = 2

    description = "Very protective of its territory. It will bark and bite to repel intruders from its space."
}

class Arcanine extends Monster {
    height = "1.9m"
    weight = "155kg"

    catchRate = 75

    baseHpStat = 90
    baseAttackStat = 110
    baseDefenseStat = 80
    baseSpeedStat = 95

    xpGraph = "Slow"
    baseXp = 194

    monsterType = Fire
    name = "Arcanine"
    originalName = "Arcanine"

    attacks(0) = Growl
    attacks(1) = Scratch
    attacks(2) = Ember
    attacks(3) = Flamethrower
    uiYShift = -8

    description = "A POKEMON that has been admired since the past for its beauty. It runs agilely as if on wings."
}

class Ponyta extends Monster {
    height = "1.0m"
    weight = "30.0kg"

    catchRate = 190

    baseHpStat = 50
    baseAttackStat = 85
    baseDefenseStat = 55
    baseSpeedStat = 90

    xpGraph = "Medium Fast"
    baseXp = 82

    monsterType = Fire
    name = "Ponyta"
    originalName = "Ponyta"

    attacks(0) = Growl
    attacks(1) = Scratch
    attacks(2) = Ember
    attacks(3) = Flamethrower
    uiYShift = -8

    description = "Its hooves are 10 times harder than diamonds. It can trample anything completely flat in little time."
}

class Rapidash extends Monster {
    height = "1.7m"
    weight = "95.0kg"

    catchRate = 60

    baseHpStat = 65
    baseAttackStat = 100
    baseDefenseStat = 70
    baseSpeedStat = 105

    xpGraph = "Medium Fast"
    baseXp = 175

    monsterType = Fire
    name = "Rapidash"
    originalName = "Rapidash"

    attacks(0) = Growl
    attacks(1) = Scratch
    attacks(2) = Ember
    attacks(3) = Flamethrower
    uiYShift = -18

    description = "Very competitive, this POKEMON will chase anything that moves fast in the hopes of racing it."
}

class Magmar extends Monster {
    height = "1.3m"
    weight = "44.5kg"

    catchRate = 45

    baseHpStat = 65
    baseAttackStat = 95
    baseDefenseStat = 57
    baseSpeedStat = 93

    xpGraph = "Medium Fast"
    baseXp = 173

    monsterType = Fire
    name = "Magmar"
    originalName = "Magmar"

    attacks(0) = Growl
    attacks(1) = Scratch
    attacks(2) = Ember
    attacks(3) = Flamethrower
    uiYShift = -12

    description = "Its body always burns with an orange glow that enables it to hide perfectly among flames."
}

class Flareon extends Monster {
    height = "0.9m"
    weight = "25.0kg"

    catchRate = 45

    baseHpStat = 65
    baseAttackStat = 130
    baseDefenseStat = 60
    baseSpeedStat = 65

    xpGraph = "Medium Fast"
    baseXp = 184

    monsterType = Fire
    name = "Flareon"
    originalName = "Flareon"

    attacks(0) = Growl
    attacks(1) = Scratch
    attacks(2) = Ember
    attacks(3) = Flamethrower
    uiYShift = 0

    description = "When storing thermal energy in its body, its temperature could soar to over 1600 degrees."
}


class Rattata extends Monster {
    height = "0.3m"
    weight = "3.5kg"

    catchRate = 255

    baseHpStat = 30
    baseAttackStat = 56
    baseDefenseStat = 35
    baseSpeedStat = 72

    xpGraph = "Medium Fast"
    baseXp = 51

    monsterType = Normal
    name = "Rattata"
    originalName = "Rattata"

    attacks(0) = Tackle
    attacks(1) = TailWhip
    attacks(2) = QuickAttack
    attacks(3) = Crunch
    uiYShift = 0

    description = "Bites anything when it attacks. Small and very quick, it is a common sight in many places."
}

class Raticate extends Monster {
    height = "0.7m"
    weight = "18.5kg"

    catchRate = 127

    baseHpStat = 55
    baseAttackStat = 81
    baseDefenseStat = 60
    baseSpeedStat = 97

    xpGraph = "Medium Fast"
    baseXp = 145

    monsterType = Normal
    name = "Raticate"
    originalName = "Raticate"

    attacks(0) = Tackle
    attacks(1) = TailWhip
    attacks(2) = QuickAttack
    attacks(3) = Crunch
    uiYShift = 7

    description = "It uses its whiskers to maintain its balance. It apparently slows down if they are cut off."
}

class Meowth extends Monster {
    height = "0.4m"
    weight = "4.2kg"

    catchRate = 255

    baseHpStat = 40
    baseAttackStat = 45
    baseDefenseStat = 30
    baseSpeedStat = 90

    xpGraph = "Medium Fast"
    baseXp = 58

    monsterType = Normal
    name = "Meowth"
    originalName = "Meowth"

    attacks(0) = Tackle
    attacks(1) = TailWhip
    attacks(2) = QuickAttack
    attacks(3) = Crunch
    uiYShift = -4

    description = "Adores circular objects. Wanders the streets on a nightly basis to look for dropped loose change."
}

class Persian extends Monster {
    height = "1.0m"
    weight = "32.0kg"

    catchRate = 90

    baseHpStat = 65
    baseAttackStat = 70
    baseDefenseStat = 60
    baseSpeedStat = 115

    xpGraph = "Medium Fast"
    baseXp = 154

    monsterType = Normal
    name = "Persian"
    originalName = "Persian"

    attacks(0) = Tackle
    attacks(1) = TailWhip
    attacks(2) = QuickAttack
    attacks(3) = Crunch
    uiYShift = 0

    description = "Although its fur has many admirers, it is tough to raise as a pet because of its fickle meanness."
}

class Lickitung extends Monster {
    height = "1.2m"
    weight = "65.5kg"

    catchRate = 45

    baseHpStat = 90
    baseAttackStat = 55
    baseDefenseStat = 75
    baseSpeedStat = 30

    xpGraph = "Medium Fast"
    baseXp = 77

    monsterType = Normal
    name = "Lickitung"
    originalName = "Lickitung"

    attacks(0) = Tackle
    attacks(1) = TailWhip
    attacks(2) = QuickAttack
    attacks(3) = Crunch
    uiYShift = 15

    description = "Its tongue can be extended like a chameleon’s. It leaves a tingling sensation when it licks enemies."
}

class Chansey extends Monster {
    height = "1.1m"
    weight = "34.6kg"

    catchRate = 30

    baseHpStat = 250
    baseAttackStat = 5
    baseDefenseStat = 5
    baseSpeedStat = 50

    xpGraph = "Fast"
    baseXp = 395

    monsterType = Normal
    name = "Chansey"
    originalName = "Chansey"

    attacks(0) = Tackle
    attacks(1) = TailWhip
    attacks(2) = QuickAttack
    attacks(3) = Crunch
    uiYShift = 11

    description = "A rare and elusive POKEMON that is said to bring happiness to those who manage to get it."
}

class Kangaskhan extends Monster {
    height = "2.2m"
    weight = "80.0kg"

    catchRate = 45

    baseHpStat = 105
    baseAttackStat = 95
    baseDefenseStat = 80
    baseSpeedStat = 90

    xpGraph = "Medium Fast"
    baseXp = 172

    monsterType = Normal
    name = "Kangaskhan"
    originalName = "Kangaskhan"

    attacks(0) = Tackle
    attacks(1) = TailWhip
    attacks(2) = QuickAttack
    attacks(3) = Crunch
    uiYShift = -4

    description = "The infant rarely ventures out of its mother’s protective pouch until it is 3 years old."
}

class Tauros extends Monster {
    height = "1.4m"
    weight = "88.4kg"

    catchRate = 45

    baseHpStat = 75
    baseAttackStat = 100
    baseDefenseStat = 95
    baseSpeedStat = 110

    xpGraph = "Slow"
    baseXp = 172

    monsterType = Normal
    name = "Tauros"
    originalName = "Tauros"

    attacks(0) = Tackle
    attacks(1) = TailWhip
    attacks(2) = QuickAttack
    attacks(3) = Crunch
    uiYShift = 15

    description = "When it targets an enemy, it charges furiously while whipping its body with its long tails."
}

class Eevee extends Monster {
    height = "0.3m"
    weight = "6.5kg"

    catchRate = 45

    baseHpStat = 55
    baseAttackStat = 55
    baseDefenseStat = 50
    baseSpeedStat = 55

    xpGraph = "Medium Fast"
    baseXp = 65

    monsterType = Normal
    name = "Eevee"
    originalName = "Eevee"

    attacks(0) = Tackle
    attacks(1) = TailWhip
    attacks(2) = QuickAttack
    attacks(3) = Crunch
    uiYShift = 5

    description = "Its genetic code is irregular. It may mutate if it is exposed to radiation from element STONEs."
}

class Porygon extends Monster {
    height = "0.8m"
    weight = "36.5kg"

    catchRate = 45

    baseHpStat = 65
    baseAttackStat = 60
    baseDefenseStat = 70
    baseSpeedStat = 40

    xpGraph = "Medium Fast"
    baseXp = 79

    monsterType = Normal
    name = "Porygon"
    originalName = "Porygon"

    attacks(0) = Tackle
    attacks(1) = TailWhip
    attacks(2) = QuickAttack
    attacks(3) = Crunch
    uiYShift = 15

    description = "A POKEMON that consists entirely of programming code. Capable of moving freely in cyberspace."
}

class Snorlax extends Monster {
    height = "2.1m"
    weight = "460kg"

    catchRate = 25

    baseHpStat = 160
    baseAttackStat = 110
    baseDefenseStat = 65
    baseSpeedStat = 30

    xpGraph = "Slow"
    baseXp = 189

    monsterType = Normal
    name = "Snorlax"
    originalName = "Snorlax"

    attacks(0) = Tackle
    attacks(1) = TailWhip
    attacks(2) = QuickAttack
    attacks(3) = Crunch
    uiYShift = 15

    description = "Very lazy. Just eats and sleeps. As its rotund bulk builds, it becomes steadily more slothful."
}