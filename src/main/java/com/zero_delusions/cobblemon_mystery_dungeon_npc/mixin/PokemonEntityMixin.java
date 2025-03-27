package com.zero_delusions.cobblemon_mystery_dungeon_npc.mixin;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.zero_delusions.cobblemon_mystery_dungeon_npc.entity.pokenpc.utils.PokeNpcUtilsKt;

@Mixin(PokemonEntity.class)
public class PokemonEntityMixin {
    @Inject(method = "canBattle", at = @At("HEAD"), cancellable = true)
    private void cantBattlePokeNpc(Player player, CallbackInfoReturnable<Boolean> cir) {
        PokemonEntity self = (PokemonEntity) (Object) this;
        if (PokeNpcUtilsKt.isPokeNpc(self)) {
            cir.setReturnValue(false);
        }
    }
}
