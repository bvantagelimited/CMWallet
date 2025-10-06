package com.credman.cmwallet.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CredentialDatabaseItem::class, PnvTokenEntity::class], version = 4)
abstract class CredentialDatabase : RoomDatabase() {
    abstract fun credentialDao(): CredentialDao
    abstract fun pnvTokenDao(): PnvTokenDao
}