package com.akeijser.igtrader.repository

import com.akeijser.igtrader.domainobjects.*
import org.springframework.data.util.ProxyUtils
import java.io.Serializable
import java.util.*
import javax.persistence.*

@MappedSuperclass
abstract class AbstractJpaPersistable<out T : Serializable> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private var id: T? = null

    fun getId(): T? {
        return id
    }

    override fun equals(other: Any?): Boolean {
        other ?: return false

        if (this === other) return true

        if (javaClass != ProxyUtils.getUserClass(other)) return false

        other as AbstractJpaPersistable<*>

        return if (null == this.getId()) false else this.getId() == other.getId()
    }

    override fun hashCode(): Int {
        return 31
    }

    override fun toString() = "Entity of type ${this.javaClass.name} with id: $id"
}

@Entity
@Table(name = "markets")
data class MarketDBO (
        @Id
        @Column(name ="id", unique = true)
        val id : Int?,
        @Column(name = "name")
        val name : String?
)


@Entity
@Table(name = "epics")
data class EpicDBO (

        @Column(name = "name")
        val name : String?,
        @Column(name="market_id")
        val marketId : Int?
): AbstractJpaPersistable<UUID>()


@Entity
@Table(name = "epic_details")
data class EpicDetailsDBO (
        @Column(name = "epic_name")
        val epicName : String?,
        @OneToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "instrument_id", referencedColumnName = "id")
        val instrument : InstrumentDBO?,
        @OneToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "dealing_rules_id", referencedColumnName = "id")
        val dealingRules : DealingRulesDBO?,
        @OneToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "snapshot_id", referencedColumnName = "id")
        val snapshot : SnapshotDBO?
): AbstractJpaPersistable<UUID>()

@Entity
@Table(name = "instrument")
@NamedQueries(
        NamedQuery(name = "instrument.findByEpic",
                query = "SELECT i FROM InstrumentDBO i WHERE i.epic = :epic")
)
data class InstrumentDBO (
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: UUID? = null,
        @Column(unique = true)
        val epic : String?,
        val expiry : String?,
        val name : String?,
        val forceOpenAllowed : Boolean?,
        val stopsLimitsAllowed : Boolean?,
        val lotSize : Int?,
        val unit : String?,
        val type : String?,
        val controlledRiskAllowed : Boolean?,
        val streamingPricesAvailable : Boolean?,
        val marketId : String?,
        @OneToMany(cascade = [CascadeType.ALL])
        @JoinColumn(name = "instrument_id", referencedColumnName = "id")
        val currencies : List<CurrenciesDBO>?,
        val sprintMarketsMinimumExpiryTime : String?,
        val sprintMarketsMaximumExpiryTime : String?,
        @OneToMany(cascade = [CascadeType.ALL])
        @JoinColumn(name = "instrument_id", referencedColumnName = "id")
        val marginDepositBands : List<MarginDepositBandsDBO>?,
        val marginFactor : Int?,
        val marginFactorUnit : String?,
        @OneToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "slippage_factor_id", referencedColumnName = "id")
        val slippageFactor : SlippageFactorDBO?,
        @OneToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "limited_risk_premium_id", referencedColumnName = "id")
        val limitedRiskPremium : LimitedRiskPremiumDBO?,
        val openingHours : String?,
        val expiryDetails : String?,
        val rolloverDetails : String?,
        val newsCode : String?,
        val chartCode : String?,
        val country : String?,
        val valueOfOnePip : Double?,
        val onePipMeans : Int?,
        val contractSize : Int?,
        @OneToMany(cascade = [CascadeType.ALL])
        @JoinColumn(name = "instrument_id", referencedColumnName = "id")
        val specialInfo : List<SpecialInfoDBO>?

) {
    //Val placed outside the constructor to bypass toHash and toString. To make sure no stackoverflow takes place on PriceDetailsDBO.toString
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "instrument", fetch = FetchType.LAZY )
    val priceDetails : MutableList<PriceDetailsDBO>? = mutableListOf()

    constructor(domain: Instrument) : this(
            id = domain.id
            , epic = domain.epic
            , expiry = domain.expiry
            , name = domain.name
            , forceOpenAllowed = domain.forceOpenAllowed
            , stopsLimitsAllowed = domain.stopsLimitsAllowed
            , lotSize = domain.lotSize
            , unit = domain.unit
            , type = domain.type
            , controlledRiskAllowed = domain.controlledRiskAllowed
            , streamingPricesAvailable = domain.streamingPricesAvailable
            , marketId = domain.marketId
            , currencies = domain.currencies?.map { CurrenciesDBO(it) }
            , sprintMarketsMinimumExpiryTime = domain.sprintMarketsMinimumExpiryTime
            , sprintMarketsMaximumExpiryTime = domain.sprintMarketsMaximumExpiryTime
            , marginDepositBands = domain.marginDepositBands?.map { MarginDepositBandsDBO(it) }
            , marginFactor = domain.marginFactor
            , marginFactorUnit = domain.marginFactorUnit
            , slippageFactor = SlippageFactorDBO(domain.slippageFactor)
            , limitedRiskPremium = LimitedRiskPremiumDBO(domain.limitedRiskPremium)
            , openingHours = domain.openingHours
            , expiryDetails = domain.expiryDetails
            , rolloverDetails = domain.rolloverDetails
            , newsCode = domain.newsCode
            , chartCode = domain.chartCode
            , country = domain.country
            , valueOfOnePip = domain.valueOfOnePip
            , onePipMeans = domain.onePipMeans
            , contractSize = domain.contractSize
            , specialInfo = domain.specialInfo?.map { SpecialInfoDBO(it) }

    )
}

