package com.rookiestwo.heatcontrol.mixin;

import com.rookiestwo.heatcontrol.gui.TemperatureGUI;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class TemHUDMixin extends DrawableHelper{
    @Shadow @Final private MinecraftClient client;

    @Inject(method = "render",at=@At("TAIL"))
    private void renderTemTexture(MatrixStack matrices, float tockDelta, CallbackInfo info){
        this.client.getTextureManager().bindTexture(TemperatureGUI.thermometer_gui);
        this.drawTexture(matrices,10,10,0,0,5,43);
    }
}
