package com.github.inikolaev.intellij.maven
import com.github.inikolaev.intellij.maven.components.editors.DependenciesEditor
import com.github.inikolaev.intellij.maven.components.editors.OverviewEditor
import com.intellij.codeHighlighting.BackgroundEditorHighlighter
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.FileEditorLocation
import com.intellij.openapi.fileEditor.FileEditorState
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Key
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.components.JBTabbedPane
import org.apache.maven.model.Model
import org.apache.maven.model.io.xpp3.MavenXpp3Reader
import org.apache.maven.model.io.xpp3.MavenXpp3Writer
import org.codehaus.plexus.util.xml.pull.XmlPullParserException
import org.jetbrains.annotations.NotNull
import java.beans.PropertyChangeListener
import java.io.StringReader
import java.io.StringWriter
import javax.swing.BorderFactory
import javax.swing.JComponent

class ModelEditor(project: Project, file: VirtualFile) : FileEditor {
    private var model: Model = Model()
    private val writer = MavenXpp3Writer()
    private val reader = MavenXpp3Reader()
    private val document = FileDocumentManager.getInstance().getDocument(file)
    private val file = file;

    private val editors = listOf(
        OverviewEditor("Overview"),
        DependenciesEditor("Dependencies")
    )

    private val modelPanel = JBTabbedPane().apply {
        border = BorderFactory.createEmptyBorder()

        editors.forEach {
            addTab(it.name, JBScrollPane(it.getComponent()).apply {
                border = BorderFactory.createEmptyBorder()
            })
        }
    }

    init {
        document?.addDocumentListener(object: DocumentListener {
            override fun documentChanged(event: DocumentEvent) {
                updateModel(event.document)
            }
        })

        editors.forEach {
            it.addUpdateListener(::updateDocument)
        }

        document?.let {
            updateModel(it)
        }
    }

    private fun updateModel(document: Document) {
        try {
            model = reader.read(StringReader(document.text))

            editors.forEach {
                it.update(model)
            }
        } catch (e: XmlPullParserException) {
            // do not update model if POM is invalid
        }
    }

    private fun updateDocument() {
        val stringWriter = StringWriter()
        writer.write(stringWriter, model)

        ApplicationManager.getApplication().runWriteAction {
            document?.setText(stringWriter.toString())
        }
    }

    override fun isModified(): Boolean {
        return false
    }

    override fun addPropertyChangeListener(p0: PropertyChangeListener) {

    }

    override fun getName(): String {
        return "Model"
    }

    override fun setState(fileEditorState: FileEditorState) {

    }

    override fun getComponent(): JComponent {
        return modelPanel
    }

    override fun getPreferredFocusedComponent(): JComponent? {
        return null
    }

    override fun <T : Any?> getUserData(p0: Key<T>): T? {
        return null
    }

    override fun selectNotify() {

    }

    override fun <T : Any?> putUserData(p0: Key<T>, p1: T?) {

    }

    override fun getCurrentLocation(): FileEditorLocation? {
        return null
    }

    override fun deselectNotify() {

    }

    override fun getBackgroundHighlighter(): BackgroundEditorHighlighter? {
        return null
    }

    override fun isValid(): Boolean {
        return true
    }

    override fun removePropertyChangeListener(p0: PropertyChangeListener) {

    }

    override fun dispose() {

    }

    @NotNull
    override fun getFile(): VirtualFile? {
        return file;
    }
}
