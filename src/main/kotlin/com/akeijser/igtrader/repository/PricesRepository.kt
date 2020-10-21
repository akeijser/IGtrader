package com.akeijser.igtrader.repository

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.Persistence
import javax.persistence.PersistenceException
import javax.transaction.Transactional

@Repository
class PricesRepository(private val marketsRepository: MarketsRepository) {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(PricesRepository::class.java)
    }

    private val em: EntityManager = Persistence
            .createEntityManagerFactory("persistenceUnitName")
            .createEntityManager()

    @Transactional
    fun insertPriceDetails(priceDetailsDBO: PriceDetailsDBO){

        try {
            em.transaction.begin()

            //Since priceDetailsDBO.instrument is manyToOne we have to make sure its in the  persistence context
            //todo: not to happy with an Instrument insert in the PricesRepository, this should not be required
            if (priceDetailsDBO.instrument.id == null) {
                priceDetailsDBO.instrument = em.getReference(InstrumentDBO::class.java, marketsRepository.insertInstrument(priceDetailsDBO.instrument).id)
            } else {
                priceDetailsDBO.instrument = em.getReference(InstrumentDBO::class.java, priceDetailsDBO.instrument.id)
            }

            em.persist(priceDetailsDBO)
            em.transaction.commit()
            em.clear()
        } catch (e: PersistenceException) {
            LOGGER.error("error inserting price details${e.localizedMessage}")
            em.clear()
        }
    }
}