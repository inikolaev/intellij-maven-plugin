package com.github.inikolaev.intellij.maven.components.editors

import java.awt.Component
import java.awt.GridLayout
import javax.swing.BoxLayout
import javax.swing.JPanel

abstract class TwoColumnEditor(name: String) : AbstractEditor(name) {
    private val leftPanel = JPanel().apply {
        layout = BoxLayout(this, BoxLayout.Y_AXIS)
    }

    private val rightPanel = JPanel().apply {
        layout = BoxLayout(this, BoxLayout.Y_AXIS)
    }

    private val panel = JPanel().apply {
        layout = GridLayout(0, 2, 8, 0)
        add(leftPanel)
        add(rightPanel)
    }

    override fun getComponent(): JPanel {
        return panel
    }

    fun addLeft(component: Component) {
        leftPanel.add(component)
    }

    fun addRight(component: Component) {
        rightPanel.add(component)
    }
}
