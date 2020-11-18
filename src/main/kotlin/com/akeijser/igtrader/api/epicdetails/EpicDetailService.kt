package com.akeijser.igtrader.api.epicdetails

import com.akeijser.igtrader.domain.Epic
import com.akeijser.igtrader.domain.EpicDetail

interface EpicDetailService {

    fun getEpicDetails(epic: Epic) : List<EpicDetail>?

    fun getEpicDetails(epicName: String, snapshotOnly: Boolean = false): List<EpicDetail>?

    fun getEpicDetails(epicNames: List<String>, snapshotOnly: Boolean = false): List<EpicDetail>?
}