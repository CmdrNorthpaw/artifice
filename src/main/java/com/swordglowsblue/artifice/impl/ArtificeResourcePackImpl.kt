package com.swordglowsblue.artifice.impl

import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.JsonObjectBuilder
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import com.swordglowsblue.artifice.api.builder.assets.*
import com.swordglowsblue.artifice.api.builder.data.AdvancementBuilder
import com.swordglowsblue.artifice.api.builder.data.LootTableBuilder
import com.swordglowsblue.artifice.api.builder.data.TagBuilder
import com.swordglowsblue.artifice.api.builder.data.dimension.DimensionBuilder
import com.swordglowsblue.artifice.api.builder.data.dimension.DimensionTypeBuilder
import com.swordglowsblue.artifice.api.builder.data.recipe.*
import com.swordglowsblue.artifice.api.builder.data.worldgen.NoiseSettingsBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.biome.BiomeBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.configured.ConfiguredCarverBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.configured.ConfiguredSurfaceBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.ConfiguredFeatureBuilder
import com.swordglowsblue.artifice.api.resource.ArtificeResource
import com.swordglowsblue.artifice.api.resource.JsonResource
import com.swordglowsblue.artifice.api.util.Builder
import com.swordglowsblue.artifice.api.util.IdUtils.wrapPath
import com.swordglowsblue.artifice.api.virtualpack.ArtificeResourcePackContainer
import com.swordglowsblue.artifice.common.ArtificeRegistry
import com.swordglowsblue.artifice.common.ClientOnly
import com.swordglowsblue.artifice.impl.ArtificeResourcePack.ClientResourcePackBuilder
import com.swordglowsblue.artifice.impl.ArtificeResourcePack.ServerResourcePackBuilder
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.api.EnvironmentInterface
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.SharedConstants
import net.minecraft.client.resource.language.LanguageDefinition
import net.minecraft.resource.ResourcePackProfile
import net.minecraft.resource.ResourcePackSource
import net.minecraft.resource.ResourceType
import net.minecraft.resource.metadata.ResourceMetadataReader
import net.minecraft.util.Identifier
import org.apache.commons.io.input.NullInputStream
import org.apache.logging.log4j.LogManager
import java.io.*
import java.util.*
import java.util.function.Predicate
import java.util.function.Supplier

