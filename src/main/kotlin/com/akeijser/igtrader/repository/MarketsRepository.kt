package com.akeijser.igtrader.repository

import com.akeijser.igtrader.domainobjects.Instrument
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.NoResultException
import javax.persistence.Persistence
import javax.persistence.PersistenceException

@Transactional
@Repository
class MarketsRepository {

    private val em: EntityManager = Persistence
            .createEntityManagerFactory("persistenceUnitName")
            .createEntityManager()

    //todo add log
    fun insertMarket(market: MarketDBO) {
        try {
            em.transaction.begin()
            em.persist(market)
            em.transaction.commit()
        } catch (e: PersistenceException) {
            println(e.message)
        }
    }

    fun insertEpic(epic: EpicDBO) {
        try {
            em.transaction.begin()
            em.persist(epic)
            em.transaction.commit()
        } catch (e: PersistenceException) {
            println(e.message)
        }
    }

    fun insertEpicDetails(epicDetailsDBO: EpicDetailsDBO){
        try {
            em.transaction.begin()
            em.persist(epicDetailsDBO)
            em.transaction.commit()
        } catch (e: PersistenceException) {
            println(e.message)
        }
    }

    fun findInstrument(epic: String): Instrument? {
        return try {
            val instrumentDBO = em.createNamedQuery("instrument.findByEpic", InstrumentDBO::class.java)
                    .setParameter("epic", epic)
                    .singleResult
            Instrument(instrumentDBO)
        } catch (n: NoResultException) {
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
            println(e.message)
            throw e
        }
    }
}