package com.example.ladybug.game.main

import kr.ac.tukorea.ge.spgp2026.a2dg.objects.VertScrollBackground
import kr.ac.tukorea.ge.spgp2026.a2dg.scene.Scene
import kr.ac.tukorea.ge.spgp2026.a2dg.scene.World
import kr.ac.tukorea.ge.spgp2026.a2dg.view.GameContext
import com.example.ladybug.R

class MainScene(gctx: GameContext) : Scene(gctx) {
    override val clipsRect = true

    override val world = World(arrayOf(0)).apply {
        add(VertScrollBackground(gctx, R.mipmap.game_background, -100f), 0)
        add(Player(gctx), 0)
    }

}