package com.credman.cmwallet.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.credman.cmwallet.pnv.PnvTokenRegistry

@Entity(tableName = "pnv_tokens")
data class PnvTokenEntity(
    @PrimaryKey @ColumnInfo(name = "tokenId") val tokenId: String,
    @ColumnInfo(name = "vct") val vct: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "subtitle") val subtitle: String?,
    @ColumnInfo(name = "providerConsent") val providerConsent: String?,
    @ColumnInfo(name = "subscriptionHint") val subscriptionHint: Int,
    @ColumnInfo(name = "carrierHint") val carrierHint: String,
    @ColumnInfo(name = "androidCarrierHint") val androidCarrierHint: Int,
    @ColumnInfo(name = "phoneNumberHint") val phoneNumberHint: String?,
    @ColumnInfo(name = "iss") val iss: String,
    @ColumnInfo(name = "icon") val icon: String?,
    @ColumnInfo(name = "phoneNumberAttributeDisplayName") val phoneNumberAttributeDisplayName: String,
    @ColumnInfo(name = "supportedAggregatorIssNames") val supportedAggregatorIssNames: String?, // Comma-separated
    @ColumnInfo(name = "verifierTermsPrefix") val verifierTermsPrefix: String?
) {
    fun toPnvTokenRegistry(): PnvTokenRegistry {
        return PnvTokenRegistry(
            tokenId = tokenId,
            vct = vct,
            title = title,
            subtitle = subtitle,
            providerConsent = providerConsent,
            subscriptionHint = subscriptionHint,
            carrierHint = carrierHint,
            androidCarrierHint = androidCarrierHint,
            phoneNumberHint = phoneNumberHint,
            iss = iss,
            icon = icon,
            phoneNumberAttributeDisplayName = phoneNumberAttributeDisplayName,
            supportedAggregatorIssNames = supportedAggregatorIssNames?.split(",")?.toSet(),
            verifierTermsPrefix = verifierTermsPrefix
        )
    }

    companion object {
        fun fromPnvTokenRegistry(token: PnvTokenRegistry): PnvTokenEntity {
            return PnvTokenEntity(
                tokenId = token.tokenId,
                vct = token.vct,
                title = token.title,
                subtitle = token.subtitle,
                providerConsent = token.providerConsent,
                subscriptionHint = token.subscriptionHint,
                carrierHint = token.carrierHint,
                androidCarrierHint = token.androidCarrierHint,
                phoneNumberHint = token.phoneNumberHint,
                iss = token.iss,
                icon = token.icon,
                phoneNumberAttributeDisplayName = token.phoneNumberAttributeDisplayName,
                supportedAggregatorIssNames = token.supportedAggregatorIssNames?.joinToString(","),
                verifierTermsPrefix = token.verifierTermsPrefix
            )
        }
    }
}
