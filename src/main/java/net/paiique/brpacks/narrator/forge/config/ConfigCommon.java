package net.paiique.brpacks.narrator.forge.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigCommon {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final ForgeConfigSpec.ConfigValue<String> OPENAI_API_KEY;
    public static final ForgeConfigSpec.ConfigValue<String> CHAT_GPT_MODEL;
    public static final ForgeConfigSpec.ConfigValue<String> VOICE_MODEL;
    public static final ForgeConfigSpec.ConfigValue<String> VOICE;
    public static final ForgeConfigSpec.ConfigValue<Integer> NARRATOR_VOLUME;
    public static final ForgeConfigSpec.ConfigValue<Integer> REQUIRED_ACTIONS_POINTS;
    public static final ForgeConfigSpec.ConfigValue<Integer> CHECK_COOLDOWN;
    public static final ForgeConfigSpec.ConfigValue<Float> NARRATOR_TEMPERATURE;
    public static final ForgeConfigSpec.ConfigValue<Boolean> DEBUG;


    static {
        BUILDER.push("Narrator Config");

        OPENAI_API_KEY = BUILDER.comment("OpenAI API Key (String)")
                .define("openai_api_key", "CHANGE_ME");

        NARRATOR_VOLUME = BUILDER.comment("Narrator Volume Percentage (Int)")
                .define("narrator_volume", 100);

        BUILDER.comment("[Advanced] (Do not change unless you know what you are doing!)");

        CHAT_GPT_MODEL = BUILDER.comment("Chat GPT Model (String)")
                .define("chat_gpt_model", "gpt-3.5-turbo");

        VOICE_MODEL = BUILDER.comment("Voice Model (String)")
                .define("voice_model", "tts-1");

        VOICE = BUILDER.comment("Voice (String)")
                .define("voice", "onyx");

        REQUIRED_ACTIONS_POINTS = BUILDER.comment("Required Action Points (Integer)")
                .define("required_action_points", 300);

        NARRATOR_TEMPERATURE = BUILDER.comment("Narrator Temperature (Float)")
                .define("narrator_temperature", 0.3f);

        CHECK_COOLDOWN = BUILDER.comment("Cooldown between checks (Integer)")
                .define("check_every_x_ticks", 200);

        BUILDER.comment("Developer settings");

        DEBUG = BUILDER.comment("Debug mode (boolean)")
                        .define("debug", false);

        BUILDER.pop();
        COMMON_SPEC = BUILDER.build();
    }
}