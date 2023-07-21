package com.rookiestwo.heatcontrol.tools;

import com.rookiestwo.heatcontrol.HCRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;

import java.util.HashMap;
import java.util.UUID;

public class AbnormalStateManager {
    private static HashMap<UUID, Integer> playerTimer = new HashMap<>();

    public static void initializePlayer(PlayerEntity player) {
        playerTimer.put(player.getUuid(), 0);
    }


    public static void AbnormalStateTick(PlayerEntity player, int deltaTicks) {
        if (!playerTimer.containsKey(player.getUuid())) {
            initializePlayer(player);
            //HeatControl.LOGGER.info("player added!");
        }

        //获取必须值
        double env_temperature = player.getAttributeInstance(HCRegistry.ENV_TEMPERATURE).getValue();
        double max_temperature = player.getAttributeInstance(HCRegistry.MAX_TEMPERATURE).getValue();
        double min_temperature = player.getAttributeInstance(HCRegistry.MIN_TEMPERATURE).getValue();
        int i = playerTimer.get(player.getUuid());
        if (i >= 150 && !player.hasStatusEffect(HCRegistry.effect_heat_stroke))
            player.addStatusEffect(new StatusEffectInstance(HCRegistry.effect_heat_stroke, 60));
        if (i <= -150 && !player.hasStatusEffect(HCRegistry.effect_hypothermia))
            player.addStatusEffect(new StatusEffectInstance(HCRegistry.effect_hypothermia, 60));

        //计算tick值
        if (env_temperature > max_temperature) {
            if (i < 150)
                playerTimer.replace(player.getUuid(), i + deltaTicks);
        }

        if (env_temperature <= max_temperature && env_temperature >= min_temperature) {
            if (i < 0) {
                playerTimer.replace(player.getUuid(), i + deltaTicks);
            }
            if (i > 0) {
                playerTimer.replace(player.getUuid(), i - deltaTicks);
            }
        }

        if (env_temperature < min_temperature) {
            if (i > -150)
                playerTimer.replace(player.getUuid(), i - deltaTicks);
        }
    }
}
