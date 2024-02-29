package net.paiique.brpacks.narrator.openai;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.LinkedList;
import java.util.List;

/**
 * @author paique
 * @version 1.0
 */
public class Data {
    public LinkedList<String> actualAiText = new LinkedList<>();

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
