package com.akeijser.igtrader.api.tokens

import com.akeijser.igtrader.dbo.OauthTokenDBO
import com.akeijser.igtrader.domain.OauthToken
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.Persistence
import javax.persistence.PersistenceException

@Repository
class TokensRepository {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(TokensRepository::class.java)
    }

    fun insertOAuthToken(oauthToken: OauthToken): OauthToken {

        val em: EntityManager = Persistence
                .createEntityManagerFactory("persistenceUnitName")
                .createEntityManager()

        val dbo = OauthTokenDBO(oauthToken)
        return try {
            em.transaction.begin()
            em.persist(dbo)
            em.transaction.commit()
            OauthToken(dbo)
        } catch (e: PersistenceException) {
            LOGGER.error("error inserting an instrument ${e.localizedMessage}")
            throw e
        }
    }

    fun findToken(): OauthToken? {

        val em: EntityManager = Persistence
                .createEntityManagerFactory("persistenceUnitName")
                .createEntityManager()

        val oauthTokenDBO = em.createNamedQuery("oauth_token.find", OauthTokenDBO::class.java)
        if (oauthTokenDBO.resultList.isEmpty()) {
            return null
        }
        println(OauthToken(oauthTokenDBO.resultList[0]))
        return OauthToken(oauthTokenDBO.resultList[0])

    }
}