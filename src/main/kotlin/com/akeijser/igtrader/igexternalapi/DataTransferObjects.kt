package com.akeijser.igtrader.igexternalapi

import com.akeijser.igtrader.domain.*
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class NodeDTO (

        @SerializedName("id") val id : Int?,
        @SerializedName("name") val name : String?
) {
    fun toDomain(): Node {
        return Node(id, name)
    }
}

data class MultiNodesDTO (
        @SerializedName("nodes")
        val nodes : List<NodeDTO>?,
        @SerializedName("markets")
        val markets : String?,
        @SerializedName("id")
        var id: Int = 0
) {
    fun toMarkets(): Markets {
        val listOfMarkets = mutableListOf<Market>()
        nodes?.forEach { listOfMarkets.add(Market(it.id, it.name)) }
        return Markets(listOfMarkets)
    }

    fun toEpics(): Epics {
        val listOfEpics = mutableListOf<Epic>()
        nodes?.forEach { listOfEpics.add(Epic( it.name, this.id)) }
        return Epics(listOfEpics)
    }
}

data class MarketNavigationDTO (
        @SerializedName("nodes")
        val nodes : List<NodeDTO>?,
        @SerializedName("markets")
        val markets : String?
) {
    fun toDomain(): MarketNavigation {
        val nodesDomain = mutableListOf<Node>()
        nodes?.forEach { nodesDomain.add(it.toDomain()) }
        return MarketNavigation(nodesDomain, markets)
    }
}

data class EpicNodesDTO (

        @SerializedName("nodes") val nodes : List<EpicNodeDTO>?,
        @SerializedName("markets") val markets : String?
) {
    fun toDomain(): EpicNodes {
        val nodesDomain = mutableListOf<EpicNode>()
        nodes?.forEach { nodesDomain.add(it.toDomain()) }
        return EpicNodes(nodesDomain, markets)
    }
}

data class EpicNodeDTO (

        @SerializedName("id") val id : Int?,
        @SerializedName("name") val name : String?
) {
    fun toDomain(): EpicNode {
        return EpicNode(id, name)
    }
}

data class EpicsDTO (

        @SerializedName("delayTime") val delayTime : Int?,
        @SerializedName("epic") val epic : String?,
        @SerializedName("netChange") val netChange : Double?,
        @SerializedName("lotSize") val lotSize : Int?,
        @SerializedName("expiry") val expiry : String?,
        @SerializedName("instrumentType") val instrumentType : String?,
        @SerializedName("instrumentName") val instrumentName : String?,
        @SerializedName("high") val high : Double?,
        @SerializedName("low") val low : String?,
        @SerializedName("percentageChange") val percentageChange : Double?,
        @SerializedName("updateTime") val updateTime : Int?,
        @SerializedName("updateTimeUTC") val updateTimeUTC : String?,
        @SerializedName("bid") val bid : String?,
        @SerializedName("offer") val offer : String?,
        @SerializedName("otcTradeable") val otcTradeable : Boolean?,
        @SerializedName("streamingPricesAvailable") val streamingPricesAvailable : Boolean?,
        @SerializedName("marketStatus") val marketStatus : String?,
        @SerializedName("scalingFactor") val scalingFactor : Int?
)



data class MultipleEpicDetailsDTO (

        @SerializedName("marketDetails") val epicDetails : List<EpicDetailsDTO>?
) {
    fun toDomain(): MultipleEpicDetails{
        val list = mutableListOf<EpicDetails>()
        epicDetails?.forEach { list.add(it.toDomain()) }
        return MultipleEpicDetails(list)
    }
}

data class EpicDetailsDTO (

        @SerializedName("instrument") val instrument : InstrumentDTO?,
        @SerializedName("dealingRules") val dealingRules : DealingRulesDTO?,
        @SerializedName("snapshot") val snapshot : SnapshotDTO?
) {
    fun toDomain():EpicDetails{
        return EpicDetails(
                instrument = instrument?.toDomain(),
                dealingRules = dealingRules?.toDomain(),
                snapshot = snapshot?.toDomain()
        )
    }
}

