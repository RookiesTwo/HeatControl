package com.rookiestwo.heatcontrol.mixin;

import com.rookiestwo.heatcontrol.HCRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(PotionItem.class)
public class PotionMixin {
    @Inject(
            method="finishUsing(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;)Lnet/minecraft/item/ItemStack;",
            at=@At(value="INVOKE",target="Lnet/minecraft/potion/PotionUtil;getPotionEffects(Lnet/minecraft/item/ItemStack;)Ljava/util/List;",shift = At.Shift.AFTER)
    )
    private void appendCool(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir){
        List<StatusEffectInstance> list = PotionUtil.getPotionEffects(stack);
        if(list.isEmpty()){
            user.addStatusEffect(new StatusEffectInstance(HCRegistry.effect_cool,3600));
        }
    }
}
