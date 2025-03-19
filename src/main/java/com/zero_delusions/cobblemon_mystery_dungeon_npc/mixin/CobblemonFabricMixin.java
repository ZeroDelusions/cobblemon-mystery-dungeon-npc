package com.zero_delusions.cobblemon_mystery_dungeon_npc.mixin;

import com.cobblemon.mod.fabric.CobblemonFabric;
import com.zero_delusions.cobblemon_mystery_dungeon_npc.core.command.PokeNpcCommands;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CobblemonFabric.class)
public class CobblemonFabricMixin {
//    @Inject(method = "initialize", at = @At("TAIL"), remap = false)
//    private void registerCustomCommands(CallbackInfo ci) {
//        CommandRegistrationCallback.EVENT.register(PokeNpcCommands.INSTANCE::register);
//    }
}
