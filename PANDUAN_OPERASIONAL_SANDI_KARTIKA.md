# BUKU PETUNJUK OPERASIONAL (BUJUKOPS) LAPANGAN
## SANDI-KARTIKA (Tactical Steganography & Offline Encryption Suite)
**Klasifikasi Dokumen: RAHASIA TAKTIS (OPSEC RESTRICTED)**  
**Pusat Siber dan Sandi TNI Angkatan Darat (Pussiberad)**

---

### DAFTAR ISI
1. **PENDAHULUAN & TUJUAN OPERASIONAL**
2. **ARSITEKTUR & PRINSIP KEAMANAN DATA**
3. **PANDUAN LENGKAP FITUR & CARA PENGGUNAAN**
   - 3.1. Tab 1: SISIP SANDI (Enkripsi & Penyisipan LSB)
   - 3.2. Tab 2: URAI SANDI (Ekstraksi, Sandi Kamuflase, & Time-Lock)
   - 3.3. Tab 3: INSPEKTUR FORENSIK (Dekomposisi Bit-Plane & Diff Map)
   - 3.4. Tab 4: DOKTRIN & SOP (Buku Panduan Internal Aplikasi)
   - 3.5. Prosedur Darurat: Panic Zeroize (Pemusnahan Memori Seketika)
4. **PROTOKOL SANDI KAMUFLASE (PLAUSIBLE DENIABILITY)**
5. **STANDAR OPERASIONAL PROSEDUR (SOP) TRANSMISI MEDIA**
6. **MATRIKS TROUBLESHOOTING & FAQ OPERASIONAL**

---

### 1. PENDAHULUAN & TUJUAN OPERASIONAL
**SANDI-KARTIKA** adalah aplikasi taktis mandiri (*standalone/offline-first*) yang dirancang khusus untuk mengamankan pertukaran instruksi, dokumen perintah operasi, dan titik koordinat sasaran di medan tugas melalui integrasi:
- **Kriptografi Berlapis**: Standar militer **AES-256-GCM** dengan derivasi kunci **PBKDF2WithHmacSHA256** (12.000 iterasi).
- **Steganografi Digital LSB 3-Bit**: Menyisipkan data terenkripsi ke dalam kanal *Red*, *Green*, dan *Blue* foto dokumentasi biasa tanpa distorsi visual.
- **Sandi Kamuflase (*Plausible Deniability*)**: Dua kata sandi dalam satu media (Sandi Master untuk instruksi tempur, Sandi Umpan untuk laporan logistik rutin).
- **Tactical Time-Lock**: Pembatasan jam buka instruksi hingga Jam-H (*H-Hour*) tiba dengan perlindungan jam monotonik anti-tamper.

---

### 2. ARSITEKTUR & PRINSIP KEAMANAN DATA
1. **Zero-Server & 100% Air-Gapped**:
   - Seluruh pemrosesan berjalan di memori lokal perangkat (RAM) prajurit.
   - Tidak ada koneksi internet keluar (*zero outbound traffic*), tidak membutuhkan server pusat, dan tidak bergantung pada API pihak ketiga.
2. **Lossless Preservation**:
   - Berkas stego diekspor dalam format **PNG murni** (*lossless*). Kanal warna dan bit LSB dijamin tidak terkompresi.

---

### 3. PANDUAN LENGKAP FITUR & CARA PENGGUNAAN

#### 3.1. Tab 1: SISIP SANDI (Penyisipan Pesan ke Citra)
1. **Pilih Citra Pembawa (Carrier)**:
   - Pilih salah satu dari 4 preset foto militer bawaan:
     - *Patroli Hutan Kalimantan* (Lanskap vegetasi rimba)
     - *Pos Perbatasan* (Menara pemantau & kawat duri)
     - *Depot Logistik* (Tumpukan peti amunisi PBN-24)
     - *Peta Kontur* (Garis topografi elevasi)
   - Atau tekan tombol **"Galeri..."** untuk memilih foto nyata dari penyimpanan perangkat.
2. **Pilih Mode Sandi**:
   - **Sandi Kamuflase (Aktif secara default)**: Menyisipkan dua paket data (Sandi Utama + Sandi Umpan Darurat).
   - Non-aktifkan sakelar (*switch*) jika hanya menginginkan enkripsi tunggal standar.
