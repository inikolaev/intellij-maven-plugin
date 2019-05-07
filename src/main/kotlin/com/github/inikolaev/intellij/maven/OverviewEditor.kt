package com.github.inikolaev.intellij.maven

import org.apache.maven.model.Parent
import java.awt.GridLayout
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import javax.swing.BoxLayout
import javax.swing.JPanel
import javax.swing.JTextField

fun JTextField.addChangeListener(listener: () -> Unit) {
    addKeyListener(object: KeyAdapter() {
        override fun keyReleased(e: KeyEvent?) {
            listener()
        }
    })
}

class OverviewEditor(name: String) : AbstractEditor(name) {
    private val artifactPanel = ArtifactPanel()
    private val parentPanel = ParentPanel()
    private val propertiesPanel = PropertiesPanel()
    private val projectPanel = ProjectPanel()

    private val leftPanel = JPanel().apply {
        layout = BoxLayout(this, BoxLayout.Y_AXIS)
        add(artifactPanel.createPanel())
        add(parentPanel.createPanel())
        add(propertiesPanel.createPanel())
    }

    private val rightPanel = JPanel().apply {
        layout = BoxLayout(this, BoxLayout.Y_AXIS)
        add(projectPanel.createPanel())
    }

    private val overviewPanel = JPanel().apply {
        layout = GridLayout(0, 2, 8, 0)
        add(leftPanel)
        add(rightPanel)
    }

    override fun getComponent(): JPanel {
        return overviewPanel
    }

    private fun ensureNotEmpty(text: String?): String? = if (text.isNullOrEmpty()) {
        null
    } else {
        text
    }

    private fun artifactUpdated() {
        with(artifactPanel) {
            model.artifactId = ensureNotEmpty(artifactId.text)
            model.groupId = ensureNotEmpty(groupId.text)
            model.version = ensureNotEmpty(version.text)
            model.packaging = ensureNotEmpty(packaging.selectedItem as String?)

            notifyListeners()
        }
    }

    private fun parentUpdated() {
        with(parentPanel) {
            val isEmptyParent = groupId.text.isNullOrEmpty()
                && artifactId.text.isNullOrEmpty()
                && version.text.isNullOrEmpty()
                && relativePath.text.isNullOrEmpty()

            model.parent = if (isEmptyParent) {
                null
            } else {
                Parent().also {
                    it.groupId = ensureNotEmpty(groupId.text)
                    it.artifactId = ensureNotEmpty(artifactId.text)
                    it.version = ensureNotEmpty(version.text)
                    it.relativePath = ensureNotEmpty(relativePath.text)
                }
            }

            notifyListeners()
        }
    }

    init {
        with(artifactPanel) {
            groupId.addChangeListener(::artifactUpdated)
            artifactId.addChangeListener(::artifactUpdated)
            version.addChangeListener(::artifactUpdated)
//            packaging.com.github.inikolaev.intellij.maven.addChangeListener {
//                model.groupId = packaging.text
//                updateDocument()
//            }
        }

        with(parentPanel) {
            groupId.addChangeListener(::parentUpdated)
            artifactId.addChangeListener(::parentUpdated)
            version.addChangeListener(::parentUpdated)
            relativePath.addChangeListener(::parentUpdated)
        }
    }

    override fun onModelUpdated() {
        with(artifactPanel) {
            groupId.text = model.groupId
            artifactId.text = model.artifactId
            version.text = model.version
            packaging.selectedItem = model.packaging
        }

        with(parentPanel) {
            groupId.text = model.parent?.groupId
            artifactId.text = model.parent?.artifactId
            version.text = model.parent?.version
            relativePath.text = model.parent?.relativePath
        }

        with(propertiesPanel) {
            propertyList.setListData(model.properties.entries.map { it.key.toString() to it.value.toString() }.toTypedArray())
        }
    }
}
