package net.paiique.brpacks.narrator;

import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.paiique.brpacks.narrator.data.Data;
import net.paiique.brpacks.narrator.forge.config.ConfigCommon;
import net.paiique.brpacks.narrator.util.FileUtil;
import org.slf4j.Logger;

import java.util.Random;

// The value here should match an entry in the META-INF/mods.toml file

/**
 * @author paique
 * @version 1.1
 */


@Mod(NarratorMod.MODID)
public class NarratorMod {
    public static final String MODID = "narrator";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static Data data;
    public static Random random;

    public static FileUtil fileUtil;


    public NarratorMod() {
        random = new Random();
        fileUtil = new FileUtil();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigCommon.COMMON_SPEC, "narrator-common.toml");
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;
        data = new Data();
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> new NarratorClient().onCtorClient(modEventBus, forgeEventBus));
    }
}