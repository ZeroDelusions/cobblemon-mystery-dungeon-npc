package com.zero_delusions.cobblemon_mystery_dungeon_npc.mixin.client;

import com.cobblemon.mod.common.client.keybind.keybinds.PartySendBinding;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.zero_delusions.cobblemon_mystery_dungeon_npc.entity.pokenpc.utils.PokeNpcUtilsKt;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PartySendBinding.class)
public class PartySendBindingMixin {
    @Inject(method = "processEntityTarget", at = @At("HEAD"), cancellable = true)
    private void preventPokeNpcBattle(LocalPlayer player, Pokemon pokemon, LivingEntity entity, CallbackInfo ci) {
        if (entity instanceof PokemonEntity targetPokemon && PokeNpcUtilsKt.isPokeNpc(targetPokemon)) {
            ci.cancel();
        }
    }
}
