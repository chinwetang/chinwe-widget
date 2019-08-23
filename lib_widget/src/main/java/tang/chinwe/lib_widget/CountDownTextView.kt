package tang.chinwe.lib_widget

import android.content.Context
import android.os.SystemClock
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
 * 倒计时View
 */
class CountDownTextView(context: Context, attrs: AttributeSet) : AppCompatTextView(context, attrs) {

    var initRealtime: Long = 0
    var countDownTime: Long = 0
    var isRunAnimator = false

    lateinit var ttt: (remain: Long) -> Unit

    fun initTime(time: Long, call: (remain: Long) -> Unit) {
        isRunAnimator = true
        ttt = call
        /**
         * 开启硬件加速
         */
        setLayerType(LAYER_TYPE_HARDWARE, null)
        countDownTime = time
        initRealtime = SystemClock.elapsedRealtime()
        ttt(countDownTime)
    }


    override fun computeScroll() {
        super.computeScroll()
        var currTime = SystemClock.elapsedRealtime()
        if (currTime - initRealtime <= countDownTime) {
            val remain = countDownTime - (currTime - initRealtime)
            val delay = 300L
            postDelayed({
                if (isRunAnimator)
                    ttt(remain)
            }, delay)
        } else {
            if (isRunAnimator)
                ttt(0)
            isRunAnimator = false
        }
    }
}