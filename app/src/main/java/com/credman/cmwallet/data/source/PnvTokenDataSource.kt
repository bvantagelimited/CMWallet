package com.credman.cmwallet.data.source

import com.credman.cmwallet.CmWalletApplication
import com.credman.cmwallet.data.room.PnvTokenEntity
import com.credman.cmwallet.pnv.PnvTokenRegistry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class PnvTokenDataSource {
    private val pnvTokenDao = CmWalletApplication.database.pnvTokenDao()
    private val scope = CoroutineScope(Dispatchers.IO)

    val tokens: Flow<List<PnvTokenRegistry>> = pnvTokenDao.getAll().map { entities ->
        entities.map { it.toPnvTokenRegistry() }
    }

    suspend fun getToken(tokenId: String): PnvTokenRegistry? {
        return pnvTokenDao.getById(tokenId)?.toPnvTokenRegistry()
    }

    fun insertToken(token: PnvTokenRegistry) {
        scope.launch {
            pnvTokenDao.insert(PnvTokenEntity.fromPnvTokenRegistry(token))
        }
    }

    suspend fun insertTokenSuspend(token: PnvTokenRegistry) {
        pnvTokenDao.insert(PnvTokenEntity.fromPnvTokenRegistry(token))
    }

    fun updateToken(token: PnvTokenRegistry) {
        scope.launch {
            pnvTokenDao.update(PnvTokenEntity.fromPnvTokenRegistry(token))
        }
    }

    suspend fun updateTokenSuspend(token: PnvTokenRegistry) {
        pnvTokenDao.update(PnvTokenEntity.fromPnvTokenRegistry(token))
    }

    fun deleteToken(tokenId: String) {
        scope.launch {
            pnvTokenDao.deleteById(tokenId)
        }
    }

    suspend fun deleteTokenSuspend(tokenId: String) {
        pnvTokenDao.deleteById(tokenId)
    }

    suspend fun initializeDefaultTokens() {
        val existingTokens = pnvTokenDao.getAll()
        // Only initialize if database is empty
        val tokens = listOf(
            PnvTokenRegistry.TEST_PNV_1_GET_PHONE_NUMBER,
            PnvTokenRegistry.TEST_PNV_1_VERIFY_PHONE_NUMBER,
            PnvTokenRegistry.TEST_PNV_2_VERIFY_PHONE_NUMBER
        )
        pnvTokenDao.insertAll(tokens.map { PnvTokenEntity.fromPnvTokenRegistry(it) })
    }
}
