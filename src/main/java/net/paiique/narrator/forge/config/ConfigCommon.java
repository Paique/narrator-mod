package net.paiique.narrator.forge.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigCommon {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final ForgeConfigSpec.ConfigValue<Float> NARRATOR_VOLUME;
    public static final ForgeConfigSpec.ConfigValue<Integer> REQUIRED_ACTIONS_POINTS;
    public static final ForgeConfigSpec.ConfigValue<Integer> CHECK_COOLDOWN;
    public static final ForgeConfigSpec.ConfigValue<Boolean> DEBUG;

    public static final ForgeConfigSpec.EnumValue<NarratorProviders.textGenerator> TEXT_GENERATOR_PROVIDER;
    public static final ForgeConfigSpec.EnumValue<NarratorProviders.voiceGenerator> VOICE_GENERATOR_PROVIDER;
    public static final ForgeConfigSpec.ConfigValue<Float> NARRATOR_TEMPERATURE;


    //OpenAI
    public static final ForgeConfigSpec.ConfigValue<String> OPENAI_API_KEY;
    public static final ForgeConfigSpec.ConfigValue<String> OPENAI_TEXT_MODEL;
    public static final ForgeConfigSpec.ConfigValue<String> OPENAI_VOICE_MODEL;
    public static final ForgeConfigSpec.ConfigValue<String> OPENAI_VOICE_TYPE;
    public static final ForgeConfigSpec.ConfigValue<Integer> OPENAI_MAX_TEXT_TOKENS;



    //ElevenLabs
    public static final ForgeConfigSpec.ConfigValue<String> ELEVENLABS_API_KEY;
    public static final ForgeConfigSpec.ConfigValue<String> ELEVENLABS_VOICE_ID;
    public static final ForgeConfigSpec.ConfigValue<String> ELEVENLABS_MODEL_ID;
    public static final ForgeConfigSpec.ConfigValue<Double> ELEVENLABS_SIMILARITY_BOOST;
    public static final ForgeConfigSpec.ConfigValue<Double> ELEVENLABS_STABILITY;
    public static final ForgeConfigSpec.ConfigValue<Double> ELEVENLABS_STYLE;
    public static final ForgeConfigSpec.ConfigValue<Boolean> ELEVENLABS_SPEAKER_BOOST;



    static {
        BUILDER.push("General");

        TEXT_GENERATOR_PROVIDER = BUILDER.comment("Text generation provider")
                .defineEnum("text_generator", NarratorProviders.textGenerator.OPEN_AI);

        VOICE_GENERATOR_PROVIDER = BUILDER.comment("Voice generation provider")
                .defineEnum("voice_generator", NarratorProviders.voiceGenerator.OPEN_AI);

        NARRATOR_VOLUME = BUILDER.comment("Narrator Volume Percentage (Float)")
                .define("narrator_volume", 1.0f);

        REQUIRED_ACTIONS_POINTS = BUILDER.comment("Required Action Points (Integer)")
                .define("required_action_points", 300);

        NARRATOR_TEMPERATURE = BUILDER.comment("Narrator Temperature (Float)")
                .define("narrator_temperature", 0.3f);

        CHECK_COOLDOWN = BUILDER.comment("Cooldown between checks (Integer)")
                .define("check_every_x_ticks", 200);

        BUILDER.pop();


        BUILDER.push("OpenAi");

        OPENAI_API_KEY = BUILDER.comment("OpenAI API Key (String)")
                .define("openai_api_key", "CHANGE_ME");

        BUILDER.push("Advanced");

        BUILDER.push("Text");

        OPENAI_TEXT_MODEL = BUILDER.comment("Chat GPT Model (String)")
                .define("chat_gpt_model", "gpt-3.5-turbo");

        OPENAI_MAX_TEXT_TOKENS = BUILDER.comment("Max Tokens (Int)")
                .define("openai_max_tokens", 50);

        BUILDER.pop();
        BUILDER.push("Voice");

        OPENAI_VOICE_MODEL = BUILDER.comment("Voice Model (String)")
                .define("openai_voice_model", "tts-1");

        OPENAI_VOICE_TYPE = BUILDER.comment("Voice Type (String)")
                .define("openai_voice_type", "onyx");

        BUILDER.pop(3);

        BUILDER.push("ElevenLabs");
        BUILDER.push("Voice");
        ELEVENLABS_API_KEY = BUILDER.comment("ElevenLabs API Key (String)")
                .define("elevenlabs_api_key", "CHANGE_ME");

        BUILDER.push("Advanced");

        ELEVENLABS_VOICE_ID = BUILDER.comment("ElevenLabs voice ID (String)")
                .define("elevenlabs_voice_id", "pNInz6obpgDQGcFmaJgB");

        ELEVENLABS_MODEL_ID = BUILDER.comment("ElevenLabs MODEL ID (String)")
                .define("elevenlabs_model_id", "eleven_multilingual_v1");

        ELEVENLABS_STYLE = BUILDER.comment("ElevenLabs Style (double)")
                .define("elevenlabs_style", 1.0);

        ELEVENLABS_SIMILARITY_BOOST = BUILDER.comment("ElevenLabs Similarity (Double)")
                .define("elevenlabs_similarity", 0.5);

        ELEVENLABS_STABILITY = BUILDER.comment("ElevenLabs Stability (Double)")
                .define("elevenlabs_stability", 0.5);

        ELEVENLABS_SPEAKER_BOOST = BUILDER.comment("ElevenLabs Speaker Boost (Boolean)")
                .define("elevenlabs_speaker_boost", true);

        BUILDER.pop(3);


        BUILDER.push("Developer");

        DEBUG = BUILDER.comment("Debug mode (boolean)")
                        .define("debug", false);

        BUILDER.pop();


        COMMON_SPEC = BUILDER.build();
    }
}