package com.masiad.arescaperoom.helper

import android.content.Context
import com.masiad.arescaperoom.R
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

    val incorrectPin: String by lazy {
        context.getString(R.string.incorrect_pin_message)
    }

    fun resolveInstruction(levelNumber: Int): String {
        return resolveString("$INSTRUCTION_PREFIX$levelNumber")
    }

    fun resolveNodeName(nodeNameId: String): String {
        return resolveString("$NODE_NAME_PREFIX$nodeNameId")
    }

    fun resolveNodeLockedMessage(nodeName: String): String {
        return context.getString(R.string.node_locked_message, nodeName)
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