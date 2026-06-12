package com.example.ladybug.game.main

import kr.ac.tukorea.ge.spgp2026.a2dg.objects.GyroscopeController
import kr.ac.tukorea.ge.spgp2026.a2dg.objects.VertScrollBackground
import kr.ac.tukorea.ge.spgp2026.a2dg.scene.Scene
import kr.ac.tukorea.ge.spgp2026.a2dg.scene.World
import kr.ac.tukorea.ge.spgp2026.a2dg.view.GameContext
import com.example.ladybug.R

class MainScene(gctx: GameContext) : Scene(gctx) {
    override val clipsRect = true
    // MainScene에서 컨트롤러를 하나 만든다.
    private val gyroscopeController = GyroscopeController(gctx)

    override val world = World(arrayOf(0)).apply {
        add(VertScrollBackground(gctx, R.mipmap.game_background, -100f), 0)
        // GyroscopeController를 World에 넣고, Player에도 넘긴다.
        add(gyroscopeController, 0)
        add(Player(gctx, gyroscopeController), 0)
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
}
