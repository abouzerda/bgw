package examples.concepts.draganddrop

import tools.aqua.bgw.core.BoardGameApplication
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.elements.container.AreaContainerView
import tools.aqua.bgw.elements.gameelements.TokenView
import tools.aqua.bgw.elements.reposition
import tools.aqua.bgw.visual.ColorVisual

fun main() {
    DragAndDropExample()
}

class DragAndDropExample : BoardGameApplication("Drag and drop example") {
    val gameScene: BoardGameScene = BoardGameScene(background = ColorVisual.LIGHT_GRAY)

    val redToken: TokenView = TokenView(posX = 20, posY = 20, visual = ColorVisual.RED)
    val greenToken: TokenView = TokenView(posX = 20, posY = 200, visual = ColorVisual.GREEN)

    val redArea: AreaContainerView<TokenView> =
        AreaContainerView(
            height = 50,
            width = 50,
            posX = 200,
            posY = 20,
            visual = ColorVisual(255, 0, 0, 100)
        )

    val greenArea: AreaContainerView<TokenView> =
        AreaContainerView(
            height = 50,
            width = 50,
            posX = 200,
            posY = 200,
            visual = ColorVisual(0, 255, 0, 100)
        )

    init {
        redToken.isDraggable = true
        redToken.onDragGestureEnded = { _, success ->
            if (success) {
                redToken.isDraggable = false
            }
        }

        greenToken.isDraggable = true
        greenToken.onDragGestureEnded = { _, success ->
            if (success) {
                greenToken.isDraggable = false
            }
        }

        redArea.dropAcceptor = { dragEvent ->
            when (dragEvent.draggedElement) {
                is TokenView -> dragEvent.draggedElement == redToken
                else -> false
            }
        }
        redArea.onDragElementDropped = { dragEvent ->
            redArea.add((dragEvent.draggedElement as TokenView).apply { reposition(0,0) })
        }

        greenArea.dropAcceptor = { dragEvent ->
            when (dragEvent.draggedElement) {
                is TokenView -> dragEvent.draggedElement == greenToken
                else -> false
            }
        }
        greenArea.onDragElementDropped = { dragEvent ->
            greenArea.add((dragEvent.draggedElement as TokenView).apply { reposition(0,0) })
        }

        gameScene.addElements(redToken, greenToken, redArea, greenArea)
        showGameScene(gameScene)
        show()
    }
}