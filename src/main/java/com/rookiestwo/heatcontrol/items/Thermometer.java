package com.rookiestwo.heatcontrol.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class Thermometer extends Item {
    public Thermometer(Settings settings){
        super(settings);
    }
    Text message=new LiteralText("Thermometer");
    @Override
    //手持温度计右键：在客户端的聊天栏显示温度数值
    public TypedActionResult<ItemStack>use(World world, PlayerEntity playerEntity, Hand hand){
        if(!world.isClient())
            playerEntity.sendMessage(message,true);
        return TypedActionResult.success(playerEntity.getStackInHand(hand));
    }
}
