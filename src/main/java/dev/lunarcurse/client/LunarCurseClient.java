package dev.lunarcurse.client;

import dev.lunarcurse.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class LunarCurseClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(WerewolfModel.LAYER, WerewolfModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.WEREWOLF, WerewolfRenderer::new);
    }
}