@Entity
@Table(name = "special_info")
data class SpecialInfoDBO (
        val info : String
): AbstractJpaPersistable<UUID>()

@Entity
@Table(name = "currencies")
data class CurrenciesDBO (
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: UUID? = null,
        val code : String?,
        val symbol : String?,
        val baseExchangeRate : Double?,
        val exchangeRate : Double?,
        val isDefault : Boolean?
) {
    constructor(domain: Currencies) : this(
            id = domain.id
            , code = domain.code
            , symbol = domain.symbol
            , baseExchangeRate = domain.baseExchangeRate
            , exchangeRate = domain.exchangeRate
            , isDefault = domain.isDefault
    )
}

@Entity
@Table(name = "dealing_rules")
data class DealingRulesDBO (
        @OneToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "min_step_distance_id", referencedColumnName = "id")
        val minStepDistance : MinStepDistanceDBO?,
        @OneToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "min_deal_size_id", referencedColumnName = "id")
        val minDealSize : MinDealSizeDBO?,
        @OneToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "min_controlled_risk_stop_distance_id", referencedColumnName = "id")
        val minControlledRiskStopDistance : MinControlledRiskStopDistanceDBO?,
        @OneToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "min_normal_stop_or_limit_distance_id", referencedColumnName = "id")
        val minNormalStopOrLimitDistance : MinNormalStopOrLimitDistanceDBO?,
        @OneToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "max_stop_or_limit_distance_id", referencedColumnName = "id")
        val maxStopOrLimitDistance : MaxStopOrLimitDistanceDBO?,
        val marketOrderPreference : String?,
        val trailingStopsPreference : String?
): AbstractJpaPersistable<UUID>()

@Entity
@Table(name = "limited_risk_premium")
data class LimitedRiskPremiumDBO (
        val value : Int?,
        val unit : String?
): AbstractJpaPersistable<UUID>() {
    constructor(domain: LimitedRiskPremium?) : this(
            value = domain?.value
            , unit = domain?.unit
    )
}

@Entity
@Table(name = "margin_deposit_bands")
data class MarginDepositBandsDBO (
        val min : Int?,
        val max : Int?,
        val margin : Int?,
        val currency : String?
): AbstractJpaPersistable<UUID>() {
    constructor(domain: MarginDepositBands) : this(
            min = domain.min
            , max = domain.max
            , margin = domain.margin
            , currency = domain.currency
    )
}

@Entity
@Table(name = "max_stop_or_limit_distance")
data class MaxStopOrLimitDistanceDBO (
        val unit : String?,
        val value : Int?
): AbstractJpaPersistable<UUID>()

@Entity
@Table(name = "min_controlled_risk_stop_distance")
data class MinControlledRiskStopDistanceDBO (
        val unit : String?,
        val value : Int?
): AbstractJpaPersistable<UUID>()

@Entity
@Table(name = "min_deal_size")
data class MinDealSizeDBO (
        val unit : String?,
        val value : Double?
): AbstractJpaPersistable<UUID>()

@Entity
@Table(name = "min_normal_stop_or_limit_distance")
data class MinNormalStopOrLimitDistanceDBO (
        val unit : String?,
        val value : Int?
): AbstractJpaPersistable<UUID>()

