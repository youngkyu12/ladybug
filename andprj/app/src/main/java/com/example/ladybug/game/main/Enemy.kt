package com.example.ladybug.game.main

import com.example.ladybug.R
import kr.ac.tukorea.ge.spgp2026.a2dg.objects.Sprite
import kr.ac.tukorea.ge.spgp2026.a2dg.view.GameContext
import android.graphics.RectF

class Enemy(
    gctx: GameContext,
    x: Float,
    y: Float,
    private val dx: Float,
    private val dy: Float,
    val level: Int = 1,
    private val speed: Float = SPEED
    ) : Sprite(gctx, R.mipmap.enemy) {
    override var width = ENEMY_WIDTH
    override var height = ENEMY_HEIGHT
    override var x = x
    override var y = y

    init {
        syncDstRect()
    }

    val collisionRect: RectF
        get() {
            syncDstRect()
            return dstRect
        }

    override fun update(gctx: GameContext) {
        x += dx * speed * gctx.frameTime
        y += dy * speed * gctx.frameTime

        if (y - height / 2f > gctx.metrics.height) {
            val scene = gctx.scene as? MainScene ?: return
            scene.world.remove(this, MainScene.Layer.ENEMY)
            return
        }

        syncDstRect()
    }

    companion object {
        const val ENEMY_WIDTH = 180f
        const val ENEMY_HEIGHT = 180f
        const val SPEED = 240f
        const val MAX_LEVEL_COUNT = 20
    }
}