data class InstrumentDTO (

        @SerializedName("epic") val epic : String?,
        @SerializedName("expiry") val expiry : String?,
        @SerializedName("name") val name : String?,
        @SerializedName("forceOpenAllowed") val forceOpenAllowed : Boolean?,
        @SerializedName("stopsLimitsAllowed") val stopsLimitsAllowed : Boolean?,
        @SerializedName("lotSize") val lotSize : Int?,
        @SerializedName("unit") val unit : String?,
        @SerializedName("type") val type : String?,
        @SerializedName("controlledRiskAllowed") val controlledRiskAllowed : Boolean?,
        @SerializedName("streamingPricesAvailable") val streamingPricesAvailable : Boolean?,
        @SerializedName("marketId") val marketId : String?,
        @SerializedName("currencies") val currencies : List<CurrenciesDTO>?,
        @SerializedName("sprintMarketsMinimumExpiryTime") val sprintMarketsMinimumExpiryTime : String?,
        @SerializedName("sprintMarketsMaximumExpiryTime") val sprintMarketsMaximumExpiryTime : String?,
        @SerializedName("marginDepositBands") val marginDepositBands : List<MarginDepositBandsDTO>?,
        @SerializedName("marginFactor") val marginFactor : Int?,
        @SerializedName("marginFactorUnit") val marginFactorUnit : String?,
        @SerializedName("slippageFactor") val slippageFactor : SlippageFactorDTO?,
        @SerializedName("limitedRiskPremium") val limitedRiskPremium : LimitedRiskPremiumDTO?,
        @SerializedName("openingHours") val openingHours : String?,
        @SerializedName("expiryDetails") val expiryDetails : String?,
        @SerializedName("rolloverDetails") val rolloverDetails : String?,
        @SerializedName("newsCode") val newsCode : String?,
        @SerializedName("chartCode") val chartCode : String?,
        @SerializedName("country") val country : String?,
        @SerializedName("valueOfOnePip") val valueOfOnePip : Double?,
        @SerializedName("onePipMeans") val onePipMeans : Int?,
        @SerializedName("contractSize") val contractSize : Int?,
        @SerializedName("specialInfo") val specialInfo : List<String>?
) {
    fun toDomain(): Instrument {


        val listCurrencies = mutableListOf<Currencies>()
        currencies?.forEach { listCurrencies.add(it.toDomain()) }

        val listMarginDepositBands = mutableListOf<MarginDepositBands>()
        marginDepositBands?.forEach { listMarginDepositBands.add(it.toDomain()) }

        return Instrument(
                epic = epic
                , expiry = expiry
                , name = name
                , forceOpenAllowed = forceOpenAllowed
                , stopsLimitsAllowed = stopsLimitsAllowed
                , lotSize = lotSize
                , unit = unit
                , type = type
                , controlledRiskAllowed = controlledRiskAllowed
                , streamingPricesAvailable = streamingPricesAvailable
                , marketId =marketId
                , currencies = listCurrencies
                , sprintMarketsMinimumExpiryTime = sprintMarketsMinimumExpiryTime
                , sprintMarketsMaximumExpiryTime = sprintMarketsMaximumExpiryTime
                , marginDepositBands = listMarginDepositBands
                , marginFactor = marginFactor
                , marginFactorUnit = marginFactorUnit
                , slippageFactor = slippageFactor?.toDomain()
                , limitedRiskPremium = limitedRiskPremium?.toDomain()
                , openingHours = openingHours
                , expiryDetails = expiryDetails
                , rolloverDetails = rolloverDetails
                , newsCode = newsCode
                , chartCode = chartCode
                , country = country
                , valueOfOnePip = valueOfOnePip
                , onePipMeans = onePipMeans
                , contractSize = contractSize
                , specialInfo = specialInfo
            )
    }
}

data class CurrenciesDTO (

        @SerializedName("code") val code : String?,
        @SerializedName("symbol") val symbol : String?,
        @SerializedName("baseExchangeRate") val baseExchangeRate : Double?,
        @SerializedName("exchangeRate") val exchangeRate : Double?,
        @SerializedName("isDefault") val isDefault : Boolean?
) {
    fun toDomain(): Currencies {
        return Currencies(
                code = code
                , symbol = symbol
                , baseExchangeRate = baseExchangeRate
                , exchangeRate = exchangeRate
                , isDefault = isDefault
        )
    }
}

