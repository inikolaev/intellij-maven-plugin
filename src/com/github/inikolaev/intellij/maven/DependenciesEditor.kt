package com.github.inikolaev.intellij.maven
import com.intellij.ui.OptionGroup
import com.intellij.ui.components.JBList
import org.apache.maven.model.Dependency
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.ListCellRenderer

class DependenciesEditor(name: String) : AbstractEditor(name) {
    private val dependencyList = JBList(listOf<Dependency>()).apply {
        cellRenderer = ListCellRenderer<Dependency> { _, value, _, _, _ ->
            JLabel("${value.groupId} : ${value.artifactId} : ${value.version ?: "LATEST"}")
        }
    }

    val dependenciesPanel = OptionGroup("Dependencies").apply {
        add(dependencyList)
    }.createPanel()

    override fun getComponent(): JPanel {
        return dependenciesPanel
    }

    override fun onModelUpdated() {
        dependencyList.setListData(model.dependencies.toTypedArray())
    }
}
