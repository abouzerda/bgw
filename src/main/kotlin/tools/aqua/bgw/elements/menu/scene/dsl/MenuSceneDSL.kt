package tools.aqua.bgw.elements

import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.elements.StaticView
import tools.aqua.bgw.elements.layoutviews.GridLayoutView
import tools.aqua.bgw.elements.uielements.*

//region Util
/**
 * MenuSceneBuilder is a static class, that can be used to build [MenuScene]s.
 * It is meant to be used in conjunction with the internal
 * domain specific language in [tools.aqua.bgw.elements.menu.scene.dsl].
 *
 * To initiate a new description of a menu scene, simply call the `dsl` function,
 * which then returns the resulting [MenuScene].
 */
class MenuSceneBuilder {
    companion object {
        /**
         * Initiates a new description of a menu scene.
         *
         * @return the resulting [MenuScene]
         */
        fun dsl(height: Number, width: Number, func: MenuScene.() -> Unit): MenuScene =
            MenuScene(height = height, width = width).apply(func)
    }
}

/**
 * Sets the componentStyle of the receiver [UIElementView] to the return value of [func].
 */
fun UIElementView.componentStyle(func: () -> String) {
    componentStyle = func.invoke()
}

/**
 * Sets the backgroundStyle of the receiver [UIElementView] to the return value of [func].
 */
fun UIElementView.backgroundStyle(func: () -> String) {
    backgroundStyle = func.invoke()
}
//endregion


//region grid
/**
 * Creates a new [GridLayoutView], applies the [func] and adds it to the receiver [MenuScene].
 *
 * @return the new [GridLayoutView].
 */
fun <T> MenuScene.grid(
    rows: Int,
    cols: Int,
    func: (GridLayoutView<T>.() -> Unit)
): GridLayoutView<T> where T : StaticView<T> {
    val gridLayoutView = GridLayoutView<T>(rows, cols).apply(func)
    addElements(gridLayoutView)
    return gridLayoutView
}
//endregion

//region ToggleGroup
/**
 * Creates a new [ToggleGroup] and sets it for all [ToggleButton]s that get created in the context of the
 * receiver [ToggleGroupBuilder] of [func]. Also adds all those [ToggleButton]s to the receiver [MenuScene].
 *
 * @see [ToggleGroupBuilder]
 *
 * @return the new [ToggleGroup].
 */
fun MenuScene.toggleGroup(func: ToggleGroupBuilder.() -> Unit): ToggleGroup {
    val tgb = ToggleGroupBuilder().apply(func)
    val data = tgb.build()
    data.second.forEach { addElements(it) }
    return data.first
}

/**
 * [ToggleGroupBuilder] may be used to build [ToggleGroup]s in a semantically more appealing way, than
 * to set the [ToggleGroup] on every [ToggleButton].
 */
class ToggleGroupBuilder {
    internal val buttons = mutableListOf<ToggleButton>()
    internal fun build(): Pair<ToggleGroup, List<ToggleButton>> {
        val toggleGroup = ToggleGroup()
        buttons.forEach { it.toggleGroup = toggleGroup }
        return Pair(toggleGroup, buttons)
    }
}

/**
 * Creates a new [ToggleButton], applies the [func] and adds it to the context of the receiver [ToggleGroupBuilder].
 *
 * @return the new [ToggleButton].
 */
fun ToggleGroupBuilder.toggleButton(func: ToggleButton.() -> Unit): ToggleButton {
    val toggleButton = ToggleButton().apply(func)
    buttons.add(toggleButton)
    return toggleButton
}

/**
 * Creates a new [RadioButton], applies the [func] and adds it to the context of the receiver [ToggleGroupBuilder].
 *
 * @return the new [RadioButton].
 */
fun ToggleGroupBuilder.radioButton(func: RadioButton.() -> Unit): RadioButton {
    val radioButton = RadioButton().apply(func)
    buttons.add(radioButton)
    return radioButton
}
//endregion

//region Button
/**
 * Creates a new [Button], applies the [func] and adds it to the receiver [MenuScene].
 *
 * @return the new [Button].
 */
fun MenuScene.button(func: Button.() -> Unit): Button {
    val btn = Button().apply(func)
    addElements(btn)
    return btn
}
//endregion

//region ToggleButton
/**
 * Creates a new [ToggleButton], applies the [func] and adds it to the receiver [MenuScene].
 *
 * @return the new [ToggleButton].
 */
fun MenuScene.toggleButton(func: ToggleButton.() -> Unit): ToggleButton {
    val btn = ToggleButton().apply(func)
    addElements(btn)
    return btn
}
//endregion

//region RadioButton
/**
 * Creates a new [RadioButton], applies the [func] and adds it to the receiver [MenuScene].
 *
 * @return the new [RadioButton].
 */
fun MenuScene.radioButton(func: ToggleButton.() -> Unit): RadioButton {
    val btn = RadioButton().apply(func)
    addElements(btn)
    return btn
}
//endregion

//region CheckBox
/**
 * Creates a new [CheckBox], applies the [func] and adds it to the receiver [MenuScene].
 *
 * @return the new [CheckBox].
 */
fun MenuScene.checkBox(func: CheckBox.() -> Unit): CheckBox {
    val checkBox = CheckBox().apply(func)
    addElements(checkBox)
    return checkBox
}
//endregion

//region Label
/**
 * Creates a new [Label], applies the [func] and adds it to the receiver [MenuScene].
 *
 * @return the new [Label].
 */
fun MenuScene.label(func: Label.() -> Unit): Label {
    val label = Label().apply(func)
    addElements(label)
    return label
}
//endregion

//region ListView
/**
 * Creates a new [ListView], applies the [func] and adds it to the receiver [MenuScene].
 *
 * @return the new [ListView].
 */
fun <T> MenuScene.listView(func: ListView<T>.() -> Unit): ListView<T> {
    val listView = ListView<T>()
    addElements(listView.apply(func))
    return listView
}
//endregion

//region TableView
/**
 * Creates a new [TableView], applies the [func] and adds it to the receiver [MenuScene].
 *
 * @return the new [TableView].
 */
fun <T> MenuScene.tableView(func: TableView<T>.() -> Unit): TableView<T> {
    val tableView = TableView<T>()
    addElements(tableView.apply(func))
    return tableView
}

/**
 * Creates a new [TableColumn] and adds it to the receiver [TableView].
 *
 * @param title the title for the new [TableColumn].
 * @param width the width for the new [TableColumn].
 * @param func the new formatFunction for the new [TableColumn].
 */
fun <T> TableView<T>.column(title: String, width: Number, func: T.() -> String) {
    columns.add(TableColumn(title = title, width = width, formatFunction = func))
}

/**
 * Sets the data model for the receiver [TableView].
 *
 * @param data the data to set.
 */
fun <T> TableView<T>.data(data: List<T>) {
    items.clear()
    items.addAll(data)
}

/**
 * Appends the supplied [data] to the data model of the receiver [TableView].
 *
 * @param data the data to append.
 */
fun <T> TableView<T>.appendData(data: List<T>) {
    items.addAll(data)
}
//endregion

//region TextArea
/**
 * Creates a new [TextArea], applies the [func] and adds it to the receiver [MenuScene].
 *
 * @return the new [TextArea].
 */
fun MenuScene.textArea(prompt: String, func: TextArea.() -> Unit): TextArea {
    val textArea = TextArea(prompt = prompt).apply(func)
    addElements(textArea)
    return textArea
}
//endregion