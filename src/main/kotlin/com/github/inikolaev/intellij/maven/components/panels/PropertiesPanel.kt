package com.github.inikolaev.intellij.maven.components.panels
import com.github.inikolaev.intellij.maven.components.ListComponent
import com.github.inikolaev.intellij.maven.components.dialogs.EditPropertyDialogWrapper
import com.intellij.icons.AllIcons
import com.intellij.ui.OptionGroup
import com.intellij.ui.components.JBLabel
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.DefaultListModel
import javax.swing.SwingConstants

class PropertiesPanel: OptionGroup("Properties") {
    private val model1 = DefaultListModel<Pair<String, String>>()

    fun setListData(data: List<Pair<String, String>>) {
        model1.clear()
        data.forEach(model1::addElement)
    }

    val mouseListener = object: MouseAdapter() {
        override fun mouseClicked(e: MouseEvent?) {
            val list = e?.component as ListComponent<Pair<String, String>>

            if (e?.clickCount == 2 && e?.button == MouseEvent.BUTTON1) {
                val label = list?.selectedValue

                if (label != null) {
                    println("Item clicked twice: ${label}")
                    val dialog = EditPropertyDialogWrapper(label.first, label.second)
                    val result = dialog.showAndGet()

                    if (result) {
                        println("Result: ${dialog.name.text} = ${dialog.value.text}")
                        model1.setElementAt(Pair(dialog.name.text, dialog.value.text), list.selectedIndex)
                    }
                }
            }
        }
    }

    val propertyList = ListComponent<Pair<String, String>> { value ->
        JBLabel("${value.first} : ${value.second}", AllIcons.Nodes.Property, SwingConstants.LEFT)
    }.apply {
        addMouseListener(mouseListener)
        model = model1
    }

    init {
        add(propertyList)
    }
}