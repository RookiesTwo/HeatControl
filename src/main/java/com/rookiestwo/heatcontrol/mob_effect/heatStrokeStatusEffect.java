package com.rookiestwo.heatcontrol.mob_effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

public class heatStrokeStatusEffect extends StatusEffect {
    public heatStrokeStatusEffect() {
        super(StatusEffectCategory.HARMFUL, 0xB80000);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity) {
            if (((PlayerEntity) entity).getMainHandStack().getItem().isDamageable()) {
                //((PlayerEntity)entity).dropItem(entity.getMainHandStack(),false,true);
                if(!entity.world.isClient()){
                    ((PlayerEntity) entity).dropItem(entity.getMainHandStack(), false, true);
                    ((PlayerEntity) entity).getInventory().dropSelectedItem(true);
                }
            }
        }
    }
}
