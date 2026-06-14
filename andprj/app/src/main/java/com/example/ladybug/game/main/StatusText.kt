package com.example.ladybug.game.main

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import kr.ac.tukorea.ge.spgp2026.a2dg.objects.IGameObject
import kr.ac.tukorea.ge.spgp2026.a2dg.util.LabelUtil
import kr.ac.tukorea.ge.spgp2026.a2dg.view.GameContext

class StatusText(private val scene: MainScene) : IGameObject {
    private val label = LabelUtil(
        textSize = 54f,
        color = Color.WHITE,
        align = Paint.Align.LEFT,
        typeface = Typeface.DEFAULT_BOLD,
    )

    override fun update(gctx: GameContext) {
    }

    override fun draw(canvas: Canvas) {
        label.draw(canvas, "Score ${scene.score}", 32f, 72f)
        label.draw(canvas, "Time ${scene.elapsedTimeText}", 32f, 132f)
    }
}
