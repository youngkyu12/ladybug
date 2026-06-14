package com.example.ladybug.game.main

import android.graphics.Canvas
import android.graphics.RectF
import android.view.MotionEvent
import kr.ac.tukorea.ge.spgp2026.a2dg.objects.Sprite
import kr.ac.tukorea.ge.spgp2026.a2dg.objects.GyroscopeController
import kr.ac.tukorea.ge.spgp2026.a2dg.objects.IBoxCollidable
import kr.ac.tukorea.ge.spgp2026.a2dg.view.GameContext
import com.example.ladybug.R

class Player(
    val gctx: GameContext,
    private val gyroscopeController: GyroscopeController,
) : Sprite(gctx, R.mipmap.ladybug_player), IBoxCollidable {
    private val _collisionRect = RectF()
    private val speed = 550f
    private var rotationDegrees = 0f
    private var shotCoolTime = 0f

    override val collisionRect: RectF
        get() = _collisionRect

    init {
        setCenterProportionalWidth(400f, 1000f, 150f)
        updateCollisionRect()
    }

    // 매 프레임 센서 입력값을 읽어 위치를 바꾼다.
    override fun update(gctx: GameContext) {
        x += gyroscopeController.x * speed * gctx.frameTime
        y += gyroscopeController.y * speed * gctx.frameTime

        if (gyroscopeController.power > 0f) {
            rotationDegrees = Math.toDegrees(gyroscopeController.angle.toDouble()).toFloat() + 90f
        }

        val halfWidth = width / 2f
        val halfHeight = height / 2f
        x = x.coerceIn(halfWidth, gctx.metrics.width - halfWidth)
        y = y.coerceIn(halfHeight, gctx.metrics.height - halfHeight)
        syncDstRect()
        updateCollisionRect()
        updateShotCoolTime(gctx)
    }

    override fun draw(canvas: Canvas) {
        canvas.save()
        canvas.rotate(rotationDegrees, x, y)
        super.draw(canvas)
        canvas.restore()
    }

    fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            fireBulletIfReady()
        }

        return true
    }

    private fun fireBulletIfReady() {
        if (shotCoolTime > 0f) return

        fireBullet()
        shotCoolTime = SHOT_INTERVAL
    }

    private fun updateShotCoolTime(gctx: GameContext) {
        if (shotCoolTime <= 0f) return
        shotCoolTime -= gctx.frameTime
        if (shotCoolTime < 0f) shotCoolTime = 0f
    }

    private fun fireBullet() {
        val scene = gctx.scene as? MainScene ?: return
        val bullet = Bullet.get(gctx, x, y - BULLET_OFFSET)
        scene.world.add(bullet, MainScene.Layer.BULLET)
    }

    private fun updateCollisionRect() {
        _collisionRect.set(dstRect)
        _collisionRect.inset(COLLISION_INSET, COLLISION_INSET)
    }

    companion object {
        const val COLLISION_INSET = 35f
        const val BULLET_OFFSET = 80f
        const val SHOT_INTERVAL = 2.0f
    }
}
