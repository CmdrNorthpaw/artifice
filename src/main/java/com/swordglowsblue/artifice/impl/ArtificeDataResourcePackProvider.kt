package com.swordglowsblue.artifice.impl;

import com.swordglowsblue.artifice.common.ArtificeRegistry;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.resource.ResourcePackProvider;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public final class ArtificeDataResourcePackProvider implements ResourcePackProvider {

    @Override
    public void register(Consumer<ResourcePackProfile> consumer, ResourcePackProfile.Factory factory) {
        for (Identifier id : ArtificeRegistry.DATA.getIds()) {
            consumer.accept(ArtificeRegistry.DATA.get(id).toServerResourcePackProfile(factory));
        }
    }
}