@Entity
@Table(name = "min_stop_distance")
data class MinStepDistanceDBO (
        val unit : String?,
        val value : Int?
): AbstractJpaPersistable<UUID>()

@Entity
@Table(name = "slippage_factor")
data class SlippageFactorDBO (
        val unit : String?,
        val value : Int?
): AbstractJpaPersistable<UUID>() {
    constructor(domain: SlippageFactor?) : this(
            unit = domain?.unit
            , value = domain?.value
    )
}

@Entity
@Table(name = "snapshot")
data class SnapshotDBO (
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
): AbstractJpaPersistable<UUID>()

@Entity
@Table(name = "close_price")
data class ClosePriceDBO (
        @Column(name = "bid")
        val bid : Double?,
        @Column(name = "ask")
        val ask : Double?,
        @Column(name = "last_traded")
        val lastTraded : String?
): AbstractJpaPersistable<UUID>(){
    constructor(closePrice: ClosePrice) : this(
            bid = closePrice.bid,
            ask = closePrice.ask,
            lastTraded = closePrice.lastTraded
    )
}

@Entity
@Table(name = "high_price")
data class HighPriceDBO (
        @Column(name = "bid")
        val bid : Double?,
        @Column(name = "ask")
        val ask : Double?,
        @Column(name = "last_traded")
        val lastTraded : String?
): AbstractJpaPersistable<UUID>(){
    constructor(highPrice: HighPrice) : this(
            bid = highPrice.bid,
            ask = highPrice.ask,
            lastTraded = highPrice.lastTraded
    )
}

@Entity
@Table(name = "low_price")
data class LowPriceDBO (
        @Column(name = "bid")
        val bid : Double?,
        @Column(name = "ask")
        val ask : Double?,
        @Column(name = "last_traded")
        val lastTraded : String?
): AbstractJpaPersistable<UUID>(){
    constructor(lowPrice: LowPrice) : this(
            bid = lowPrice.bid,
            ask = lowPrice.ask,
            lastTraded = lowPrice.lastTraded
    )
}

@Entity
@Table(name = "open_price")
data class OpenPriceDBO (
        @Column(name = "bid")
        val bid : Double?,
        @Column(name = "ask")
        val ask : Double?,
        @Column(name = "last_traded")
        val lastTraded : String?
): AbstractJpaPersistable<UUID>() {
    constructor(openPrice: OpenPrice) : this(
            bid = openPrice.bid,
            ask = openPrice.ask,
            lastTraded = openPrice.lastTraded
    )
}

@Entity
@Table(name = "price_details")
data class PriceDetailsDBO (

        @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        @JoinColumn(name = "instrument_id", referencedColumnName = "id")
        var instrument: InstrumentDBO,
        @Column(name = "snapshot_time")
        val snapshotTime : String?,
        @OneToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "open_price_id", referencedColumnName = "id")
        val openPrice : OpenPriceDBO?,
        @OneToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "close_price_id", referencedColumnName = "id")
        val closePrice : ClosePriceDBO?,
        @OneToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "high_price_id", referencedColumnName = "id")
        val highPrice : HighPriceDBO?,
        @OneToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "low_price_id", referencedColumnName = "id")
        val lowPrice : LowPriceDBO?,
        @Column(name = "last_traded_volume")
        val lastTradedVolume : Int?
): AbstractJpaPersistable<UUID>() {
    constructor(pricesDetails: PricesDetails): this(
            instrument = InstrumentDBO(pricesDetails.instrument)
            , snapshotTime = pricesDetails.snapshotTime
            , openPrice = pricesDetails.openPrice?.let { OpenPriceDBO(it) }
            , closePrice = pricesDetails.closePrice?.let { ClosePriceDBO(it) }
            , highPrice = pricesDetails.highPrice?.let { HighPriceDBO(it) }
            , lowPrice = pricesDetails.lowPrice?.let { LowPriceDBO(it) }
            , lastTradedVolume = pricesDetails.lastTradedVolume
    )
}

enum class  MarketSearchFilterDBO {
    ALL, SNAPSHOT_ONLY
}

enum class ResolutionDBO {
    MINUTE, MINUTE_2, MINUTE_3, MINUTE_5, MINUTE_10, MINUTE_15, MINUTE_30, HOUR, HOUR_2, HOUR_3, HOUR_4, DAY, WEEK, MONTH
}

