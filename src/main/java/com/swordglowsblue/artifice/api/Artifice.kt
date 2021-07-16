package com.swordglowsblue.artifice.api

import com.swordglowsblue.artifice.impl.ArtificeResourcePack.ClientResourcePackBuilder
import com.swordglowsblue.artifice.impl.ArtificeResourcePack.ServerResourcePackBuilder
import com.swordglowsblue.artifice.api.util.Processor
import com.swordglowsblue.artifice.common.ArtificeRegistry
import com.swordglowsblue.artifice.impl.ArtificeImpl
import com.swordglowsblue.artifice.impl.ArtificeResourcePack
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.util.Identifier

/**
 * Registry methods for Artifice's virtual resource pack support.
 */
@Deprecated("Please use the top-level functions. If in Java, use com.swordglowsblue.artifice.Artifice",
replaceWith = ReplaceWith("Artifice", imports = ["uk.cmdrnorthpaw.artifice.Artifice"]))
object Artifice {

    @Environment(EnvType.CLIENT)
    @Deprecated("Deprecated in favor of registerAssetPack",
    replaceWith = ReplaceWith("registerAssetPack(id: String, register: Builder<ClientResourcePackBuilder>)",
    imports = ["com.swordglowsblue.artifice.registerAssetPack"]))
    fun registerAssets(id: String, register: Processor<ClientResourcePackBuilder>): ArtificeResourcePack {
        return registerAssets(Identifier(id), register)
    }

    @Deprecated("Deprecated in favour of registerDataPack",
    replaceWith = ReplaceWith("registerDataPack(id: String, register: Builder<ServerResourcePackBuilder>)",
    imports = ["com.swordglowsblue.artifice.registerDataPack"]))
    fun registerData(id: String, register: Processor<ServerResourcePackBuilder>): ArtificeResourcePack {
        return registerData(Identifier(id), register)
    }

    @Environment(EnvType.CLIENT)
    @Deprecated("Deprecated in favor of registerAssetPack",
    replaceWith = ReplaceWith("registerAssetPack(id: Identifier, register: Builder<ClientResourcePackBuilder>",
    imports = ["com.swordglowsblue.artifice.registerAssetPack"]))
    fun registerAssets(id: Identifier, register: Processor<ClientResourcePackBuilder>): ArtificeResourcePack {
        ArtificeImpl.LOGGER.warn("Using deprecated Artifice#registerAssets! Please use registerAssetPack! Issues may occur!")
        return ArtificeImpl.registerSafely(ArtificeRegistry.ASSETS, id, ArtificeResourcePack.ofAssets(register))
    }

    @Deprecated("Deprecated in favor of registerDataPack",
    replaceWith = ReplaceWith("registerDataPack(id: Identifier, register: Builder<ServerResourcePackBuilder>")
    )
    fun registerData(id: Identifier, register: Processor<ServerResourcePackBuilder>): ArtificeResourcePack {
        ArtificeImpl.LOGGER.warn("Using deprecated Artifice#registerData! Please use registerDataPack! Issues may occur!")
        return ArtificeImpl.registerSafely(ArtificeRegistry.DATA, id, ArtificeResourcePack.ofData(register))
    }
}