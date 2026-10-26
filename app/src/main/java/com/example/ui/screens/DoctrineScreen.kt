package com.example.ui.screens

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
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
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
import com.example.ui.theme.TacticalDecoySurface
import com.example.ui.theme.TacticalEmerald
import com.example.ui.theme.TacticalGold
import com.example.ui.theme.TacticalTextMuted
import com.example.ui.theme.TacticalTextPrimary
import com.example.ui.theme.TacticalTextSecondary

@Composable
fun DoctrineScreen(
    modifier: Modifier = Modifier
) {
    var selectedSection by remember { mutableStateOf(0) } // 0 = Panduan SOP Langkah-demi-Langkah, 1 = Doktrin Kriptografi

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(TacticalBlack),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Toggle Header: SOP Praktis vs Doktrin
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(TacticalDarkSurface)
                    .border(1.dp, TacticalCardBorder, RoundedCornerShape(8.dp))
                    .padding(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (selectedSection == 0) TacticalEmerald else TacticalDarkSurface)
                        .clickable { selectedSection = 0 }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Assignment,
                            contentDescription = null,
                            tint = if (selectedSection == 0) TacticalBlack else TacticalTextSecondary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "BUKU PANDUAN SOP",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (selectedSection == 0) TacticalBlack else TacticalTextSecondary,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (selectedSection == 1) TacticalCyan else TacticalDarkSurface)
                        .clickable { selectedSection = 1 }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Security,
                            contentDescription = null,
                            tint = if (selectedSection == 1) TacticalBlack else TacticalTextSecondary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "DOKTRIN SIBER",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (selectedSection == 1) TacticalBlack else TacticalTextSecondary,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }
        }

        if (selectedSection == 0) {
            // ==========================================
            // BUKU PANDUAN LANGKAH-DEMI-LANGKAH (SOP)
            // ==========================================
            item {
                StepCard(
                    stepNumber = "LANGKAH 1",
                    title = "MENYIAPKAN & MENYISIPKAN PESAN (TAB SISIP)",
                    icon = Icons.Default.Lock,
                    accentColor = TacticalEmerald
                ) {
                    InstructionPoint(
                        number = "1.1",
                        title = "Pilih Citra Pembawa (Carrier Media)",
                        desc = "Gunakan preset foto militer bawaan (Patroli Hutan, Pos Perbatasan, Depot Logistik, Peta Kontur) atau ambil foto nyata dari galeri dengan tombol 'Galeri...'."
                    )
                    InstructionPoint(
                        number = "1.2",
                        title = "Pilih Mode Sandi",
                        desc = "Aktifkan 'Sandi Kamuflase' jika beroperasi di wilayah rawan tangkap. Ini menyertakan 2 sandi sekaligus (Sandi Asli + Sandi Umpan). Non-aktifkan jika hanya butuh enkripsi tunggal."
                    )
                    InstructionPoint(
                        number = "1.3",
                        title = "Tuliskan Instruksi Rahasia & Koordinat",
                        desc = "Ketik judul perintah, isi pesan rahasia, koordinat GPS/MGRS sasaran, atau gunakan tombol template operasional cepat (PO Khusus, Recon Intel, CASEVAC)."
                    )
                    InstructionPoint(
                        number = "1.4",
                        title = "Tentukan Kata Sandi Utama (Master Key)",
                        desc = "Masukkan sandi rahasia yang hanya diketahui oleh perwira/penerima sah (misal: 'KARTIKA-2026')."
                    )
                    InstructionPoint(
                        number = "1.5",
                        title = "Isi Laporan Umpan (Khusus Sandi Kamuflase)",
                        desc = "Jika mode kamuflase aktif, masukkan judul laporan rutin (misal logistik beras/BBM) dan buat 'Sandi Darurat/Umpan' (misal: 'BAMA-LOGISTIK')."
                    )
                    InstructionPoint(
                        number = "1.6",
                        title = "Atur Tactical Time-Lock (Opsional)",
                        desc = "Jika perintah hanya boleh dibuka saat Jam Serangan (H-Hour), aktifkan time-lock dan pilih waktu buka (+1, +4, +12, atau +24 jam)."
                    )
                    InstructionPoint(
                        number = "1.7",
                        title = "Tekan 'ENKRIPSI & SISIP KE CITRA STEGO'",
                        desc = "Citra stego lossless PNG akan di-generate dalam hitungan milidetik. Tekan 'BAGIKAN .PNG' untuk mengirimkan dokumen ke rekan operasi."
                    )
                }
            }

            item {
                StepCard(
                    stepNumber = "LANGKAH 2",
                    title = "MENERIMA & MENGURAI PESAN (TAB URAI)",
                    icon = Icons.Default.LockOpen,
                    accentColor = TacticalGold
                ) {
                    InstructionPoint(
                        number = "2.1",
                        title = "Muat Citra Stego",
                        desc = "Pilih foto PNG yang diterima dari rekan melalui penyimpanan perangkat dengan tombol 'Pilih Citra dari Galeri'."
                    )
                    InstructionPoint(
                        number = "2.2",
                        title = "Masukkan Kata Sandi",
                        desc = "Ketikkan kata sandi operasional Anda. Sistem secara otomatis mendeteksi apakah sandi yang Anda masukkan cocok dengan Sandi Master atau Sandi Umpan."
                    )
                    InstructionPoint(
                        number = "2.3",
                        title = "Tekan 'URAI INSTRUKSI TAKTIS'",
                        desc = "Jika memasukkan Sandi Utama: Instruksi Top Secret dan koordinat muncul dalam bingkai merah/emas.\nJika memasukkan Sandi Umpan: Dokumen logistik rutin muncul dalam bingkai biru tanpa ada peringatan apa pun."
                    )
                    InstructionPoint(
                        number = "2.4",
                        title = "Menghadapi Pesan Time-Locked",
                        desc = "Jika pesan terkunci waktu, teks perintah akan disamarkan hingga hitung mundur habis. Jika situasi darurat lapangan mendesak, tekan 'Buka Paksa (Otoritas Perwira)' dan masukkan PIN komandan (Default: PUSSIBERAD-991)."
                    )
                }
            }

            item {
                StepCard(
                    stepNumber = "LANGKAH 3",
                    title = "INSPEKSI FORENSIK CITRA (TAB INSPEKTUR)",
                    icon = Icons.Default.Assignment,
                    accentColor = TacticalCyan
                ) {
                    InstructionPoint(
                        number = "3.1",
                        title = "Uji Bit-Plane 0 (LSB)",
                        desc = "Ketuk tab '0(LSB)' untuk memeriksa sebaran derau bit. Anda akan melihat bahwa bit sandi menyatu dengan noise sensor kamera dan tidak memperlihatkan pola pesan kasat mata."
                    )
                    InstructionPoint(
                        number = "3.2",
                        title = "Bandingkan dengan Bit-Plane 7 (MSB)",
                        desc = "Ketuk tab '7' untuk melihat struktur foto utama yang tetap utuh 100% tanpa distorsi."
                    )
                    InstructionPoint(
                        number = "3.3",
                        title = "Periksa Peta Anomali Selisih (Diff Map)",
                        desc = "Lihat visualisasi piksel hijau terang yang memetakan area mana saja pada foto yang telah dimuati paket enkripsi."
                    )
                }
            }

            item {
                StepCard(
                    stepNumber = "LANGKAH 4",
                    title = "TINDAKAN DARURAT (EMERGENCY PANIC ZEROIZE)",
                    icon = Icons.Default.DeleteForever,
                    accentColor = TacticalAlertRed
                ) {
                    InstructionPoint(
                        number = "4.1",
                        title = "Tombol Tempat Sampah Merah di Kanan Atas",
                        desc = "Tekan ikon tempat sampah merah pada bilah atas saat berada dalam situasi bahaya/terpojok."
                    )
                    InstructionPoint(
                        number = "4.2",
                        title = "Konfirmasi Musnahkan Memori",
                        desc = "Sistem seketika menolkan (*zeroize*) seluruh kata sandi dari RAM, menghapus teks hasil dekripsi, dan membersihkan berkas stego aktif dari cache memori internal perangkat."
                    )
                }
            }

            item {
                val context = androidx.compose.ui.platform.LocalContext.current
                StepCard(
                    stepNumber = "LANGKAH 5",
                    title = "EKSPOR WEB TOOL MANDIRI (ZERO-INSTALL .HTML)",
                    icon = Icons.Default.Share,
                    accentColor = TacticalEmerald
                ) {
                    InstructionPoint(
                        number = "5.1",
                        title = "Versi Web Portabel 1 Berkas Tanpa Instalasi",
                        desc = "Hasilkan 1 berkas HTML mandiri (~500 KB) berisi mesin enkripsi Web Crypto & steganografi LSB yang dapat dijalankan di laptop maupun browser HP apa pun tanpa instalasi APK."
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    androidx.compose.material3.Button(
                        onClick = {
                            val (_, uri) = com.example.stego.StandaloneHtmlGenerator.generateHtmlFile(context)
                            val shareIntent = android.content.Intent(android.content.Intent.ACTION_SEND).apply {
                                type = "text/html"
                                putExtra(android.content.Intent.EXTRA_STREAM, uri)
                                addFlags(android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            }
                            context.startActivity(android.content.Intent.createChooser(shareIntent, "Bagikan Berkas Web Sandi-Kartika (.html)"))
                        },
                        modifier = Modifier.fillMaxWidth().height(42.dp),
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = TacticalEmerald,
                            contentColor = TacticalBlack
                        ),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "EKSPOR / BAGIKAN BERKAS .HTML MANDIRI",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }
        } else {
            // ==========================================
            // DOKTRIN & KEAMANAN SIBER
            // ==========================================
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
                                    text = "KEUNGGULAN MATEMATIS DUAL-KEY",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TacticalDecoyBlue,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Lawan yang menguji media dengan Sandi Umpan akan melihat laporan logistik yang terbuka dengan sempurna. Blok data master terenkripsi memiliki entropi identik dengan derau acak, sehingga pemeriksa lawan tidak memiliki bukti komputasi adanya pesan kedua.",
                                fontSize = 9.5.sp,
                                color = TacticalTextPrimary,
                                lineHeight = 13.sp
                            )
                        }
                    }
                }
            }

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
                        "Seluruh pemrosesan berjalan di RAM terlindungi menggunakan JCA murni (AES-256-GCM + PBKDF2).",
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
                        text = "Instruksi operasi khusus seringkali hanya boleh dibaca tepat pada Jam-H (Waktu Serangan). Fitur Time-Lock memastikan perintah taktis tetap terkunci secara kriptografis dan diawasi oleh jam monotonik anti-tamper.",
                        fontSize = 10.5.sp,
                        color = TacticalTextSecondary,
                        lineHeight = 15.sp
                    )
                }
            }

            item {
                TacticalCard(
                    title = "4. Peringatan Transmisi Media (Larangan Kompresi JPEG)",
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
}

@Composable
private fun StepCard(
    stepNumber: String,
    title: String,
    icon: ImageVector,
    accentColor: androidx.compose.ui.graphics.Color,
    content: @Composable () -> Unit
) {
    TacticalCard(
        title = stepNumber,
        badgeText = "SOP LAPANGAN",
        badgeColor = accentColor,
        borderColor = accentColor.copy(alpha = 0.5f)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(accentColor.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(16.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = TacticalTextPrimary,
                fontFamily = FontFamily.Monospace
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        content()
    }
}

@Composable
private fun InstructionPoint(
    number: String,
    title: String,
    desc: String
) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(18.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(TacticalCardBorder),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = number,
                    fontSize = 8.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = TacticalEmerald,
                    fontFamily = FontFamily.Monospace
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = TacticalTextPrimary
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = desc,
            fontSize = 10.sp,
            color = TacticalTextSecondary,
            lineHeight = 14.sp,
            modifier = Modifier.padding(start = 26.dp)
        )
    }
}
