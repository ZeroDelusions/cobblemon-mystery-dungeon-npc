package com.zero_delusions.cobblemon_mystery_dungeon_npc.core.config

import com.google.gson.FieldNamingPolicy
import com.zero_delusions.cobblemon_mystery_dungeon_npc.core.config.data.ServerConfigData
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler
import dev.isxander.yacl3.config.v2.api.SerialEntry
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.resources.ResourceLocation
import java.security.MessageDigest
import kotlin.io.path.exists
import kotlin.io.path.pathString
import kotlin.io.path.readBytes


class ServerConfig {
    companion object {
        @JvmStatic
        @SerialEntry
        var data = ServerConfigData()

        private var hash = ""

        private const val CONFIG_FILE_NAME = "poke_npc"
        private const val USE_JSON_5 = true
        private const val NAMESPACE = "_server"
        private val CONFIG_FILE_EXTENSION: String by lazy {
            if (USE_JSON_5) ".json5" else ".json"
        }

        @JvmField
        val HANDLER: ConfigClassHandler<ServerConfig> = ConfigClassHandler.createBuilder(ServerConfig::class.java)
            .id(ResourceLocation.fromNamespaceAndPath("poke-npc", CONFIG_FILE_NAME))
            .serializer { config ->
                GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().configDir.resolve(CONFIG_FILE_NAME + NAMESPACE + CONFIG_FILE_EXTENSION))
                    .appendGsonBuilder { gsonBuilder ->
                        gsonBuilder
                            .setPrettyPrinting()
                            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    }
                    .setJson5(true)
                    .build()
            }
            .build()

        @JvmStatic
        fun properLoad() {
            HANDLER.load()
            hash = calculateConfigHash()
        }

        @JvmStatic
        fun calculateConfigHash(): String {
            val configFile = FabricLoader.getInstance().configDir.resolve(CONFIG_FILE_NAME + NAMESPACE + CONFIG_FILE_EXTENSION)

            if (!configFile.exists()) {
                throw IllegalArgumentException("Config file not found at path: ${configFile.pathString}")
            }

            val bytes = configFile.readBytes()
            val digest = MessageDigest.getInstance("SHA-256")
            val hashBytes = digest.digest(bytes)
            return hashBytes.joinToString("") { "%02x".format(it) }
        }

        @JvmStatic
        fun shouldSync(): Boolean = hash != calculateConfigHash()

        @JvmStatic
        fun sync() {
            if (shouldSync()) properLoad()
        }
    }
}