class ArtificeResourcePackImpl<T: ArtificeResourcePack.ResourcePackBuilder>(
    override val type: ResourceType,
    private val identifier: Identifier?,
    registerResources: Builder<T>
) : ArtificeResourcePack {
    private val namespaces: MutableSet<String> = HashSet()
    private val resources: MutableMap<Identifier, ArtificeResource<*>> = HashMap()
    private val languages: MutableSet<LanguageDefinition> = HashSet()
    private val metadata: JsonResource<JsonObject>
    private var description: String? = null
    private var displayName: String? = null
    override var isOptional = false
        private set
    override var isVisible = false
        private set
    override var isShouldOverwrite = false
        private set
    private val isClient: Boolean
        get() = try {
            FabricLoader.getInstance().environmentType == EnvType.CLIENT
        } catch (e: NullPointerException) {
            true
        }

    private fun addLanguages(languageMeta: JsonObject) {
        for (def in languages) {
            languageMeta.add(
                def.code, JsonObjectBuilder()
                    .add("name", def.name)
                    .add("region", def.region)
                    .add("bidirectional", def.isRightToLeft)
                    .build()
            )
        }
    }

    @Throws(IOException::class, IllegalArgumentException::class)
    override fun dumpResources(folderPath: String) {
        LogManager.getLogger()
            .info("[Artifice] Dumping " + name + " " + type.directory + " to " + folderPath + ", this may take a while.")
        val dir = File(folderPath)
        if (!dir.exists() && !dir.mkdirs()) {
            throw IOException("Can't dump resources to $folderPath; couldn't create parent directories")
        }
        require(dir.isDirectory) { "Can't dump resources to $folderPath as it's not a directory" }
        if (!dir.canWrite()) {
            throw IOException("Can't dump resources to $folderPath; permission denied")
        }
        writeResourceFile(File("$folderPath/pack.mcmeta"), metadata)
        resources.forEach { (id: Identifier, resource: ArtificeResource<*>) ->
            val path = String.format("./%s/%s/%s/%s", folderPath, type.directory, id.namespace, id.path)
            writeResourceFile(File(path), resource)
        }
        LogManager.getLogger().info("[Artifice] Finished dumping " + name + " " + type.directory + ".")
    }

    private fun writeResourceFile(output: File, resource: ArtificeResource<*>) {
        try {
            if (output.parentFile.exists() || output.parentFile.mkdirs()) {
                val writer = BufferedWriter(FileWriter(output))
                if (resource.data is JsonElement) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    writer.write(gson.toJson(resource.data))
                } else {
                    writer.write(resource.data.toString())
                }
                writer.close()
            } else {
                throw IOException("Failed to dump resource file " + output.path + "; couldn't create parent directories")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @EnvironmentInterface(value = EnvType.CLIENT, itf = ClientResourcePackBuilder::class)
    private inner class ArtificeResourcePackBuilder : ClientResourcePackBuilder, ServerResourcePackBuilder {
        override fun setDisplayName(name: String) {
            displayName = name
        }

        override fun setDescription(desc: String) {
            description = desc
        }

        @Throws(IOException::class)
        override fun dumpResources(filePath: String, type: String) {
            LogManager.getLogger().info("[Artifice] Dumping $name $type to $filePath, this may take a while.")
            val dir = File(filePath)
            if (!dir.exists() && !dir.mkdirs()) {
                throw IOException("Can't dump resources to $filePath; couldn't create parent directories")
            }
            require(dir.isDirectory) { "Can't dump resources to $filePath as it's not a directory" }
            if (!dir.canWrite()) {
                throw IOException("Can't dump resources to $filePath; permission denied")
            }
            val packMeta = JsonObjectBuilder()
                .add("pack_format", SharedConstants.getGameVersion().packVersion)
                .add(
                    "description",
                    (if (description != null) description else "In-memory resource pack created with Artifice")!!
                )
                .build()
            val languageMeta = JsonObject()
            if (isClient) {
                addLanguages(languageMeta)
            }
            val builder = JsonObjectBuilder()
            builder.add("pack", packMeta)
            if (languages.size > 0) builder.add("language", languageMeta)
            val mcmeta = JsonResource(builder.build())
            writeResourceFile(File("$filePath/pack.mcmeta"), mcmeta)
            resources.forEach { (id: Identifier, resource: ArtificeResource<*>) ->
                val path = String.format("./%s/%s/%s/%s", filePath, type, id.namespace, id.path)
                writeResourceFile(File(path), resource)
            }
            LogManager.getLogger().info("[Artifice] Finished dumping $name $type.")
        }

        private fun writeResourceFile(output: File, resource: ArtificeResource<*>) {
            try {
                if (output.parentFile.exists() || output.parentFile.mkdirs()) {
                    val writer = BufferedWriter(FileWriter(output))
                    if (resource.data is JsonElement) {
                        val gson = GsonBuilder().setPrettyPrinting().create()
                        writer.write(gson.toJson(resource.data))
                    } else {
                        writer.write(resource.data.toString())
                    }
                    writer.close()
                } else {
                    throw IOException("Failed to dump resource file " + output.path + "; couldn't create parent directories")
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        override fun setVisible() {
            isVisible = true
        }

        override fun setOptional() {
            isOptional = true
            isVisible = true
        }

        override fun shouldOverwrite() {
            isOptional = false
            isVisible = false
            isShouldOverwrite = true
        }

        override fun add(id: Identifier, resource: ArtificeResource<*>) {
            resources[id] = resource
            namespaces.add(id.namespace)
        }

        override fun addItemModel(id: Identifier, f: Builder<ModelBuilder>) {
            this.add("models/item/", id, ".json", f) { ModelBuilder() }
        }

        override fun addBlockModel(id: Identifier, f: Builder<ModelBuilder>) {
            this.add("models/block/", id, ".json", f) { ModelBuilder() }
        }

        override fun addBlockState(id: Identifier, f: Builder<BlockStateBuilder>) {
            this.add("blockstates/", id, ".json", f) { BlockStateBuilder() }
        }

        override fun addTranslations(id: Identifier, f: Builder<TranslationBuilder>) {
            this.add("lang/", id, ".json", f) { TranslationBuilder() }
        }

        override fun addParticle(id: Identifier, f: Builder<ParticleBuilder>) {
            this.add("particles/", id, ".json", f) { ParticleBuilder() }
        }

        override fun addItemAnimation(id: Identifier, f: Builder<AnimationBuilder>) {
            this.add("textures/item/", id, ".mcmeta", f) { AnimationBuilder() }
        }

        override fun addBlockAnimation(id: Identifier, f: Builder<AnimationBuilder>) {
            this.add("textures/block/", id, ".mcmeta", f) { AnimationBuilder() }
        }

        override fun addLanguage(def: LanguageDefinition) {
            languages.add(def)
        }

        @Environment(EnvType.CLIENT)
        override fun addLanguage(code: String, region: String, name: String, rtl: Boolean) {
            this.addLanguage(LanguageDefinition(code, region, name, rtl))
        }

        override fun addAdvancement(id: Identifier, f: Builder<AdvancementBuilder>) {
            this.add("advancements/", id, ".json", f) { AdvancementBuilder() }
        }

        override fun addDimensionType(id: Identifier, f: Builder<DimensionTypeBuilder>) {
            this.add("dimension_type/", id, ".json", f) { DimensionTypeBuilder() }
        }

        override fun addDimension(id: Identifier, f: Builder<DimensionBuilder>) {
            this.add("dimension/", id, ".json", f) { DimensionBuilder() }
        }

        override fun addBiome(id: Identifier, f: Builder<BiomeBuilder>) {
            this.add("worldgen/biome/", id, ".json", f) { BiomeBuilder() }
        }

        override fun addConfiguredCarver(id: Identifier, f: Builder<ConfiguredCarverBuilder>) {
            this.add("worldgen/configured_carver/", id, ".json", f) { ConfiguredCarverBuilder() }
        }

        override fun addConfiguredFeature(id: Identifier, f: Builder<ConfiguredFeatureBuilder>) {
            this.add("worldgen/configured_feature/", id, ".json", f) { ConfiguredFeatureBuilder() }
        }

        override fun addConfiguredSurfaceBuilder(id: Identifier, f: Builder<ConfiguredSurfaceBuilder>) {
            this.add("worldgen/configured_surface_builder/", id, ".json", f) { ConfiguredSurfaceBuilder() }
        }

        override fun addNoiseSettingsBuilder(id: Identifier, f: Builder<NoiseSettingsBuilder>) {
            this.add("worldgen/noise_settings/", id, ".json", f) { NoiseSettingsBuilder() }
        }

        override fun addLootTable(id: Identifier, f: Builder<LootTableBuilder>) {
            this.add("loot_tables/", id, ".json", f) { LootTableBuilder() }
        }

        override fun addItemTag(id: Identifier, f: Builder<TagBuilder>) {
            this.add("tags/items/", id, ".json", f) { TagBuilder() }
        }

        override fun addBlockTag(id: Identifier, f: Builder<TagBuilder>) {
            this.add("tags/blocks/", id, ".json", f) { TagBuilder() }
        }

        override fun addEntityTypeTag(id: Identifier, f: Builder<TagBuilder>) {
            this.add("tags/entity_types/", id, ".json", f) { TagBuilder() }
        }

        override fun addFluidTag(id: Identifier, f: Builder<TagBuilder>) {
            this.add("tags/fluids/", id, ".json", f) { TagBuilder() }
        }

        override fun addFunctionTag(id: Identifier, f: Builder<TagBuilder>) {
            this.add("tags/functions/", id, ".json", f) { TagBuilder() }
        }

        override fun addGenericRecipe(recipeType: Identifier, id: Identifier, f: Builder<GenericRecipeBuilder>) {
            this.add("recipes/", id, ".json", f) { GenericRecipeBuilder(recipeType) }
        }

        override fun addShapedRecipe(id: Identifier, f: Builder<ShapedRecipeBuilder>) {
            this.add("recipes/", id, ".json", f) { ShapedRecipeBuilder() }
        }

        override fun addShapelessRecipe(id: Identifier, f: Builder<ShapelessRecipeBuilder>) {
            this.add("recipes/", id, ".json", f) { ShapelessRecipeBuilder() }
        }

        override fun addStonecuttingRecipe(id: Identifier, f: Builder<StonecuttingRecipeBuilder>) {
            this.add("recipes/", id, ".json", f) { StonecuttingRecipeBuilder() }
        }

        override fun addSmeltingRecipe(id: Identifier, f: Builder<CookingRecipeBuilder>) {
            this.add("recipes/", id, ".json", f,) {
                CookingRecipeBuilder(CookingRecipeBuilder.CookingRecipeType.SMELTING)
            }
        }

        override fun addBlastingRecipe(id: Identifier, f: Builder<CookingRecipeBuilder>) {
            this.add("recipes/", id, ".json", f,) {
                CookingRecipeBuilder(CookingRecipeBuilder.CookingRecipeType.BLASTING)
            }
        }

        override fun addSmokingRecipe(id: Identifier, f: Builder<CookingRecipeBuilder>) {
            this.add("recipes/", id, ".json", f) {
                CookingRecipeBuilder(CookingRecipeBuilder.CookingRecipeType.SMOKING)
            }
        }

        override fun addCampfireRecipe(id: Identifier, f: Builder<CookingRecipeBuilder>) {
            this.add("recipes/", id, ".json", f) {
                CookingRecipeBuilder(CookingRecipeBuilder.CookingRecipeType.CAMPFIRE)
            }
        }

        override fun addSmithingRecipe(id: Identifier, f: Builder<SmithingRecipeBuilder>) {
            this.add("recipes/", id, ".json", f) { SmithingRecipeBuilder() }
        }

        private fun <T : TypedJsonBuilder<out JsonResource<*>>> add(
            path: String,
            id: Identifier,
            ext: String,
            f: Builder<T>,
            ctor: Supplier<T>
        ) {
            this.add(wrapPath(path, id, ext), ctor.get().apply(f).build())
        }
    }

    override fun openRoot(fname: String): InputStream {
        return if (fname == "pack.mcmeta") metadata.toInputStream() else NullInputStream(0)
    }

    @Throws(IOException::class)
    override fun open(type: ResourceType, id: Identifier): InputStream {
        if (!contains(type, id)) throw FileNotFoundException(id.path)
        return resources[id]!!.toInputStream()
    }

    override fun findResources(
        type: ResourceType,
        namespace: String,
        prefix: String,
        maxDepth: Int,
        pathFilter: Predicate<String>
    ): Collection<Identifier> {
        if (type != this.type) return HashSet()
        val keys: MutableSet<Identifier> = HashSet(resources.keys)
        keys.removeIf { id: Identifier -> !id.path.startsWith(prefix) || !pathFilter.test(id.path) }
        return keys
    }

    override fun contains(type: ResourceType, id: Identifier): Boolean {
        return type == this.type && resources.containsKey(id)
    }

    override fun <T> parseMetadata(reader: ResourceMetadataReader<T>): T? {
        return if (metadata.data.has(reader.key)) reader.fromJson(metadata.data.getAsJsonObject(reader.key)) else null
    }

    override fun getNamespaces(type: ResourceType): Set<String> {
        return HashSet(namespaces)
    }

    override fun close() {}
    override fun getName(): String {
        val display = displayName
        return if (display == null) {
            when (type) {
                ResourceType.CLIENT_RESOURCES -> {
                    val aid = ArtificeRegistry.ASSETS.getId(this)
                    aid?.toString() ?: "Generated Resources".also { displayName = it }
                }
                ResourceType.SERVER_DATA -> {
                    val did = ArtificeRegistry.DATA.getId(this)
                    did?.toString() ?: "Generated Data".also { displayName = it }
                }
            }
        } else display
    }

    @Environment(EnvType.CLIENT)
    override fun <T : ResourcePackProfile> toClientResourcePackProfile(factory: ResourcePackProfile.Factory): ClientOnly<ResourcePackProfile> {
        val profile: ResourcePackProfile
        val id = identifier.toString()
        profile = if (!isShouldOverwrite) {
            ArtificeResourcePackContainer(
                isOptional, isVisible, Objects.requireNonNull(
                    ResourcePackProfile.of(
                        id,
                        false, { this }, factory,
                        if (isOptional) ResourcePackProfile.InsertionPosition.TOP else ResourcePackProfile.InsertionPosition.BOTTOM,
                        ARTIFICE_RESOURCE_PACK_SOURCE
                    )
                )!!
            )
        } else {
            ArtificeResourcePackContainer(
                false, false, Objects.requireNonNull(
                    ResourcePackProfile.of(
                        id,
                        true, { this }, factory,
                        ResourcePackProfile.InsertionPosition.TOP,
                        ARTIFICE_RESOURCE_PACK_SOURCE
                    )
                )!!
            )
        }
        return ClientOnly(profile)
    }

    override fun <T : ResourcePackProfile> toServerResourcePackProfile(factory: ResourcePackProfile.Factory): ResourcePackProfile {
        return ResourcePackProfile.of(
            identifier?.toString() ?: "null",
            !isOptional, { this }, factory,
            ResourcePackProfile.InsertionPosition.BOTTOM,
            ARTIFICE_RESOURCE_PACK_SOURCE
        )!!
    }

    companion object {
        val ARTIFICE_RESOURCE_PACK_SOURCE: ResourcePackSource = ResourcePackSource.nameAndSource("pack.source.artifice")
    }

    init {
        registerResources(ArtificeResourcePackBuilder() as T)
        val packMeta = JsonObjectBuilder()
            .add("pack_format", SharedConstants.getGameVersion().packVersion)
            .add(
                "description",
                (if (description != null) description else "In-memory resource pack created with Artifice")!!
            )
            .build()
        val languageMeta = JsonObject()
        if (isClient) {
            addLanguages(languageMeta)
        }
        val builder = JsonObjectBuilder()
        builder.add("pack", packMeta)
        if (languages.size > 0) builder.add("language", languageMeta)
        metadata = JsonResource(builder.build())
    }
}