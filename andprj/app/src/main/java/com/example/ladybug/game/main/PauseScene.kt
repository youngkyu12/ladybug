package com.example.ladybug.game.main

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.view.MotionEvent
import kr.ac.tukorea.ge.spgp2026.a2dg.scene.Scene
import kr.ac.tukorea.ge.spgp2026.a2dg.view.GameContext

class PauseScene(gctx: GameContext) : Scene(gctx) {
    override val clipsRect = true
    override val isTransparent = true

    private val resumeRect = RectF()
    private val exitRect = RectF()

    private val overlayPaint = Paint().apply {
        color = Color.argb(150, 0, 0, 0)
    }
    private val titlePaint = Paint().apply {
        color = Color.WHITE
        textSize = 92f
        textAlign = Paint.Align.CENTER
        typeface = Typeface.DEFAULT_BOLD
        isAntiAlias = true
    }
    private val menuPaint = Paint().apply {
        color = Color.WHITE
        textSize = 56f
        textAlign = Paint.Align.CENTER
        typeface = Typeface.DEFAULT_BOLD
        isAntiAlias = true
    }

    init {
        val centerX = gctx.metrics.width / 2f
        resumeRect.set(centerX - BUTTON_WIDTH / 2f, 450f, centerX + BUTTON_WIDTH / 2f, 550f)
        exitRect.set(centerX - BUTTON_WIDTH / 2f, 600f, centerX + BUTTON_WIDTH / 2f, 700f)
    }

    override fun draw(canvas: Canvas) {
        canvas.drawRect(gctx.metrics.borderRect, overlayPaint)
        canvas.drawText("PAUSED", gctx.metrics.width / 2f, 340f, titlePaint)
        drawMenu(canvas, resumeRect, "RESUME")
        drawMenu(canvas, exitRect, "EXIT")
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.actionMasked != MotionEvent.ACTION_DOWN) return true

        val point = gctx.metrics.fromScreen(event.x, event.y)
        when {
            resumeRect.contains(point.x, point.y) -> pop()
            exitRect.contains(point.x, point.y) -> gctx.sceneStack.popAll()
        }
        return true
    }

    private fun drawMenu(canvas: Canvas, rect: RectF, text: String) {
        canvas.drawText(text, rect.centerX(), rect.centerY() + 20f, menuPaint)
    }

    companion object {
        private const val BUTTON_WIDTH = 420f
    }
}
