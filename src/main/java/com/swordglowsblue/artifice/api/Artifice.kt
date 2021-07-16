package com.swordglowsblue.artifice.api

import com.swordglowsblue.artifice.api.ArtificeResourcePack.ClientResourcePackBuilder
import com.swordglowsblue.artifice.api.ArtificeResourcePack.ServerResourcePackBuilder
import com.swordglowsblue.artifice.api.util.Processor
import com.swordglowsblue.artifice.common.ArtificeRegistry
import com.swordglowsblue.artifice.impl.ArtificeImpl
import com.swordglowsblue.artifice.impl.DynamicResourcePackFactory
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.resource.ResourceType
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
    @Deprecated("Deprecated in favor of {@link Artifice#registerAssetPack(Identifier, Processor)}")
    fun registerAssets(id: Identifier, register: Processor<ClientResourcePackBuilder>): ArtificeResourcePack {
        ArtificeImpl.LOGGER.warn("Using deprecated Artifice#registerAssets! Please use registerAssetPack! Issues may occur!")
        return ArtificeImpl.registerSafely(ArtificeRegistry.ASSETS, id, ArtificeResourcePack.ofAssets(register))
    }

    @Deprecated("Deprecated in favor of registerDataPack")
    fun registerData(id: Identifier, register: Processor<ServerResourcePackBuilder>): ArtificeResourcePack {
        ArtificeImpl.LOGGER.warn("Using deprecated Artifice#registerData! Please use registerDataPack! Issues may occur!")
        return ArtificeImpl.registerSafely(ArtificeRegistry.DATA, id, ArtificeResourcePack.ofData(register))
    }

    /**
     * Register a new server-side resource pack, creating resources with the given callback.
     *
     * @param id       The pack ID.
     * @param register A callback which will be passed a [ServerResourcePackBuilder].
     * @return The registered pack.
     */
    @JvmStatic
    fun registerDataPack(id: Identifier, register: Processor<ServerResourcePackBuilder>) {
        ArtificeImpl.registerSafely(
            ArtificeRegistry.DATA,
            id,
            DynamicResourcePackFactory(ResourceType.SERVER_DATA, id, register)
        )
    }

    /**
     * Register a new client-side resource pack.
     *
     * @param id   The pack ID.
     * @param pack The pack to register.
     * @return The registered pack.
     */
    @Environment(EnvType.CLIENT)
    fun registerAssets(id: String, pack: ArtificeResourcePack): ArtificeResourcePack {
        return registerAssets(Identifier(id), pack)
    }

    /**
     * Register a new server-side resource pack.
     *
     * @param id   The pack ID.
     * @param pack The pack to register.
     * @return The registered pack.
     */
    fun registerData(id: String, pack: ArtificeResourcePack): ArtificeResourcePack {
        return registerData(Identifier(id), pack)
    }

    /**
     * Register a new client-side resource pack.
     *
     * @param id   The pack ID.
     * @param pack The pack to register.
     * @return The registered pack.
     * @see ArtificeResourcePack.ofAssets
     */
    @JvmStatic
    @Environment(EnvType.CLIENT)
    fun registerAssets(id: Identifier, pack: ArtificeResourcePack): ArtificeResourcePack {
        require(pack.type == ResourceType.CLIENT_RESOURCES) { "Cannot register a server-side pack as assets" }
        return ArtificeImpl.registerSafely(ArtificeRegistry.ASSETS, id, pack)
    }

    /**
     * Register a new server-side resource pack.
     *
     * @param id   The pack ID.
     * @param pack The pack to register.
     * @return The registered pack.
     * @see ArtificeResourcePack.ofData
     */
    @JvmStatic
    fun registerData(id: Identifier, pack: ArtificeResourcePack): ArtificeResourcePack {
        require(pack.type == ResourceType.SERVER_DATA) { "Cannot register a client-side pack as data" }
        return ArtificeImpl.registerSafely(ArtificeRegistry.DATA, id, pack)
    }
}