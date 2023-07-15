package com.rookiestwo.heatcontrol.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import com.rookiestwo.heatcontrol.HeatControl;
import java.text.DecimalFormat;

public class Thermometer extends Item {
    public Thermometer(Settings settings){
        super(settings);
    }

    @Override
    //手持温度计右键：在客户端的聊天栏显示温度数值
    public TypedActionResult<ItemStack>use(World world, PlayerEntity playerEntity, Hand hand){
        if(!world.isClient()){
            //输出调制后的小数
            Text message = new LiteralText(String.format("%.1f",playerEntity.getAttributes().getValue(HeatControl.env_temperature))+" °C");
            playerEntity.sendMessage(message,true);
        }
        return TypedActionResult.success(playerEntity.getStackInHand(hand));
    }
}
