package com.github.inikolaev.intellij.maven
import com.intellij.ui.OptionGroup
import javax.swing.JLabel
import javax.swing.JTextField

class ParentPanel: OptionGroup("Parent") {
    private val groupIdLabel = JLabel("Group Id:")
    val groupId = JTextField()

    private val artifactIdLabel = JLabel("Artifact Id:")
    val artifactId = JTextField()

    private val versionLabel = JLabel("Version:*")
    val version = JTextField()

    private val relativePathLabel = JLabel("Relative Path:")
    val relativePath = JTextField()

    init {
        add(groupIdLabel, groupId)
        add(artifactIdLabel, artifactId)
        add(versionLabel, version)
        add(relativePathLabel, relativePath)
    }
}