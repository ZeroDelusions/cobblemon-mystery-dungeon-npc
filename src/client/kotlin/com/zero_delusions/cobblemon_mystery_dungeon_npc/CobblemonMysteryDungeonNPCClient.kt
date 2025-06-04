package com.zero_delusions.cobblemon_mystery_dungeon_npc

import com.cobblemon.mod.common.Cobblemon
import com.zero_delusions.cobblemon_mystery_dungeon_npc.manager.PokemonLookupManager
import com.zero_delusions.cobblemon_mystery_dungeon_npc.manager.PokemonSpawnManager
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.Level
import java.util.*

object CobblemonMysteryDungeonNPCClient : ClientModInitializer {
	override fun onInitializeClient() {
		ClientPlayConnectionEvents.JOIN.register { handler, sender, client ->
			// Use client.execute to ensure you are on the main thread
			client.execute {
				// Get a reference to the server-level (verify this cast is valid in your environment)
				val level = Cobblemon.getLevel(Level.OVERWORLD) as? ServerLevel ?: return@execute
				val entity = PokemonSpawnManager.spawn() ?: return@execute

				// Delay the lookup to allow the world time to register the entity
				// Using a Timer to schedule the lookup after approximately one tick (~50 ms)
				Timer().schedule(object : TimerTask() {
					override fun run() {
						// You need to schedule this lookup back onto the main thread if required.
						// If client.execute() is safe to call from here, you can do:
						client.execute {
							val entity1 = PokemonLookupManager.lookup(entity.uuid, level, entity.blockPosition())
							println("AAAA: $entity1")
						}
					}
				}, 2000) // Adjust the delay if necessary; 50 ms is roughly one game tick.
			}
		}
	}
}