3. **Isi Pesan Rahasia Utama (Master)**:
   - Pilih template operasional cepat:
     - *PO KARTIKA SAKTI - FASE 2* (Perintah Operasi Khusus)
     - *INTEL RECON // PATROLI MALAM* (Laporan Pengintaian Titik Kritis)
     - *CASEVAC EXFIL PROTOKOL DELTA* (Prosedur Evakuasi Darurat)
   - Masukkan **Judul Perintah**, **Isi Instruksi**, dan **Koordinat Sasaran** (GPS / MGRS).
   - Masukkan **Kata Sandi Utama (Master Key)** (contoh: `KARTIKA-2026`).
4. **Isi Pesan Umpan / Kamuflase (Decoy)** *(Jika Mode Kamuflase Aktif)*:
   - Pilih template laporan rutin yang tidak mencurigakan:
     - *Laporan Rutin Bama* (Stok beras, minyak, gula)
     - *Status Logistik Har-Ranpur BBM* (Pengecekan truk NPS)
     - *Catatan Cuaca Harian* (Suhu, curah hujan, debit sungai)
   - Masukkan **Kata Sandi Darurat / Umpan (Duress Key)** (contoh: `BAMA-LOGISTIK`).
   - *Penting: Kata sandi utama dan kata sandi umpan tidak boleh sama.*
5. **Konfigurasi Tactical Time-Lock (Opsional)**:
   - Aktifkan sakelar *Tactical Time-Lock* jika instruksi hanya boleh didekripsi saat operasi dimulai.
   - Pilih penambahan waktu: `+1 Jam`, `+4 Jam`, `+12 Jam`, atau `+24 Jam`.
6. **Eksekusi Penyisipan**:
   - Periksa indikator *Kapasitas Citra Stego* (memastikan ukuran muatan muat).
   - Tekan tombol **"ENKRIPSI & SISIP KE CITRA STEGO"**.
   - Hasil citra stego akan muncul seketika beserta ringkasan ukuran byte dan sidik jari *SHA-256*.
   - Tekan **"BAGIKAN .PNG"** untuk membagikan file ke rekan operasi.

---

#### 3.2. Tab 2: URAI SANDI (Ekstraksi & Pembacaan Pesan)
1. **Muat Citra Stego**:
   - Jika baru saja melakukan penyisipan di Tab 1, citra stego otomatis termuat.
   - Jika menerima file dari luar, tekan **"Pilih Citra dari Galeri"** dan pilih file PNG yang diterima.
2. **Masukkan Kata Sandi**:
   - Masukkan kata sandi pada kolom input.
   - *Catatan Pengujian*: Tersedia tombol cepat untuk menguji Sandi Master atau Sandi Umpan.
3. **Tekan Tombol "URAI INSTRUKSI TAKTIS"**:
   - **Skenario A (Memasukkan Sandi Utama)**:
     - Kartu hasil berwarna **Merah/Emas** bertuliskan `SANGAT RAHASIA (TOP SECRET)`.
     - Teks perintah asli, unit otoritas, dan tombol salin koordinat GPS/MGRS ditampilkan.
   - **Skenario B (Memasukkan Sandi Umpan / Di Bawah Tekanan Interogasi Musuh)**:
     - Kartu hasil berwarna **Biru/Abu-abu** bertuliskan `LOGISTIK UMUM / TERBUKA`.
     - Menampilkan laporan rutin bahan makanan atau truk tanpa indikasi adanya pesan kedua.
   - **Skenario C (Pesan Memiliki Time-Lock & Waktu Belum Tiba)**:
     - Teks instruksi disamarkan. Banner peringatan kuning menampilkan hitung mundur waktu operasional.
     - Untuk pembukaan darurat sebelum jam-H, tekan tombol **"Buka Paksa (Otoritas Perwira)"** dan masukkan PIN perwira (`PUSSIBERAD-991`).

---

#### 3.3. Tab 3: INSPEKTUR FORENSIK (Analisis Bit-Plane)
1. **Dekomposisi Bit-Plane (0 sampai 7)**:
   - **Bit 0 (LSB)**: Menampilkan lapisan bit terendah. Bit sandi terdistribusi menyatu sempurna dengan derau termal alami sensor kamera.
   - **Bit 7 (MSB)**: Menampilkan lapisan struktur utama foto. Membuktikan bahwa visual foto tidak mengalami perubahan bentuk kasat mata.
2. **Peta Anomali Selisih Bit (Amplified Diff Map)**:
   - Memvisualisasikan piksel yang dimodifikasi oleh enkripsi dengan pendaran warna hijau zamrud (*cyber emerald*).
