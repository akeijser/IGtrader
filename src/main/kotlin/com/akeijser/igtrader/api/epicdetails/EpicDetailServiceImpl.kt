package com.akeijser.igtrader.api.epicdetails

import com.akeijser.igtrader.api.http.HttpRequestBuilder
import com.akeijser.igtrader.api.http.HttpSender
import com.akeijser.igtrader.api.tokens.Tokens
import com.akeijser.igtrader.domain.Epic
import com.akeijser.igtrader.domain.EpicDetail
import com.akeijser.igtrader.domain.RequestBuildType
import com.akeijser.igtrader.dto.MarketSearchFilterDTO
import com.akeijser.igtrader.dto.MultipleEpicDetailsDTO
import com.google.gson.Gson
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class EpicDetailServiceImpl(
        private val tokens: Tokens,
        private val httpRequestBuilder: HttpRequestBuilder,
        private val httpSender: HttpSender
) : EpicDetailService {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(EpicDetailServiceImpl::class.java)
    }

    override fun getEpicDetails(epic: Epic): List<EpicDetail>? {
        return getEpicDetails(listOf(epic.name), false)
    }

    override fun getEpicDetails(epicName: String, snapshotOnly: Boolean): List<EpicDetail>? {
        return getEpicDetails(listOf(epicName), false)
    }

    override fun getEpicDetails(epicNames: List<String>, snapshotOnly: Boolean): List<EpicDetail>? {

        val filter = if (snapshotOnly) {
            MarketSearchFilterDTO.SNAPSHOT_ONLY
        } else {
            MarketSearchFilterDTO.ALL
        }

        val epicNamesURLEncoded = epicNames.map { java.net.URLEncoder.encode(it, "utf-8")}

        val uriParameters = "?epics=${epicNamesURLEncoded.joinToString(separator = "%2C", transform = { it.replace(" ", "") })}&filter=${filter.name}"

        val token = tokens.getOAuthToken()

        val request = httpRequestBuilder.build(RequestBuildType.MARKETDETAILS, uriParameters = uriParameters, oauthToken = token)

        val response = httpSender.send(request)
        if (response.responseCode != 200 ){
            LOGGER.info("get epic details response headers: ${response.headers}")
            LOGGER.info("get epic details response body: ${response.body}")
        }
        val result =  Gson().fromJson(response.body, MultipleEpicDetailsDTO::class.java)
        return result.epicDetails?.map { it.toDomain() }
    }
}