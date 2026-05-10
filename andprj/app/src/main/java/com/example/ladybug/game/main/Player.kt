package com.example.ladybug.game.main

import kr.ac.tukorea.ge.spgp2026.a2dg.objects.Sprite
import kr.ac.tukorea.ge.spgp2026.a2dg.view.GameContext
import com.example.ladybug.R

class Player(gctx: GameContext): Sprite(gctx, R.mipmap.ladybug_player) {
    init {
        setCenterProportionalWidth(200f, 700f, 200f)
    }
}
