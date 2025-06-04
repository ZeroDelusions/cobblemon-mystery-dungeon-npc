package com.zero_delusions.cobblemon_mystery_dungeon_npc.core.config.data

import dev.isxander.yacl3.config.v2.api.SerialEntry

data class ServerConfigData(
    @JvmField @SerialEntry var pokeNpc: List<String> = getDefaultPokeNpc()
) {
    companion object {
        fun getDefaultPokeNpc(): List<String> {
            return listOf("mawile")
        }
    }
}
