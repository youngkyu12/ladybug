package com.example.ladybug.game.main

import android.graphics.RectF
import com.example.ladybug.R
import kr.ac.tukorea.ge.spgp2026.a2dg.objects.IBoxCollidable
import kr.ac.tukorea.ge.spgp2026.a2dg.objects.IRecyclable
import kr.ac.tukorea.ge.spgp2026.a2dg.objects.Sprite
import kr.ac.tukorea.ge.spgp2026.a2dg.view.GameContext

class Enemy(
    gctx: GameContext,
) : Sprite(gctx, R.mipmap.enemy), IBoxCollidable, IRecyclable {
    override val collisionRect = RectF()
    override var width = ENEMY_WIDTH
    override var height = ENEMY_HEIGHT
    override var x = 0f
    override var y = 0f
    private var dx = 0f
    private var dy = 0f
    var level = 1
        private set
    private var speed = SPEED
    val score: Int
        get() = level * SCORE_PER_LEVEL

    fun init(
        x: Float,
        y: Float,
        dx: Float,
        dy: Float,
        level: Int = 1,
        speed: Float = SPEED,
    ): Enemy {
        this.x = x
        this.y = y
        this.dx = dx
        this.dy = dy
        this.level = level
        this.speed = speed
        syncDstRect()
        updateCollisionRect()
        return this
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
        updateCollisionRect()
    }

    override fun onRecycle() {
    }

    private fun updateCollisionRect() {
        collisionRect.set(dstRect)
        collisionRect.inset(COLLISION_INSET, COLLISION_INSET)
    }

    companion object {
        const val ENEMY_WIDTH = 180f
        const val ENEMY_HEIGHT = 180f
        const val SPEED = 240f
        const val MAX_LEVEL_COUNT = 20
        const val COLLISION_INSET = 40f
        const val SCORE_PER_LEVEL = 10

        fun get(
            gctx: GameContext,
            x: Float,
            y: Float,
            dx: Float,
            dy: Float,
            level: Int = 1,
            speed: Float = SPEED,
        ): Enemy {
            val scene = gctx.scene as? MainScene ?: return Enemy(gctx).init(x, y, dx, dy, level, speed)
            val enemy = scene.world.obtain(Enemy::class.java) ?: Enemy(gctx)
            return enemy.init(x, y, dx, dy, level, speed)
        }
    }
}
