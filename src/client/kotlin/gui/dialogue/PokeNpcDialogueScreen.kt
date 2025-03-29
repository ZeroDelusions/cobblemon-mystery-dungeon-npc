package gui.dialogue

import net.minecraft.client.gui.components.Renderable
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component

class PokeNpcDialogueScreen: Screen(Component.literal("PokeNpc Dialogue")), Renderable {
    val scaledWidth
        get() = this.minecraft!!.window.guiScaledWidth
    val scaledHeight
        get() = this.minecraft!!.window.guiScaledHeight

    companion object {

    }

    override fun init() {
        super.init()
    }
}