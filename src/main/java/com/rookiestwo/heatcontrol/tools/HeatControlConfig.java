package com.rookiestwo.heatcontrol.tools;

import com.google.gson.annotations.SerializedName;

public class HeatControlConfig {

    @SerializedName("Temerature_Fresh_Time")
    public static double temperatureFreshTime = 0.5;
    @SerializedName("Base_Environment_Temperature")
    public static double baseEnvironmentTemperature = 25.0;
    @SerializedName("Bedrock_Layer_Underground_Temperature")
    public static double bedrockLayerTemperature = 45.0;
    @SerializedName("Underground_Temperature_When_Y_Equals_Zero")
    public static double zeroYLayerTemperature = 19.7;
    @SerializedName("Nether_Base_Environment_Temperature")
    public static double netherBaseEnvironmentTemperature = 52.3;
    @SerializedName("Max_Temperature_Player_Can_Stand")
    public static double maxPlayerTemperature = 40.0;
    @SerializedName("Min_Temperature_Player_Can_Stand")
    public static double minPlayerTemperature = 16.0;

}
