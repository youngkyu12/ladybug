package com.example.ladybug.game.main

import com.example.ladybug.R
import kr.ac.tukorea.ge.spgp2026.a2dg.objects.Sprite
import kr.ac.tukorea.ge.spgp2026.a2dg.view.GameContext
import kotlin.collections.remove

class Enemy(gctx: GameContext, x: Float, y: Float) : Sprite(gctx, R.mipmap.enemy) {
    override var width = ENEMY_WIDTH
    override var height = ENEMY_HEIGHT
    override var x = x
    override var y = -ENEMY_HEIGHT / 2f

    init {
        syncDstRect()
    }


    override fun update(gctx: GameContext) {
        y += SPEED * gctx.frameTime

        if (y - height / 2f > gctx.metrics.height) {
            val scene = gctx.scene as? MainScene ?: return
            scene.world.remove(this, MainScene.Layer.ENEMY)
        }
        syncDstRect()
    }

    companion object {
        const val ENEMY_WIDTH = 180f
        const val ENEMY_HEIGHT = 180f
        const val SPEED = 240f
    }
}
