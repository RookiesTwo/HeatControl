package com.rookiestwo.heatcontrol.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.rookiestwo.heatcontrol.HeatControl;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class TemperatureDisplayHUD implements HudRenderCallback {
    private static final Identifier  VERTICAL_TEMPERATURE_DISPLAY = new Identifier(HeatControl.MOD_ID,
            "textures/gui/thermometer_vertical.png");
    private static double targetY=0;

    private float targetFreshTimer=0;

    private static double CursorMovingSpeedPerTick=0.333;

    private static double currentY=0;

    public void setTargetY(double input){
        targetY=input;
    }

    @Override
    public void onHudRender(MatrixStack Matrixstack, float tickDelta){
        MinecraftClient client = MinecraftClient.getInstance();
        //若为创造或观察者就不渲染
        if (client.player != null && (client.player.isCreative() || client.player.isSpectator())) {
            return;
        }
        int x=0;
        int y=0;

        int width=client.getWindow().getScaledWidth();
        int height=client.getWindow().getScaledHeight();

        x=width/2+93;
        y=height-39;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0,VERTICAL_TEMPERATURE_DISPLAY);

        //0.5秒刷新一次计量表指针的目标值
        targetFreshTimer+=tickDelta;
        if(targetFreshTimer>0.5){
            double max_temp=client.player.getAttributes().getValue(HeatControl.max_temperature);
            double min_temp=client.player.getAttributes().getValue(HeatControl.min_temperature);
            double env_temp=client.player.getAttributes().getValue(HeatControl.env_temperature);

            targetY=(14/(min_temp-max_temp))*(env_temp-max_temp)+12-1;
            //HeatControl.LOGGER.info(String.valueOf(littleY));
            if(targetY<2)targetY=2;
            if(targetY>33)targetY=33;

            targetFreshTimer=0;
        }
        if((int)currentY>(int)targetY)currentY-=CursorMovingSpeedPerTick;
        if((int)currentY<(int)targetY)currentY+=CursorMovingSpeedPerTick;
        DrawableHelper.drawTexture(Matrixstack,x,y,0,0,5,38,256,256);
        DrawableHelper.drawTexture(Matrixstack,x-1,y+(int)currentY,5,0,7,3,256,256);
        //HeatControl.LOGGER.info("HUD Draw!");
    }

}