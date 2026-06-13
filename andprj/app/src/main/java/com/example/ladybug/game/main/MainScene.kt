package com.example.ladybug.game.main

import android.view.MotionEvent
import kr.ac.tukorea.ge.spgp2026.a2dg.objects.GyroscopeController
import kr.ac.tukorea.ge.spgp2026.a2dg.objects.VertScrollBackground
import kr.ac.tukorea.ge.spgp2026.a2dg.scene.Scene
import kr.ac.tukorea.ge.spgp2026.a2dg.scene.World
import kr.ac.tukorea.ge.spgp2026.a2dg.view.GameContext
import com.example.ladybug.R

class MainScene(gctx: GameContext) : Scene(gctx) {
    override val clipsRect = true

    // MainScene에서 컨트롤러를 하나 만든다.
    val gyroscopeController = GyroscopeController(gctx)
    val player = Player(gctx, gyroscopeController)

    enum class Layer {
        BACKGROUND,
        PLAYER,
        BULLET,
        ENEMY,
    }

    override val world = World(arrayOf(Layer.BACKGROUND, Layer.PLAYER, Layer.BULLET, Layer.ENEMY)).apply {
        add(VertScrollBackground(gctx, R.mipmap.game_background, -100f), Layer.BACKGROUND)
        add(player, Layer.PLAYER)
    }

    override fun onEnter() {
        gyroscopeController.start()
    }

    override fun onExit() {
        gyroscopeController.stop()
    }

    override fun onPause() {
        gyroscopeController.stop()
    }

    override fun onResume() {
        gyroscopeController.start()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.actionMasked == MotionEvent.ACTION_DOWN) {
            val pt = gctx.metrics.fromScreen(event.x, event.y)
            val enemyY = Enemy.ENEMY_HEIGHT / 2f
            val enemy = Enemy(gctx, pt.x, enemyY)
            world.add(enemy, Layer.ENEMY)
        }
        return true
    }
}