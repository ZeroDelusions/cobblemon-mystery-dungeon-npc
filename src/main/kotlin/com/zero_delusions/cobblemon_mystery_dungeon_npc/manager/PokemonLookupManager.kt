package com.zero_delusions.cobblemon_mystery_dungeon_npc.manager;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import java.util.*

object PokemonLookupManager {
    fun lookup(uuid: UUID, level: ServerLevel, pos: BlockPos): PokemonEntity? {
        val chunk = level.getChunkAt(pos);

        val pokemonEntity = level.getEntity(uuid);
        return pokemonEntity as? PokemonEntity;
    }
}
