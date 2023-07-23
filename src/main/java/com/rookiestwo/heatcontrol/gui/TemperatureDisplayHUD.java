package com.rookiestwo.heatcontrol.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.rookiestwo.heatcontrol.HCRegistry;
import com.rookiestwo.heatcontrol.HeatControl;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import java.lang.Math;

@Environment(EnvType.CLIENT)
public class TemperatureDisplayHUD implements HudRenderCallback {

    private static final Identifier  VERTICAL_TEMPERATURE_DISPLAY = new Identifier(HeatControl.MOD_ID, "textures/gui/thermometer_vertical.png");
    private static final Identifier POWDER_SNOW_OUTLINE = new Identifier("minecraft","textures/misc/powder_snow_outline.png");

    private static final Identifier HEAT_STOKE_OUTLINE = new Identifier(HeatControl.MOD_ID,"textures/misc/heat_stroke_outline.png");

    private static double targetY=0;

    private static float targetFreshTimer=0;

    private static float pointerFreshTimer=0;

    private static double currentY = 0;

    private static float targetFreshTime = 0.5F;

    private static float pointerFreshTime = 0.8F;

    private static int overHeatBarLength = 12;

    private static int properHeatBarLength = 14;

    private static final MinecraftClient client = MinecraftClient.getInstance();

    private double max_temp;
    private double min_temp;
    private double env_temp;

    private static float abnormalStateTicker=0.0f;

    @Override
    public void onHudRender(MatrixStack Matrixstack, float tickDelta) {
        max_temp = client.player.getAttributes().getValue(HCRegistry.MAX_TEMPERATURE);
        min_temp = client.player.getAttributes().getValue(HCRegistry.MIN_TEMPERATURE);
        env_temp = client.player.getAttributes().getValue(HCRegistry.ENV_TEMPERATURE);

        //若为创造或观察者就跳过后边所有内容
        if (client.player != null && (client.player.isCreative() || client.player.isSpectator())) {
            return;
        }

        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();

        int x=width/2+93;
        int y=height-39;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0,VERTICAL_TEMPERATURE_DISPLAY);

        //固定时间间隔刷新一次计量表指针的目标值
        if(!client.isPaused()) {
            targetFreshTimer += tickDelta;
            if (targetFreshTimer > targetFreshTime) {
                if (env_temp <= max_temp && env_temp >= min_temp) {
                    targetY = (properHeatBarLength / (min_temp - max_temp)) * (env_temp - max_temp) + overHeatBarLength - 1;
                }
                //为了让提示更明显 超过适合温度范围的显示分开处理
                if (env_temp > max_temp) {
                    targetY = overHeatBarLength - (int) ((env_temp - max_temp) * 1.5) - 1;
                }
                if (env_temp < min_temp) {
                    targetY = overHeatBarLength + properHeatBarLength + (int) ((min_temp - env_temp) * 1.5) - 1;
                }

                if (targetY < 2) targetY = 2;
                if (targetY > 33) targetY = 33;

                targetFreshTimer = 0;
            }
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

        //每tick都判断一次屏幕效果
        if(!client.isPaused()) {
            if(!(client.player.getFrozenTicks()>0)){
                if (env_temp > max_temp) {
                    abnormalStateTicker += tickDelta;
                    if (abnormalStateTicker >= 175) abnormalStateTicker = 175;
                }

                if (env_temp < min_temp) {
                    abnormalStateTicker -= tickDelta;
                    if (abnormalStateTicker <= -175) abnormalStateTicker = -175;
                }
            }
            if ((env_temp >= min_temp && env_temp <= max_temp)||client.player.getFrozenTicks()>0) {
                if (abnormalStateTicker < 0 && Math.abs(abnormalStateTicker) > 1)
                    abnormalStateTicker += tickDelta;
                if (abnormalStateTicker > 0 && Math.abs(abnormalStateTicker) > 1)
                    abnormalStateTicker -= tickDelta;
            }
        }
    }
    public static void renderAbnormalStateOverlay(){
        if(client.player.isCreative()||client.player.isSpectator())return;
        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();
        if(abnormalStateTicker>25){
            OverlayRenderer.renderOverlay(HEAT_STOKE_OUTLINE,(Math.abs(abnormalStateTicker)-25)/151,width,height);
        }
        if(abnormalStateTicker<-25){
            OverlayRenderer.renderOverlay(POWDER_SNOW_OUTLINE,(Math.abs(abnormalStateTicker)-25)/151,width,height);
        }
    }
}