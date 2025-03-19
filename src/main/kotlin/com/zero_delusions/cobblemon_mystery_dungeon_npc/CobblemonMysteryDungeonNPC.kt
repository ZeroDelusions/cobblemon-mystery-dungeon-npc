package com.zero_delusions.cobblemon_mystery_dungeon_npc

import com.cobblemon.mod.common.api.events.CobblemonEvents
import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import com.cobblemon.mod.common.api.properties.CustomPokemonProperty
import com.zero_delusions.cobblemon_mystery_dungeon_npc.core.command.PokeNpcCommands
import com.zero_delusions.cobblemon_mystery_dungeon_npc.core.config.ServerConfig
import com.zero_delusions.cobblemon_mystery_dungeon_npc.entity.pokenpc.AllowedPokeNpc
import com.zero_delusions.cobblemon_mystery_dungeon_npc.entity.pokenpc.properties.PokeNpcProperty
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import org.slf4j.LoggerFactory

object CobblemonMysteryDungeonNPC : ModInitializer {
	const val MOD_ID = "cobblemon-mystery-dungeon-npc"
    private val logger = LoggerFactory.getLogger("cobblemon-mystery-dungeon-npc")

	override fun onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		logger.info("Hello Fabric world!")

		ServerConfig.properLoad()

		PokemonSpecies.observable.subscribe {
			AllowedPokeNpc.init();
		}
		CommandRegistrationCallback.EVENT.register(PokeNpcCommands::register)
	}
}