package com.shortstack.hackertracker.View

import android.content.Context
import android.support.v7.widget.AppCompatCheckBox
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import com.orhanobut.logger.Logger
import com.pedrogomez.renderers.RendererAdapter
import com.pedrogomez.renderers.RendererBuilder
import com.shortstack.hackertracker.Application.App
import com.shortstack.hackertracker.Model.Filter
import com.shortstack.hackertracker.Model.SelectableType
import com.shortstack.hackertracker.Model.Type
import com.shortstack.hackertracker.R
import com.shortstack.hackertracker.schedule.SelectableTypeRenderer
import kotlinx.android.synthetic.main.alert_filter.view.*
import java.util.*

class FilterView : LinearLayout {

    lateinit var checkboxes : Array<AppCompatCheckBox>

    constructor(context : Context, types : List<Type>, filter : Filter) : super(context) {
        init(types)
    }

    private fun setFilter(filter : Filter? = null) {
        val typesArray = filter?.typesArray ?: return

        if (typesArray.isEmpty()) {
            for (type in checkboxes) {
                type.isChecked = true
            }
        }


        for (aTypesArray in typesArray) {
            for (i1 in checkboxes.indices) {
                if (aTypesArray == checkboxes[i1].text.toString()) {
                    checkboxes[i1].isChecked = true
                }
            }
        }
    }

    private fun init(types : List<Type>) {
        View.inflate(context, R.layout.alert_filter, this)

        val time = System.currentTimeMillis()

        Logger.d("Starting filter view! ")


        Logger.d("Got types in ${System.currentTimeMillis() - time}ms.")

        val colours = context.resources.getIntArray(R.array.colors)

        val states = arrayOf(intArrayOf(android.R.attr.state_checked), intArrayOf())

        Logger.d("Setup ${System.currentTimeMillis() - time}")


        val builder = RendererBuilder<SelectableType>()
                .bind(SelectableType::class.java, SelectableTypeRenderer())

        val adapter = RendererAdapter<SelectableType>(builder)

        rootView.list.layoutManager = LinearLayoutManager(context)
        rootView.list.adapter = adapter

        val selectable = Array(types.size, {SelectableType(types[it], false)}).toList()

        adapter.addAllAndNotify(selectable)





        Logger.d("Created checkboxes ${System.currentTimeMillis() - time}")
//        checkboxes.forEachIndexed { index, box ->
//            if (index <= types.size / 2)
//                filter_left.addView(box)
//            else
//                filter_right.addView(box)
//        }

        Logger.d("Setting filter ${System.currentTimeMillis() - time}")

        //setFilter(null)

    }

    fun save() : Filter {
        val selected = ArrayList<String>()

        for (i in checkboxes.indices) {
            val type = checkboxes[i]
            if (type.isChecked) {
                selected.add(checkboxes[i].text.toString())
            }
        }

        val strings = selected.toTypedArray()

        val filter = Filter(strings)

        App.application.storage.saveFilter(filter)

        return filter
    }
}
