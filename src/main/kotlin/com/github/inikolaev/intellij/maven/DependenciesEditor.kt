package com.github.inikolaev.intellij.maven
import com.intellij.icons.AllIcons
import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.OptionGroup
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBList
import org.apache.maven.model.Dependency
import org.apache.maven.model.Exclusion
import java.awt.Dimension
import java.awt.GridLayout
import javax.swing.BoxLayout
import javax.swing.JCheckBox
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField
import javax.swing.ListSelectionModel
import javax.swing.SwingConstants

class DependenciesEditor(name: String) : AbstractEditor(name) {
    private val dependencyList = JBList(listOf<Dependency>()).apply {
        selectionMode = ListSelectionModel.SINGLE_SELECTION

        installCellRenderer<Dependency> { value ->
            JBLabel("${value.groupId} : ${value.artifactId} : ${value.version ?: "LATEST"}", AllIcons.FileTypes.Archive, SwingConstants.LEFT).apply {
                preferredSize = Dimension(preferredSize).apply {
                    height += 3
                }
            }
        }
    }

    private val groupIdLabel = JLabel("Group Id:")
    val groupId = JTextField()

    private val artifactIdLabel = JLabel("Artifact Id:*")
    val artifactId = JTextField()

    private val versionLabel = JLabel("Version:")
    val version = JTextField()

    private val classifierLabel = JLabel("Classifier:")
    val classifier = JTextField()

    private val typeLabel = JLabel("Type:")
    val type = JTextField()

    private val scopeLabel = JLabel("Scope:")
    val scope = ComboBox<String>(arrayOf("compile", "import", "provided", "runtime", "system", "test"))

    private val systemPathLabel = JLabel("System Path:")
    val systemPath = JTextField()

    val optional = JCheckBox("Optional")

    val dependenciesListPanel = OptionGroup("Dependencies").apply {
        add(dependencyList)
    }.createPanel()

    val dependencyDetailsPanel = OptionGroup("Dependency Details").apply {
        add(groupIdLabel, groupId)
        add(artifactIdLabel, artifactId)
        add(versionLabel, version)
        add(classifierLabel, classifier)
        add(typeLabel, type)
        add(scopeLabel, scope)
        add(systemPathLabel, systemPath)
        add(JLabel(), optional)
    }.createPanel()

    private val exclusionList = JBList(listOf<Exclusion>()).apply {
        selectionMode = ListSelectionModel.SINGLE_SELECTION

        installCellRenderer<Exclusion> { value ->
            JBLabel("${value.groupId} : ${value.artifactId}", AllIcons.FileTypes.Archive, SwingConstants.LEFT).apply {
                preferredSize = Dimension(preferredSize).apply {
                    height += 3
                }
            }
        }
    }

    private val dependencyManagementList = JBList(listOf<Dependency>()).apply {
        selectionMode = ListSelectionModel.SINGLE_SELECTION

        installCellRenderer<Dependency> { value ->
            JBLabel("${value.groupId} : ${value.artifactId} : ${value.version ?: "LATEST"}", AllIcons.FileTypes.Archive, SwingConstants.LEFT).apply {
                preferredSize = Dimension(preferredSize).apply {
                    height += 3
                }
            }
        }
    }

    val exclusionsPanel = OptionGroup("Exclusions").apply {
        add(exclusionList)
    }.createPanel()

    val dependencyManagementPanel = OptionGroup("Dependency Management").apply {
        add(dependencyManagementList)
    }.createPanel()

    private val exclusionDetailsGroupIdLabel = JLabel("Group Id:")
    val exclusionDetailsGroupId = JTextField()

    private val exclusionDetailsArtifactIdLabel = JLabel("Artifact Id:*")
    val exclusionDetailsArtifactId = JTextField()

    val exclusionDetailsPanel = OptionGroup("Exclusion Details").apply {
        add(exclusionDetailsArtifactIdLabel, exclusionDetailsArtifactId)
        add(exclusionDetailsGroupIdLabel, exclusionDetailsGroupId)
    }.createPanel()

    private val leftPanel = JPanel().apply {
        layout = BoxLayout(this, BoxLayout.Y_AXIS)
        add(dependenciesListPanel)
        add(dependencyManagementPanel)
    }

    private val rightPanel = JPanel().apply {
        layout = BoxLayout(this, BoxLayout.Y_AXIS)
        add(dependencyDetailsPanel)
        add(exclusionsPanel)
        add(exclusionDetailsPanel)
    }

    private val dependenciesPanel = JPanel().apply {
        layout = GridLayout(0, 2, 8, 0)
        add(leftPanel)
        add(rightPanel)
    }

    init {
        dependencyList.addListSelectionListener {
            dependencyList.selectedValue?.let { dependency ->
                artifactId.text = dependency.artifactId
                groupId.text = dependency.groupId
                version.text = dependency.version
                classifier.text = dependency.classifier
                type.text = dependency.type
                scope.selectedItem = dependency.scope ?: "compile"
                systemPath.text = dependency.systemPath
                optional.isSelected = dependency.isOptional

                exclusionList.setListData(dependency.exclusions.toTypedArray())
                exclusionDetailsArtifactId.text = ""
                exclusionDetailsGroupId.text = ""
            }
        }

        exclusionList.addListSelectionListener {
            exclusionList.selectedValue?.let { exclusion ->
                exclusionDetailsArtifactId.text = exclusion.artifactId
                exclusionDetailsGroupId.text = exclusion.groupId
            }
        }
    }

    override fun getComponent(): JPanel {
        return dependenciesPanel
    }

    override fun onModelUpdated() {
        dependencyList.setListData(emptyArray())
        artifactId.text = ""
        groupId.text = ""
        version.text = ""
        classifier.text = ""
        type.text = ""
        scope.selectedItem = null
        systemPath.text = ""
        optional.isSelected = false

        exclusionList.setListData(emptyArray())
        exclusionDetailsArtifactId.text = ""
        exclusionDetailsGroupId.text = ""

        model.dependencies?.let {
            dependencyList.setListData(it.toTypedArray())
        }

        dependencyManagementList.setListData(emptyArray())
        model.dependencyManagement?.dependencies?.let {
            dependencyManagementList.setListData(it.toTypedArray())
        }
    }
}
