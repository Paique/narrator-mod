package net.paiique.narrator.data;

import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.*;

/**
 * @author paique
 * @version 1.0
 */
@Getter
public class Data {
    public static LinkedList<String> actualAiText = new LinkedList<>();
    protected static boolean narratorThreadLock = false;

    protected static int actionsPoints = 0;
    protected static int messageCooldown = 0;
    protected static BlockPos lastPlacedBlockPos = new BlockPos(0, 1256, 0);
    protected static BlockPos lastBreakedBlockPos = new BlockPos(0, 1256, 0);
    protected static String lastPlacedBlockName;
    protected static String lastBreakedBlockName;
    protected static boolean firstLogin = true;
    protected static Map<UUID, String> disconnectedPlayers = new HashMap<>();


    public List<Block> iterableBlocks = List.of(
            Blocks.ACACIA_DOOR,
            Blocks.ACACIA_FENCE_GATE,
            Blocks.DARK_OAK_DOOR,
            Blocks.DARK_OAK_FENCE_GATE,
            Blocks.BIRCH_DOOR,
            Blocks.BIRCH_FENCE_GATE,
            Blocks.CRIMSON_DOOR,
            Blocks.CRIMSON_FENCE_GATE,
            Blocks.JUNGLE_DOOR,
            Blocks.JUNGLE_FENCE_GATE,
            Blocks.OAK_DOOR,
            Blocks.OAK_FENCE_GATE,
            Blocks.SPRUCE_DOOR,
            Blocks.SPRUCE_FENCE_GATE,
            Blocks.WARPED_DOOR,
            Blocks.WARPED_FENCE_GATE,
            Blocks.OAK_FENCE
    );

}
