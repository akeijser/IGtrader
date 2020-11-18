package com.akeijser.igtrader.domain

import com.akeijser.igtrader.dbo.*
import com.akeijser.igtrader.dto.*
import java.time.LocalDateTime
import java.util.*

data class MarketNavigation (
        val nodes : List<Node>?,
        val markets : String?
)

data class Markets (
        val markets: List<Market>
)

data class Market (
        val id: Int,
        val name: String
) {
    constructor(marketDBO : MarketDBO): this (
            id = marketDBO.id
            , name = marketDBO.name
    )
}

//todo can we get rid of this?
data class Epics (
        val nodes : List<Epic>?

)

data class Epic(
        val name : String,
        val marketId : Int
) {
    fun toDBO(): EpicDBO {
        return EpicDBO(name, marketId)
    }
}

data class Node (

        val id : Int?,
        val name : String?
)

data class EpicNodes (
        val nodes : List<EpicNode>?,
        val markets : String?
)

data class EpicNode(
        val id : Int?,
        val name : String?
)

data class MultipleEpicDetails (

        val multipleEpicDetails : List<EpicDetail>?
)

data class EpicDetail (

        val instrument : Instrument?,
        val dealingRules : DealingRules?,
        val snapshot : Snapshot?
)

data class Instrument (
        var id : UUID? = null,
        val epic : String? = null,
        val expiry : String? = null,
        val name : String? = null,
        val forceOpenAllowed : Boolean? = null,
        val stopsLimitsAllowed : Boolean? = null,
        val lotSize : Int? = null,
        val unit : String? = null,
        val type : String? = null,
        val controlledRiskAllowed : Boolean? = null,
        val streamingPricesAvailable : Boolean? = null,
        val marketId : String? = null,
        val currencies : List<Currencies>? = null,
        val sprintMarketsMinimumExpiryTime : String? = null,
        val sprintMarketsMaximumExpiryTime : String? = null,
        val marginDepositBands : List<MarginDepositBands>? = null,
        val marginFactor : Int? = null,
        val marginFactorUnit : String? = null,
        val slippageFactor : SlippageFactor? = null,
        val limitedRiskPremium : LimitedRiskPremium? = null,
        val openingHours : String? = null,
        val expiryDetails : String? = null,
        val rolloverDetails : String? = null,
        val newsCode : String? = null,
        val chartCode : String? = null,
        val country : String? = null,
        val valueOfOnePip : Double? = null,
        val onePipMeans : Int? = null,
        val contractSize : Int? = null,
        val specialInfo : List<String>? = null
) {
    constructor(dbo: InstrumentDBO?) : this(
            id = dbo?.getId()
            , epic = dbo?.epic
            , expiry = dbo?.expiry
            , name = dbo?.name
            , forceOpenAllowed = dbo?.forceOpenAllowed
            , stopsLimitsAllowed = dbo?.stopsLimitsAllowed
            , lotSize = dbo?.lotSize
            , unit = dbo?.unit
            , type = dbo?.type
            , controlledRiskAllowed = dbo?.controlledRiskAllowed
            , streamingPricesAvailable = dbo?.streamingPricesAvailable
            , marketId = dbo?.marketId
            , currencies = dbo?.currencies?.map { Currencies(it) }
            , sprintMarketsMinimumExpiryTime = dbo?.sprintMarketsMinimumExpiryTime
            , sprintMarketsMaximumExpiryTime = dbo?.sprintMarketsMaximumExpiryTime
            , marginDepositBands = dbo?.marginDepositBands?.map { MarginDepositBands(it) }
            , marginFactor = dbo?.marginFactor
            , marginFactorUnit = dbo?.marginFactorUnit
            , slippageFactor = dbo?.slippageFactor?.let { SlippageFactor(dbo.slippageFactor) }
            , limitedRiskPremium= LimitedRiskPremium(dbo?.limitedRiskPremium)
            , openingHours = dbo?.openingHours
            , expiryDetails = dbo?.expiryDetails
            , rolloverDetails = dbo?.rolloverDetails
            , newsCode = dbo?.newsCode
            , chartCode = dbo?.chartCode
            , country = dbo?.country
            , valueOfOnePip = dbo?.valueOfOnePip
            , onePipMeans = dbo?.onePipMeans
            , contractSize = dbo?.contractSize
            , specialInfo = dbo?.specialInfo?.map { it.info }
    )

    fun toDbo() : InstrumentDBO {

        val listCurrencies = mutableListOf<CurrenciesDBO>()
        currencies?.forEach { listCurrencies.add(it.toDbo()) }

        val listMarginDepositBands = mutableListOf<MarginDepositBandsDBO>()
        marginDepositBands?.forEach { listMarginDepositBands.add(it.toDbo()) }

        val listSpecialInfo = mutableListOf<SpecialInfoDBO>()
        specialInfo?.forEach { listSpecialInfo.add(SpecialInfoDBO(it)) }

        return InstrumentDBO(
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
                , marketId = marketId
                , currencies = listCurrencies
                , sprintMarketsMinimumExpiryTime = sprintMarketsMinimumExpiryTime
                , sprintMarketsMaximumExpiryTime = sprintMarketsMaximumExpiryTime
                , marginDepositBands = listMarginDepositBands
                , marginFactor = marginFactor
                , marginFactorUnit = marginFactorUnit
                , slippageFactor = slippageFactor?.let { SlippageFactorDBO(slippageFactor) }
                , limitedRiskPremium = limitedRiskPremium?.toDbo()
                , openingHours = openingHours
                , expiryDetails = expiryDetails
                , rolloverDetails = rolloverDetails
                , newsCode = newsCode
                , chartCode = chartCode
                , country = country
                , valueOfOnePip = valueOfOnePip
                , onePipMeans = onePipMeans
                , contractSize = contractSize
                , specialInfo = listSpecialInfo
        )
    }

}

