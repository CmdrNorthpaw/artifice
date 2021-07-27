package com.swordglowsblue.artifice.impl

import com.swordglowsblue.artifice.common.ArtificeRegistry
import net.minecraft.resource.ResourcePackProfile
import net.minecraft.resource.ResourcePackProvider
import java.util.function.Consumer

class ArtificeDataResourcePackProvider : ResourcePackProvider {
    override fun register(consumer: Consumer<ResourcePackProfile>, factory: ResourcePackProfile.Factory) {
        for (id in ArtificeRegistry.DATA.ids) {
            consumer.accept(ArtificeRegistry.DATA[id]!!.toServerResourcePackProfile<ResourcePackProfile>(factory))
        }
    }
}