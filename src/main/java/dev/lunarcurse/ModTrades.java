package dev.lunarcurse;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;

import java.util.Optional;

public class ModTrades {
    public static void init() {
        TradeOfferHelper.registerVillagerOffers(ModVillagers.PRIEST, 1, f -> {
            f.add((e, r) -> new TradeOffer(new TradedItem(Items.EMERALD, 3),
                    new ItemStack(ModItems.SILVER_INGOT, r.nextBetween(2, 4)), 12, 2, 0.05f));
            f.add((e, r) -> new TradeOffer(new TradedItem(Items.EMERALD, 5),
                    new ItemStack(ModItems.HOLY_WATER_BOTTLE, r.nextBetween(1, 2)), 8, 2, 0.05f));
        });
        TradeOfferHelper.registerVillagerOffers(ModVillagers.PRIEST, 2, f -> {
            f.add((e, r) -> new TradeOffer(new TradedItem(Items.EMERALD, 6),
                    new ItemStack(ModItems.INCENSE, 1), 8, 5, 0.05f));
            f.add((e, r) -> new TradeOffer(new TradedItem(Items.EMERALD, 4),
                    new ItemStack(ModItems.SALT, 4), 8, 5, 0.05f));
        });
        TradeOfferHelper.registerVillagerOffers(ModVillagers.PRIEST, 3, f -> {
            f.add((e, r) -> new TradeOffer(new TradedItem(Items.EMERALD, 10),
                    Optional.of(new TradedItem(Items.EMERALD_BLOCK, 1)),
                    new ItemStack(ModItems.CROSS_FRAGMENT, 1), 4, 10, 0.05f));
            f.add((e, r) -> new TradeOffer(new TradedItem(ModItems.SILVER_SWORD, 1),
                    new ItemStack(Items.EMERALD, 24), 3, 15, 0.05f));
        });
    }
}