data class DealingRulesDTO (

        @SerializedName("minStepDistance") val minStepDistance : MinStepDistanceDTO?,
        @SerializedName("minDealSize") val minDealSize : MinDealSizeDTO?,
        @SerializedName("minControlledRiskStopDistance") val minControlledRiskStopDistance : MinControlledRiskStopDistanceDTO?,
        @SerializedName("minNormalStopOrLimitDistance") val minNormalStopOrLimitDistance : MinNormalStopOrLimitDistanceDTO?,
        @SerializedName("maxStopOrLimitDistance") val maxStopOrLimitDistance : MaxStopOrLimitDistanceDTO?,
        @SerializedName("marketOrderPreference") val marketOrderPreference : String?,
        @SerializedName("trailingStopsPreference") val trailingStopsPreference : String?
)
{
    fun toDomain(): DealingRules {
        return DealingRules(
                minStepDistance = minStepDistance?.toDomain()
                , minDealSize = minDealSize?.toDomain()
                , minControlledRiskStopDistance = minControlledRiskStopDistance?.toDomain()
                , minNormalStopOrLimitDistance = minNormalStopOrLimitDistance?.toDomain()
                , maxStopOrLimitDistance = maxStopOrLimitDistance?.toDomain()
                , marketOrderPreference = marketOrderPreference
                , trailingStopsPreference = trailingStopsPreference
        )
    }
}

data class LimitedRiskPremiumDTO (

        @SerializedName("value") val value : Int?,
        @SerializedName("unit") val unit : String?
) {
    fun toDomain(): LimitedRiskPremium {
        return LimitedRiskPremium(value, unit)
    }
}

data class MarginDepositBandsDTO (

        @SerializedName("min") val min : Int?,
        @SerializedName("max") val max : Int?,
        @SerializedName("margin") val margin : Int?,
        @SerializedName("currency") val currency : String?
) {
    fun toDomain(): MarginDepositBands {
        return MarginDepositBands(
                min = min
                , max = max
                , margin = margin
                , currency = currency
        )
    }
}

data class MaxStopOrLimitDistanceDTO (

        @SerializedName("unit") val unit : String?,
        @SerializedName("value") val value : Int?
) {
    fun toDomain(): MaxStopOrLimitDistance {
        return MaxStopOrLimitDistance(
                unit = unit
                , value = value
        )
    }
}

data class MinControlledRiskStopDistanceDTO (

        @SerializedName("unit") val unit : String?,
        @SerializedName("value") val value : Int?
) {
    fun toDomain(): MinControlledRiskStopDistance {
        return MinControlledRiskStopDistance(
                unit = unit
                , value = value
        )
    }
}

data class MinDealSizeDTO (

        @SerializedName("unit") val unit : String?,
        @SerializedName("value") val value : Double?
) {
    fun toDomain(): MinDealSize {
        return MinDealSize(
                unit = unit
                , value = value
        )
    }
}

data class MinNormalStopOrLimitDistanceDTO (

        @SerializedName("unit") val unit : String?,
        @SerializedName("value") val value : Int?
) {
    fun toDomain(): MinNormalStopOrLimitDistance {
        return MinNormalStopOrLimitDistance(
                unit = unit
                , value = value
        )
    }
}

data class MinStepDistanceDTO (

        @SerializedName("unit") val unit : String?,
        @SerializedName("value") val value : Int?
) {
    fun toDomain(): MinStepDistance {
        return MinStepDistance(
                unit = unit
                , value = value
        )
    }
}

data class SlippageFactorDTO (

        @SerializedName("unit") val unit : String?,
        @SerializedName("value") val value : Int?
) {
    fun toDomain(): SlippageFactor {
        return SlippageFactor(unit, value)
    }
}

