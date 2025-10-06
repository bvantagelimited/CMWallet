package com.credman.cmwallet.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.credman.cmwallet.pnv.PnvTokenRegistry

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PnvTokenEditDialog(
    token: PnvTokenRegistry?,
    onDismiss: () -> Unit,
    onSave: (PnvTokenRegistry) -> Unit
) {
    var tokenId by remember { mutableStateOf(token?.tokenId ?: "") }
    var vct by remember { mutableStateOf(token?.vct ?: PnvTokenRegistry.VCT_VERIFY_PHONE_NUMBER) }
    var title by remember { mutableStateOf(token?.title ?: "") }
    var subtitle by remember { mutableStateOf(token?.subtitle ?: "") }
    var providerConsent by remember { mutableStateOf(token?.providerConsent ?: "") }
    var subscriptionHint by remember { mutableStateOf(token?.subscriptionHint?.toString() ?: "1") }
    var carrierHint by remember { mutableStateOf(token?.carrierHint ?: "") }
    var androidCarrierHint by remember { mutableStateOf(token?.androidCarrierHint?.toString() ?: "3") }
    var phoneNumberHint by remember { mutableStateOf(token?.phoneNumberHint ?: "") }
    var iss by remember { mutableStateOf(token?.iss ?: "") }
    var icon by remember { mutableStateOf(token?.icon ?: "") }
    var phoneNumberAttributeDisplayName by remember { 
        mutableStateOf(token?.phoneNumberAttributeDisplayName ?: "Phone number") 
    }
    var verifierTermsPrefix by remember { mutableStateOf(token?.verifierTermsPrefix ?: "") }
    
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = if (token == null) "Add PNV Token" else "Edit PNV Token",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    OutlinedTextField(
                        value = tokenId,
                        onValueChange = { tokenId = it },
                        label = { Text("Token ID *") },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = token == null, // Only editable when creating new
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // VCT Dropdown
                    var vctExpanded by remember { mutableStateOf(false) }
                    ExposedDropdownMenuBox(
                        expanded = vctExpanded,
                        onExpandedChange = { vctExpanded = !vctExpanded }
                    ) {
                        OutlinedTextField(
                            value = vct,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("VCT Type *") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = vctExpanded) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = vctExpanded,
                            onDismissRequest = { vctExpanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Verify Phone Number") },
                                onClick = {
                                    vct = PnvTokenRegistry.VCT_VERIFY_PHONE_NUMBER
                                    vctExpanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Get Phone Number") },
                                onClick = {
                                    vct = PnvTokenRegistry.VCT_GET_PHONE_NUMBER
                                    vctExpanded = false
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Title *") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = subtitle,
                        onValueChange = { subtitle = it },
                        label = { Text("Subtitle") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = providerConsent,
                        onValueChange = { providerConsent = it },
                        label = { Text("Provider Consent") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 2,
                        maxLines = 4
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = phoneNumberHint,
                        onValueChange = { phoneNumberHint = it },
                        label = { Text("Phone Number Hint") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        placeholder = { Text("+16502157890") }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = subscriptionHint,
                        onValueChange = { subscriptionHint = it },
                        label = { Text("Subscription Hint *") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = carrierHint,
                        onValueChange = { carrierHint = it },
                        label = { Text("Carrier Hint *") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        placeholder = { Text("310250") }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = androidCarrierHint,
                        onValueChange = { androidCarrierHint = it },
                        label = { Text("Android Carrier Hint *") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = iss,
                        onValueChange = { iss = it },
                        label = { Text("Issuer (iss) *") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        placeholder = { Text("https://example.com") }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = icon,
                        onValueChange = { icon = it },
                        label = { Text("Icon (Base64)") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 2,
                        maxLines = 4
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = phoneNumberAttributeDisplayName,
                        onValueChange = { phoneNumberAttributeDisplayName = it },
                        label = { Text("Phone Number Display Name *") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = verifierTermsPrefix,
                        onValueChange = { verifierTermsPrefix = it },
                        label = { Text("Verifier Terms Prefix") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 2,
                        maxLines = 3
                    )

                    if (showError) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancel")
                    }
                    Button(
                        onClick = {
                            // Validate required fields
                            if (tokenId.isBlank() || title.isBlank() || carrierHint.isBlank() || 
                                iss.isBlank() || phoneNumberAttributeDisplayName.isBlank()) {
                                showError = true
                                errorMessage = "Please fill in all required fields (*)"
                                return@Button
                            }

                            val subHint = subscriptionHint.toIntOrNull()
                            val androidCarrierHintInt = androidCarrierHint.toIntOrNull()
                            
                            if (subHint == null || androidCarrierHintInt == null) {
                                showError = true
                                errorMessage = "Subscription and Android Carrier hints must be valid integers"
                                return@Button
                            }

                            val newToken = PnvTokenRegistry(
                                tokenId = tokenId.trim(),
                                vct = vct,
                                title = title.trim(),
                                subtitle = subtitle.trim().takeIf { it.isNotBlank() },
                                providerConsent = providerConsent.trim().takeIf { it.isNotBlank() },
                                subscriptionHint = subHint,
                                carrierHint = carrierHint.trim(),
                                androidCarrierHint = androidCarrierHintInt,
                                phoneNumberHint = phoneNumberHint.trim().takeIf { it.isNotBlank() },
                                iss = iss.trim(),
                                icon = icon.trim().takeIf { it.isNotBlank() },
                                phoneNumberAttributeDisplayName = phoneNumberAttributeDisplayName.trim(),
                                supportedAggregatorIssNames = null,
                                verifierTermsPrefix = verifierTermsPrefix.trim().takeIf { it.isNotBlank() }
                            )
                            onSave(newToken)
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}
