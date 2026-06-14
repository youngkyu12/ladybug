package com.example.ladybug.game.main

import com.example.ladybug.R
import kr.ac.tukorea.ge.spgp2026.a2dg.objects.Sprite
import kr.ac.tukorea.ge.spgp2026.a2dg.view.GameContext
import android.graphics.RectF

class Bullet(
    gctx: GameContext,
    startX: Float,
    startY: Float,
) : Sprite(gctx, R.mipmap.bullet) {
    private val _collisionRect = RectF()
    override var width = BULLET_WIDTH
    override var height = BULLET_HEIGHT
    override var x = startX
    override var y = startY

    init {
        syncDstRect()
        updateCollisionRect()
    }

    val collisionRect: RectF
        get() = _collisionRect

    override fun update(gctx: GameContext) {
        // 현재 Bullet 은 x 는 그대로 두고 y 만 감소시키며 위쪽으로 직진한다.
        y -= SPEED * gctx.frameTime
        syncDstRect()
        updateCollisionRect()

        // 총알이 화면 위를 완전히 벗어나면 현재 Scene 의 BULLET layer 에서 제거한다.
        if (y + height / 2f < 0f) {
            val scene = gctx.scene as? MainScene ?: return
            scene.world.remove(this, MainScene.Layer.BULLET)
        }
    }

    private fun updateCollisionRect() {
        _collisionRect.set(dstRect)
        _collisionRect.inset(COLLISION_INSET, COLLISION_INSET)
    }

    companion object {
        const val BULLET_WIDTH = 150f
        const val BULLET_HEIGHT = BULLET_WIDTH
        const val SPEED = 200f
        const val COLLISION_INSET = 40f
    }
}