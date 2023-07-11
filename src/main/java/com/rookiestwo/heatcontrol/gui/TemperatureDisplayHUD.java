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

    private static float targetFreshTimer=0;

    private static float pointerFreshTimer=0;

    //private static double CursorMovingSpeedPerTick=0.333;

    private static double currentY=0;

    private static float targetFreshTime= 0.5F;

    private static float pointerFreshTime=0.3F;

//    public void setTargetY(double input){
//        targetY=input;
//    }

    @Override
    public void onHudRender(MatrixStack Matrixstack, float tickDelta){
        MinecraftClient client = MinecraftClient.getInstance();
        //若为创造或观察者就不渲染
        if (client.player != null && (client.player.isCreative() || client.player.isSpectator())) {
            return;
        }

        int width=client.getWindow().getScaledWidth();
        int height=client.getWindow().getScaledHeight();

        int x=width/2+93;
        int y=height-39;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0,VERTICAL_TEMPERATURE_DISPLAY);

        //固定时间间隔刷新一次计量表指针的目标值
        targetFreshTimer+=tickDelta;
        if(targetFreshTimer>targetFreshTime){
            double max_temp=client.player.getAttributes().getValue(HeatControl.max_temperature);
            double min_temp=client.player.getAttributes().getValue(HeatControl.min_temperature);
            double env_temp=client.player.getAttributes().getValue(HeatControl.env_temperature);

            targetY=(14/(min_temp-max_temp))*(env_temp-max_temp)+12-1;

            if(targetY<2)targetY=2;
            if(targetY>33)targetY=33;

            targetFreshTimer=0;
        }

        //固定时间间隔将HUD量表指针移动一个像素点（指针移动速度控制）
        pointerFreshTimer+=tickDelta;
        if(pointerFreshTimer>pointerFreshTime){
            if((int)currentY>(int)targetY)currentY--;
            if((int)currentY<(int)targetY)currentY++;

            pointerFreshTimer=0;
        }

        //渲染温度条
        DrawableHelper.drawTexture(Matrixstack,x,y,0,0,5,38,256,256);
        //渲染温度指针
        DrawableHelper.drawTexture(Matrixstack,x-1,y+(int)currentY,5,0,7,3,256,256);

        //HeatControl.LOGGER.info("HUD Draw!");
    }

}