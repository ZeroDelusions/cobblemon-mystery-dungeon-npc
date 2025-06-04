@file:Suppress("INVISIBLE_MEMBER", "INVISIBLE_REFERENCE")

package com.zero_delusions.cobblemon_mystery_dungeon_npc.command.argument

import com.cobblemon.mod.common.Cobblemon
import com.cobblemon.mod.common.api.pokemon.PokemonProperties
import com.cobblemon.mod.common.pokemon.properties.PropertiesCompletionProvider
import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import com.zero_delusions.cobblemon_mystery_dungeon_npc.entity.pokenpc.AllowedPokeNpc
import net.minecraft.commands.SharedSuggestionProvider
import java.util.concurrent.CompletableFuture

class PokeNpcPropertiesArgumentType: ArgumentType<PokemonProperties> {

    companion object {
        val EXAMPLES: List<String> = listOf("eevee")
        private val ASSIGNER = "="

        fun properties() = PokeNpcPropertiesArgumentType()

        fun <S> getPokemonProperties(context: CommandContext<S>, name: String): PokemonProperties {
            return context.getArgument(name, PokemonProperties::class.java)
        }
    }

    override fun parse(reader: StringReader): PokemonProperties {
        val properties = reader.remaining
        reader.cursor = reader.totalLength
        return PokemonProperties.parse(properties)
    }

    override fun <S : Any?> listSuggestions(
        context: CommandContext<S>,
        builder: SuggestionsBuilder
    ): CompletableFuture<Suggestions> {
        val sections = builder.remainingLowerCase.split(" ")
        if (sections.isEmpty())
            return this.suggestSpeciesAndPropertyKeys(builder)
        val currentSection = sections.last()

        if (currentSection.contains(ASSIGNER)) {
            val propertyKey = currentSection.substringBefore(ASSIGNER)
            val currentValue = currentSection.substringAfter(ASSIGNER)
            return PropertiesCompletionProvider.suggestValues(propertyKey, currentValue, builder)
        }

        else if (sections.size >= 2) {
            val usedKeys = sections.filter { it.contains("=") }.map { it.substringBefore("=") }.toSet()
            return PropertiesCompletionProvider.suggestKeys(currentSection, usedKeys, builder)
        }
        return this.suggestSpeciesAndPropertyKeys(builder)
    }

    private fun suggestSpeciesAndPropertyKeys(builder: SuggestionsBuilder) = SharedSuggestionProvider.suggest(this.collectSpeciesIdentifiers() + PropertiesCompletionProvider.keys(), builder)

    private fun collectSpeciesIdentifiers() = AllowedPokeNpc.species.map { if (it.resourceIdentifier.namespace == Cobblemon.MODID) it.resourceIdentifier.path else it.resourceIdentifier.toString() }

    override fun getExamples() = EXAMPLES
}