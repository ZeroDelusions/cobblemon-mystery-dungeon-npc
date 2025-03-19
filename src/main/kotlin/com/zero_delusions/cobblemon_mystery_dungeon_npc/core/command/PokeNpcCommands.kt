
package com.zero_delusions.cobblemon_mystery_dungeon_npc.core.command

import com.mojang.brigadier.CommandDispatcher
import com.zero_delusions.cobblemon_mystery_dungeon_npc.command.argument.SpawnPokeNpc

import net.minecraft.commands.CommandBuildContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands

object PokeNpcCommands {
    fun register(
        dispatcher: CommandDispatcher<CommandSourceStack>,
        registry: CommandBuildContext,
        selection: Commands.CommandSelection,
    ) {
        SpawnPokeNpc.register(dispatcher)
    }
}