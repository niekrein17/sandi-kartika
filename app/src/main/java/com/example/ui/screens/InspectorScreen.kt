package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Compare
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stego.StegoAnalyzer
import com.example.ui.MainViewModel
import com.example.ui.components.TacticalCard
import com.example.ui.theme.TacticalAlertRed
import com.example.ui.theme.TacticalBlack
import com.example.ui.theme.TacticalCardBorder
import com.example.ui.theme.TacticalCardSurface
import com.example.ui.theme.TacticalCyan
import com.example.ui.theme.TacticalDarkSurface
import com.example.ui.theme.TacticalEmerald
import com.example.ui.theme.TacticalGold
import com.example.ui.theme.TacticalTextMuted
import com.example.ui.theme.TacticalTextPrimary
import com.example.ui.theme.TacticalTextSecondary

@Composable
fun InspectorScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val inspectorState by viewModel.inspectorState.collectAsState()
    val embedState by viewModel.embedState.collectAsState()

    val currentBmp = inspectorState.inspectedBitmap ?: embedState.stegoBitmap ?: embedState.carrierBitmap

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(TacticalBlack),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Section 1: Overview & Metrics
        item {
            TacticalCard(
                title = "Inspeksi Forensik Citra Stego",
                badgeText = "ANALISIS LSB",
                badgeColor = TacticalCyan
            ) {
                if (currentBmp != null) {
                    val report = StegoAnalyzer.analyzeCarrier(currentBmp, embedState.payloadSizeBytes)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "RESOLUSI CITRA",
                                fontSize = 9.sp,
                                color = TacticalTextMuted,
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                text = "${report.width} x ${report.height} PX",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = TacticalTextPrimary,
                                fontFamily = FontFamily.Monospace
                            )
                        }

                        Column {
                            Text(
                                text = "KAPASITAS TOTAL",
                                fontSize = 9.sp,
                                color = TacticalTextMuted,
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                text = "${report.maxCapacityBytes / 1024} KB",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = TacticalEmerald,
                                fontFamily = FontFamily.Monospace
                            )
                        }

                        Column {
                            Text(
                                text = "MUATAN AKTIF",
                                fontSize = 9.sp,
                                color = TacticalTextMuted,
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                text = "${report.detectedPayloadBytes ?: 0} B (${"%.2f".format(report.usagePercentage)}%)",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = TacticalGold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(6.dp))
                            .background(TacticalDarkSurface)
                            .border(1.dp, TacticalEmerald.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
                            .padding(8.dp)
                    ) {
                        Column {
                            Text(
                                text = "TINGKAT KAMUFLASE: ${report.stealthScore}",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = TacticalEmerald,
                                fontFamily = FontFamily.Monospace
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = report.recommendation,
                                fontSize = 9.5.sp,
                                color = TacticalTextSecondary
                            )
                        }
                    }
                } else {
                    Text(
                        text = "Belum ada citra yang dimuat untuk dianalisis.",
                        fontSize = 11.sp,
                        color = TacticalTextMuted
                    )
                }
            }
        }

        // Section 2: Bit-Plane Decomposition
        item {
            TacticalCard(
                title = "Visualisasi Dekomposisi Bit-Plane (0-7)",
                badgeText = "BIT ${inspectorState.bitPlaneIndex} (${if (inspectorState.bitPlaneIndex == 0) "LSB" else if (inspectorState.bitPlaneIndex == 7) "MSB" else "MID"})",
                badgeColor = if (inspectorState.bitPlaneIndex == 0) TacticalEmerald else TacticalGold
            ) {
                Text(
                    text = "Pilih lapisan bit (Bit-Plane) untuk melihat pola persebaran visual bit data:",
                    fontSize = 10.5.sp,
                    color = TacticalTextSecondary
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Bit selector tabs 0..7
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    (0..7).forEach { bitIdx ->
                        val isSel = inspectorState.bitPlaneIndex == bitIdx
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(4.dp))
                                .background(if (isSel) TacticalEmerald else TacticalDarkSurface)
                                .border(
                                    1.dp,
                                    if (isSel) TacticalEmerald else TacticalCardBorder,
                                    RoundedCornerShape(4.dp)
                                )
                                .clickable { viewModel.updateBitplane(bitIdx) }
                                .padding(vertical = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (bitIdx == 0) "0(LSB)" else "$bitIdx",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSel) TacticalBlack else TacticalTextSecondary,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Bitplane Image
                val bpBmp = inspectorState.bitPlaneBitmap
                if (bpBmp != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .border(1.dp, TacticalCardBorder, RoundedCornerShape(8.dp))
                            .background(TacticalBlack),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            bitmap = bpBmp.asImageBitmap(),
                            contentDescription = "Bit Plane View",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                } else if (inspectorState.isProcessing) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = TacticalEmerald)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = if (inspectorState.bitPlaneIndex == 0)
                        "Bit-Plane 0 (LSB) berisi bit terendah yang menyatu dengan noise termal sensor kamera. Modifikasi pada lapisan ini 100% kasat mata terhadap mata manusia normal."
                    else if (inspectorState.bitPlaneIndex == 7)
                        "Bit-Plane 7 (MSB) menyimpan informasi struktur visual utama citra. Lapisan ini tidak disentuh untuk menjaga foto tetap tampak normal."
                    else
                        "Bit-Plane ${inspectorState.bitPlaneIndex} menyimpan tingkatan intensitas warna menengah citra.",
                    fontSize = 9.5.sp,
                    color = TacticalTextMuted,
                    lineHeight = 13.sp
                )
            }
        }

        // Section 3: Amplified Diff Map
        val diffBmp = inspectorState.diffBitmap
        if (diffBmp != null) {
            item {
                TacticalCard(
                    title = "Peta Anomali Selisih Bit (Amplified Diff)",
                    badgeText = "DETEKSI INJEKSI",
                    badgeColor = TacticalEmerald
                ) {
                    Text(
                        text = "Piksel hijau terang menunjukkan lokasi piksel yang telah disisipi data terenkripsi AES-256:",
                        fontSize = 10.5.sp,
                        color = TacticalTextSecondary
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .border(1.dp, TacticalEmerald.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                            .background(TacticalBlack),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            bitmap = diffBmp.asImageBitmap(),
                            contentDescription = "Amplified Diff Map",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp),
                            contentScale = ContentScale.Fit
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Distribusi bit terkonsentrasi secara terstruktur pada blok awal sesuai kaidah enkripsi sekuensial.",
                        fontSize = 9.sp,
                        color = TacticalTextMuted,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }
    }
}
