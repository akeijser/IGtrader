package com.akeijser.igtrader.api.instruments

import com.akeijser.igtrader.dbo.*
import com.akeijser.igtrader.domain.Instrument
import com.akeijser.igtrader.domain.Market
import org.slf4j.LoggerFactory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.persistence.EntityManager
import javax.persistence.Persistence
import javax.persistence.PersistenceException

@Transactional
@Repository
interface InstrumentRepository : JpaRepository<InstrumentDBO, UUID>{

//    fun find(epic: String): Instrument {
//        return Instrument(findByEpic(epic))
//    }

    fun findByEpic(epic: String): InstrumentDBO?

//    companion object{
//        private val LOGGER = LoggerFactory.getLogger(InstrumentRepository::class.java)
//    }

//    fun get(): List<Instrument> {
//        val em: EntityManager = Persistence
//                .createEntityManagerFactory("persistenceUnitName")
//                .createEntityManager()
//
//        return em.createQuery("from InstrumentDBO", InstrumentDBO::class.java).resultList.map { Instrument(it) }
//    }
//
//    fun insertInstrument(dbo: InstrumentDBO): Instrument {
//
//        val em: EntityManager = Persistence
//                .createEntityManagerFactory("persistenceUnitName")
//                .createEntityManager()
//
//        return try {
//            em.transaction.begin()
//            em.persist(dbo)
//            em.transaction.commit()
//            Instrument(dbo)
//        } catch (e: PersistenceException) {
//            LOGGER.error("error inserting an instrument ${e.localizedMessage}")
//            throw e
//        }
//    }
}

/*
A bunch of unused inserts I might need later

private fun insertSpecialInfo(dbo: SpecialInfoDBO) {
        val em: EntityManager = Persistence
                .createEntityManagerFactory("persistenceUnitName")
                .createEntityManager()

        em.transaction.begin()
        em.persist(dbo)
        em.transaction.commit()
    }

    private fun insertLimitedRiskPremium(dbo: LimitedRiskPremiumDBO) {
        val em: EntityManager = Persistence
                .createEntityManagerFactory("persistenceUnitName")
                .createEntityManager()

        em.transaction.begin()
        em.persist(dbo)
        em.transaction.commit()
    }

    private fun insertSlippageFactor(dbo: SlippageFactorDBO): SlippageFactor {
        val em: EntityManager = Persistence
                .createEntityManagerFactory("persistenceUnitName")
                .createEntityManager()

        em.transaction.begin()
        em.persist(dbo)
        em.transaction.commit()
        return SlippageFactor(dbo)
    }

    private fun insertMarginDepositBands(dbo: MarginDepositBandsDBO) {
        val em: EntityManager = Persistence
                .createEntityManagerFactory("persistenceUnitName")
                .createEntityManager()

        em.transaction.begin()
        em.persist(dbo)
        em.transaction.commit()
    }

    private fun insertCurrency(dbo: CurrenciesDBO) : Currencies {
        val em: EntityManager = Persistence
                .createEntityManagerFactory("persistenceUnitName")
                .createEntityManager()

        em.transaction.begin()
        em.persist(dbo)
        em.transaction.commit()

        return Currencies(dbo)
    }
 */