package com.rookiestwo.heatcontrol.mixin;

import com.rookiestwo.heatcontrol.gui.TemperatureDisplayHUD;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class HeatControlClientMixin {
    @Inject(
            method = "render(Lnet/minecraft/client/util/math/MatrixStack;F)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;lerp(FFF)F", ordinal = 1, shift=At.Shift.BEFORE)
            )
    private void renderOverlay(CallbackInfo info){
        TemperatureDisplayHUD.renderAbnormalStateOverlay();
    }
}
