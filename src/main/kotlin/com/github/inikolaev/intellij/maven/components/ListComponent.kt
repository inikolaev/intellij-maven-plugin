package com.github.inikolaev.intellij.maven.components

import com.intellij.ui.components.JBList
import java.awt.Dimension
import javax.swing.JComponent
import javax.swing.ListSelectionModel

class ListComponent<T>(cellRenderer: (T) -> JComponent): JBList<T>() {
    init {
        selectionMode = ListSelectionModel.SINGLE_SELECTION

        installCellRenderer<T> { value ->
            cellRenderer(value).apply {
                preferredSize = Dimension(preferredSize).apply {
                    height += 3
                }
            }
        }
    }
}