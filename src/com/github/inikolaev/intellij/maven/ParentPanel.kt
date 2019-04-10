package com.github.inikolaev.intellij.maven
import java.awt.GridLayout
import javax.swing.BorderFactory
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField

class ParentPanel: JPanel() {
    private val groupIdLabel = JLabel("Group Id:")
    val groupId = JTextField()

    private val artifactIdLabel = JLabel("Artifact Id:")
    val artifactId = JTextField()

    private val versionLabel = JLabel("Version:*")
    val version = JTextField()

    private val relativePathLabel = JLabel("Relative Path:")
    val relativePath = JTextField()

    init {
        border = BorderFactory.createTitledBorder("Parent")
        layout = GridLayout(0, 2)

        add(groupIdLabel)
        add(groupId)

        add(artifactIdLabel)
        add(artifactId)

        add(versionLabel)
        add(version)

        add(relativePathLabel)
        add(relativePath)
    }
}