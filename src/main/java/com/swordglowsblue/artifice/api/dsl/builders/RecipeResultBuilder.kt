package com.swordglowsblue.artifice.api.dsl.builders

import com.swordglowsblue.artifice.api.builder.data.recipe.RecipeResult
import com.swordglowsblue.artifice.api.util.Builder

fun recipeResult(builder: Builder<RecipeResult.Builder>) = RecipeResult.Builder().apply(builder).build()