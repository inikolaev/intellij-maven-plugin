package com.github.inikolaev.intellij.maven.components.panels
import com.github.inikolaev.intellij.maven.components.ListComponent
import com.intellij.icons.AllIcons
import com.intellij.ui.OptionGroup
import com.intellij.ui.components.JBLabel
import javax.swing.SwingConstants

class PropertiesPanel: OptionGroup("Properties") {
    val propertyList = ListComponent<Pair<String, String>> { value ->
        JBLabel("${value.first} : ${value.second}", AllIcons.Nodes.Property, SwingConstants.LEFT)
    }

    init {
        add(propertyList)
    }
}