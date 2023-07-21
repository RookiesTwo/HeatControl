package com.rookiestwo.heatcontrol.mob_effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

public class hypothermiaStatusEffect extends StatusEffect {

    private static final EntityAttributeModifier MOVEMENT_SPEED_DECREASE = new EntityAttributeModifier("movement_speed_decrease", -0.6, EntityAttributeModifier.Operation.MULTIPLY_BASE);
    public hypothermiaStatusEffect() {
        super(StatusEffectCategory.HARMFUL, 0xCCFFFF);
    }
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        int i = 25 >> amplifier;
        if (i > 0) {
            return duration % i == 0;
        }
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        entity.damage(DamageSource.FREEZE, 2.0f);
        if (entity instanceof PlayerEntity player) {
            player.addExhaustion(0.005f * (float) (amplifier + 1));

            if (!player.getAttributes().hasModifierForAttribute(EntityAttributes.GENERIC_MOVEMENT_SPEED, MOVEMENT_SPEED_DECREASE.getId())) {
                player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).addTemporaryModifier(MOVEMENT_SPEED_DECREASE);
            }
        }
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if(entity instanceof PlayerEntity player) {
            player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).addTemporaryModifier(MOVEMENT_SPEED_DECREASE);
        }
    }
    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier){
        if(entity instanceof PlayerEntity player) {
            player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).removeModifier(MOVEMENT_SPEED_DECREASE);
        }
    }
}
