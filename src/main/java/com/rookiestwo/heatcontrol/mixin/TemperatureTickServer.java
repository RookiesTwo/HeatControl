package com.rookiestwo.heatcontrol.mixin;

import com.rookiestwo.heatcontrol.HeatControl;
import com.rookiestwo.heatcontrol.tools.HeatAttributeManager;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.world.World;

import java.util.logging.Logger;

@Mixin (PlayerEntity.class)
public class TemperatureTickServer {
    @Inject(
            method ="tick",
            at = @At(
                    value="INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;tick()V",
                    ordinal = 0,
                    shift=At.Shift.AFTER
            )
    )
    private void tickPlayerTemperature(CallbackInfo ci){
        PlayerEntity player = (PlayerEntity)(Object)this;
        World world = player.world;
        //如果为客户端或为观察者就不执行tick
        if(world.isClient()||player.isSpectator()){
            return;
        }
        //HeatControl.LOGGER.info("设置！");
        player.getAttributeInstance(HeatControl.env_temperature).setBaseValue(HeatAttributeManager.calculateHeatValue(player));
    }
}
