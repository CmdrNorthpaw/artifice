package com.swordglowsblue.artifice.test

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.swordglowsblue.artifice.api.builder.data.dimension.ChunkGeneratorTypeBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.BlockStateProviderBuilder.SimpleBlockStateProviderBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.configured.decorator.config.CountExtraDecoratorConfigBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.configured.decorator.config.DecoratedDecoratorConfigBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.config.DecoratedFeatureConfigBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.config.TreeFeatureConfigBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.gen.FeatureSizeBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.gen.FeatureSizeBuilder.TwoLayersFeatureSizeBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.gen.FoliagePlacerBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.gen.FoliagePlacerBuilder.BlobFoliagePlacerBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.gen.TrunkPlacerBuilder.FancyTrunkPlacerBuilder
import com.swordglowsblue.artifice.api.resource.StringResource
import com.swordglowsblue.artifice.impl.ArtificeResourcePack
import com.swordglowsblue.artifice.registerAssetPack
import com.swordglowsblue.artifice.registerDataPack
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.tag.BlockTags
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey
import net.minecraft.world.ChunkRegion
import net.minecraft.world.HeightLimitView
import net.minecraft.world.Heightmap
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.source.BiomeSource
import net.minecraft.world.chunk.Chunk
import net.minecraft.world.gen.GenerationStep
import net.minecraft.world.gen.StructureAccessor
import net.minecraft.world.gen.chunk.ChunkGenerator
import net.minecraft.world.gen.chunk.StructuresConfig
import net.minecraft.world.gen.chunk.VerticalBlockSample
import net.minecraft.world.gen.feature.StructureFeature
import java.io.IOException
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executor
import java.util.function.BiFunction

