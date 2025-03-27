package com.zero_delusions.cobblemon_mystery_dungeon_npc.entity.pokenpc.utils

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import com.cobblemon.mod.common.pokemon.Pokemon
import com.zero_delusions.cobblemon_mystery_dungeon_npc.entity.pokenpc.properties.PokeNpcProperty

fun Pokemon.isPokeNpc(): Boolean {
    return forcedAspects.contains(PokeNpcProperty.keys.first())
}

fun PokemonEntity.isPokeNpc(): Boolean {
    return aspects.contains(PokeNpcProperty.keys.first())
}