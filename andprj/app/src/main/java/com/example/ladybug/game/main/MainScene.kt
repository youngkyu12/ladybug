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
    private val statusText = StatusText(this)

    var score = 0
        private set
    var elapsedTime = 0f
        private set
    private var nextSurvivalScoreTime = 1f
    val elapsedTimeText: String
        get() {
            val totalSeconds = elapsedTime.toInt()
            val minutes = totalSeconds / 60
            val seconds = totalSeconds % 60
            return "%02d:%02d".format(minutes, seconds)
        }

    enum class Layer {
        BACKGROUND,
        PLAYER,
        BULLET,
        ENEMY,
        CONTROLLER,
        UI,
    }

    override val world = World(Layer.entries.toTypedArray()).apply {
        add(VertScrollBackground(gctx, R.mipmap.game_background, 80f), Layer.BACKGROUND)
        add(player, Layer.PLAYER)
        add(enemyGenerator, Layer.CONTROLLER)
        add(collisionChecker, Layer.CONTROLLER)
        add(statusText, Layer.UI)
    }

    override fun update(gctx: GameContext) {
        elapsedTime += gctx.frameTime
        while (elapsedTime >= nextSurvivalScoreTime) {
            addScore(SURVIVAL_SCORE_PER_SECOND)
            nextSurvivalScoreTime += 1f
        }
        super.update(gctx)
    }

    fun addScore(amount: Int) {
        score += amount
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

    override fun onBackPressed(): Boolean {
        PauseScene(gctx).push()
        return true
    }

    companion object {
        const val SURVIVAL_SCORE_PER_SECOND = 1
    }
}
