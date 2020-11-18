package com.akeijser.igtrader.api.testhelpers

import com.akeijser.igtrader.api.instruments.InstrumentRepository
import com.akeijser.igtrader.api.markets.MarketsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

interface ResetDB {
    fun reset()
}

//Todo: should be able to just add resetDB : ResetDB to constructor of class
// and have this config set a default and pick the one needed for the class where resetDB is injected in. @Primary does not cut it
//@Configuration
//class ResetDBConfig{
//
//    @Autowired
//    private val instrumentRepository = InstrumentRepository()
//
//    @Autowired
//    private val marketsRepository = MarketsRepository()
//
//    @Bean
//    @ConditionalOnClass(value = [InstrumentRepositoryIntegrationTest::class])
//    fun resetInstrument(): ResetDB {
//        return ResetInstrument()
//    }
//
//    @Bean
//    @ConditionalOnClass(value = [MarketsRepositoryIntegrationTest::class])
//    fun resetMarket(): ResetDB {
//        return ResetMarket()
//    }
//}

@Repository
class ResetMarket : ResetDB {

    @Autowired
    lateinit var marketsRepository: MarketsRepository

     override fun reset() {
         println("ResetMarket.reset()")

         marketsRepository.deleteAll()

    }
}

@Repository
class ResetInstrument: ResetDB{

    @Autowired
    lateinit var instrumentRepository: InstrumentRepository
    override fun reset() {
        println("ResetInstument.reset()")
        instrumentRepository.deleteAll()

    }
}

