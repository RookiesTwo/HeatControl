package com.rookiestwo.heatcontrol.mob_effect;

import com.rookiestwo.heatcontrol.HCRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

public class coolStatusEffect extends StatusEffect {

    public coolStatusEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0x3399FF);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return false;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity) {
            addAttributeModifier(HCRegistry.MAX_TEMPERATURE, , , )
        }
    }

}
