package com.zero_delusions.cobblemon_mystery_dungeon_npc.entity.pokenpc

import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import com.cobblemon.mod.common.pokemon.Species
import com.zero_delusions.cobblemon_mystery_dungeon_npc.core.config.ServerConfig

object AllowedPokeNpc {
    @JvmStatic
    var species = mutableSetOf<Species>()
        private set

    @JvmStatic
    fun init() {
        val pokeNpcNames = ServerConfig.data.pokeNpc


        pokeNpcNames.forEach { name ->
            PokemonSpecies.getByName(name)?.let { species.add(it) }
        }
    }
}