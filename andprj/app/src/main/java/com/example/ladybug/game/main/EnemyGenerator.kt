package com.example.ladybug.game.main

import android.graphics.Canvas
import kr.ac.tukorea.ge.spgp2026.a2dg.objects.IGameObject
import kr.ac.tukorea.ge.spgp2026.a2dg.view.GameContext
import kotlin.math.sqrt
import kotlin.random.Random

class EnemyGenerator(
    private val gctx: GameContext,
    private val player: Player,
) : IGameObject {
    private var waveTime = WAVE_INTERVAL // 다음 웨이브까지 남은 시간
    private var spawnTime = 0f  // 웨이브 도중 다음 적 1마리 생성까지 남은 시간
    private var remainingEnemies = 0    // 현재 웨이브에서 앞으로 몇 마리를 더 만들지 저장
    private var nextLevel = 1
    private var wave = 0

    override fun update(gctx: GameContext) {
        // 먼저 현재 웨이브가 진행 중인지 본다.
        if (remainingEnemies > 0) {
            updateWave(gctx)
            return
        }
        wave++
        waveTime -= gctx.frameTime
        // 시간이 다 지나면 웨이브를 시작한다.
        if (waveTime <= 0f) {
            remainingEnemies = COUNT_PER_WAVE
            spawnTime = 0f
        }
    }

    // 웨이브 진행 중에 적을 하나씩 생성한다.
    private fun updateWave(gctx: GameContext) {
        // 먼저 다음 적 생성까지 남은 시간을 줄인다.
        spawnTime -= gctx.frameTime
        // 아직 시간이 남았으면 아무것도 하지 않는다.
        if (spawnTime > 0f) return

        // 시간이 다 되면 적 1마리를 생성한다.
        generateOne()
        remainingEnemies -= 1

        // 아직 더 생성할 적이 남아 있으면 다음 적까지의 간격을 다시 설정한다.
        // 나중에 시간에 따라 웨이브 변화를 주고싶다면 추가 변수로 컨트롤할 예정
        if (remainingEnemies > 0) {
            spawnTime = SPAWN_INTERVAL
        } else {
            waveTime = WAVE_INTERVAL
        }
    }

    // 적 한 마리를 만드는 함수
    private fun generateOne() {
        val scene = gctx.scene as? MainScene ?: return


        val minX = Enemy.ENEMY_WIDTH / 2f   // 적 반너비
        val maxX = gctx.metrics.width - Enemy.ENEMY_WIDTH / 2f  // 화면 너비 - 적 반너비

        val x = Random.nextFloat() * (maxX - minX) + minX   // 랜덤 x 좌표
        val y = -Enemy.ENEMY_HEIGHT / 2f    // y는 화면 위쪽 바깥에서 시작

        // 생성 순간의 플레이어 위치를 향하는 방향 계산
        // 이 값을 그대로 쓰면 거리가 멀수록 속도가 빨라지기 때문에 길이를 1로 정규화
        val vx = player.x - x
        val vy = player.y - y
        val distance = sqrt(vx * vx + vy * vy)
        if (distance <= 0f) return

        // 방향만 가진 단위 벡터
        val dx = vx / distance
        val dy = vy / distance

        val speed = Enemy.SPEED + (wave - 1) * SPEED_STEP

        scene.world.add(
            Enemy(gctx, x, y, dx, dy, level = nextLevel, speed = speed),
            MainScene.Layer.ENEMY
        )
        nextLevel = if (nextLevel == Enemy.MAX_LEVEL_COUNT) 1 else nextLevel + 1
    }

    override fun draw(canvas: Canvas) {
        // EnemyGenerator 는 화면에 직접 보이는 오브젝트가 아니라
        // "언제 적을 만들지"만 판단하는 담당자이므로 그릴 것은 없다.
    }

    companion object {
        const val WAVE_INTERVAL = 3f
        const val SPAWN_INTERVAL = 0.5f
        const val COUNT_PER_WAVE = 5
        const val SPEED_STEP = 0.5f
    }
}
