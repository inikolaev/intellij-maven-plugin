package com.github.inikolaev.intellij.maven
import com.intellij.openapi.ui.ComboBox
import java.awt.GridLayout
import javax.swing.BorderFactory
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField

class ArtifactPanel: JPanel() {
    private val groupIdLabel = JLabel("Group Id:")
    val groupId = JTextField()

    private val artifactIdLabel = JLabel("Artifact Id:*")
    val artifactId = JTextField()

    private val versionLabel = JLabel("Version:")
    val version = JTextField()

    private val packagingLabel = JLabel("Packaging:")
    val packaging = ComboBox<String>(arrayOf("pom", "jar", "war", "ear"))

    init {
        border = BorderFactory.createTitledBorder("Artifact")
        layout = GridLayout(0, 2)

        add(groupIdLabel)
        add(groupId)

        add(artifactIdLabel)
        add(artifactId)

        add(versionLabel)
        add(version)

        add(packagingLabel)
        add(packaging)
    }
}