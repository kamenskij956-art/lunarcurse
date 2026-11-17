package dev.lunarcurse.client;

import dev.lunarcurse.LunarCurse;
import dev.lunarcurse.entity.WerewolfEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.CrossbowPosing;
import net.minecraft.client.render.entity.model.EntityModelLayer;

/** Biped body + wolf snout, ears and tail. Texture: textures/entity/werewolf.png (64x64). */
public class WerewolfModel extends BipedEntityModel<WerewolfEntity> {
    public static final EntityModelLayer LAYER = new EntityModelLayer(LunarCurse.id("werewolf"), "main");

    public WerewolfModel(ModelPart root) { super(root); }

    public static TexturedModelData getTexturedModelData() {
        ModelData data = BipedEntityModel.getModelData(Dilation.NONE, 0.0f);
        ModelPartData root = data.getRoot();
        ModelPartData head = root.getChild(EntityModelPartNames.HEAD);
        head.addChild("snout", ModelPartBuilder.create().uv(0, 32).cuboid(-2f, -3f, -8f, 4f, 3f, 4f), ModelTransform.NONE);
        head.addChild("ear_left", ModelPartBuilder.create().uv(0, 40).cuboid(1.5f, -11f, -1f, 2f, 3f, 1f), ModelTransform.NONE);
        head.addChild("ear_right", ModelPartBuilder.create().uv(0, 40).cuboid(-3.5f, -11f, -1f, 2f, 3f, 1f), ModelTransform.NONE);
        root.getChild(EntityModelPartNames.BODY).addChild("tail",
                ModelPartBuilder.create().uv(0, 48).cuboid(-1f, 0f, 0f, 2f, 2f, 6f),
                ModelTransform.of(0f, 10f, 2f, -0.5f, 0f, 0f));
        return TexturedModelData.of(data, 64, 64);
    }

    @Override
    public void setAngles(WerewolfEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        super.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
        CrossbowPosing.meleeAttack(this.leftArm, this.rightArm, entity.isAttacking(), this.handSwingProgress, animationProgress);
    }
}
