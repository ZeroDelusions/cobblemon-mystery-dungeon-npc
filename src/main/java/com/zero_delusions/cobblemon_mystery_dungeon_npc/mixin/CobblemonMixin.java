package com.zero_delusions.cobblemon_mystery_dungeon_npc.mixin;

import com.cobblemon.mod.common.Cobblemon;
import com.zero_delusions.cobblemon_mystery_dungeon_npc.command.argument.PokeNpcPropertiesArgumentType;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.reflect.KClass;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.cobblemon.mod.common.util.MiscUtilsKt.cobblemonResource;

@Mixin(Cobblemon.class)
public class CobblemonMixin {
//    @Inject(
//            method = "initialize",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lcom/cobblemon/mod/common/api/properties/CustomPokemonProperty$Companion;register(Lcom/cobblemon/mod/common/api/properties/CustomPokemonPropertyType;)V",
//                    ordinal = 0
//            ),
//            remap = false
//    )
//    private void registerNpcProperty(CallbackInfo ci) {
//        CustomPokemonProperty.Companion.register(PokeNpcProperty.INSTANCE);
//    }

    @Inject(
            method = "registerArgumentTypes",
            at = @At("TAIL"),
            remap = false
    )
    private void addCustomArgumentTypes(CallbackInfo ci) {
        KClass<PokeNpcPropertiesArgumentType> kClass = JvmClassMappingKt.getKotlinClass(PokeNpcPropertiesArgumentType.class);

        Cobblemon.implementation.registerCommandArgument(
                cobblemonResource("npc"),
                kClass,
                SingletonArgumentInfo.contextFree(PokeNpcPropertiesArgumentType.Companion::properties)
        );
    }
}
