package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DeviceHub
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.TacticalCard
import com.example.ui.theme.TacticalAlertRed
import com.example.ui.theme.TacticalAlertRedDim
import com.example.ui.theme.TacticalBlack
import com.example.ui.theme.TacticalCardBorder
import com.example.ui.theme.TacticalCyan
import com.example.ui.theme.TacticalDarkSurface
import com.example.ui.theme.TacticalDecoyBlue
import com.example.ui.theme.TacticalEmerald
import com.example.ui.theme.TacticalGold
import com.example.ui.theme.TacticalTextMuted
import com.example.ui.theme.TacticalTextPrimary
import com.example.ui.theme.TacticalTextSecondary

@Composable
fun DoctrineScreen(
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(TacticalBlack),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Doctrine 1: Plausible Deniability
        item {
            TacticalCard(
                title = "1. Doktrin Sandi Kamuflase (Plausible Deniability)",
                badgeText = "PROTOKOL INTEROGASI",
                badgeColor = TacticalDecoyBlue
            ) {
                Text(
                    text = "Prinsip Perlindungan Prajurit & Perwira Lapangan:",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = TacticalTextPrimary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Ketika prajurit tertangkap atau dipaksa membuka sandi oleh pihak lawan di bawah ancaman fisik (coercion), prajurit dilarang membocorkan Sandi Utama. Sebaliknya, prajurit wajib memberikan SANDI DARURAT / UMPAN.",
                    fontSize = 10.5.sp,
                    color = TacticalTextSecondary,
                    lineHeight = 15.sp
                )
                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(6.dp))
                        .background(TacticalDarkSurface)
                        .border(1.dp, TacticalDecoyBlue.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
                        .padding(10.dp)
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Security, contentDescription = null, tint = TacticalDecoyBlue, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "KEUNGGULAN MATEMATIS KRIPTOGRAFI DUAL-KEY",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = TacticalDecoyBlue,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Blok rahasia utama memiliki sifat indistinguishable from random noise (tidak dapat dibedakan dari derau acak). Pemeriksa lawan tidak memiliki bukti komputasi bahwa ada lapisan data kedua di dalam citra.",
                            fontSize = 9.5.sp,
                            color = TacticalTextPrimary,
                            lineHeight = 13.sp
                        )
                    }
                }
            }
        }

        // Doctrine 2: Zero-Server & Air-Gap
        item {
            TacticalCard(
                title = "2. Prinsip Air-Gap & Zero-Server",
                badgeText = "KEDAULATAN DATA",
                badgeColor = TacticalEmerald
            ) {
                Text(
                    text = "Zero-Server Policy menjamin kerahasiaan 100% di perangkat lokal prajurit:",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = TacticalTextPrimary
                )
                Spacer(modifier = Modifier.height(6.dp))

                val points = listOf(
                    "Tidak ada lalu-lintas jaringan keluar (Zero Outbound Traffic).",
                    "Kunci sandi tidak pernah disimpan di disk atau database permanen.",
                    "Seluruh pemrosesan berjalan di RAM terlindungi menggunakan Web Crypto / JCA murni.",
                    "Penyimpanan media dilakukan secara mandiri oleh personel operasi."
                )

                points.forEach { pt ->
                    Row(
                        modifier = Modifier.padding(vertical = 3.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = TacticalEmerald,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = pt,
                            fontSize = 10.sp,
                            color = TacticalTextSecondary,
                            lineHeight = 14.sp
                        )
                    }
                }
            }
        }

        // Doctrine 3: Tactical Time-Lock
        item {
            TacticalCard(
                title = "3. Tactical Time-Lock (Sinkronisasi Jam Tempur)",
                badgeText = "H-HOUR SYNC",
                badgeColor = TacticalGold
            ) {
                Text(
                    text = "Mencegah Pembocoran Prematur Sebelum Operasi:",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = TacticalTextPrimary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Instruksi operasi khusus seringkali hanya boleh dibaca tepat pada Jam-H (Waktu Serangan). Fitur Time-Lock memastikan perintah taktis tetap terkunci secara kriptografis dan memberikan peringatan disiplin tempur bila diakses sebelum waktunya.",
                    fontSize = 10.5.sp,
                    color = TacticalTextSecondary,
                    lineHeight = 15.sp
                )
            }
        }

        // Doctrine 4: Larangan Kompresi Lossy
        item {
            TacticalCard(
                title = "4. Peringatan Transmisi Media (Larangan JPEG)",
                badgeText = "SOP TRANSMISI",
                badgeColor = TacticalAlertRed,
                borderColor = TacticalAlertRed
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(6.dp))
                        .background(TacticalAlertRedDim)
                        .border(1.dp, TacticalAlertRed, RoundedCornerShape(6.dp))
                        .padding(10.dp)
                ) {
                    Row(verticalAlignment = Alignment.Top) {
                        Icon(imageVector = Icons.Default.Warning, contentDescription = null, tint = TacticalAlertRed, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "PERINGATAN KERUSAKAN LSB:",
                                fontSize = 10.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = TacticalAlertRed,
                                fontFamily = FontFamily.Monospace
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Pengiriman citra stego melalui aplikasi pesan instan (seperti WhatsApp/Telegram mode foto standar) akan mengompresi gambar menjadi lossy JPEG, yang memusnahkan bit LSB rahasia. Kirimkan citra stego SELALU dalam bentuk DOKUMEN ASLI (.PNG) tanpa kompresi!",
                                fontSize = 9.5.sp,
                                color = TacticalTextPrimary,
                                lineHeight = 13.5.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
