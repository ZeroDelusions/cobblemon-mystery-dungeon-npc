package com.zero_delusions.cobblemon_mystery_dungeon_npc

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object CobblemonMysteryDungeonNPC : ModInitializer {
	const val MOD_ID = "cobblemon-mystery-dungeon-npc"
    private val logger = LoggerFactory.getLogger("cobblemon-mystery-dungeon-npc")

	override fun onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		logger.info("Hello Fabric world!")
	}
}