package com.example.ladybug.game.main

import com.example.ladybug.R
import kr.ac.tukorea.ge.spgp2026.a2dg.objects.Sprite
import kr.ac.tukorea.ge.spgp2026.a2dg.view.GameContext

class Enemy(gctx: GameContext, x: Float, y: Float) : Sprite(gctx, R.mipmap.enemy) {
    override var width = ENEMY_WIDTH
    override var height = ENEMY_HEIGHT
    override var x = x
    override var y = y

    init {
        syncDstRect()
    }

    override fun update(gctx: GameContext) {
        y += SPEED * gctx.frameTime
        syncDstRect()
    }

    companion object {
        const val ENEMY_WIDTH = 180f
        const val ENEMY_HEIGHT = 180f
        const val SPEED = 240f
    }
}
