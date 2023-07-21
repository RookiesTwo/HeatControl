package com.rookiestwo.heatcontrol.mob_effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

import java.util.UUID;

public class hypothermiaStatusEffect extends StatusEffect {
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
        if (entity instanceof PlayerEntity) {
            ((PlayerEntity) entity).addExhaustion(0.005f * (float) (amplifier + 1));
        }
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, UUID.randomUUID().toString(), -0.6, EntityAttributeModifier.Operation.MULTIPLY_BASE);
        super.onApplied(entity, attributes, amplifier);
    }
}
