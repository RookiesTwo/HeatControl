package com.rookiestwo.heatcontrol.mob_effect;

import com.rookiestwo.heatcontrol.HCRegistry;
import com.rookiestwo.heatcontrol.tools.HeatControlConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

public class coolStatusEffect extends StatusEffect {
    private static final EntityAttributeModifier MAX_TEMPERATURE_UPPER = new EntityAttributeModifier("max_temperature_upper", HeatControlConfig.coolEffectValue, EntityAttributeModifier.Operation.ADDITION);
    public coolStatusEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0x30cce8);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity player) {
            if (!player.getAttributes().hasModifierForAttribute(HCRegistry.MAX_TEMPERATURE, MAX_TEMPERATURE_UPPER.getId())) {
                player.getAttributeInstance(HCRegistry.MAX_TEMPERATURE).addTemporaryModifier(MAX_TEMPERATURE_UPPER);
            }
        }
    }
    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if(entity instanceof PlayerEntity player) {
            if (!player.getAttributes().hasModifierForAttribute(HCRegistry.MAX_TEMPERATURE, MAX_TEMPERATURE_UPPER.getId()))
                player.getAttributeInstance(HCRegistry.MAX_TEMPERATURE).addTemporaryModifier(MAX_TEMPERATURE_UPPER);
        }
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier){
        if(entity instanceof PlayerEntity player) {
            if (player.getAttributes().hasModifierForAttribute(HCRegistry.MAX_TEMPERATURE, MAX_TEMPERATURE_UPPER.getId()))
                player.getAttributeInstance(HCRegistry.MAX_TEMPERATURE).removeModifier(MAX_TEMPERATURE_UPPER);
        }
    }
}
