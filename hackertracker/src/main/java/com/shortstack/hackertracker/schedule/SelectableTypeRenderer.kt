package com.shortstack.hackertracker.schedule

import android.support.v7.widget.AppCompatCheckBox
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pedrogomez.renderers.Renderer
import com.shortstack.hackertracker.Model.SelectableType
import com.shortstack.hackertracker.R

/**
 * Created by Chris on 12/11/2017.
 */
class SelectableTypeRenderer : Renderer<SelectableType>() {
    override fun render(payloads : MutableList<Any>?) {

//        val colours = context?.resources?.getIntArray(R.array.colors) ?: return
//        val states = arrayOf(intArrayOf(android.R.attr.state_checked), intArrayOf())

        (rootView as AppCompatCheckBox).apply {
            text = content.type.type
//            CompoundButtonCompat.setButtonTintList(this, ColorStateList(states, intArrayOf(colours[0], colours[0])))
        }

    }

    override fun inflate(inflater : LayoutInflater, parent : ViewGroup?) : View {
        return inflater.inflate(R.layout.item_type, parent, false)
    }
}