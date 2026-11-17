package dev.lunarcurse.client;

import dev.lunarcurse.LunarCurse;
import dev.lunarcurse.entity.WerewolfEntity;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class WerewolfRenderer extends BipedEntityRenderer<WerewolfEntity, WerewolfModel> {
    private static final Identifier TEXTURE = LunarCurse.id("textures/entity/werewolf.png");

    public WerewolfRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new WerewolfModel(ctx.getPart(WerewolfModel.LAYER)), 0.5f);
    }

    @Override
    public Identifier getTexture(WerewolfEntity entity) { return TEXTURE; }

    @Override
    protected void scale(WerewolfEntity entity, MatrixStack matrices, float amount) {
        matrices.scale(1.1f, 1.1f, 1.1f);
    }
}
