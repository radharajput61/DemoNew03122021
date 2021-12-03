package com.app.demo.marker

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.core.view.drawToBitmap

import com.app.demo.R
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import kotlinx.android.synthetic.main.custom_map_marker.view.*

class MapMarker constructor(
    private val rootViewGroup: ViewGroup,
    type: Type = Type.GREEN() ,
    onClickListener: (() -> Unit)? = null
) {

    sealed class Type {
        class RED(val title: String? = null, val subTitle: String? = null) : Type()
        class GREEN(val title: String? = null, val subTitle: String? = null) : Type()
        class YELLOW(val title: String? = null, val subTitle: String? = null) : Type()
        class BLUE(val eta: String? = null) : Type()
    }

    sealed class State {
        object Expanded : State()
        object ExpandedLeft : State()
        object Dropped : State()
        object Loading : State()
        object Moving : State()
    }

    private val markerView: MarkerView = MarkerView(rootViewGroup, type , onClickListener)



    var type = type
        set(value) {
            field = value
            markerView.type = type
        }

    var onClickListener: (() -> Unit)? = onClickListener
        set(value) {
            field = value
            markerView.onClickListener = value
        }

    fun remove() {
        rootViewGroup.removeView(markerView)
    }

    fun getGoogleMapsMarker(): BitmapDescriptor? {
        markerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        markerView.layout(0, 0, markerView.measuredWidth, markerView.measuredHeight)
        val bitmap = markerView.drawToBitmap(Bitmap.Config.ARGB_8888)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}

private class MarkerView(
    rootViewGroup: ViewGroup,
    type: MapMarker.Type,
    onClickListener: (() -> Unit)?
) : RelativeLayout(rootViewGroup.context) {

    private lateinit var view: View


    var type: MapMarker.Type = type
        set(value) {
            if (type == value) {
                return
            }
            field = value
            setIndicatorType()
        }



    var onClickListener: (() -> Unit)? = onClickListener
        set(value) {
            field = value
            setListener()
        }

    var onMeasured: (() -> Unit)? = null

    init {
        initLayoutParams()
        inflate()
        measure()
        setProperties()
    }

    private fun initLayoutParams() {
        layoutParams = ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
    }

    private fun measure() {
        measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED)
    }

    private fun inflate() {
        view = View.inflate(context, R.layout.custom_map_marker, this)
    }

    private fun setProperties() {
        setIndicatorType()
        setListener()

    }

    private fun setIndicatorType() {
        when (type) {
            is MapMarker.Type.RED -> {
                iv_marker.setImageResource(R.drawable.red)
            }
            is MapMarker.Type.GREEN -> {
                iv_marker.setImageResource(R.drawable.green)
            } is MapMarker.Type.YELLOW -> {
            iv_marker.setImageResource(R.drawable.yellow)
            }
            is MapMarker.Type.BLUE -> {
                iv_marker.setImageResource(R.drawable.blue)
            }
        }
    }

    private fun updateEtaInfo(eta: String?) {

    }

    private fun updateInfo(title: String?, subTitle: String?) {

    }



    private fun setListener() {

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        onMeasured?.invoke()
    }

    private fun expandLeft() {

    }

    private fun expand() {

    }

    private fun drop() {

    }

    private fun load() {}
    private fun move() {}
}
