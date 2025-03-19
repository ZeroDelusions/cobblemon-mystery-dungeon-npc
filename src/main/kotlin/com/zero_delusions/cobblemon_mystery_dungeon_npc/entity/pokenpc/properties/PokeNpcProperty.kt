package com.zero_delusions.cobblemon_mystery_dungeon_npc.entity.pokenpc.properties

import com.cobblemon.mod.common.api.properties.CustomPokemonPropertyType
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import com.cobblemon.mod.common.pokemon.properties.BooleanProperty

object PokeNpcProperty: CustomPokemonPropertyType<BooleanProperty> {
    override val keys = setOf("npc")
    override val needsKey = true
    override fun fromString(value: String?) =
        when(value?.lowercase()) {
            "true", "yes" -> npc(true)
            "false", "no" -> npc(false)
            else -> null
        }

    fun npc(value: Boolean) = BooleanProperty(
        key = keys.first(),
        value = value,
        pokemonApplicator = { _, _ -> },
        entityApplicator = { pokemonEntity, npc -> putInPersistentData(pokemonEntity, keys.first(), value)},
        pokemonMatcher = { _, _ -> false },
        entityMatcher = { pokemonEntity, npc -> pokemonEntity.pokemon.persistentData.getBoolean(keys.first()) == value }
    )

    private fun putInPersistentData(pokemonEntity: PokemonEntity, key: String, value: Boolean) {
        val pokemon = pokemonEntity.pokemon
        pokemon.persistentData.putBoolean(key, value)
        pokemon.anyChangeObservable.emit(pokemon)
    }

    override fun examples() = setOf("yes", "no")
}