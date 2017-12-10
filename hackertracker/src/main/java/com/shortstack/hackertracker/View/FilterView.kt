package com.shortstack.hackertracker.View

import android.content.Context
import android.support.v7.widget.AppCompatCheckBox
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.orhanobut.logger.Logger
import com.shortstack.hackertracker.Application.App
import com.shortstack.hackertracker.Model.Filter
import com.shortstack.hackertracker.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.alert_filter.view.*
import java.util.*

class FilterView : LinearLayout {

    lateinit var checkboxes : Array<AppCompatCheckBox>

    constructor(context : Context) : super(context) {
        init()
    }

    constructor(context : Context, filter : Filter) : super(context) {
        init()
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

    constructor(context : Context, attrs : AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context : Context, attrs : AttributeSet, defStyleAttr : Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.alert_filter, this)

        val time = System.currentTimeMillis()

        Logger.d("Starting filter view! ")

        App.application.database.apply {
            typeDao().getTypes()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { types ->

                        Logger.d("Got types in ${System.currentTimeMillis() - time}ms.")

                        val colours = context.resources.getIntArray(R.array.colors)

                        val states = arrayOf(intArrayOf(android.R.attr.state_checked), intArrayOf())

                        Logger.d("Setup ${System.currentTimeMillis() - time}")


                        checkboxes = Array(types.size, {
                            AppCompatCheckBox(context)
                        })

                        Logger.d("Created checkboxes ${System.currentTimeMillis() - time}")
                        checkboxes.forEachIndexed { index, box ->
                            if (index <= types.size / 2)
                                filter_left.addView(box)
                            else
                                filter_right.addView(box)
                        }

                        Logger.d("Setting filter ${System.currentTimeMillis() - time}")

                        //setFilter(null)

                    }
        }
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
