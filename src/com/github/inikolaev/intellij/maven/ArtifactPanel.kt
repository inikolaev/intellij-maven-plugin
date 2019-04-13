package com.github.inikolaev.intellij.maven
import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.OptionGroup
import javax.swing.JLabel
import javax.swing.JTextField

class ArtifactPanel: OptionGroup("Artifact") {
    private val groupIdLabel = JLabel("Group Id:")
    val groupId = JTextField()

    private val artifactIdLabel = JLabel("Artifact Id:*")
    val artifactId = JTextField()

    private val versionLabel = JLabel("Version:")
    val version = JTextField()

    private val packagingLabel = JLabel("Packaging:")
    val packaging = ComboBox<String>(arrayOf("pom", "jar", "war", "ear"))

    init {
        add(groupIdLabel, groupId)
        add(artifactIdLabel, artifactId)
        add(versionLabel, version)
        add(packagingLabel, packaging)
    }
}