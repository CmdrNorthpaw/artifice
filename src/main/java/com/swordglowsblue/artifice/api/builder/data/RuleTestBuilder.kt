package com.swordglowsblue.artifice.api.builder.data

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import com.swordglowsblue.artifice.api.util.Processor
import java.util.function.Function

open class RuleTestBuilder : TypedJsonBuilder<JsonObject>(JsonObject(), { j: JsonObject -> j }) {
    fun <R : RuleTestBuilder> predicateType(type: String): R {
        this.root.addProperty("predicate_type", type)
        return this as R
    }

    class AlwaysTrueRuleTestBuilder : RuleTestBuilder() {
        init {
            predicateType<RuleTestBuilder>("minecraft:always_true")
        }
    }

    class BlockRuleTestBuilder : RuleTestBuilder() {
        fun block(blockID: String): BlockRuleTestBuilder {
            this.root.addProperty("block", blockID)
            return this
        }

        init {
            predicateType<RuleTestBuilder>("minecraft:block_match")
        }
    }

    class BlockStateRuleTestBuilder : RuleTestBuilder() {
        fun blockState(processor: StateDataBuilder.() -> Unit): BlockStateRuleTestBuilder {
            with("block_state", { JsonObject() }) { jsonObject: JsonObject ->
                StateDataBuilder().apply(processor).buildTo(jsonObject)
            }
            return this
        }

        init {
            predicateType<RuleTestBuilder>("minecraft:blockstate_match")
        }
    }

    class TagMatchRuleTestBuilder : RuleTestBuilder() {
        fun tag(tagID: String): TagMatchRuleTestBuilder {
            this.root.addProperty("tag", tagID)
            return this
        }

        init {
            predicateType<RuleTestBuilder>("minecraft:tag_match")
        }
    }

    class RandomBlockMatchRuleTestBuilder : RuleTestBuilder() {
        fun block(blockID: String): RandomBlockMatchRuleTestBuilder {
            this.root.addProperty("block", blockID)
            return this
        }

        fun probability(probability: Float): RandomBlockMatchRuleTestBuilder {
            this.root.addProperty("probability", probability)
            return this
        }

        init {
            predicateType<RuleTestBuilder>("minecraft:random_block_match")
        }
    }

    class RandomBlockStateMatchRuleTestBuilder : RuleTestBuilder() {
        fun blockState(processor: Processor<StateDataBuilder>): RandomBlockStateMatchRuleTestBuilder {
            with("block_state", { JsonObject() }) { jsonObject: JsonObject ->
                processor.process(StateDataBuilder()).buildTo(jsonObject)
            }
            return this
        }

        fun probability(probability: Float): RandomBlockStateMatchRuleTestBuilder {
            this.root.addProperty("probability", probability)
            return this
        }

        init {
            predicateType<RuleTestBuilder>("minecraft:random_block_match")
        }
    }
}