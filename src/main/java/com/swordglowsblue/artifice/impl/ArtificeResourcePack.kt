package com.swordglowsblue.artifice.impl

import com.swordglowsblue.artifice.registerDataPack
import com.swordglowsblue.artifice.registerAssetPack
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
import com.swordglowsblue.artifice.api.util.Builder
import com.swordglowsblue.artifice.common.ClientResourcePackProfileLike
import com.swordglowsblue.artifice.common.ServerResourcePackProfileLike
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.resource.language.LanguageDefinition
import net.minecraft.resource.ResourcePack
import net.minecraft.resource.ResourceType
import net.minecraft.util.Identifier
import java.io.IOException

/**
 * A resource pack containing Artifice-based resources. May be used
 * as a virtual resource pack with [registerAssetPack] or [registerDataPack].
 */
sealed interface ArtificeResourcePack : ResourcePack, ServerResourcePackProfileLike, ClientResourcePackProfileLike {
    /**
     * @return The [ResourceType] this pack contains.
     */
    val type: ResourceType

    /**
     * @return Whether this pack is set as optional
     */
    val isOptional: Boolean

    /**
     * @return Whether this pack is set as visible in the resource packs menu (only relevant for client-side packs)
     */
    val isVisible: Boolean

    /**
     * Dump all resources from this pack to the given folder path.
     *
     * @param folderPath The path generated resources should go under (relative to Minecraft's installation folder)
     * @throws IOException              if there is an error creating the necessary directories.
     * @throws IllegalArgumentException if the given path points to a file that is not a directory.
     */
    @Deprecated("", replaceWith = ReplaceWith("dumpResources(folderPath, type)"))
    @Throws(IOException::class)
    fun dumpResources(folderPath: String)

    /**
     * The pack will be placed on top of all other packs in order to overwrite them, it will not be optional or visible.
     */
    val isShouldOverwrite: Boolean

    /**
     * Passed to resource construction callbacks to register resources.
     */
    interface ResourcePackBuilder {
        /**
         * Add a resource at the given path.
         *
         * @param id       The resource path.
         * @param resource The resource to add.
         */
        fun add(id: Identifier, resource: ArtificeResource<*>)

        /**
         * Set this pack's display name. Defaults to the pack's ID if not set.
         *
         * @param name The desired name.
         */
        fun setDisplayName(name: String)

        /**
         * Set this pack's description.
         *
         * @param desc The desired description.
         */
        fun setDescription(desc: String)

        /**
         * Dumps the pack files
         *
         * @param filePath The path to dump to
         */
        @Throws(IOException::class)
        fun dumpResources(filePath: String, type: String)

        /**
         * Mark this pack as optional (can be disabled in the resource packs menu). Will automatically mark it as visible.
         */
        fun setOptional()
        fun shouldOverwrite()
    }

    /**
     * Passed to resource construction callbacks to register client-side resources.
     */
    @Environment(EnvType.CLIENT)
    interface ClientResourcePackBuilder : ResourcePackBuilder {
        /**
         * Add an item model for the given item ID.
         *
         * @param id An item ID, which will be converted into the correct path.
         * @param f  A callback which will be passed a [ModelBuilder] to create the item model.
         */
        fun addItemModel(id: Identifier, f: Builder<ModelBuilder>)

        /**
         * Add a block model for the given block ID.
         *
         * @param id A block ID, which will be converted into the correct path.
         * @param f  A callback which will be passed a [ModelBuilder] to create the block model.
         */
        fun addBlockModel(id: Identifier, f: Builder<ModelBuilder>)

        /**
         * Add a blockstate definition for the given block ID.
         *
         * @param id A block ID, which will be converted into the correct path.
         * @param f  A callback which will be passed a [BlockStateBuilder] to create the blockstate definition.
         */
        fun addBlockState(id: Identifier, f: Builder<BlockStateBuilder>)

        /**
         * Add a translation file for the given language.
         *
         * @param id The namespace and language code of the desired language.
         * @param f  A callback which will be passed a [TranslationBuilder] to create the language file.
         */
        fun addTranslations(id: Identifier, f: Builder<TranslationBuilder>)

        /**
         * Add a particle definition for the given particle ID.
         *
         * @param id A particle ID, which will be converted into the correct path.
         * @param f  A callback which will be passed a [ParticleBuilder] to create the particle definition.
         */
        fun addParticle(id: Identifier, f: Builder<ParticleBuilder>)

        /**
         * Add a texture animation for the given item ID.
         *
         * @param id An item ID, which will be converted into the correct path.
         * @param f  A callback which will be passed an [AnimationBuilder] to create the texture animation.
         */
        fun addItemAnimation(id: Identifier, f: Builder<AnimationBuilder>)

