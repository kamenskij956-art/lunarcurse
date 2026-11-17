package dev.lunarcurse;

import dev.lunarcurse.item.AntidoteItem;
import dev.lunarcurse.item.HolyWaterItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

public class ModItems {
    // components (no effects yet)
    public static final Item SILVER_INGOT = reg("silver_ingot", new Item(new Item.Settings()));
    public static final Item HOLY_WATER_BOTTLE = reg("holy_water_bottle", new Item(new Item.Settings().maxCount(16)));
    public static final Item INCENSE = reg("incense", new Item(new Item.Settings()));
    public static final Item CROSS_FRAGMENT = reg("cross_fragment", new Item(new Item.Settings()));
    public static final Item SALT = reg("salt", new BlockItem(ModBlocks.SALT, new Item.Settings()));
    public static final Item ALTAR = reg("altar", new BlockItem(ModBlocks.ALTAR, new Item.Settings()));

    // holy items
    public static final Item HOLY_CROSS = reg("holy_cross", new Item(new Item.Settings().maxDamage(64)));
    public static final Item HOLY_WATER = reg("holy_water", new HolyWaterItem(new Item.Settings().maxCount(16)));
    /** TODO: custom ToolMaterial (silver). MVP uses iron stats. */
    public static final Item SILVER_SWORD = reg("silver_sword", new SwordItem(ToolMaterials.IRON,
            new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.IRON, 3, -2.4f))));
    public static final Item ANTIDOTE = reg("antidote", new AntidoteItem(new Item.Settings().maxCount(16)));

    public static final Item WEREWOLF_SPAWN_EGG = reg("werewolf_spawn_egg",
            new SpawnEggItem(ModEntities.WEREWOLF, 0x5A4632, 0xA02020, new Item.Settings()));

    public static final ItemGroup GROUP = Registry.register(Registries.ITEM_GROUP, LunarCurse.id("main"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(HOLY_CROSS))
                    .displayName(Text.translatable("itemGroup.lunarcurse"))
                    .entries((ctx, e) -> {
                        e.add(ALTAR); e.add(SALT);
                        e.add(SILVER_INGOT); e.add(HOLY_WATER_BOTTLE); e.add(INCENSE); e.add(CROSS_FRAGMENT);
                        e.add(HOLY_CROSS); e.add(HOLY_WATER); e.add(SILVER_SWORD); e.add(ANTIDOTE);
                        e.add(WEREWOLF_SPAWN_EGG);
                    }).build());

    private static Item reg(String name, Item item) { return Registry.register(Registries.ITEM, LunarCurse.id(name), item); }
    public static void init() {}
}
