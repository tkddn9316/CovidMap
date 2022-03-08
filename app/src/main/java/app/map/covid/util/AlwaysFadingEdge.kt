package app.map.covid.util

import android.content.Context
import android.util.AttributeSet
import android.widget.ScrollView

class AlwaysFadingEdge : ScrollView {
    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attr: AttributeSet?) : this(context, attr, 0)
    constructor(context: Context, attr: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attr,
        defStyleAttr
    )

    override fun getTopFadingEdgeStrength(): Float {
        return 0f
    }

    override fun getBottomFadingEdgeStrength(): Float {
        if (childCount == 0) {
            return 0.0f
        }
        return 1.0f
    }
}