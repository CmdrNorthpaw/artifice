package com.swordglowsblue.artifice.impl

import com.swordglowsblue.artifice.common.ArtificeRegistry
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.resource.ResourcePackProfile
import net.minecraft.resource.ResourcePackProvider
import java.util.function.Consumer

class ArtificeAssetsResourcePackProvider : ResourcePackProvider {
    @Environment(EnvType.CLIENT)
    override fun register(consumer: Consumer<ResourcePackProfile>, factory: ResourcePackProfile.Factory) {
        for (id in ArtificeRegistry.ASSETS.ids) {
            consumer.accept(
                ArtificeRegistry.ASSETS[id]!!.toClientResourcePackProfile<ResourcePackProfile>(factory).get()
            )
        }
    }
}