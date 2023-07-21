package com.rookiestwo.heatcontrol.mixin;


import com.rookiestwo.heatcontrol.HCRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(LivingEntity.class)
public class HeatControlMixin {
    @Inject(
            method = "createLivingAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;",
            at=@At("RETURN")
    )
    private static void addAttribute(final CallbackInfoReturnable<DefaultAttributeContainer.Builder> info){
        info.getReturnValue().add(HCRegistry.ENV_TEMPERATURE).add(HCRegistry.MAX_TEMPERATURE).add(HCRegistry.MIN_TEMPERATURE);
    }
}
