package com.swordglowsblue.artifice.api.util

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.dsl.Tag
import net.minecraft.util.Identifier

fun JsonObject.addProperty(key: String, tag: Tag?) = this.addProperty(key, tag?.id.toString())

fun tagOrNull(from: Identifier?): Tag? = if (from == null) null else Tag(from)
