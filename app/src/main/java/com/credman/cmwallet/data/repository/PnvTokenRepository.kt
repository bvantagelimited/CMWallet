package com.credman.cmwallet.data.repository

import com.credman.cmwallet.data.source.PnvTokenDataSource
import com.credman.cmwallet.pnv.PnvTokenRegistry
import kotlinx.coroutines.flow.Flow

class PnvTokenRepository {
    private val dataSource = PnvTokenDataSource()

    val tokens: Flow<List<PnvTokenRegistry>> = dataSource.tokens

    suspend fun getToken(tokenId: String): PnvTokenRegistry? {
        return dataSource.getToken(tokenId)
    }

    suspend fun insertToken(token: PnvTokenRegistry) {
        dataSource.insertTokenSuspend(token)
    }

    suspend fun updateToken(token: PnvTokenRegistry) {
        dataSource.updateTokenSuspend(token)
    }

    suspend fun deleteToken(tokenId: String) {
        dataSource.deleteTokenSuspend(tokenId)
    }

    suspend fun initializeDefaultTokens() {
        dataSource.initializeDefaultTokens()
    }
}
