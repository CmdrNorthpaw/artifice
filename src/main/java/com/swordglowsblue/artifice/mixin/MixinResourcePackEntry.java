package com.swordglowsblue.artifice.mixin;

import net.minecraft.client.gui.screen.pack.PackListWidget;
import net.minecraft.client.gui.screen.pack.ResourcePackOrganizer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PackListWidget.ResourcePackEntry.class)
public interface MixinResourcePackEntry {
    @Accessor("pack")
    ResourcePackOrganizer.Pack getResourcePackInfo();
}
