package com.credman.cmwallet.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PnvTokenDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(token: PnvTokenEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tokens: List<PnvTokenEntity>): List<Long>

    @Update
    suspend fun update(token: PnvTokenEntity)

    @Delete
    suspend fun delete(token: PnvTokenEntity)

    @Query("DELETE FROM pnv_tokens WHERE tokenId = :tokenId")
    suspend fun deleteById(tokenId: String)

    @Query("SELECT * FROM pnv_tokens")
    fun getAll(): Flow<List<PnvTokenEntity>>

    @Query("SELECT * FROM pnv_tokens WHERE tokenId = :tokenId")
    suspend fun getById(tokenId: String): PnvTokenEntity?

    @Query("SELECT * FROM pnv_tokens WHERE vct = :vct")
    fun getByVct(vct: String): Flow<List<PnvTokenEntity>>
}
