package com.github.inikolaev.intellij.maven.components.panels
import com.intellij.ui.OptionGroup
import javax.swing.JLabel
import javax.swing.JTextField

class ProjectPanel: OptionGroup("Project") {
    private val nameLabel = JLabel("Name:")
    val name = JTextField()

    private val urlLabel = JLabel("URL:")
    val url = JTextField()

    private val descriptionLabel = JLabel("Description:")
    val description = JTextField()

    private val inceptionLabel = JLabel("Inception:")
    val inception = JTextField()

    init {
        add(nameLabel, name)
        add(urlLabel, url)
        add(descriptionLabel, description)
        add(inceptionLabel, inception)
    }
}