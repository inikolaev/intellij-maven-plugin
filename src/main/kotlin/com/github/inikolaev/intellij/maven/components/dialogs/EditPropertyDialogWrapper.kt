package com.github.inikolaev.intellij.maven.components.dialogs

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.OptionGroup
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JTextField


class EditPropertyDialogWrapper(name: String, value: String) : DialogWrapper(true) {
    val name = JTextField(name)
    val value = JTextField(value)

    init {
        init()
        title = "Edit Property"
    }

    override fun createCenterPanel(): JComponent? {
        val dialogPanel = OptionGroup()

        dialogPanel.add(JLabel("Name"), name)
        dialogPanel.add(JLabel("Value"), value)

        return dialogPanel.createPanel()
    }
}