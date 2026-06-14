package com.example.ladybug.game.main

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.view.MotionEvent
import kr.ac.tukorea.ge.spgp2026.a2dg.scene.Scene
import kr.ac.tukorea.ge.spgp2026.a2dg.view.GameContext

class GameOverScene(
    gctx: GameContext,
    private val score: Int,
    private val elapsedTimeText: String,
) : Scene(gctx) {
    override val clipsRect = true

    private val restartRect = RectF()
    private val exitRect = RectF()

    private val bgPaint = Paint().apply {
        color = Color.rgb(20, 24, 30)
    }
    private val titlePaint = Paint().apply {
        color = Color.WHITE
        textSize = 92f
        textAlign = Paint.Align.CENTER
        typeface = Typeface.DEFAULT_BOLD
        isAntiAlias = true
    }
    private val textPaint = Paint().apply {
        color = Color.WHITE
        textSize = 54f
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
    }
    private val guidePaint = Paint().apply {
        color = Color.rgb(210, 220, 230)
        textSize = 52f
        textAlign = Paint.Align.CENTER
        typeface = Typeface.DEFAULT_BOLD
        isAntiAlias = true
    }

    init {
        val centerX = gctx.metrics.width / 2f
        restartRect.set(centerX - BUTTON_WIDTH / 2f, 620f, centerX + BUTTON_WIDTH / 2f, 720f)
        exitRect.set(centerX - BUTTON_WIDTH / 2f, 760f, centerX + BUTTON_WIDTH / 2f, 860f)
    }

    override fun draw(canvas: Canvas) {
        val centerX = gctx.metrics.width / 2f
        canvas.drawRect(gctx.metrics.borderRect, bgPaint)
        canvas.drawText("GAME OVER", centerX, 310f, titlePaint)
        canvas.drawText("Score $score", centerX, 440f, textPaint)
        canvas.drawText("Time $elapsedTimeText", centerX, 520f, textPaint)
        drawMenu(canvas, restartRect, "RESTART")
        drawMenu(canvas, exitRect, "EXIT")
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.actionMasked != MotionEvent.ACTION_DOWN) return true

        val point = gctx.metrics.fromScreen(event.x, event.y)
        when {
            restartRect.contains(point.x, point.y) -> MainScene(gctx).change()
            exitRect.contains(point.x, point.y) -> gctx.sceneStack.popAll()
        }
        return true
    }

    override fun onBackPressed(): Boolean {
        gctx.sceneStack.popAll()
        return true
    }

    private fun drawMenu(canvas: Canvas, rect: RectF, text: String) {
        canvas.drawText(text, rect.centerX(), rect.centerY() + 18f, guidePaint)
    }

    companion object {
        private const val BUTTON_WIDTH = 420f
    }
}