        /**
         * Add a texture animation for the given block ID.
         *
         * @param id A block ID, which will be converted into the correct path.
         * @param f  A callback which will be passed an [AnimationBuilder] to create the texture animation.
         */
        fun addBlockAnimation(id: Identifier, f: Builder<AnimationBuilder>)

        /**
         * Add a custom language. Translations must be added separately with [ClientResourcePackBuilder.addTranslations].
         *
         * @param def A [LanguageDefinition] for the desired language.
         */
        fun addLanguage(def: LanguageDefinition)

        /**
         * Add a custom language. Translations must be added separately with [ClientResourcePackBuilder.addTranslations].
         *
         * @param code   The language code for the custom language (i.e. `en_us`). Must be all lowercase alphanum / underscores.
         * @param region The name of the language region (i.e. United States).
         * @param name   The name of the language (i.e. English).
         * @param rtl    Whether the language is written right-to-left instead of left-to-right.
         */
        fun addLanguage(code: String, region: String, name: String, rtl: Boolean)

        /**
         * Mark this pack as visible (will be shown in the resource packs menu).
         */
        fun setVisible()
    }

    /**
     * Passed to resource construction callbacks to register server-side resources.
     */
    interface ServerResourcePackBuilder : ResourcePackBuilder {
        /**
         * Add an advancement with the given ID.
         *
         * @param id The ID of the advancement, which will be converted into the correct path.
         * @param f  A callback which will be passed an [AdvancementBuilder] to create the advancement.
         */
        fun addAdvancement(id: Identifier, f: Builder<AdvancementBuilder>)

        /**
         * Add a Dimension Type with the given ID.
         *
         * @param id The ID of the dimension type, which will be converted into the correct path.
         * @param f A callback which will be passed an [DimensionTypeBuilder] to create the dimension type.
         */
        fun addDimensionType(id: Identifier, f: Builder<DimensionTypeBuilder>)

        /**
         * Add a Dimension with the given ID.
         *
         * @param id The ID of the dimension, which will be converted into the correct path.
         * @param f A callback which will be passed an [DimensionBuilder] to create the dimension.
         */
        fun addDimension(id: Identifier, f: Builder<DimensionBuilder>)

        /**
         * Add a Biome with the given ID.
         *
         * @param id The ID of the biome, which will be converted into the correct path.
         * @param f A callback which will be passed an [BiomeBuilder] to create the biome.
         */
        fun addBiome(id: Identifier, f: Builder<BiomeBuilder>)

        /**
         * Add a Carver with the given ID.
         *
         * @param id The ID of the carver, which will be converted into the correct path.
         * @param f A callback which will be passed an [ConfiguredCarverBuilder] to create the carver.
         */
        fun addConfiguredCarver(id: Identifier, f: Builder<ConfiguredCarverBuilder>)

        /**
         * Add a Feature with the given ID.
         *
         * @param id The ID of the feature, which will be converted into the correct path.
         * @param f A callback which will be passed an [ConfiguredFeatureBuilder] to create the feature.
         */
        fun addConfiguredFeature(id: Identifier, f: Builder<ConfiguredFeatureBuilder>)

        /**
         * Add a ConfiguredSurfaceBuilder with the given ID.
         *
         * @param id The ID of the configured surface builder, which will be converted into the correct path.
         * @param f A callback which will be passed an [ConfiguredSurfaceBuilder]
         * to create the configured surface .
         */
        fun addConfiguredSurfaceBuilder(id: Identifier, f: Builder<ConfiguredSurfaceBuilder>)

        /**
         * Add a NoiseSettingsBuilder with the given ID.
         *
         * @param id The ID of the noise settings builder, which will be converted into the correct path.
         * @param f A callback which will be passed an [NoiseSettingsBuilder]
         * to create the noise settings .
         */
        fun addNoiseSettingsBuilder(id: Identifier, f: Builder<NoiseSettingsBuilder>)

        /**
         * Add a loot table with the given ID.
         *
         * @param id The ID of the loot table, which will be converted into the correct path.
         * @param f  A callback which will be passed a [LootTableBuilder] to create the loot table.
         */
        fun addLootTable(id: Identifier, f: Builder<LootTableBuilder>)

        /**
         * Add an item tag with the given ID.
         *
         * @param id The ID of the tag, which will be converted into the correct path.
         * @param f  A callback which will be passed a [TagBuilder] to create the tag.
         */
        fun addItemTag(id: Identifier, f: Builder<TagBuilder>)

        /**
         * Add a block tag with the given ID.
         *
         * @param id The ID of the tag, which will be converted into the correct path.
         * @param f  A callback which will be passed a [TagBuilder] to create the tag.
         */
        fun addBlockTag(id: Identifier, f: Builder<TagBuilder>)

