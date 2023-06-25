package com.rookiestwo.heatcontrol.mixin;


import com.rookiestwo.heatcontrol.HeatControl;
import com.rookiestwo.heatcontrol.tools.HeatAttributeManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.dimension.DimensionType;
import org.lwjgl.system.SharedLibrary;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(LivingEntity.class)
public class HeatControllerMixin {
    @Inject(
            method = "createLivingAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;",
            at=@At("RETURN")

    )
    private static void addAttribute(final CallbackInfoReturnable<DefaultAttributeContainer.Builder> info){
        info.getReturnValue().add(HeatControl.env_temperature);
    }
}
