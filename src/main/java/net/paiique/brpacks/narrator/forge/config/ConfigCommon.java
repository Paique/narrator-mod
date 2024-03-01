package net.paiique.brpacks.narrator.forge.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigCommon {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final ForgeConfigSpec.ConfigValue<String> OPENAI_API_KEY;
    public static final ForgeConfigSpec.ConfigValue<String> CHAT_GPT_MODEL;
    public static final ForgeConfigSpec.ConfigValue<String> VOICE_MODEL;
    public static final ForgeConfigSpec.ConfigValue<String> VOICE;
    public static final ForgeConfigSpec.ConfigValue<Boolean> DEBUG_MODE;
    public static final ForgeConfigSpec.ConfigValue<Float> NARRATOR_VOLUME;
    public static final ForgeConfigSpec.ConfigValue<Float> NARRATOR_RATE;
    public static final ForgeConfigSpec.ConfigValue<Integer> REQUIRED_ACTIONS_POINTS;
    public static final ForgeConfigSpec.ConfigValue<Integer> CHECK_COOLDOWN;


    static {
        BUILDER.push("Narrator Config");

        OPENAI_API_KEY = BUILDER.comment("OpenAI API Key (String)")
                .define("openai_api_key", "CHANGE_ME");

        CHAT_GPT_MODEL = BUILDER.comment("Chat GPT Model (String)")
                .define("chat_gpt_model", "gpt-3.5-turbo");

        VOICE_MODEL = BUILDER.comment("Voice Model (String)")
                .define("voice_model", "tts-1");

        VOICE = BUILDER.comment("Voice (String)")
                .define("voice", "onyx");

        NARRATOR_VOLUME = BUILDER.comment("Narrator Volume Percentage (Float)")
                .define("narrator_volume", 100.0f);

        NARRATOR_RATE = BUILDER.comment("Narrator Rate (Float)")
                .define("narrator_rate", 1.0f);

        REQUIRED_ACTIONS_POINTS = BUILDER.comment("Required Actions Points (Integer)")
                .define("required_actions_points", 300);

        CHECK_COOLDOWN = BUILDER.comment("Cooldown between checks (Integer)")
                .define("check_every_x_ticks", 200);

        DEBUG_MODE = BUILDER.comment("Debug Mode (Boolean)")
                .define("debug_mode", false);



        BUILDER.pop();
        COMMON_SPEC = BUILDER.build();
    }

}