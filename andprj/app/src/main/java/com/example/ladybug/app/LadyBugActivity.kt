package com.example.ladybug.app

import com.example.ladybug.BuildConfig
import kr.ac.tukorea.ge.spgp2026.a2dg.activity.BaseGameActivity
import kr.ac.tukorea.ge.spgp2026.a2dg.scene.Scene
import kr.ac.tukorea.ge.spgp2026.a2dg.view.GameContext
import com.example.ladybug.game.main.MainScene

class LadyBugActivity : BaseGameActivity() {
    override val drawsDebugGrid = BuildConfig.DEBUG
    override val drawsDebugInfo = BuildConfig.DEBUG
    override val drawsFpsGraph = BuildConfig.DEBUG

    override fun createRootScene(gctx: GameContext): Scene {
        return MainScene(gctx)
    }
}
