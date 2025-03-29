package com.zero_delusions.cobblemon_mystery_dungeon_npc.entity.pokenpc.dialogue

import java.util.UUID

data class DialogueNode(
    val id: String = UUID.randomUUID().toString(),
    val text: String,
    val species: String, // Can be either species name or Pokédex number
    val emotion: PortraitEmotion,
    val shiny: Boolean? = null,
    val mega: Boolean? = null,
    val answers: List<DialogueAnswer> = emptyList(),
    val onEnterEvent: () -> Unit = {}
)

data class DialogueAnswer(
    val text: String,
    val type: AnswerType,
    val nextNodeId: String? = null,
    val onSelectEvent: () -> Unit = {}
)

enum class AnswerType { LEAF, BRANCH, ROOT }
enum class PortraitEmotion(value: String) {
    ANGRY("Angry"),
    CRYING("Crying"),
    DETERMINED("Determined"),
    DIZZY("Dizzy"),
    HAPPY("Happy"),
    INSPIRED("Inspired"),
    JOYOUS("Joyous"),
    NORMAL("Normal"),
    PAIN("Pain"),
    SAD("Sad"),
    SHOUTING("Shouting"),
    SIGH("Sigh"),
    STUNNED("Stunned"),
    SURPRISED("Surprised"),
    TEARY("Teary-Eyed"),
    WORRIED("Worried")
}

class DialogueManager(
    private val nodes: Map<String, DialogueNode>,
    startNodeId: String
) {
    private val history = ArrayDeque<DialogueNode>()
    private val lockedNodes = mutableSetOf<String>()
    private var currentNode: DialogueNode = nodes[startNodeId]!!
        private set(value) {
            field = value
            value.onEnterEvent()
        }

    init {
        currentNode.onEnterEvent()
    }

    fun selectAnswer(answer: DialogueAnswer) {
        answer.onSelectEvent()

        when (answer.type) {
            AnswerType.LEAF -> history.addLast(currentNode)
            AnswerType.BRANCH -> lockedNodes.add(currentNode.id)
            AnswerType.ROOT -> {
                history.clear()
                lockedNodes.clear()
            }
        }

        answer.nextNodeId?.let { nextId ->
            nodes[nextId]?.let { nextNode ->
                currentNode = nextNode
            } ?: endConversation()
        } ?: endConversation()
    }

    fun goBack() {
        while (history.isNotEmpty()) {
            val previous = history.removeLast()
            if (!lockedNodes.contains(previous.id)) {
                currentNode = previous
                return
            }
        }
        endConversation()
    }

    fun getAvailableAnswers(): List<DialogueAnswer> {
        return currentNode.answers.filter { answer ->
            answer.nextNodeId?.let { !lockedNodes.contains(it) } ?: true
        }
    }

    private fun endConversation() {
        // TODO: Handle exit logic
    }
}


