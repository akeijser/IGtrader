package com.akeijser.igtrader.repository

import com.akeijser.igtrader.domain.Instrument
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.NoResultException
import javax.persistence.Persistence
import javax.persistence.PersistenceException

@Transactional
@Repository
class MarketsRepository {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(MarketsRepository::class.java)
    }

    private val em: EntityManager = Persistence
            .createEntityManagerFactory("persistenceUnitName")
            .createEntityManager()

    fun insertMarket(market: MarketDBO) {
        try {
            em.transaction.begin()
            em.persist(market)
            em.transaction.commit()
        } catch (e: PersistenceException) {
            LOGGER.error("error inserting market ${e.localizedMessage}")
        }
    }

    fun insertEpic(epic: EpicDBO) {
        try {
            em.transaction.begin()
            em.persist(epic)
            em.transaction.commit()
        } catch (e: PersistenceException) {
            LOGGER.error("error inserting epic ${e.localizedMessage}")
        }
    }

    fun insertEpicDetails(epicDetailsDBO: EpicDetailsDBO){
        try {
            em.transaction.begin()
            em.persist(epicDetailsDBO)
            em.transaction.commit()
        } catch (e: PersistenceException) {
            LOGGER.error("error inserting epic details ${e.localizedMessage}")
        }
    }

    fun findInstrument(epic: String): Instrument? {
        return try {
            val instrumentDBO = em.createNamedQuery("instrument.findByEpic", InstrumentDBO::class.java)
                    .setParameter("epic", epic)
                    .singleResult
            Instrument(instrumentDBO)
        } catch (n: NoResultException) {
            LOGGER.info(n.localizedMessage)
            LOGGER.error("instrument not found exception ${n.localizedMessage}")
            null
        }
    }

    fun insertInstrument(instrumentDBO: InstrumentDBO): Instrument{
        return try {
            em.transaction.begin()
            em.persist(instrumentDBO)
            em.transaction.commit()
            Instrument(instrumentDBO)
        } catch (e: PersistenceException) {
            LOGGER.error("error inserting an instrument ${e.localizedMessage}")
            throw e
        }
    }
}

