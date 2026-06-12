package kr.ac.tukorea.ge.spgp2026.a2dg.objects

import android.content.Context
import android.graphics.Canvas
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kr.ac.tukorea.ge.spgp2026.a2dg.view.GameContext
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.sqrt

// GyroscopeController 는 기기 기울기를 조이스틱과 비슷한 -1.0~1.0 입력값으로 바꿔 주는 공통 입력 객체이다.
// 실제 각속도 센서인 Gyroscope 보다, "어느 쪽으로 기울어졌는지"를 바로 알 수 있는 Gravity 센서를 우선 사용한다.
class GyroscopeController(
    gctx: GameContext,
    private val maxTiltGravity: Float = 4.5f,
    private val deadZone: Float = 0.08f,
) : IGameObject, SensorEventListener {
    private val sensorManager = gctx.view.context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    // Android의 TYPE_GRAVITY 센서를 우선 사용하고, 없으면 TYPE_ACCELEROMETER를 사용한다.
    // 실제 Gyroscope 센서는 “기기가 얼마나 빠르게 회전 중인지”를 주는 센서라서,
    // 게임에서 필요한 “지금 어느 방향으로 기울어져 있는지”와는 다르기 때문이다.
    private val sensor: Sensor? =
        sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
            ?: sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    var x = 0f
        private set
    var y = 0f
        private set
    var power = 0f
        private set
    var angle = 0f
        private set

    val isAvailable: Boolean
        get() = sensor != null

    // 센서 입력을 받기 시작한다.
    fun start() {
        val sensor = sensor ?: return
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME)
    }

    // 센서 입력을 중지한다.
    fun stop() {
        sensorManager.unregisterListener(this)
        reset()
    }

    // 센서 값이 바뀔 때 Android가 자동으로 호출한다.
    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type != Sensor.TYPE_GRAVITY && event.sensor.type != Sensor.TYPE_ACCELEROMETER) {
            return
        }
        // 기기의 물리적인 기울기 값을 읽는다.
        val rawX = event.values[0]
        val rawY = event.values[1]

        // 게임 입력값으로 바꾼다.
        // x < 0: 왼쪽 이동
        // x > 0: 오른쪽 이동
        // y < 0: 위쪽 이동
        // y > 0: 아래쪽 이동
        x = applyDeadZone((-rawX / maxTiltGravity).coerceIn(-1f, 1f))
        y = applyDeadZone((rawY / maxTiltGravity).coerceIn(-1f, 1f))

        // 기울어진 세기
        power = sqrt(x * x + y * y).coerceIn(0f, 1f)

        // 기울어진 방향 각도
        angle = atan2(y, x)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun update(gctx: GameContext) {
    }

    // 그리지 않지만 PC 디버깅 필요 시 추가 예정
    override fun draw(canvas: Canvas) {
    }

    // 아주 작은 흔들림을 무시한다.
    private fun applyDeadZone(value: Float): Float {
        if (abs(value) < deadZone) return 0f
        return value
    }

    private fun reset() {
        x = 0f
        y = 0f
        power = 0f
        angle = 0f
    }
}