        /**
         * Add an entity type tag with the given ID.
         *
         * @param id The ID of the tag, which will be converted into the correct path.
         * @param f  A callback which will be passed a [TagBuilder] to create the tag.
         */
        fun addEntityTypeTag(id: Identifier, f: Builder<TagBuilder>)

        /**
         * Add a fluid tag with the given ID.
         *
         * @param id The ID of the tag, which will be converted into the correct path.
         * @param f  A callback which will be passed a [TagBuilder] to create the tag.
         */
        fun addFluidTag(id: Identifier, f: Builder<TagBuilder>)

        /**
         * Add a function tag with the given ID.
         *
         * @param id The ID of the tag, which will be converted into the correct path.
         * @param f  A callback which will be passed a [TagBuilder] to create the tag.
         */
        fun addFunctionTag(id: Identifier, f: Builder<TagBuilder>)

        /**
         * Add a recipe with the given ID.
         *
         * @param id The ID of the recipe, which will be converted into the correct path.
         * @param f  A callback which will be passed a [GenericRecipeBuilder] to create the recipe.
         */
        fun addGenericRecipe(recipeType: Identifier, id: Identifier, f: Builder<GenericRecipeBuilder>)

        /**
         * Add a shaped crafting recipe with the given ID.
         *
         * @param id The ID of the recipe, which will be converted into the correct path.
         * @param f  A callback which will be passed a [ShapedRecipeBuilder] to create the recipe.
         */
        fun addShapedRecipe(id: Identifier, f: Builder<ShapedRecipeBuilder>)

        /**
         * Add a shapeless crafting recipe with the given ID.
         *
         * @param id The ID of the recipe, which will be converted into the correct path.
         * @param f  A callback which will be passed a [ShapelessRecipeBuilder] to create the recipe.
         */
        fun addShapelessRecipe(id: Identifier, f: Builder<ShapelessRecipeBuilder>)

        /**
         * Add a stonecutter recipe with the given ID.
         *
         * @param id The ID of the recipe, which will be converted into the correct path.
         * @param f  A callback which will be passed a [StonecuttingRecipeBuilder] to create the recipe.
         */
        fun addStonecuttingRecipe(id: Identifier, f: Builder<StonecuttingRecipeBuilder>)

        /**
         * Add a smelting recipe with the given ID.
         *
         * @param id The ID of the recipe, which will be converted into the correct path.
         * @param f  A callback which will be passed a [CookingRecipeBuilder] to create the recipe.
         */
        fun addSmeltingRecipe(id: Identifier, f: Builder<CookingRecipeBuilder>)

        /**
         * Add a blast furnace recipe with the given ID.
         *
         * @param id The ID of the recipe, which will be converted into the correct path.
         * @param f  A callback which will be passed a [CookingRecipeBuilder] to create the recipe.
         */
        fun addBlastingRecipe(id: Identifier, f: Builder<CookingRecipeBuilder>)

        /**
         * Add a smoker recipe with the given ID.
         *
         * @param id The ID of the recipe, which will be converted into the correct path.
         * @param f  A callback which will be passed a [CookingRecipeBuilder] to create the recipe.
         */
        fun addSmokingRecipe(id: Identifier, f: Builder<CookingRecipeBuilder>)

        /**
         * Add a campfire recipe with the given ID.
         *
         * @param id The ID of the recipe, which will be converted into the correct path.
         * @param f  A callback which will be passed a [CookingRecipeBuilder] to create the recipe.
         */
        fun addCampfireRecipe(id: Identifier, f: Builder<CookingRecipeBuilder>)

        /**
         * Add a smithing table recipe with the given ID.
         *
         * @param id The ID of the recipe, which will be converted into the correct path.
         * @param f  A callback which will be passed a [CookingRecipeBuilder] to create the recipe.
         */
        fun addSmithingRecipe(id: Identifier, f: Builder<SmithingRecipeBuilder>)
    }

    companion object {
        /**
         * @param register A callback which will be passed a [ClientResourcePackBuilder].
         * @return The created pack.
         */
        @JvmStatic
        @Environment(EnvType.CLIENT)
        fun ofAssets(register: Builder<ClientResourcePackBuilder>): ArtificeResourcePack {
            return ArtificeResourcePackImpl(ResourceType.CLIENT_RESOURCES, null, register)
        }

        /**
         * Create a new server-side [ArtificeResourcePack] and register resources using the given callback.
         *
         * @param register A callback which will be passed a [ServerResourcePackBuilder].
         * @return The created pack.
         */
        @JvmStatic
        fun ofData(register: Builder<ServerResourcePackBuilder>): ArtificeResourcePack {
            return ArtificeResourcePackImpl(ResourceType.SERVER_DATA, null, register)
        }
    }
}