package com.github.inikolaev.intellij.maven
import com.intellij.icons.AllIcons
import com.intellij.ui.OptionGroup
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBList
import java.awt.Dimension
import javax.swing.ListSelectionModel
import javax.swing.SwingConstants

class PropertiesPanel: OptionGroup("Properties") {
    val propertyList = JBList(listOf<Pair<String, String>>()).apply {
        selectionMode = ListSelectionModel.SINGLE_SELECTION

        installCellRenderer<Pair<String, String>> { value ->
            JBLabel("${value.first} : ${value.second}", AllIcons.Nodes.Property, SwingConstants.LEFT).apply {
                preferredSize = Dimension(preferredSize).apply {
                    height += 3
                }
            }
        }
    }

    init {
        add(propertyList)
    }
}