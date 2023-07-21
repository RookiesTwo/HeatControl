package com.rookiestwo.heatcontrol.mob_effect;

import com.rookiestwo.heatcontrol.HCRegistry;
import com.rookiestwo.heatcontrol.tools.HeatControlConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

import java.util.UUID;

public class coolStatusEffect extends StatusEffect {

    public coolStatusEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0x3399FF);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (entity instanceof PlayerEntity) {
            addAttributeModifier(HCRegistry.MAX_TEMPERATURE, UUID.randomUUID().toString(), HeatControlConfig.coolEffectValue, EntityAttributeModifier.Operation.ADDITION);
        }
        super.onApplied(entity, attributes, amplifier);
    }
}
