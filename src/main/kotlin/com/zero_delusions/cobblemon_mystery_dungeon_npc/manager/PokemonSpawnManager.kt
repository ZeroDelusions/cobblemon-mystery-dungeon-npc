package com.zero_delusions.cobblemon_mystery_dungeon_npc.manager;

import com.cobblemon.mod.common.Cobblemon
import com.cobblemon.mod.common.CobblemonEntities
import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import com.cobblemon.mod.common.pokemon.Pokemon
import net.minecraft.world.level.Level

object PokemonSpawnManager {

    fun spawn(): PokemonEntity? {
        println("ADADAD")
        val level = Cobblemon.getLevel(Level.OVERWORLD) ?: return null;
        println("ADADAD1")
        val pokemon = Pokemon();
        val species = PokemonSpecies.getByName("mewtwo") ?: return null;
        println("ADADAD2")
        pokemon.species = species;

        val pokemonEntity = PokemonEntity(level, pokemon, CobblemonEntities.POKEMON);
        pokemonEntity.setPos(1000.0, -60.0, 1000.0);
        pokemonEntity.setPersistenceRequired();

        level.addFreshEntity(pokemonEntity);

        println("FFF ${pokemonEntity}")

        return pokemonEntity;
    }
}