data class Currencies (
        var id : UUID? = null,
        val code : String?,
        val symbol : String?,
        val baseExchangeRate : Double?,
        val exchangeRate : Double?,
        val isDefault : Boolean?
) {
    constructor(dbo: CurrenciesDBO) : this(
            code = dbo.code
            , symbol = dbo.symbol
            , baseExchangeRate = dbo.baseExchangeRate
            , exchangeRate = dbo.exchangeRate
            , isDefault = dbo.isDefault
    )

    fun toDbo(): CurrenciesDBO {
        return CurrenciesDBO(
                code = code
                , symbol = symbol
                , baseExchangeRate = baseExchangeRate
                , exchangeRate = exchangeRate
                , isDefault = isDefault
        )
    }
}

data class DealingRules (

        val minStepDistance : MinStepDistance?,
        val minDealSize : MinDealSize?,
        val minControlledRiskStopDistance : MinControlledRiskStopDistance?,
        val minNormalStopOrLimitDistance : MinNormalStopOrLimitDistance?,
        val maxStopOrLimitDistance : MaxStopOrLimitDistance?,
        val marketOrderPreference : String?,
        val trailingStopsPreference : String?
) {
    fun toDbo(): DealingRulesDBO? {
        return DealingRulesDBO(
                minStepDistance = minStepDistance?.toDbo()
                , minDealSize = minDealSize?.toDbo()
                , minControlledRiskStopDistance = minControlledRiskStopDistance?.toDbo()
                , minNormalStopOrLimitDistance = minNormalStopOrLimitDistance?.toDbo()
                , maxStopOrLimitDistance = maxStopOrLimitDistance?.toDbo()
                , marketOrderPreference = marketOrderPreference
                , trailingStopsPreference = trailingStopsPreference
        )
    }
}

data class LimitedRiskPremium (

        val value : Int?,
        val unit : String?
) {
    constructor(dbo: LimitedRiskPremiumDBO?) : this(
            value = dbo?.value
            , unit = dbo?.unit
    )

    fun toDbo(): LimitedRiskPremiumDBO? {
          return LimitedRiskPremiumDBO(
                  value = value
                  , unit = unit

          )
    }
}

data class MarginDepositBands (

        val min : Int?,
        val max : Int?,
        val margin : Int?,
        val currency : String?
) {
    constructor(dbo: MarginDepositBandsDBO) : this(

            min = dbo.min
            , max = dbo.max
            , margin = dbo.margin
            , currency = dbo.currency
    )

    fun toDbo(): MarginDepositBandsDBO {
        return MarginDepositBandsDBO(
                min = min
                , max = max
                , margin = margin
                , currency = currency
        )
    }
}

data class MaxStopOrLimitDistance (

        val unit : String?,
        val value : Int?
) {
    fun toDbo(): MaxStopOrLimitDistanceDBO? {
        return MaxStopOrLimitDistanceDBO(
                unit = unit
                , value = value
        )
    }
}

data class MinControlledRiskStopDistance (

        val unit : String?,
        val value : Int?
) {
    fun toDbo(): MinControlledRiskStopDistanceDBO? {
        return MinControlledRiskStopDistanceDBO(
                unit = unit
                , value = value
        )
    }
}

data class MinDealSize (

        val unit : String?,
        val value : Double?
) {
    fun toDbo(): MinDealSizeDBO? {
        return MinDealSizeDBO(
                unit = unit
                , value = value
        )
    }
}

data class MinNormalStopOrLimitDistance (

        val unit : String?,
        val value : Int?
) {
    fun toDbo(): MinNormalStopOrLimitDistanceDBO? {
        return MinNormalStopOrLimitDistanceDBO(
                unit = unit
                , value = value
        )
    }
}

data class MinStepDistance (
        val unit : String?,
        val value : Int?
) {
    fun toDbo(): MinStepDistanceDBO? {
        return MinStepDistanceDBO(
                value = value
                , unit = unit
        )
    }
}

data class SlippageFactor (
        val id: UUID? = null,
        val unit : String,
        val value : Int
) {
    constructor(dbo: SlippageFactorDBO) : this(
            id = dbo.getId()
            , unit = dbo.unit
            , value = dbo.value
    )

    constructor(dto : SlippageFactorDTO) : this (
            unit = dto.unit
            , value = dto.value
    )
}

