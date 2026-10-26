package com.example.ui.screens

import android.content.Intent
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockClock
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.PayloadClassification
import com.example.data.model.TacticalTemplate
import com.example.stego.StegoEngine
import com.example.ui.MainViewModel
import com.example.ui.TacticalNavTab
import com.example.ui.components.CapacityGauge
import com.example.ui.components.TacticalCard
import com.example.ui.components.TacticalClassificationBadge
import com.example.ui.components.TacticalTextField
import com.example.ui.theme.TacticalAlertRed
import com.example.ui.theme.TacticalAlertRedDim
import com.example.ui.theme.TacticalBlack
import com.example.ui.theme.TacticalCardBorder
import com.example.ui.theme.TacticalCardBorderActive
import com.example.ui.theme.TacticalCardSurface
import com.example.ui.theme.TacticalCyan
import com.example.ui.theme.TacticalDarkSurface
import com.example.ui.theme.TacticalDecoyBlue
import com.example.ui.theme.TacticalDecoyBorder
import com.example.ui.theme.TacticalDecoySurface
import com.example.ui.theme.TacticalEmerald
import com.example.ui.theme.TacticalGold
import com.example.ui.theme.TacticalTextMuted
import com.example.ui.theme.TacticalTextPrimary
import com.example.ui.theme.TacticalTextSecondary

