package com.masiad.arescaperoom.helper

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StringHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        private const val INSTRUCTION_PREFIX = "instruction_level_"

        private const val NODE_NAME_PREFIX = "node_name_"
    }

    fun resolveInstruction(levelNumber: Int): String {
        return resolveString("$INSTRUCTION_PREFIX$levelNumber")
    }

    fun resolveNodeName(nodeNameId: String): String {
        return resolveString("$NODE_NAME_PREFIX$nodeNameId")
    }

    private fun resolveString(identifierName: String): String {
        val resId = context.resources.getIdentifier(
            identifierName,
            "string",
            context.packageName
        )
        return context.getString(resId)
    }
}