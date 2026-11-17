package dev.lunarcurse;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;

public class ModBlocks {
    public static final Block ALTAR = reg("altar", new Block(AbstractBlock.Settings.create()
            .mapColor(MapColor.STONE_GRAY).strength(3.5f).sounds(BlockSoundGroup.STONE)));
    /** TODO: make it a thin salt line (carpet-like) + pathfinding avoidance. MVP: solid block, nothing walks through it. */
    public static final Block SALT = reg("salt", new Block(AbstractBlock.Settings.create()
            .mapColor(MapColor.WHITE).strength(0.5f).sounds(BlockSoundGroup.SAND)));

    private static Block reg(String name, Block b) { return Registry.register(Registries.BLOCK, LunarCurse.id(name), b); }
    public static void init() {}
}
