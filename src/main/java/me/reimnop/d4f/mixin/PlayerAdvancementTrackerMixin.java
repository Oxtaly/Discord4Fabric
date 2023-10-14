package me.reimnop.d4f.mixin;

import me.reimnop.d4f.events.PlayerAdvancementCallback;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerAdvancementTracker.class)
public class PlayerAdvancementTrackerMixin {
    @Shadow private ServerPlayerEntity owner;

    @Inject(method = "method_53637(Lnet/minecraft/advancement/AdvancementEntry;Lnet/minecraft/advancement/AdvancementDisplay;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/PlayerManager;broadcast(Lnet/minecraft/text/Text;Z)V",
                    ordinal = 0
            )
    )
    private void grantCriterion(AdvancementEntry advancementEntry, AdvancementDisplay display, CallbackInfo ci) {
        PlayerAdvancementCallback.EVENT.invoker().onAdvancementGranted(owner, advancementEntry.value());
    }
}