data class SnapshotDTO (

        @SerializedName("marketStatus") val marketStatus : String?,
        @SerializedName("netChange") val netChange : Double?,
        @SerializedName("percentageChange") val percentageChange : Double?,
        @SerializedName("updateTime") val updateTime : String?,
        @SerializedName("delayTime") val delayTime : Int?,
        @SerializedName("bid") val bid : Double?,
        @SerializedName("offer") val offer : Double?,
        @SerializedName("high") val high : Double?,
        @SerializedName("low") val low : Double?,
        @SerializedName("binaryOdds") val binaryOdds : String?,
        @SerializedName("decimalPlacesFactor") val decimalPlacesFactor : Int?,
        @SerializedName("scalingFactor") val scalingFactor : Int?,
        @SerializedName("controlledRiskExtraSpread") val controlledRiskExtraSpread : Int?
) {
    fun toDomain(): Snapshot {
        return Snapshot(
                marketStatus = marketStatus
                , netChange = netChange
                , percentageChange = percentageChange
                , updateTime = updateTime
                , delayTime = delayTime
                , bid = bid
                , offer = offer
                , high = high
                , low = low
                , binaryOdds = binaryOdds
                , decimalPlacesFactor = decimalPlacesFactor
                , scalingFactor = scalingFactor
                , controlledRiskExtraSpread = controlledRiskExtraSpread
        )
    }
}

data class PricesDTO (

        @SerializedName("prices") val pricesDetails : List<PriceDetailsDTO>?,
        @SerializedName("instrumentType") val instrumentType : String?,
        @SerializedName("allowance") val allowance : AllowanceDTO?
)

data class AllowanceDTO (

        @SerializedName("remainingAllowance") val remainingAllowance : Int?,
        @SerializedName("totalAllowance") val totalAllowance : Int?,
        @SerializedName("allowanceExpiry") val allowanceExpiry : Int?
)

data class ClosePriceDTO (

        @SerializedName("bid") val bid : Double?,
        @SerializedName("ask") val ask : Double?,
        @SerializedName("lastTraded") val lastTraded : String?
)

data class HighPriceDTO (

        @SerializedName("bid") val bid : Double?,
        @SerializedName("ask") val ask : Double?,
        @SerializedName("lastTraded") val lastTraded : String?
)

data class LowPriceDTO (

        @SerializedName("bid") val bid : Double?,
        @SerializedName("ask") val ask : Double?,
        @SerializedName("lastTraded") val lastTraded : String?
)

data class OpenPriceDTO (

        @SerializedName("bid") val bid : Double?,
        @SerializedName("ask") val ask : Double?,
        @SerializedName("lastTraded") val lastTraded : String?
)

data class PriceDetailsDTO (

        @SerializedName("snapshotTime") val snapshotTime : String?,
        @SerializedName("openPrice") val openPrice : OpenPriceDTO?,
        @SerializedName("closePrice") val closePrice : ClosePriceDTO?,
        @SerializedName("highPrice") val highPrice : HighPriceDTO?,
        @SerializedName("lowPrice") val lowPrice : LowPriceDTO?,
        @SerializedName("lastTradedVolume") val lastTradedVolume : Int?
)

enum class  MarketSearchFilterDTO {
    ALL, SNAPSHOT_ONLY
}

enum class ResolutionDTO {
    MINUTE, MINUTE_2, MINUTE_3, MINUTE_5, MINUTE_10, MINUTE_15, MINUTE_30, HOUR, HOUR_2, HOUR_3, HOUR_4, DAY, WEEK, MONTH
}


//Login DTO's
data class OauthResponse (

        @SerializedName("clientId")
        val clientId : Int,
        @SerializedName("accountId")
        val accountId : String,
        @SerializedName("timezoneOffset")
        val timezoneOffset : Int,
        @SerializedName("lightstreamerEndpoint")
        val lightstreamerEndpoint : String,
        @SerializedName("oauthToken")
        var oauthToken : OauthToken
)

data class OauthToken (

        @SerializedName("access_token")
        val access_token : String,
        @SerializedName("refresh_token")
        val refresh_token : String,
        @SerializedName("scope")
        val scope : String,
        @SerializedName("token_type")
        val token_type : String,
        @SerializedName("expires_in")
        val expires_in : Int,
        var expiresDateTime: LocalDateTime
) {
    fun setExpiresDateTime() {
        expiresDateTime = LocalDateTime.now().plusSeconds(expires_in.toLong() -1)
    }
}

data class OAuthLogin (
        val identifier: String,
        val password: String
)

data class RefreshToken (
        @SerializedName("refresh_token")
        val refreshToken: String
)

data class StreamTokens(val cst: String, val xSecurityToken: String, val streamEndpoint: String)