@Composable
fun EmbedScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val embedState by viewModel.embedState.collectAsState()
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            viewModel.setCustomCarrierUri(uri)
        }
    }

    val presetNames = listOf(
        "Patroli Hutan",
        "Pos Perbatasan",
        "Depot Logistik",
        "Peta Kontur"
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(TacticalBlack),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Section 1: Carrier Media Selection
        item {
            TacticalCard(
                title = "1. Citra Media Carrier (Penyamaran)",
                badgeText = "FORMAT: LOSSLESS",
                badgeColor = TacticalCyan
            ) {
                Text(
                    text = "Pilih foto dokumentasi yang akan menjadi media pembawa pesan rahasia:",
                    fontSize = 11.sp,
                    color = TacticalTextSecondary
                )
                Spacer(modifier = Modifier.height(10.dp))

                // Presets horizontal selector
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    presetNames.forEachIndexed { index, name ->
                        val isSelected = embedState.selectedPresetIndex == index
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (isSelected) TacticalEmerald.copy(alpha = 0.2f) else TacticalDarkSurface)
                                .border(
                                    1.dp,
                                    if (isSelected) TacticalEmerald else TacticalCardBorder,
                                    RoundedCornerShape(6.dp)
                                )
                                .clickable { viewModel.loadPresetCarrier(index) }
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                                .testTag("carrier_preset_$index")
                        ) {
                            Text(
                                text = name,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) TacticalEmerald else TacticalTextSecondary,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }

                    // Custom from Gallery
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(if (embedState.selectedPresetIndex == -1) TacticalCyan.copy(alpha = 0.2f) else TacticalDarkSurface)
                            .border(
                                1.dp,
                                if (embedState.selectedPresetIndex == -1) TacticalCyan else TacticalCardBorder,
                                RoundedCornerShape(6.dp)
                            )
                            .clickable {
                                photoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                            .testTag("pick_carrier_gallery")
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AddPhotoAlternate,
                                contentDescription = null,
                                tint = TacticalCyan,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Galeri...",
                                fontSize = 11.sp,
                                color = TacticalCyan,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Carrier Preview Box
                val carrier = embedState.carrierBitmap
                if (carrier != null) {
                    val maxCap = StegoEngine.calculateMaxCapacityBytes(carrier.width, carrier.height)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(TacticalDarkSurface)
                            .border(1.dp, TacticalCardBorder, RoundedCornerShape(8.dp))
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            bitmap = carrier.asImageBitmap(),
                            contentDescription = "Carrier Preview",
                            modifier = Modifier
                                .size(72.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .border(1.dp, TacticalCardBorderActive, RoundedCornerShape(6.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "DIMENSI: ${carrier.width} x ${carrier.height} PIXEL",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = TacticalTextPrimary,
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                text = "KAPASITAS MAKS: ${maxCap / 1024} KB (${maxCap} Bytes)",
                                fontSize = 10.sp,
                                color = TacticalEmerald,
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                text = "PENYISIPAN: 3-BIT LSB (R-G-B CHANNEL)",
                                fontSize = 9.sp,
                                color = TacticalTextMuted,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }

                    if (embedState.opsecSanitized) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(6.dp))
                                .background(TacticalEmerald.copy(alpha = 0.12f))
                                .border(1.dp, TacticalEmerald.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Shield,
                                        contentDescription = null,
                                        tint = TacticalEmerald,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "OPSEC BERSIH: EXIF & GEOTAG GPS DIHAPUS",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TacticalEmerald,
                                        fontFamily = FontFamily.Monospace
                                    )
                                }
                                if (embedState.opsecStrippedTags.isNotEmpty()) {
                                    Text(
                                        text = "Metadata dinetralisir: ${embedState.opsecStrippedTags.joinToString(", ")}",
                                        fontSize = 9.sp,
                                        color = TacticalTextSecondary,
                                        fontFamily = FontFamily.Monospace
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Section 2: Plausible Deniability (Sandi Kamuflase) Toggle
        item {
            TacticalCard(
                borderColor = if (embedState.isDeniableMode) TacticalDecoyBorder else TacticalCardBorder,
                backgroundColor = if (embedState.isDeniableMode) TacticalDarkSurface else TacticalCardSurface
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (embedState.isDeniableMode) TacticalDecoyBlue.copy(alpha = 0.2f) else TacticalCardSurface)
                                .border(1.dp, if (embedState.isDeniableMode) TacticalDecoyBlue else TacticalCardBorder, RoundedCornerShape(6.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Security,
                                contentDescription = null,
                                tint = if (embedState.isDeniableMode) TacticalDecoyBlue else TacticalTextMuted,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "SANDI KAMUFLASE (DENIABLE ENCRYPTION)",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (embedState.isDeniableMode) TacticalDecoyBlue else TacticalTextPrimary,
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                text = "2 Sandi dalam 1 Foto (Sandi Utama + Sandi Umpan Darurat)",
                                fontSize = 9.sp,
                                color = TacticalTextSecondary
                            )
                        }
                    }

                    Switch(
                        checked = embedState.isDeniableMode,
                        onCheckedChange = { viewModel.toggleDeniableMode(it) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = TacticalDecoyBlue,
                            checkedTrackColor = TacticalDecoySurface,
                            uncheckedThumbColor = TacticalTextMuted,
                            uncheckedTrackColor = TacticalDarkSurface
                        ),
                        modifier = Modifier.testTag("toggle_deniable_mode")
                    )
                }

                if (embedState.isDeniableMode) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(6.dp))
                            .background(TacticalDecoySurface)
                            .border(1.dp, TacticalDecoyBorder.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "DOKTRIN INTELIJEN: Bila prajurit diinterogasi dan dipaksa lawan membuka pesan, berikan Sandi Umpan. Lawan hanya akan melihat laporan logistik tanpa bukti adanya pesan rahasia kedua.",
                            fontSize = 9.5.sp,
                            color = TacticalDecoyBlue,
                            lineHeight = 13.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }
        }

        // Section 3: Master Order (Pesan Rahasia Operasi)
        item {
            TacticalCard(
                title = "2. Pesan Rahasia Operasi (Master)",
                badgeText = "SANGAT RAHASIA",
                badgeColor = TacticalAlertRed
            ) {
                // Templates row
                Text(
                    text = "Gunakan Template Operasional Cepat:",
                    fontSize = 10.sp,
                    color = TacticalTextSecondary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    TacticalTemplate.REAL_TEMPLATES.forEach { tmpl ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(TacticalDarkSurface)
                                .border(1.dp, TacticalCardBorder, RoundedCornerShape(4.dp))
                                .clickable { viewModel.applyMasterTemplate(tmpl) }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = tmpl.title,
                                fontSize = 10.sp,
                                color = TacticalGold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                TacticalTextField(
                    value = embedState.masterTitle,
                    onValueChange = { viewModel.updateMasterFields(title = it) },
                    label = "Judul Perintah / Operasi",
                    testTag = "input_master_title"
                )

                Spacer(modifier = Modifier.height(8.dp))

                TacticalTextField(
                    value = embedState.masterMessage,
                    onValueChange = { viewModel.updateMasterFields(message = it) },
                    label = "Isi Instruksi Rahasia (Plaintext)",
                    singleLine = false,
                    maxLines = 6,
                    testTag = "input_master_message"
                )

                Spacer(modifier = Modifier.height(8.dp))

                TacticalTextField(
                    value = embedState.masterCoordinates,
                    onValueChange = { viewModel.updateMasterFields(coordinates = it) },
                    label = "Koordinat Sasaran (GPS / MGRS)",
                    leadingIcon = Icons.Default.LocationOn,
                    isMonospace = true,
                    placeholder = "Misal: 07°14'22.1\"S 110°24'18.8\"E",
                    testTag = "input_master_coordinates"
                )

                Spacer(modifier = Modifier.height(8.dp))

                TacticalTextField(
                    value = embedState.masterPassword,
                    onValueChange = { viewModel.updateMasterFields(password = it) },
                    label = "Kata Sandi Utama (Master Key)",
                    isPassword = true,
                    leadingIcon = Icons.Default.Key,
                    isMonospace = true,
                    testTag = "input_master_password"
                )
            }
        }

        // Section 4: Decoy Order (Pesan Kamuflase / Umpan)
        if (embedState.isDeniableMode) {
            item {
                TacticalCard(
                    title = "3. Pesan Umpan Kamuflase (Decoy)",
                    badgeText = "SANDI DARURAT",
                    badgeColor = TacticalDecoyBlue,
                    borderColor = TacticalDecoyBorder
                ) {
                    Text(
                        text = "Pilih Template Laporan Rutin / Tak Mencurigakan:",
                        fontSize = 10.sp,
                        color = TacticalTextSecondary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        TacticalTemplate.DECOY_TEMPLATES.forEach { tmpl ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(TacticalDarkSurface)
                                    .border(1.dp, TacticalDecoyBorder.copy(alpha = 0.5f), RoundedCornerShape(4.dp))
                                    .clickable { viewModel.applyDecoyTemplate(tmpl) }
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = tmpl.title,
                                    fontSize = 10.sp,
                                    color = TacticalDecoyBlue,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    TacticalTextField(
                        value = embedState.decoyTitle,
                        onValueChange = { viewModel.updateDecoyFields(title = it) },
                        label = "Judul Laporan Umpan",
                        testTag = "input_decoy_title"
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TacticalTextField(
                        value = embedState.decoyMessage,
                        onValueChange = { viewModel.updateDecoyFields(message = it) },
                        label = "Isi Laporan Umpan (Tampak Biasa / Rutin)",
                        singleLine = false,
                        maxLines = 5,
                        testTag = "input_decoy_message"
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TacticalTextField(
                        value = embedState.decoyPassword,
                        onValueChange = { viewModel.updateDecoyFields(password = it) },
                        label = "Kata Sandi Darurat / Umpan (Duress Key)",
                        isPassword = true,
                        leadingIcon = Icons.Default.Key,
                        isMonospace = true,
                        testTag = "input_decoy_password"
                    )
                }
            }
        }

        // Section 5: Tactical Time-Lock
        item {
            TacticalCard(
                title = "4. Tactical Time-Lock (Jadwal Buka Operasi)",
                badgeText = if (embedState.isTimeLockEnabled) "AKTIF" else "NON-AKTIF",
                badgeColor = if (embedState.isTimeLockEnabled) TacticalGold else TacticalTextMuted
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Kunci Jam Buka Instruksi",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = TacticalTextPrimary
                        )
                        Text(
                            text = "Pesan hanya dapat didekripsi jika waktu operasi telah tiba",
                            fontSize = 9.sp,
                            color = TacticalTextSecondary
                        )
                    }

                    Switch(
                        checked = embedState.isTimeLockEnabled,
                        onCheckedChange = { viewModel.toggleTimeLock(it) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = TacticalGold,
                            checkedTrackColor = TacticalGold.copy(alpha = 0.2f)
                        ),
                        modifier = Modifier.testTag("toggle_timelock")
                    )
                }

                if (embedState.isTimeLockEnabled) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Kunci Terbuka Dalam:",
                        fontSize = 10.sp,
                        color = TacticalTextSecondary,
                        fontFamily = FontFamily.Monospace
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf(1, 4, 12, 24).forEach { hours ->
                            val isSel = embedState.timeLockHoursAhead == hours
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(if (isSel) TacticalGold.copy(alpha = 0.2f) else TacticalDarkSurface)
                                    .border(
                                        1.dp,
                                        if (isSel) TacticalGold else TacticalCardBorder,
                                        RoundedCornerShape(6.dp)
                                    )
                                    .clickable { viewModel.toggleTimeLock(true, hours) }
                                    .padding(vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "+$hours Jam",
                                    fontSize = 11.sp,
                                    fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSel) TacticalGold else TacticalTextSecondary,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                        }
                    }
                }
            }
        }

        // Section 6: Capacity Meter & Action Button
        item {
            val carrier = embedState.carrierBitmap
            val maxCap = if (carrier != null) StegoEngine.calculateMaxCapacityBytes(carrier.width, carrier.height) else 0

            CapacityGauge(
                usedBytes = embedState.payloadSizeBytes,
                maxBytes = maxCap
            )

            if (embedState.error != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(6.dp))
                        .background(TacticalAlertRedDim)
                        .border(1.dp, TacticalAlertRed, RoundedCornerShape(6.dp))
                        .padding(10.dp)
                ) {
                    Text(
                        text = "PERINGATAN: ${embedState.error}",
                        fontSize = 11.sp,
                        color = TacticalAlertRed,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { viewModel.executeEmbedAndStego() },
                enabled = !embedState.isProcessing,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("embed_stego_button"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = TacticalEmerald,
                    contentColor = TacticalBlack
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                if (embedState.isProcessing) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = TacticalBlack,
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "MENYISIPKAN LSB PIXEL...",
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "ENKRIPSI & SISIP KE CITRA STEGO",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        letterSpacing = 1.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }

        // Section 7: Result Output Card
        if (embedState.stegoBitmap != null && embedState.savedUri != null) {
            item {
                TacticalCard(
                    title = "HASIL ENKRIPSI & CITRA STEGO",
                    badgeText = "SUKSES DISISIPKAN",
                    badgeColor = TacticalEmerald,
                    borderColor = TacticalEmerald
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            bitmap = embedState.stegoBitmap!!.asImageBitmap(),
                            contentDescription = "Stego Result",
                            modifier = Modifier
                                .size(88.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .border(1.dp, TacticalEmerald, RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = TacticalEmerald,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "CITRA STEGO BERHASIL DIBUAT",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TacticalEmerald,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Format: PNG Lossless (Tidak Rusak)",
                                fontSize = 10.sp,
                                color = TacticalTextSecondary
                            )
                            Text(
                                text = "Ukuran Paket: ${embedState.payloadSizeBytes} Bytes",
                                fontSize = 10.sp,
                                color = TacticalTextSecondary,
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                text = "SHA-256: ${embedState.hashSha256.take(16)}...",
                                fontSize = 9.sp,
                                color = TacticalTextMuted,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                    type = "image/png"
                                    putExtra(Intent.EXTRA_STREAM, embedState.savedUri)
                                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                }
                                context.startActivity(Intent.createChooser(shareIntent, "Bagikan Citra Taktis Stego"))
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                                .testTag("share_stego_button"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = TacticalEmerald,
                                contentColor = TacticalBlack
                            ),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "BAGIKAN .PNG",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }

                        OutlinedButton(
                            onClick = { viewModel.selectTab(TacticalNavTab.EXTRACT) },
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                                .testTag("verify_extract_button"),
                            shape = RoundedCornerShape(6.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = TacticalCyan)
                        ) {
                            Text(
                                text = "UJI URAI SEKARANG",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                val bundleUris = viewModel.getBundledShareUris()
                                val shareIntent = Intent(Intent.ACTION_SEND_MULTIPLE).apply {
                                    type = "*/*"
                                    putParcelableArrayListExtra(Intent.EXTRA_STREAM, bundleUris)
                                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                }
                                context.startActivity(Intent.createChooser(shareIntent, "Bagikan Paket Taktis (.PNG Stego + Web Decryptor)"))
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(42.dp)
                                .testTag("share_bundle_button"),
                            shape = RoundedCornerShape(6.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = TacticalGold,
                                contentColor = TacticalBlack
                            )
                        ) {
                            Text(
                                text = "PAKET LENGKAP (.PNG + .HTML)",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }

                        OutlinedButton(
                            onClick = {
                                viewModel.setWebSuitePreviewVisible(true)
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(42.dp)
                                .testTag("open_web_preview_btn"),
                            shape = RoundedCornerShape(6.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = TacticalEmerald)
                        ) {
                            Text(
                                text = "BUKA WEB TOOL (OFFLINE)",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }
            }
        }
    }
}
