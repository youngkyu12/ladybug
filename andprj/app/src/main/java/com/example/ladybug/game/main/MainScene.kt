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
    private val enemyGenerator = EnemyGenerator(gctx, player)
    private val collisionChecker = CollisionChecker(gctx)

    enum class Layer {
        BACKGROUND,
        PLAYER,
        BULLET,
        ENEMY,
        CONTROLLER,
    }

    override val world = World(Layer.entries.toTypedArray()).apply {
        add(VertScrollBackground(gctx, R.mipmap.game_background, -100f), Layer.BACKGROUND)
        add(player, Layer.PLAYER)
        add(enemyGenerator, Layer.CONTROLLER)
        add(collisionChecker, Layer.CONTROLLER)
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
        return player.onTouchEvent(event)
    }
}