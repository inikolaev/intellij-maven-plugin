package com.github.inikolaev.intellij.maven
import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.FileEditorPolicy
import com.intellij.openapi.fileEditor.FileEditorProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile


class ModelEditorProvider: FileEditorProvider {
    override fun getEditorTypeId(): String {
        return "model-editor-provider"
    }

    override fun accept(project: Project, file: VirtualFile): Boolean {
        return file.name == "pom.xml"
    }

    override fun createEditor(project: Project, file: VirtualFile): FileEditor {
        println("Creating new dependencies editor")
        return ModelEditor(project, file)
    }

    override fun getPolicy(): FileEditorPolicy {
        return FileEditorPolicy.PLACE_BEFORE_DEFAULT_EDITOR
    }
}