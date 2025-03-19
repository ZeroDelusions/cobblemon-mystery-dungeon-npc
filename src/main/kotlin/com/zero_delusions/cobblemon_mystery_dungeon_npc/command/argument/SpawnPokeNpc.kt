package com.zero_delusions.cobblemon_mystery_dungeon_npc.command.argument

import com.cobblemon.mod.common.api.permission.CobblemonPermissions
import com.cobblemon.mod.common.api.pokemon.PokemonProperties
import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import com.cobblemon.mod.common.api.properties.CustomPokemonProperty
import com.cobblemon.mod.common.api.text.red
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import com.cobblemon.mod.common.util.alias
import com.cobblemon.mod.common.util.commandLang
import com.cobblemon.mod.common.util.permission
import com.cobblemon.mod.common.util.toBlockPos
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import com.zero_delusions.cobblemon_mystery_dungeon_npc.entity.pokenpc.AllowedPokeNpc
import com.zero_delusions.cobblemon_mystery_dungeon_npc.entity.pokenpc.properties.PokeNpcProperty
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands.argument
import net.minecraft.commands.Commands.literal
import net.minecraft.commands.arguments.coordinates.Vec3Argument
import net.minecraft.network.chat.Component
import net.minecraft.world.phys.Vec3
import net.minecraft.world.level.Level

object SpawnPokeNpc {
    private const val NAME = "spawnpokenpc"
    private const val PROPERTIES = "properties"
    private const val POSITION = "pos"
    private const val ALIAS = "pokenpcspawn"
    private const val AT_NAME = "${NAME}at"
    private const val AT_ALIAS = "${ALIAS}at"
    private val NO_SPECIES_EXCEPTION = SimpleCommandExceptionType(commandLang("${NAME}.nospecies").red())
    private fun NOT_NPC_EXCEPTION(pokemonName: String) =
        SimpleCommandExceptionType(commandLang("$pokemonName can't be npc").red()).create()

    private val INVALID_POS_EXCEPTION = SimpleCommandExceptionType(Component.literal("Invalid position").red())
    private val FAILED_SPAWN_EXCEPTION =
        SimpleCommandExceptionType(Component.literal("Unable to spawn at the given position").red())

    fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
        val contextPositionCommand = dispatcher.register(
            literal(NAME)
            .permission(CobblemonPermissions.SPAWN_POKEMON)
            .then(
                argument(PROPERTIES, PokeNpcPropertiesArgumentType.properties())
                    .executes { context -> this.execute(context, context.source.position) }
            )
        )
        dispatcher.register(contextPositionCommand.alias(ALIAS))
        val argumentPositionCommand = dispatcher.register(
            literal(AT_NAME)
            .permission(CobblemonPermissions.SPAWN_POKEMON)
            .then(
                argument(POSITION, Vec3Argument.vec3())
                .then(
                    argument(PROPERTIES, PokeNpcPropertiesArgumentType.properties())
                        .executes { context -> execute(context, Vec3Argument.getVec3(context, POSITION)) }
                )
            )
        )
        dispatcher.register(argumentPositionCommand.alias(AT_ALIAS))
    }

    private fun execute(context: CommandContext<CommandSourceStack>, pos: Vec3): Int {
        val world = context.source.level
        val blockPos = pos.toBlockPos()
        if (!Level.isInSpawnableBounds(blockPos)) {
            throw INVALID_POS_EXCEPTION.create()
        }

        val properties = PokeNpcPropertiesArgumentType.getPokemonProperties(context, PROPERTIES)
        properties.customProperties.add(PokeNpcProperty.npc(true))

        if (properties.species == null) {
            throw NO_SPECIES_EXCEPTION.create()
        }
        val species = PokemonSpecies.getByName(properties.species!!) ?: throw NO_SPECIES_EXCEPTION.create()
        if (!AllowedPokeNpc.species.contains(species)) throw NOT_NPC_EXCEPTION(species.translatedName.string)

        val pokemonEntity = properties.createEntity(world)
        pokemonEntity.moveTo(pos.x, pos.y, pos.z, pokemonEntity.yRot, pokemonEntity.xRot)
        pokemonEntity.entityData.set(PokemonEntity.SPAWN_DIRECTION, pokemonEntity.random.nextFloat() * 360F)
        if (world.addFreshEntity(pokemonEntity)) {
            return Command.SINGLE_SUCCESS
        }
        throw FAILED_SPAWN_EXCEPTION.create()
    }

}