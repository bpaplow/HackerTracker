package com.shortstack.hackertracker.ui.schedule.list

import com.pedrogomez.renderers.RendererBuilder
import com.shortstack.hackertracker.models.Day
import com.shortstack.hackertracker.models.Item
import com.shortstack.hackertracker.models.Time
import com.shortstack.hackertracker.ui.schedule.renderers.ItemRenderer
import com.shortstack.hackertracker.ui.schedule.renderers.RelativeDayRender
import com.shortstack.hackertracker.ui.schedule.renderers.RelativeTimeRenderer


class ScheduleItemBuilder : RendererBuilder<Item>() {
    init {

        bind(Item::class.java, ItemRenderer())
                .bind(Day::class.java, RelativeDayRender())
                .bind(Time::class.java, RelativeTimeRenderer())
    }
}