data class Snapshot (

        val marketStatus : String?,
        val netChange : Double?,
        val percentageChange : Double?,
        val updateTime : String?,
        val delayTime : Int?,
        val bid : Double?,
        val offer : Double?,
        val high : Double?,
        val low : Double?,
        val binaryOdds : String?,
        val decimalPlacesFactor : Int?,
        val scalingFactor : Int?,
        val controlledRiskExtraSpread : Int?
) {
    fun toDbo(): SnapshotDBO? {
        return SnapshotDBO(
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

//todo check if lastTraded has to be a LocalDateTime
data class ClosePrice (

        val bid : Double?,
        val ask : Double?,
        val lastTraded : String?
) {
    constructor(closePriceDTO: ClosePriceDTO) : this(
            bid = closePriceDTO.bid
            , ask = closePriceDTO.ask
            , lastTraded = closePriceDTO.lastTraded
    )
}

data class HighPrice (

        val bid : Double?,
        val ask : Double?,
        val lastTraded : String?
) {
    constructor(highPriceDTO: HighPriceDTO) : this(
            bid = highPriceDTO.bid
            , ask = highPriceDTO.ask
            , lastTraded = highPriceDTO.lastTraded
    )
}

data class LowPrice (

        val bid : Double?,
        val ask : Double?,
        val lastTraded : String?
) {
    constructor(lowPriceDTO: LowPriceDTO) : this(
            bid = lowPriceDTO.bid
            , ask = lowPriceDTO.ask
            , lastTraded = lowPriceDTO.lastTraded
    )
}

data class OpenPrice (

        val bid : Double?,
        val ask : Double?,
        val lastTraded : String?
) {
    constructor(openPriceDTO: OpenPriceDTO) : this(
            bid = openPriceDTO.bid
            , ask = openPriceDTO.ask
            , lastTraded = openPriceDTO.lastTraded
    )
}

data class PricesDetails (
        val epicName: String,
        val snapshotTime : String?,
        val openPrice : OpenPrice?,
        val closePrice : ClosePrice?,
        val highPrice : HighPrice?,
        val lowPrice : LowPrice?,
        val lastTradedVolume : Int?
) {

    constructor(priceDetailsDTO: PriceDetailsDTO, epicName: String) : this(
            epicName = epicName
            , snapshotTime = priceDetailsDTO.snapshotTime
            , openPrice = priceDetailsDTO.openPrice?.let { OpenPrice(it) }
            , closePrice = priceDetailsDTO.closePrice?.let { ClosePrice(it) }
            , highPrice = priceDetailsDTO.highPrice?.let { HighPrice(it) }
            , lowPrice = priceDetailsDTO.lowPrice?.let { LowPrice(it) }
            , lastTradedVolume = priceDetailsDTO.lastTradedVolume
    )
}

data class OauthToken (

        val access_token : String,
        val refresh_token : String,
        val scope : String,
        val token_type : String,
        val expires_in : Int,
        val creationDateTime : LocalDateTime = LocalDateTime.now()

) {

    constructor(oauthTokenDTO: OauthTokenDTO) : this(
            access_token = oauthTokenDTO.access_token
            , refresh_token = oauthTokenDTO.refresh_token
            , scope = oauthTokenDTO.scope
            , token_type = oauthTokenDTO.token_type
            , expires_in = oauthTokenDTO.expires_in
            , creationDateTime = LocalDateTime.now()
    )

    constructor(oauthTokenDBO: OauthTokenDBO) : this(
            access_token = oauthTokenDBO.accessToken
            , refresh_token = oauthTokenDBO.refreshToken
            , scope = oauthTokenDBO.scope
            , token_type = oauthTokenDBO.tokenType
            , expires_in = oauthTokenDBO.expiresIn
            , creationDateTime = oauthTokenDBO.creationDateTime
    )

    val expiresDateTime: LocalDateTime = creationDateTime.plusSeconds(expires_in.toLong() -1)
}

/**
 * dataPoints will not be used when startDate and endDate are set
 */
data class PriceRequest(
        val epic : String
        , val resolution: ResolutionDTO = ResolutionDTO.MINUTE
        , val startDate: LocalDateTime? = null
        , val endDate: LocalDateTime? = null
        , val dataPoints: Int = 10
)

/**
 * this class helps mocking unit tests, since HttpResponse is hard to mockk
 */
data class IGHttpResponse(
        val responseCode : Int = 200
        , val headers : Map<String, List<String>>
        , val body : String
)

enum class  MarketSearchFilter {
    ALL, SNAPSHOT_ONLY
}

enum class Resolution {
    MINUTE, MINUTE_2, MINUTE_3, MINUTE_5, MINUTE_10, MINUTE_15, MINUTE_30, HOUR, HOUR_2, HOUR_3, HOUR_4, DAY, WEEK, MONTH
}

enum class RequestBuildType {
    PRICEDETAILS, PRICEDETAILSWITHDATE, OAUTHLOGIN, STREAMTOKEN, MARKETNAVIGATION, NODENAVIGATION, MARKETDETAILS
}