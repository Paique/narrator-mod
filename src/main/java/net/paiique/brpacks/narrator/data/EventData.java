package net.paiique.brpacks.narrator.data;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.BlockPos;

@Getter
@Setter
public class EventData extends Data {
    protected static int actionsPoints = 0;
    protected static BlockPos lastPlacedBlockPos = new BlockPos(0, 1256, 0);
    protected static BlockPos lastBreakedBlockPos = new BlockPos(0, 1256, 0);
    protected static String lastPlacedBlockName;
    protected static String lastBreakedBlockName;
    protected static boolean firstLogin = true;
}