class ArtificeTestMod : ModInitializer, ClientModInitializer {
    override fun onInitialize() {
        Registry.register(
            Registry.CHUNK_GENERATOR,
            RegistryKey.of(Registry.CHUNK_GENERATOR_KEY, id("test_chunk_generator")).value,
            TestChunkGenerator.CODEC
        )
        registerDataPack(id("optional_test")) {
            setOptional()
            add(
                id("recipes/test_optional.json"), StringResource(
                    """{
  "type": "minecraft:crafting_shaped",
  "group": "wooden_door",
  "pattern": [
    "##",
    "##",
    "   "
  ],
  "key": {
    "#": {
      "item": "minecraft:stone"
    }
  },
  "result": {
    "item": "artifice:test_item",
    "count": 2
  }
}"""
                )
            )
        }
        registerDataPack(id("testmod")) {
            setDisplayName("Artifice Test Data")
            setDescription("Data for the Artifice test mod")
            add(
                id("recipes/test_item.json"), StringResource(
                    """{
  "type": "minecraft:crafting_shaped",
  "group": "wooden_door",
  "pattern": [
    "##",
    "##",
    "##"
  ],
  "key": {
    "#": {
      "item": "minecraft:stone"
    }
  },
  "result": {
    "item": "artifice:test_item",
    "count": 3
  }
}"""
                )
            )
            addDimensionType(testDimension.value) {
                this
                    .natural(false).hasRaids(false).respawnAnchorWorks(true).bedWorks(false).piglinSafe(false)
                    .ambientLight(6.0f).infiniburn(BlockTags.INFINIBURN_OVERWORLD.id)
                    .ultrawarm(false).hasCeiling(false).hasSkylight(false).coordinate_scale(1.0).logicalHeight(832)
                    .height(832).minimumY(-512).effects("minecraft:the_end")
            }
            addDimension(id("test_dimension")) {
                this.dimensionType(testDimension.value).flatGenerator {
                    addLayer { block("minecraft:bedrock").height(2) }
                    addLayer { block("minecraft:stone").height(2) }
                    addLayer { block("minecraft:granite").height(2) }
                    biome("minecraft:plains")
                    structureManager {
                            addStructure(
                                Registry.STRUCTURE_FEATURE.getId(StructureFeature.MINESHAFT).toString()
                            ) { salt(1999999).separation(1).spacing(2) }
                        }
                }
            }
            addDimensionType(testDimensionCustom.value) {
                natural(true)
                hasRaids(false)
                respawnAnchorWorks(false)
                bedWorks(false)
                piglinSafe(false)
                ambientLight(0.0f)
                infiniburn(BlockTags.INFINIBURN_OVERWORLD.id)
                    .ultrawarm(false).hasCeiling(false).hasSkylight(true).coordinate_scale(1.0).logicalHeight(832)
                    .height(832).minimumY(-512)
            }
            addDimension(id("test_dimension_custom")) {
                dimensionType(testDimensionCustom.value) /*.generator(testChunkGeneratorTypeBuilder -> {
                    testChunkGeneratorTypeBuilder.testBool(true).biomeSource(biomeSourceBuilder -> {
                        biomeSourceBuilder.biome(id("test_biome").toString());
                    }, new BiomeSourceBuilder.FixedBiomeSourceBuilder());
                }, new TestChunkGeneratorTypeBuilder())*/
                noiseGenerator {
                    fixedBiomeSource {
                        biome(id("test_biome").toString())
                        seed(Random().nextLong().toInt())
                    }
                    noiseSettings("minecraft:overworld")
                    seed(Random().nextLong().toInt())
                }
            }
            addBiome(id("test_biome")) {
                precipitation(Biome.Precipitation.RAIN)
                surfaceBuilder("minecraft:grass")
                category(Biome.Category.PLAINS)
                depth(0.125f)
                scale(0.05f)
                temperature(0.8f)
                downfall(0.4f)
                effects {
                    waterColor(4159204)
                    waterFogColor(329011)
                    fogColor(12638463)
                    skyColor(4159204)
                }
                addAirCarvers(id("test_carver").toString())
                addFeaturesbyStep(
                    GenerationStep.Feature.LAKES,
                    "minecraft:lake_water",
                    "minecraft:lake_lava"
                )
                    .addFeaturesbyStep(
                        GenerationStep.Feature.VEGETAL_DECORATION,
                        id("test_decorated_feature").toString()
                    )
            }
            addConfiguredCarver(id("test_carver")) {
                probability(0.9f).name(Identifier("cave").toString())
            }
            addConfiguredSurfaceBuilder(id("test_surface_builder")) {
                surfaceBuilderID("minecraft:default")
                topMaterial { name("minecraft:gold_block") }
                underMaterial { name("minecraft:gold_ore") }
                underwaterMaterial { name("minecraft:bedrock") }
            }

            // Tested, it works now. Wasn't in 20w28a.
            addConfiguredFeature(id("test_featureee")) {
                featureID("minecraft:tree")
                featureConfig({ treeFeatureConfigBuilder ->
                        treeFeatureConfigBuilder
                            .ignoreVines(true)
                            .maxWaterDepth(5)
                            .trunkProvider(SimpleBlockStateProviderBuilder()) {
                                state {
                                    it.name(
                                        "minecraft:oak_log"
                                    ).setProperty("axis", "y")
                                }
                            }
                            .leavesProvider(SimpleBlockStateProviderBuilder()) {
                                state { blockStateDataBuilder ->
                                    blockStateDataBuilder.name("minecraft:spruce_leaves")
                                        .setProperty("persistent", "false")
                                        .setProperty("distance", "7")
                                }
                            }
                            .foliagePlacer(FoliagePlacerBuilder.BushFoliagePlacerBuilder()){
                                height(2)
                                offset(1)
                                radius(2)
                            }
                            .trunkPlacer(FancyTrunkPlacerBuilder()) {
                                baseHeight(12).heightRandA(3).heightRandB(4)
                            }
                            .minimumSize(TwoLayersFeatureSizeBuilder()) {
                                limit(10)
                                lowerSize(1)
                                upperSize(9)
                            }
                            .heightmap(Heightmap.Type.OCEAN_FLOOR)
                    }, TreeFeatureConfigBuilder())
            }

            // Should be working but Minecraft coders did something wrong and the default feature is being return when it shouldn't resulting in a crash.
            addConfiguredFeature(id("test_decorated_feature")) {
                featureID("minecraft:decorated")
                featureConfig({
                        it.feature {
                            featureID("minecraft:decorated")
                            featureConfig({ decoratedConfigBuilder ->
                                    decoratedConfigBuilder.feature(id("test_featureee").toString())
                                        .decorator {
                                            name("minecraft:decorated")
                                            config(DecoratedDecoratorConfigBuilder()) {
                                                    innerDecorator {
                                                        defaultConfig()
                                                        name("minecraft:heightmap")
                                                    }
                                                outerDecorator {
                                                    defaultConfig()
                                                    name("minecraft:square")
                                                        }
                                                }
                                        }
                                }, DecoratedFeatureConfigBuilder())
                        }.decorator {
                            name("minecraft:count_extra")
                                .config( CountExtraDecoratorConfigBuilder()) {
                                    count(10)
                                    extraChance(0.2f)
                                    extraCount(2)
                                }
                        }
                    }, DecoratedFeatureConfigBuilder())
            }
            try {
                dumpResources("testing_data", "data")
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    @Environment(EnvType.CLIENT)
    override fun onInitializeClient() {
        registerAssetPack("artifice:testmod") {
            setDisplayName("Artifice Test Resources")
            setDescription("Resources for the Artifice test mod")

            addItemModel(id("test_item")) {
                parent(Identifier("item/generated"))
                texture("layer0", Identifier("block/sand"))
                texture("layer1", Identifier("block/dead_bush"))
            }

            addItemModel(id("test_block")) { parent(id("block/test_block")) }

            addBlockState(id("test_block")) {
                weightedVariant("") {
                    model(id("block/test_block"))
                    weight(3)
                }

                weightedVariant("") {
                    model(Identifier("block/coarse_dirt"))
                }
            }
            addBlockModel(id("test_block")) {
                parent(Identifier("block/cube_all"))
                texture("all", Identifier("item/diamond_sword"))
            }

            addTranslations(id("en_us")) {
                entry("item.artifice.test_item", "Artifice Test Item")
                entry("block.artifice.test_block", "Artifice Test Block")
            }

            addLanguage("ar_tm", "Artifice", "Test Mod", false)

            addTranslations(id("ar_tm")) {
                entry("item.artifice.test_item", "Artifice Test Item in custom lang")
                entry("block.artifice.test_block", "Artifice Test Block in custom lang")
            }

            try {
                dumpResources("testing_assets", "assets")
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    class TestChunkGenerator(biomeSource: BiomeSource, private val testBool: Boolean) :
        ChunkGenerator(biomeSource, StructuresConfig(false)) {
        override fun getCodec(): Codec<out ChunkGenerator> {
            return CODEC
        }

        override fun withSeed(seed: Long): ChunkGenerator {
            return this
        }

        override fun buildSurface(region: ChunkRegion, chunk: Chunk) {}
        override fun populateNoise(
            executor: Executor,
            accessor: StructureAccessor,
            chunk: Chunk
        ): CompletableFuture<Chunk> {
            return CompletableFuture<Chunk>()
        }

        override fun getHeight(x: Int, z: Int, heightmap: Heightmap.Type, world: HeightLimitView): Int {
            return 0
        }

        override fun getColumnSample(x: Int, z: Int, world: HeightLimitView): VerticalBlockSample {
            return VerticalBlockSample(10, arrayOfNulls(0))
        }

        companion object {
            val CODEC = RecordCodecBuilder.create { instance: RecordCodecBuilder.Instance<TestChunkGenerator> ->
                instance.group(
                    BiomeSource.CODEC.fieldOf("biome_source")
                        .forGetter { generator: TestChunkGenerator -> generator.biomeSource },
                    Codec.BOOL.fieldOf("test_bool").forGetter { generator: TestChunkGenerator -> generator.testBool }
                )
                    .apply(
                        instance, instance.stable(
                            BiFunction { biomeSource: BiomeSource, testBool: Boolean ->
                                TestChunkGenerator(
                                    biomeSource,
                                    testBool
                                )
                            })
                    )
            }
        }
    }

    class TestChunkGeneratorTypeBuilder : ChunkGeneratorTypeBuilder(id("test_chunk_generate")) {
        fun testBool(customBool: Boolean): TestChunkGeneratorTypeBuilder {
            this.root.addProperty("test_bool", customBool)
            return this
        }
    }

    companion object {
        private fun id(name: String): Identifier {
            return Identifier("artifice", name)
        }

        private val itemSettings = Item.Settings().group(ItemGroup.MISC)
        private val testItem = Registry.register(Registry.ITEM, id("test_item"), Item(itemSettings))
        private val testBlock =
            Registry.register(Registry.BLOCK, id("test_block"), Block(FabricBlockSettings.copy(Blocks.STONE)))
        private val testBlockItem: Item =
            Registry.register(Registry.ITEM, id("test_block"), BlockItem(testBlock, itemSettings))
        private val testDimension = RegistryKey.of(Registry.DIMENSION_TYPE_KEY, id("test_dimension_type_vanilla"))
        private val testDimensionCustom = RegistryKey.of(Registry.DIMENSION_TYPE_KEY, id("test_dimension_type_custom"))
    }
}