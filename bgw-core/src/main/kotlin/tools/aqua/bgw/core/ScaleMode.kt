@file:Suppress("unused")

package tools.aqua.bgw.core

/**
 * Used to define the scaling behaviour of a [Scene].
 *
 * @see tools.aqua.bgw.core.BoardGameScene
 * @see tools.aqua.bgw.core.BoardGameApplication
 */
enum class ScaleMode {
	/**
	 * Fully automatic scaling to window size.
	 */
	FULL,
	
	/**
	 * Scaling only shrinks [Scene] when window gets to small.
	 */
	ONLY_SHRINK,
	
	/**
	 * Disables automatic rescaling.
	 */
	NO_SCALE
}