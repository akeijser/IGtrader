package com.akeijser.igtrader.api.testhelpers

import com.akeijser.igtrader.domain.*
import com.akeijser.igtrader.dto.*
import java.time.LocalDateTime
import java.util.*

class InstrumentFixture {
    companion object{
        fun create(
                id : UUID = UUID.randomUUID()
                ,   epic : String = "test epic"
                ,   expiry : String = "-"
                ,   name : String = "Bitcoin (\$1)"
                ,   forceOpenAllowed: Boolean = true
                ,   stopsLimitsAllowed : Boolean = true
                ,   lotSize : Int = 1
                ,   unit : String = "CONTRACTS"
                ,   type : String = "CURRENCIES"
                ,   controlledRiskAllowed : Boolean = true
                ,   streamingPricesAvailable : Boolean = true
                ,   marketId : String = "" //todo even nadenken hoe ik deze meepak, waarschijnlijk veranderen naar market in Instrument class ipv id
                ,   currencies : List<Currencies> = listOf(CurrenciesFixture.create())
                ,   sprintMarketsMinimumExpiryTime : String = ""
                ,   sprintMarketsMaximumExpiryTime : String = ""
                ,   marginDepositBands : List<MarginDepositBands> = listOf(MarginDepositBandsFixture.create())
                ,   marginFactor : Int = 50
                ,   marginFactorUnit : String = "PERCENTAGE"
                ,   slippageFactor : SlippageFactor = SlippageFactorFixture.create()
                ,   limitedRiskPremium : LimitedRiskPremium = LimitedRiskPremiumFixture.create()
                ,   openingHours : String = ""
                ,   expiryDetails : String = ""
                ,   rolloverDetails : String = ""
                ,   newsCode : String = "BTC="
                ,   chartCode : String = "BTC"
                ,   country : String = ""
                ,   valueOfOnePip : Double = 1.0
                ,   onePipMeans : Int = 1
                ,   contractSize : Int = 1
                ,   specialInfo : List<String> = listOf("")

        ): Instrument {
            return Instrument(
                    id = id
                    , epic = epic
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
                    , currencies = currencies
                    , sprintMarketsMinimumExpiryTime = sprintMarketsMinimumExpiryTime
                    , sprintMarketsMaximumExpiryTime = sprintMarketsMaximumExpiryTime
                    , marginDepositBands = marginDepositBands
                    , marginFactor = marginFactor
                    , marginFactorUnit = marginFactorUnit
                    , slippageFactor = slippageFactor
                    , limitedRiskPremium = limitedRiskPremium
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

}

class LimitedRiskPremiumFixture {
    companion object{
        fun create(
                value : Int = 45
                , unit : String = "POINTS"
        ): LimitedRiskPremium{
            return LimitedRiskPremium(
                    value = value
                    , unit = unit
            )
        }
    }
}

class SlippageFactorFixture {
    companion object{
        fun create(
                unit: String = "pct"
                , value: Int = 100
        ):SlippageFactor{
            return SlippageFactor(
                    unit = unit
                    , value = value
            )
        }
    }
}

class CurrenciesFixture {
    companion object{
        fun create(
                id : UUID = UUID.randomUUID(),
                code : String = "USD",
                symbol : String = "$",
                baseExchangeRate : Double = 1.178441,
                exchangeRate : Double = 0.66,
                isDefault : Boolean = false
        ):Currencies {
            return Currencies(
                    id = id
                    ,code = code
                    ,symbol = symbol
                    ,baseExchangeRate = baseExchangeRate
                    ,exchangeRate = exchangeRate
                    ,isDefault = isDefault
            )
        }
    }
}

class MarginDepositBandsFixture {
    companion object{
        fun create(
                min : Int = 0,
                max : Int = 30,
                margin : Int = 50,
                currency : String = "USD"

        ):MarginDepositBands {
            return MarginDepositBands(
                    min = min
                    ,max = max
                    ,margin = margin
                    ,currency = currency
            )
        }
    }
}

class OauthResponseDTOFixture {
    companion object{
        fun create(
                clientId : Int = 1
                , accountId : String = "1"
                , timezoneOffset : Int = 0
                , lightstreamerEndpoint : String = "https://lightstreamerendpoint.com"
                , oauthTokenDTO : OauthTokenDTO = OauthTokenDTOFixture.create()
        ): OauthResponseDTO {
            return OauthResponseDTO(
                    clientId = clientId
                    , accountId = accountId
                    , timezoneOffset = timezoneOffset
                    , lightstreamerEndpoint = lightstreamerEndpoint
                    , oauthTokenDTO = oauthTokenDTO
            )
        }
    }
}

class OauthTokenFixture {
    companion object{
        fun create(
                access_token: String = "access_token"
                , refresh_token : String = "refresh_token"
                , scope : String = "demo"
                , token_type : String = "token_type"
                , expires_in : Int = 60
                , creationDateTime : LocalDateTime = LocalDateTime.now().plusSeconds(60)

        ): OauthToken{
            return OauthToken(
                    access_token = access_token
                    , refresh_token = refresh_token
                    , scope = scope
                    , token_type = token_type
                    , expires_in = expires_in
                    , creationDateTime = creationDateTime
            )
        }
    }
}

class OauthTokenDTOFixture {
    companion object{
        fun create(
                access_token: String = "access_token"
                , refresh_token : String = "refresh_token"
                , scope : String = "demo"
                , token_type : String = "token_type"
                , expires_in : Int = 60
                , expires_date_time : LocalDateTime = LocalDateTime.now().plusSeconds(60)

        ): OauthTokenDTO {
            return OauthTokenDTO(
                    access_token = access_token
                    , refresh_token = refresh_token
                    , scope = scope
                    , token_type = token_type
                    , expires_in = expires_in
//                    , expiresDateTime = expires_date_time
            )
        }
    }
}

class StreamTokenFixture {
    companion object{
        fun create(
                cst: String = ""
                , xSecurityToken : String = ""
                , streamEndpoint : String = ""
        ): StreamToken {
            return StreamToken(
                    cst = cst
                    , xSecurityToken = xSecurityToken
                    , streamEndpoint = streamEndpoint
            )
        }
    }
}

class PriceDetailsFixture {
    companion object{
        fun create(
                epicName : String = "Some Epic Name"
                , snapshotTime : String = "01/01/2020 00:00:00"
                , openPrice : OpenPrice = OpenPriceFixture.create()
                , closePrice : ClosePrice = ClosePriceFixture.create()
                , highPrice : HighPrice = HighPriceFixture.create()
                , lowPrice : LowPrice = LowPriceFixture.create()
                , lastTradedVolume : Int = 100
        ): PricesDetails{
            return PricesDetails(
                    epicName = epicName
                    , snapshotTime = snapshotTime
                    , openPrice = openPrice
                    , closePrice = closePrice
                    , highPrice = highPrice
                    , lowPrice = lowPrice
                    , lastTradedVolume = lastTradedVolume
            )
        }
    }
}

class LowPriceFixture {
    companion object{
        fun create(
                bid : Double = 0.0
                , ask : Double = 0.0
                , lastTraded : String = "01/01/2020 00:00:00"
        ): LowPrice{
            return LowPrice(
                    bid = bid
                    , ask = ask
                    , lastTraded = lastTraded
            )
        }
    }
}

class HighPriceFixture {
    companion object{
        fun create(
                bid : Double = 0.0
                , ask : Double = 0.0
                , lastTraded : String = "01/01/2020 00:00:00"
        ): HighPrice{
            return HighPrice(
                    bid = bid
                    , ask = ask
                    , lastTraded = lastTraded
            )
        }
    }
}

class ClosePriceFixture {
    companion object{
        fun create(
                bid : Double = 0.0
                , ask : Double = 0.0
                , lastTraded : String = "01/01/2020 00:00:00"
        ): ClosePrice{
            return ClosePrice(
                    bid = bid
                    , ask = ask
                    , lastTraded = lastTraded
            )
        }
    }
}

class OpenPriceFixture {
    companion object{
        fun create(
                bid : Double = 0.0
                , ask : Double = 0.0
                , lastTraded : String = "01/01/2020 00:00:00"
        ): OpenPrice{
            return OpenPrice(
                    bid = bid
                    , ask = ask
                    , lastTraded = lastTraded
            )
        }
    }
}

class PricesDTOFixture {
    companion object{
        fun create(
                pricesDetails : List<PriceDetailsDTO> = listOf(PriceDetailsDTOFixture.create()),
                instrumentType : String = "instrumentType",
                allowance : AllowanceDTO = AllowanceDTOFixture.create()
        ): PricesDTO
        {
            return PricesDTO(

                    pricesDetails = pricesDetails
                    , instrumentType = instrumentType
                    , allowance = allowance
            )
        }

    }
}

class AllowanceDTOFixture {
    companion object{
        fun create(
                remainingAllowance : Int = 0
                , totalAllowance : Int = 0
                , allowanceExpiry : Int = 0
        ): AllowanceDTO {
            return AllowanceDTO(
                    remainingAllowance = remainingAllowance
                    , totalAllowance = totalAllowance
                    , allowanceExpiry = allowanceExpiry
            )
        }
    }
}

class PriceDetailsDTOFixture {
        companion object{
            fun create(
                    snapshotTime : String = ""
                    , openPrice : OpenPriceDTO = OpenPriceDTOFixture.create()
                    , closePrice : ClosePriceDTO = ClosePriceDTOFixture.create()
                    , highPrice : HighPriceDTO = HighPriceDTOFixture.create()
                    , lowPrice : LowPriceDTO = LowPriceDTOFixture.create()
                    , lastTradedVolume : Int = 0
            ): PriceDetailsDTO {
                return PriceDetailsDTO(
                        snapshotTime = snapshotTime
                        ,openPrice = openPrice
                        ,closePrice= closePrice
                        ,highPrice = highPrice
                        ,lowPrice =lowPrice
                        ,lastTradedVolume = lastTradedVolume
                )
            }
        }
}

class OpenPriceDTOFixture {
    companion object{
        fun create(
                bid: Double = 0.0
                , ask : Double = 0.0
                , lastTraded : String = ""
        ): OpenPriceDTO {
            return OpenPriceDTO(
                    bid = bid
                    , ask = ask
                    , lastTraded = lastTraded
            )
        }
    }
}

class ClosePriceDTOFixture {
    companion object {
        fun create(
                bid: Double = 0.0
                , ask : Double = 0.0
                , lastTraded : String = ""
        ): ClosePriceDTO {
            return ClosePriceDTO(
                    bid = bid
                    , ask = ask
                    , lastTraded = lastTraded
            )
        }
    }
}

class HighPriceDTOFixture {
    companion object {
        fun create(
                bid: Double = 0.0
                , ask : Double = 0.0
                , lastTraded : String = ""
        ): HighPriceDTO {
            return HighPriceDTO(
                    bid = bid
                    , ask = ask
                    , lastTraded = lastTraded
            )
        }
    }
}

class LowPriceDTOFixture {
    companion object {
        fun create(
                bid: Double = 0.0
                , ask : Double = 0.0
                , lastTraded : String = ""
        ): LowPriceDTO {
            return LowPriceDTO(
                    bid = bid
                    , ask = ask
                    , lastTraded = lastTraded
            )
        }
    }
}

//todo feels redundant to have an object to to have a list<Market>
class MarketsFixture {
    companion object {
        fun create(
                markets : List<Market> = listOf(MarketFixture.create())
        ): Markets {
            return Markets(
                    markets = markets
            )
        }
    }
}

class MarketFixture {
    companion object {
        fun create(
                id: Int = 1,
                name: String = "MarketName"
        ): Market {
            return Market(
                    id = id
                    , name = name
            )
        }
    }
}

//todo feels redundant to have an object to to have a list<Epic>
class EpicsFixture {
    companion object {
        fun create(
                nodes : List<Epic> = listOf(EpicFixture.create())
        ): Epics{
            return Epics(
                    nodes = nodes
            )
        }
    }
}

class EpicFixture {
    companion object {
        fun create(
                name : String = "Epic Name"
                , marketId: Int = 1
        ): Epic {
            return Epic(
                   name = name
                    , marketId = marketId
            )
        }
    }
}

class MultipleEpicDetailsFixture {
    companion object {
        fun create(
               multipleEpicDetails : List<EpicDetail> = listOf(EpicDetailsFixture.create())
        ): MultipleEpicDetails {
            return MultipleEpicDetails(
                    multipleEpicDetails = multipleEpicDetails
            )
        }
    }
}

class EpicDetailsFixture {
    companion object {
        fun create(
                instrument: Instrument = InstrumentFixture.create()
                , dealingRules : DealingRules = DealingRulesFixture.create()
                , snapshot : Snapshot = SnapshotFixture.create()
        ): EpicDetail {
            return EpicDetail(
                    instrument = instrument
                    , dealingRules = dealingRules
                    , snapshot = snapshot
            )
        }
    }
}

//Todo make this reflect an actual snapshot
class SnapshotFixture {
    companion object {
        fun create(
                marketStatus : String = "Market status"
                , netChange: Double = 0.1
                , percentageChange: Double = 0.1
                , updateTime : String = "01/01/2020 00:00:00"
                , delayTime : Int = 1
                , bid : Double = 0.1
                , offer : Double = 4342.1
                , high : Double = 375.21
                , low : Double = 345.65
                , binaryOdds : String = "binary odds"
                , decimalPlacesFactor : Int = 2
                , scalingFactor : Int = 1
                , controlledRiskExtraSpread : Int = 1

        ): Snapshot{
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
}

class DealingRulesFixture {
    companion object {
        fun create(
                minStepDistance: MinStepDistance = MinStepDistanceFixture.create()
                , minDealSize: MinDealSize = MinDealSizeFixture.create()
                , minControlledRiskStopDistance : MinControlledRiskStopDistance = MinControlledRiskStopDistanceFixture.create()
                , minNormalStopOrLimitDistance : MinNormalStopOrLimitDistance = MinNormalStopOrLimitDistanceFixture.create()
                , maxStopOrLimitDistance: MaxStopOrLimitDistance = MaxStopOrLimitDistanceFixture.create()
                , marketOrderPreference : String = "Market order preference"
                , trailingStopsPreference : String = "Trailing stops preference"
        ): DealingRules {
            return DealingRules(
                    minStepDistance = minStepDistance
                    , minDealSize = minDealSize
                    , minControlledRiskStopDistance = minControlledRiskStopDistance
                    , minNormalStopOrLimitDistance = minNormalStopOrLimitDistance
                    , maxStopOrLimitDistance = maxStopOrLimitDistance
                    , marketOrderPreference = marketOrderPreference
                    , trailingStopsPreference = trailingStopsPreference
            )
        }
    }
}

class MaxStopOrLimitDistanceFixture {
    companion object {
        fun create(
                unit : String = "EUR"
                , value : Int = 1
        ): MaxStopOrLimitDistance {
            return MaxStopOrLimitDistance(
                    unit = unit
                    , value = value
            )
        }
    }
}

class MinNormalStopOrLimitDistanceFixture {
    companion object {
        fun create(
                unit : String = "EUR"
                , value : Int = 1
        ) : MinNormalStopOrLimitDistance {
            return MinNormalStopOrLimitDistance(
                    unit = unit
                    , value = value
            )
        }
    }
}

class MinControlledRiskStopDistanceFixture {
    companion object {
        fun create(
                unit : String = "EUR"
                , value : Int = 1
        ) : MinControlledRiskStopDistance {
            return MinControlledRiskStopDistance(
                    unit = unit
                    , value = value
            )
        }
    }
}

class MinDealSizeFixture {
    companion object {
        fun create(
                unit : String = "EUR"
                , value : Double = 1.0
        ): MinDealSize {
            return MinDealSize(
                    unit = unit
                    , value = value
            )
        }
    }
}

class MinStepDistanceFixture {
    companion object {
        fun create(
                unit : String = "EUR"
                , value : Int = 1
        ): MinStepDistance {
            return MinStepDistance(
                    unit = unit
                    , value = value
            )
        }
    }
}


class PriceRequestFixture {
    companion object {
        fun create(
                epic : String = "epic Name"
                , resolution : ResolutionDTO = ResolutionDTO.MINUTE
                , startDate : LocalDateTime? = null
                , endDate : LocalDateTime? = null
                , dataPoints : Int = 10
        ): PriceRequest{
            return PriceRequest(
                    epic = epic
                    , resolution = resolution
                    , startDate = startDate
                    , endDate = endDate
                    , dataPoints = dataPoints
            )
        }
    }
}