3. **Indikator Stealth Score**:
   - Memberikan evaluasi tingkat kamuflase (misal *99.8% Optimal Stealth*) berdasarkan rasio muatan terhadap kapasitas total gambar.

---

#### 3.4. Tab 4: DOKTRIN & BUKU PANDUAN INTERNAL
- Tab ini dapat diakses kapan saja secara luring di perangkat untuk membaca panduan SOP langkah demi langkah atau mendalami prinsip doktrin siber.

---

#### 3.5. Prosedur Darurat: Panic Zeroize (Pemusnahan Memori)
- **Kapan Digunakan**: Saat posisi prajurit terancam disergap, pos penjagaan diterobos, atau perangkat akan disita lawan.
- **Cara Kerja**:
  1. Ketuk ikon **Tempat Sampah Merah** pada pojok kanan atas bilah navigasi.
  2. Dialog konfirmasi darurat bertuliskan *"EMERGENCY ZEROIZE (PANIK)"* akan muncul.
  3. Tekan **"MUSNAHKAN MEMORI"**.
  4. Perangkat akan bergetar haptik dan seketika menghapus seluruh kata sandi dari RAM, membersihkan teks plaintext, serta mengosongkan cache citra stego.

---

### 4. PROTOKOL SANDI KAMUFLASE (PLAUSIBLE DENIABILITY)
Dalam doktrin intelijen taktis:
1. **Aturan Utama Interogasi**:
   - Jika prajurit tertangkap dan dipaksa membuka sandi di bawah ancaman kekerasan fisik, prajurit **DILARANG KERAS** membocorkan Sandi Utama.
   - Prajurit **WAJIB** memberikan Sandi Darurat / Umpan (`BAMA-LOGISTIK`).
2. **Respon Sistem**:
   - Aplikasi akan membuka laporan logistik rutin yang sangat wajar.
   - Lawan tidak memiliki instrumen matematis untuk membuktikan bahwa di balik data logistik tersebut tersimpan instruksi tempur rahasia, karena blok sandi master memiliki tingkat entropi yang identik dengan derau acak.

---

### 5. STANDAR OPERASIONAL PROSEDUR (SOP) TRANSMISI MEDIA
- **LARANGAN KERAS PENGIRIMAN JPEG**:
  - Dilarang mengirimkan citra stego menggunakan fitur kirim gambar standar WhatsApp / Telegram / media sosial karena algoritma kompresi JPEG akan mereset bit LSB dan merusak instruksi rahasia secara permanen.
- **METODE PENGIRIMAN YANG SAH**:
  - Kirim selalu sebagai **BERKAS DOKUMEN ASLI (.PNG)** tanpa kompresi.
  - Atau pindahkan menggunakan media fisik *air-gapped* (kabel data offline, USB OTG terenkripsi, MicroSD terenkripsi, atau Wi-Fi Direct P2P).

---

### 6. MATRIKS TROUBLESHOOTING & FAQ OPERASIONAL

| Permasalahan | Kemungkinan Penyebab | Tindakan Koreksi |
|---|---|---|
| Muncul pesan: *"Kata sandi tidak valid. GCM Authentication Tag gagal diverifikasi"* | Salah ketik kata sandi atau berkas terkompresi | Pastikan besar/kecil huruf kata sandi benar. Pastikan berkas yang dibuka berformat PNG asli. |
| Muncul pesan: *"Ukuran muatan melebihi kapasitas citra"* | Teks instruksi terlalu panjang untuk resolusi foto | Gunakan foto beresolusi lebih tinggi atau kurangi panjang pesan instruksi. |
| Pesan terbuka tapi teksnya *"TERKUNCI SECARA KRIPTOGRAFIS HINGGA..."* | Fitur *Tactical Time-Lock* aktif dan waktu jam operasi belum tiba | Tunggu hingga hitung mundur habis, atau gunakan tombol *Buka Paksa* dengan PIN Perwira Lapangan. |
| Tombol bagikan tidak memunculkan aplikasi lain | Belum ada aplikasi penerima dokumen yang terpasang | Gunakan pengelola berkas (*File Manager*) untuk memindahkan berkas dari folder penyimpanan aplikasi. |

---
*Dokumen ini diterbitkan untuk jajaran satuan operasi sandi dan telekomunikasi taktis di lingkungan Pussiberad.*
