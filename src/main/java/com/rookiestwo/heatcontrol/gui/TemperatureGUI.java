package com.rookiestwo.heatcontrol.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class TemperatureGUI extends Screen{
    public static final Identifier thermometer_gui=new Identifier("heatcontrol","thermometer_vertical");

    public TemperatureGUI(){
        super(Text.of("Temperature GUI"));
    }
//    @Override
//    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
//        MinecraftClient.getInstance().getTextureManager().bindTexture(thermometer_gui);
//        DrawableHelper.drawTexture(matrices, 10, 10, 0, 0, 5, 43, 256, 256);
//    }
}
