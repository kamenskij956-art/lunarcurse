package dev.lunarcurse.item;

import dev.lunarcurse.LunarCurse;
import dev.lunarcurse.ModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class AntidoteItem extends Item {
    public AntidoteItem(Settings settings) { super(settings); }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (!user.hasStatusEffect(ModEffects.LUNAR_FEVER)) return TypedActionResult.pass(stack);
        if (!world.isClient()) user.removeStatusEffect(ModEffects.LUNAR_FEVER);
        stack.decrementUnlessCreative(1, user);
        return TypedActionResult.success(stack, world.isClient());
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (entity instanceof VillagerEntity v && v.getCommandTags().contains(LunarCurse.INFECTED_TAG)) {
            if (!user.getWorld().isClient()) v.removeCommandTag(LunarCurse.INFECTED_TAG);
            stack.decrementUnlessCreative(1, user);
            return ActionResult.success(user.getWorld().isClient());
        }
        return ActionResult.PASS;
    }
    // TODO: cure a transformed werewolf (strip tag from its StoredVillager NBT).
}
