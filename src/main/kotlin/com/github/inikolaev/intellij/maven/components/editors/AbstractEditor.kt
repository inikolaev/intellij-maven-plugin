package com.github.inikolaev.intellij.maven.components.editors
import org.apache.maven.model.Model
import javax.swing.JPanel

abstract class AbstractEditor(val name: String) {
    protected var model: Model = Model()
    private val listeners = mutableListOf<() -> Unit>()

    fun update(model: Model) {
        this.model = model

        onModelUpdated()
    }

    fun addUpdateListener(listener: () -> Unit) {
        listeners.add(listener)
    }

    fun notifyListeners() {
        listeners.forEach {
            it()
        }
    }

    abstract fun onModelUpdated()
    abstract fun getComponent(): JPanel
}
