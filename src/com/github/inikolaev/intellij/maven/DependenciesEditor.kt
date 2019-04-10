package com.github.inikolaev.intellij.maven
import com.intellij.ui.components.JBList
import org.apache.maven.model.Dependency
import java.awt.GridLayout
import javax.swing.BorderFactory
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.ListCellRenderer

class DependenciesEditor(name: String) : AbstractEditor(name) {
    private val dependencyList = JBList(listOf<Dependency>()).apply {
        border = BorderFactory.createTitledBorder("Dependencies")
        cellRenderer = ListCellRenderer<Dependency> { _, value, _, _, _ ->
            JLabel("${value.groupId} : ${value.artifactId} : ${value.version ?: "LATEST"}")
        }
    }

    private val dependenciesPanel = JPanel(GridLayout(0, 1)).apply {
        add(dependencyList)
    }

    override fun getComponent(): JPanel {
        return dependenciesPanel
    }

    override fun onModelUpdated() {
        dependencyList.setListData(model.dependencies.toTypedArray())
    }
}
