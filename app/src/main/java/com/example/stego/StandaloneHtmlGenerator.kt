package com.example.stego

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

/**
 * Generates an all-in-one, zero-dependency, standalone single-file HTML/JS application.
 * Enables military personnel to execute AES-256-GCM encryption and LSB steganography
 * on laptops or phones directly in any web browser without installing any APK or contacting any server.
 */
object StandaloneHtmlGenerator {

    fun generateHtmlFile(context: Context): Pair<File, Uri> {
        val cacheDir = File(context.cacheDir, "standalone_web").apply { mkdirs() }
        val htmlFile = File(cacheDir, "SANDI_KARTIKA_OFFLINE_SUITE.html")

        val htmlContent = """
<!DOCTYPE html>
<html lang="id">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>SANDI-KARTIKA // PUSSIBERAD TACTICAL OFFLINE SUITE</title>
  <style>
    :root {
      --bg: #070c09;
      --card: #0f1812;
      --card-border: #1a2f23;
      --primary: #2ee59d;
      --primary-dim: rgba(46, 229, 157, 0.15);
      --text: #e6f4ec;
      --muted: #789284;
      --gold: #e5b842;
      --decoy: #60a5fa;
      --danger: #ff4848;
      --danger-dim: rgba(255, 72, 72, 0.15);
      --font-mono: "SF Mono", "Consolas", "Courier New", monospace;
    }
    * { box-sizing: border-box; margin: 0; padding: 0; font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, var(--font-mono); }
    body { background: var(--bg); color: var(--text); padding: 14px; max-width: 840px; margin: auto; line-height: 1.4; }
    header { background: #0b1510; border: 1px solid var(--card-border); border-radius: 8px; padding: 12px 16px; margin-bottom: 14px; display: flex; justify-content: space-between; align-items: center; }
    .badge { font-size: 10px; font-weight: bold; padding: 3px 8px; border-radius: 4px; background: var(--primary-dim); color: var(--primary); border: 1px solid var(--primary); letter-spacing: 0.5px; }
    .badge-danger { background: var(--danger-dim); color: var(--danger); border-color: var(--danger); }
    .nav-tabs { display: flex; gap: 8px; margin-bottom: 14px; }
    .tab-btn { flex: 1; padding: 11px; background: var(--card); color: var(--muted); border: 1px solid var(--card-border); border-radius: 6px; cursor: pointer; font-weight: bold; font-size: 11px; letter-spacing: 0.5px; transition: all 0.2s; font-family: var(--font-mono); }
    .tab-btn.active { background: var(--primary); color: #000; border-color: var(--primary); }
    .card { background: var(--card); border: 1px solid var(--card-border); border-radius: 8px; padding: 14px; margin-bottom: 14px; }
    h2 { font-size: 12.5px; margin-bottom: 10px; color: var(--primary); letter-spacing: 0.8px; font-family: var(--font-mono); text-transform: uppercase; }
    label { display: block; font-size: 10.5px; color: var(--muted); margin-bottom: 4px; font-family: var(--font-mono); }
    input[type="text"], input[type="password"], textarea, select { width: 100%; padding: 10px; background: #060b08; border: 1px solid var(--card-border); border-radius: 6px; color: var(--text); margin-bottom: 10px; font-size: 12px; font-family: var(--font-mono); }
    input:focus, textarea:focus, select:focus { border-color: var(--primary); outline: none; }
    .btn { width: 100%; padding: 12px; background: var(--primary); color: #000; border: none; border-radius: 6px; font-weight: bold; cursor: pointer; font-size: 12.5px; font-family: var(--font-mono); letter-spacing: 0.5px; transition: 0.2s; }
    .btn:hover { filter: brightness(1.12); }
    .btn-secondary { background: var(--card); color: var(--text); border: 1px solid var(--card-border); margin-top: 6px; }
    .btn-danger { background: var(--danger); color: #fff; }
    .result-box { background: #050906; border: 1px solid var(--card-border); border-radius: 6px; padding: 12px; margin-top: 10px; }
    canvas { max-width: 100%; border-radius: 6px; border: 1px solid var(--card-border); display: block; margin: 8px 0; background: #000; }
    .row { display: flex; gap: 8px; }
    .col { flex: 1; }
    .toggle-row { display: flex; align-items: center; justify-content: space-between; margin-bottom: 12px; padding: 8px 12px; background: #08110b; border: 1px solid var(--card-border); border-radius: 6px; }
    .presets-bar { display: flex; gap: 6px; overflow-x: auto; margin-bottom: 10px; }
    .preset-chip { padding: 6px 10px; font-size: 10.5px; background: #09120c; border: 1px solid var(--card-border); border-radius: 4px; cursor: pointer; color: var(--muted); white-space: nowrap; font-family: var(--font-mono); }
    .preset-chip.active { border-color: var(--primary); color: var(--primary); }
    .countdown-hud { font-family: var(--font-mono); font-size: 20px; font-weight: bold; color: var(--danger); letter-spacing: 2px; text-align: center; padding: 14px; background: var(--danger-dim); border: 1px solid var(--danger); border-radius: 6px; margin-bottom: 12px; }
  </style>
</head>
<body>
  <header>
    <div>
      <div style="font-weight:900; letter-spacing:1.5px; color:var(--text); font-size:14px; font-family:var(--font-mono);">SANDI-KARTIKA // WEB SUITE</div>
      <div style="font-size:9.5px; color:var(--muted); font-family:var(--font-mono);">PUSSIBERAD // 100% OFFLINE ZERO-SERVER CRYPTO & STEGO</div>
    </div>
    <div class="badge" id="airgapBadge">AIR-GAPPED LOCAL ENCLAVE</div>
  </header>

  <div class="nav-tabs">
    <button class="tab-btn active" onclick="switchTab('embed')">1. SISIP SANDI (ENCODE)</button>
    <button class="tab-btn" onclick="switchTab('extract')">2. URAI SANDI (DECODE)</button>
    <button class="tab-btn" onclick="switchTab('inspect')">3. INSPEKSI FORENSIK</button>
  </div>

  <!-- TAB 1: EMBED -->
  <div id="tab-embed">
    <div class="card">
      <h2>1. CITRA MEDIA CARRIER (PENYAMARAN)</h2>
      <label>PILIH PRESET TAKTIS ATAU UNGGAH FOTO SENDIRI:</label>
      <div class="presets-bar">
        <div class="preset-chip active" onclick="drawCarrierPreset(0)">Patroli Hutan</div>
        <div class="preset-chip" onclick="drawCarrierPreset(1)">Pos Perbatasan</div>
        <div class="preset-chip" onclick="drawCarrierPreset(2)">Depot Logistik</div>
        <div class="preset-chip" onclick="drawCarrierPreset(3)">Peta Kontur</div>
      </div>
      <input type="file" id="carrierFile" accept="image/*" onchange="loadCustomCarrier(event)">
      <canvas id="carrierCanvas" width="480" height="480"></canvas>
      <div style="font-size:10px; color:var(--muted); font-family:var(--font-mono);" id="carrierStats">Kapasitas Maksimal: ~86,400 Bytes (3-bit/pixel LSB)</div>
    </div>

    <!-- Mode Deniable Toggle -->
    <div class="toggle-row">
      <div>
        <div style="font-weight:bold; font-size:11.5px; color:var(--primary); font-family:var(--font-mono);">SANDI KAMUFLASE (PLAUSIBLE DENIABILITY)</div>
        <div style="font-size:9.5px; color:var(--muted);">Satu gambar berisi 2 sandi: Sandi Utama (Rahasia) & Sandi Umpan (Interogasi)</div>
      </div>
      <input type="checkbox" id="toggleDeniable" checked onchange="toggleDeniableView()">
    </div>

    <!-- Master Classified Payload -->
    <div class="card" style="border-color:var(--primary);">
      <h2>INSTRUKSI OPERASI TAKTIS (MASTER KEY)</h2>
      <div class="row">
        <div class="col">
          <label>JUDUL PERINTAH</label>
          <input type="text" id="masterTitle" value="PO KARTIKA SAKTI - FASE 2">
        </div>
        <div class="col">
          <label>KLASIFIKASI</label>
          <select id="masterClass">
            <option value="TOP_SECRET" selected>SANGAT RAHASIA (TOP SECRET)</option>
            <option value="CONFIDENTIAL">RAHASIA TAKTIS (CONFIDENTIAL)</option>
            <option value="RESTRICTED">TERBATAS (RESTRICTED)</option>
          </select>
        </div>
      </div>
      <label>KOORDINAT SASARAN (MGRS / GEOGRAFIS)</label>
      <input type="text" id="masterCoords" value="07°14'22.1&quot;S 110°24'18.8&quot;E (MGRS: 49M FM 3418 9912)">
      <label>ISI INSTRUKSI OPERASI RAHASIA</label>
      <textarea id="masterMsg" rows="3">1. Sasaran utama: Radar musuh sektor barat daya.
2. Waktu H-Hour: 04.30 WIB.
3. Rute senyap menyusuri lembah 4.
4. Kode panggil sandi: RAJAWALI-9.</textarea>
      <label>KATA SANDI UTAMA (MASTER PASSPHRASE)</label>
      <input type="password" id="masterPass" value="KARTIKA-2026">
    </div>

    <!-- Decoy Camouflage Payload -->
    <div class="card" id="decoySection" style="border-color:var(--decoy);">
      <h2 style="color:var(--decoy)">SANDI KAMUFLASE (DECOY / UMPAN INTEROGASI)</h2>
      <label>JUDUL LAPORAN UMPAN</label>
      <input type="text" id="decoyTitle" value="LAPORAN RUTIN BAMA PEKAN KE-IV">
      <label>ISI LAPORAN LOGISTIK / JAGA BIASA</label>
      <textarea id="decoyMsg" rows="3">Kepada Yth. Pasi Logistik:
Stok aman untuk 14 hari ke depan:
- Beras: 450 Kg
- Minyak: 60 L
- Daging T2: 120 pack
Permohonan tambahan tabung gas.</textarea>
      <label>KATA SANDI UMPAN (DURESS KEY)</label>
      <input type="password" id="decoyPass" value="BAMA-LOGISTIK">
    </div>

    <!-- Tactical Time-Lock Toggle -->
    <div class="card" style="border-color:var(--gold);">
      <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:8px;">
        <h2 style="color:var(--gold); margin-bottom:0;">PEMBATASAN WAKTU BUKA (TACTICAL TIME-LOCK)</h2>
        <input type="checkbox" id="toggleTimeLock" onchange="toggleTimeLockView()">
      </div>
      <div id="timeLockOptions" style="display:none;">
        <label>PESAN HANYA DAPAT DIBUKA SETELAH (JAM DARI SEKARANG):</label>
        <select id="timeLockHours">
          <option value="1">1 Jam ke Depan</option>
          <option value="4" selected>4 Jam ke Depan (Jadwal Operasi Malam)</option>
          <option value="8">8 Jam ke Depan</option>
          <option value="24">24 Jam ke Depan</option>
        </select>
      </div>
    </div>

    <button class="btn" onclick="executeEmbed()">ENKRIPSI & SISIP KE CITRA STEGO (AES-256-GCM)</button>

    <div id="embedResult" style="display:none;" class="card" style="border-color:var(--primary); margin-top:14px;">
      <h2 style="color:var(--primary);">SUKSES! CITRA STEGO LOSSLESS SIAP:</h2>
      <canvas id="stegoCanvas"></canvas>
      <div id="embedDetails" style="font-size:10px; color:var(--muted); font-family:var(--font-mono); margin-bottom:8px;"></div>
      <a id="downloadLink" class="btn" style="display:block; text-align:center; text-decoration:none; margin-top:6px;">UNDUH CITRA STEGO (.PNG LOSSLESS)</a>
    </div>
  </div>

  <!-- TAB 2: EXTRACT -->
  <div id="tab-extract" style="display:none;">
    <div class="card">
      <h2>UNGGAH CITRA STEGO YANG INGIN DIURAI</h2>
      <input type="file" id="stegoFileInput" accept="image/png,image/*" onchange="loadStegoImage(event)">
      <canvas id="stegoExtractCanvas" width="400" height="300" style="display:none;"></canvas>
      <label style="margin-top:8px;">MASUKKAN KATA SANDI (MASTER ATAU SANDI UMPAN):</label>
      <input type="password" id="extractPass" placeholder="Ketik kata sandi...">
      <button class="btn" onclick="executeExtract()">URAI INSTRUKSI TAKTIS</button>
    </div>

    <!-- Time-Lock Warning Banner -->
    <div id="timeLockHud" style="display:none;" class="card" style="border-color:var(--danger);">
      <h2 style="color:var(--danger);">INSTRUKSI MASIH TERKUNCI JADWAL OPERASI</h2>
      <div class="countdown-hud" id="countdownHudText">00:00:00</div>
      <p style="font-size:11px; color:var(--muted); margin-bottom:10px; font-family:var(--font-mono);">Pesan belum diizinkan untuk dibuka sebelum H-Hour jadwal operasi tercapai demi kerahasiaan rute.</p>
      <label>PIN KHUSUS KOMANDAN (OVERRIDE):</label>
      <input type="password" id="overridePin" placeholder="Masukkan PIN komandan...">
      <button class="btn btn-danger" onclick="overrideTimeLock()">BUKA PAKSA DENGAN PIN KOMANDAN</button>
    </div>

    <!-- Result Display -->
    <div id="extractResult" style="display:none;" class="card">
      <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:8px;">
        <h2 id="extractStatusTitle" style="margin-bottom:0;">HASIL DEKRIPSI</h2>
        <div id="extractBadge" class="badge">DEKODING SUKSES</div>
      </div>
      <div class="result-box">
        <div id="resCoordsBox" style="display:none; margin-bottom:8px; padding:6px; background:#0b1911; border:1px solid var(--primary); border-radius:4px; font-size:10.5px; font-family:var(--font-mono); color:var(--primary);"></div>
        <h3 id="resTitle" style="font-size:13px; color:var(--text); margin-bottom:6px; font-family:var(--font-mono);"></h3>
        <p id="resMsg" style="font-size:11.5px; line-height:1.6; white-space:pre-wrap; color:var(--text); font-family:var(--font-mono);"></p>
      </div>
      <div id="resMeta" style="font-size:9.5px; color:var(--muted); font-family:var(--font-mono); margin-top:8px;"></div>
    </div>
  </div>

  <!-- TAB 3: FORENSIC INSPECTOR -->
  <div id="tab-inspect" style="display:none;">
    <div class="card">
      <h2>ANALISIS FORENSIK BIT-PLANE STEGANOGRAFI</h2>
      <p style="font-size:11px; color:var(--muted); margin-bottom:10px;">Visualisasikan lapisan bit citra (LSB Bit-0 vs MSB Bit-7) untuk mengonfirmasi bahwa data tersembunyi tidak menimbulkan artefak visual pada foto asli.</p>
      <div class="presets-bar">
        <div class="preset-chip active" onclick="renderBitPlane(0)">Red LSB (Bit 0)</div>
        <div class="preset-chip" onclick="renderBitPlane(1)">Green LSB (Bit 0)</div>
        <div class="preset-chip" onclick="renderBitPlane(2)">Blue LSB (Bit 0)</div>
        <div class="preset-chip" onclick="renderBitPlane(7)">MSB (Bit 7 - Kontur)</div>
      </div>
      <canvas id="inspectorCanvas" width="480" height="480"></canvas>
    </div>
  </div>

  <script>
    // Tactical Audio Synthesizer (Native Web Audio API, Zero external audio files)
    let audioCtx = null;
    function playTacticalChirp(freq = 880, type = 'sine', duration = 0.08) {
      try {
        if (!audioCtx) audioCtx = new (window.AudioContext || window.webkitAudioContext)();
        const osc = audioCtx.createOscillator();
        const gain = audioCtx.createGain();
        osc.type = type;
        osc.frequency.setValueAtTime(freq, audioCtx.currentTime);
        gain.gain.setValueAtTime(0.08, audioCtx.currentTime);
        gain.gain.exponentialRampToValueAtTime(0.0001, audioCtx.currentTime + duration);
        osc.connect(gain);
        gain.connect(audioCtx.destination);
        osc.start();
        osc.stop(audioCtx.currentTime + duration);
      } catch(e) {}
    }

    let activeCarrierImg = null;
    let loadedStegoImg = null;
    let pendingLockedPayload = null;
    let countdownInterval = null;

    // Preset procedural carrier graphics
    function drawCarrierPreset(idx) {
      const cv = document.getElementById('carrierCanvas');
      const ctx = cv.getContext('2d');
      const w = cv.width, h = cv.height;
      document.querySelectorAll('.presets-bar .preset-chip').forEach((c, i) => {
        if (i < 4) c.className = 'preset-chip ' + (i === idx ? 'active' : '');
      });

      if (idx === 0) {
        // Forest Recon Patrol
        const grad = ctx.createLinearGradient(0, 0, 0, h);
        grad.addColorStop(0, '#122630');
        grad.addColorStop(1, '#4a6246');
        ctx.fillStyle = grad;
        ctx.fillRect(0, 0, w, h);
        // Mountain
        ctx.fillStyle = '#20362d';
        ctx.beginPath();
        ctx.moveTo(0, h * 0.5); ctx.lineTo(w * 0.3, h * 0.3); ctx.lineTo(w * 0.7, h * 0.45); ctx.lineTo(w, h * 0.35); ctx.lineTo(w, h); ctx.lineTo(0, h);
        ctx.fill();
        // Trees
        ctx.fillStyle = '#0c1c14';
        for (let i = 0; i < 16; i++) {
          const tx = i * (w / 15);
          ctx.beginPath();
          ctx.moveTo(tx, h * 0.6); ctx.lineTo(tx + 20, h); ctx.lineTo(tx - 20, h);
          ctx.fill();
        }
      } else if (idx === 1) {
        // Border Outpost
        const grad = ctx.createLinearGradient(0, 0, 0, h);
        grad.addColorStop(0, '#191e2d'); grad.addColorStop(0.6, '#a5552d'); grad.addColorStop(1, '#181a1c');
        ctx.fillStyle = grad;
        ctx.fillRect(0, 0, w, h);
        ctx.fillStyle = '#f0b446';
        ctx.beginPath(); ctx.arc(w * 0.75, h * 0.35, 45, 0, Math.PI * 2); ctx.fill();
        // Ground & Tower
        ctx.fillStyle = '#0f1012';
        ctx.fillRect(0, h * 0.65, w, h * 0.35);
        ctx.fillRect(w * 0.3, h * 0.45, 40, h * 0.2);
      } else if (idx === 2) {
        // Logistics Depot
        ctx.fillStyle = '#23282c';
        ctx.fillRect(0, 0, w, h);
        ctx.fillStyle = '#303438';
        ctx.fillRect(0, h * 0.5, w, h * 0.5);
        // Crates
        const colors = ['#554b32', '#46553c', '#3c4437'];
        for (let r = 0; r < 2; r++) {
          for (let c = 0; c < 3; c++) {
            ctx.fillStyle = colors[(r + c) % 3];
            ctx.fillRect(w * 0.15 + c * 90, h * 0.55 + r * 70, 80, 60);
          }
        }
      } else {
        // Topographic Map
        ctx.fillStyle = '#0e161c';
        ctx.fillRect(0, 0, w, h);
        ctx.strokeStyle = '#234655';
        ctx.lineWidth = 1.5;
        for (let r = 20; r < w * 0.6; r += 28) {
          ctx.beginPath();
          ctx.arc(w * 0.45, h * 0.45, r, 0, Math.PI * 2);
          ctx.stroke();
        }
        ctx.strokeStyle = '#2ee59d';
        ctx.strokeRect(w * 0.4, h * 0.4, 40, 40);
      }

      activeCarrierImg = new Image();
      activeCarrierImg.src = cv.toDataURL();
      playTacticalChirp(600, 'sine', 0.05);
    }

    function loadCustomCarrier(e) {
      const file = e.target.files[0];
      if (!file) return;
      const reader = new FileReader();
      reader.onload = ev => {
        const img = new Image();
        img.onload = () => {
          const cv = document.getElementById('carrierCanvas');
          cv.width = img.width; cv.height = img.height;
          cv.getContext('2d').drawImage(img, 0, 0);
          activeCarrierImg = img;
          const cap = Math.floor((img.width * img.height * 3) / 8) - 4;
          document.getElementById('carrierStats').innerText = 'Resolusi: ' + img.width + 'x' + img.height + ' px | Kapasitas: ~' + cap + ' Bytes';
        };
        img.src = ev.target.result;
      };
      reader.readAsDataURL(file);
    }

    function switchTab(t) {
      document.getElementById('tab-embed').style.display = t === 'embed' ? 'block' : 'none';
      document.getElementById('tab-extract').style.display = t === 'extract' ? 'block' : 'none';
      document.getElementById('tab-inspect').style.display = t === 'inspect' ? 'block' : 'none';
      document.querySelectorAll('.nav-tabs .tab-btn').forEach((b, i) => {
        const isThis = (i === 0 && t === 'embed') || (i === 1 && t === 'extract') || (i === 2 && t === 'inspect');
        b.className = 'tab-btn ' + (isThis ? 'active' : '');
      });
      if (t === 'inspect') renderBitPlane(0);
      playTacticalChirp(900, 'sine', 0.04);
    }

    function toggleDeniableView() {
      const checked = document.getElementById('toggleDeniable').checked;
      document.getElementById('decoySection').style.display = checked ? 'block' : 'none';
    }

    function toggleTimeLockView() {
      const checked = document.getElementById('toggleTimeLock').checked;
      document.getElementById('timeLockOptions').style.display = checked ? 'block' : 'none';
    }

    // Key Derivation with PBKDF2 (Matches Android CryptoEngine 12,000 iterations)
    async function deriveKey(pw, salt) {
      const enc = new TextEncoder();
      const keyMat = await crypto.subtle.importKey('raw', enc.encode(pw), 'PBKDF2', false, ['deriveKey']);
      return crypto.subtle.deriveKey(
        { name: 'PBKDF2', salt, iterations: 12000, hash: 'SHA-256' },
        keyMat,
        { name: 'AES-GCM', length: 256 },
        false,
        ['encrypt', 'decrypt']
      );
    }

    // Execute Embed
    async function executeEmbed() {
      if (!activeCarrierImg) { alert('Pilih atau buat gambar carrier terlebih dahulu.'); return; }
      const mPass = document.getElementById('masterPass').value;
      if (!mPass) { alert('Sandi Master wajib diisi.'); return; }
      const isDeniable = document.getElementById('toggleDeniable').checked;
      const dPass = document.getElementById('decoyPass').value;
      if (isDeniable && !dPass) { alert('Sandi Umpan wajib diisi dalam mode Sandi Kamuflase.'); return; }
      if (isDeniable && mPass === dPass) { alert('Sandi Master dan Sandi Umpan tidak boleh sama demi keamanan.'); return; }

      const isTimeLock = document.getElementById('toggleTimeLock').checked;
      const hours = parseInt(document.getElementById('timeLockHours').value) || 0;
      const timeLockEpoch = isTimeLock ? (Date.now() + (hours * 3600 * 1000)) : 0;

      const masterPayloadObj = {
        type: 'REAL',
        title: document.getElementById('masterTitle').value,
        message: document.getElementById('masterMsg').value,
        coordinates: document.getElementById('masterCoords').value,
        classification: document.getElementById('masterClass').value,
        timeLockEpochMs: timeLockEpoch,
        createdAtEpochMs: Date.now(),
        authorUnit: 'PUSSIBERAD // WEB-ENCLAVE'
      };

      const enc = new TextEncoder();
      const mPlain = enc.encode(JSON.stringify(masterPayloadObj));
      const mSalt = crypto.getRandomValues(new Uint8Array(16));
      const mIv = crypto.getRandomValues(new Uint8Array(12));
      const mKey = await deriveKey(mPass, mSalt);
      const mCt = new Uint8Array(await crypto.subtle.encrypt({ name: 'AES-GCM', iv: mIv }, mKey, mPlain));
      // Full master encrypted block: Salt(16) + IV(12) + CT
      const mEncBlock = new Uint8Array(16 + 12 + mCt.length);
      mEncBlock.set(mSalt, 0); mEncBlock.set(mIv, 16); mEncBlock.set(mCt, 28);

      let packageBytes;
      const now = Date.now();

      if (!isDeniable) {
        // Mode Single (0x01): [SDKR:4B] + [0x01:1B] + [Timestamp:8B] + [MasterLen:4B] + [MasterBlock]
        const totalSize = 4 + 1 + 8 + 4 + mEncBlock.length;
        const buf = new ArrayBuffer(totalSize);
        const view = new DataView(buf);
        const u8 = new Uint8Array(buf);
        u8.set([0x53, 0x44, 0x4B, 0x52, 0x01], 0);
        view.setBigInt64(5, BigInt(now));
        view.setInt32(13, mEncBlock.length);
        u8.set(mEncBlock, 17);
        packageBytes = u8;
      } else {
        // Mode Deniable (0x02): [SDKR:4B] + [0x02:1B] + [Timestamp:8B] + [DecoyLen:4B] + [DecoyBlock] + [MasterLen:4B] + [MasterBlock]
        const decoyPayloadObj = {
          type: 'DECOY',
          title: document.getElementById('decoyTitle').value,
          message: document.getElementById('decoyMsg').value,
          coordinates: '',
          classification: 'DECOY_ROUTINE',
          timeLockEpochMs: 0,
          createdAtEpochMs: now,
          authorUnit: 'BAMA LOGISTIK'
        };
        const dPlain = enc.encode(JSON.stringify(decoyPayloadObj));
        const dSalt = crypto.getRandomValues(new Uint8Array(16));
        const dIv = crypto.getRandomValues(new Uint8Array(12));
        const dKey = await deriveKey(dPass, dSalt);
        const dCt = new Uint8Array(await crypto.subtle.encrypt({ name: 'AES-GCM', iv: dIv }, dKey, dPlain));
        const dEncBlock = new Uint8Array(16 + 12 + dCt.length);
        dEncBlock.set(dSalt, 0); dEncBlock.set(dIv, 16); dEncBlock.set(dCt, 28);

        const totalSize = 4 + 1 + 8 + 4 + dEncBlock.length + 4 + mEncBlock.length;
        const buf = new ArrayBuffer(totalSize);
        const view = new DataView(buf);
        const u8 = new Uint8Array(buf);
        u8.set([0x53, 0x44, 0x4B, 0x52, 0x02], 0);
        view.setBigInt64(5, BigInt(now));
        let off = 13;
        view.setInt32(off, dEncBlock.length); off += 4;
        u8.set(dEncBlock, off); off += dEncBlock.length;
        view.setInt32(off, mEncBlock.length); off += 4;
        u8.set(mEncBlock, off);
        packageBytes = u8;
      }

      // Embed into Canvas via 3-bit LSB (Red, Green, Blue)
      const stegoCv = document.getElementById('stegoCanvas');
      stegoCv.width = activeCarrierImg.width; stegoCv.height = activeCarrierImg.height;
      const ctx = stegoCv.getContext('2d');
      ctx.drawImage(activeCarrierImg, 0, 0);
      const imgData = ctx.getImageData(0, 0, stegoCv.width, stegoCv.height);
      const px = imgData.data;

      // 4-byte length prefix (big endian)
      const fullLen = packageBytes.length;
      const fullData = new Uint8Array(4 + fullLen);
      fullData[0] = (fullLen >> 24) & 0xff;
      fullData[1] = (fullLen >> 16) & 0xff;
      fullData[2] = (fullLen >> 8) & 0xff;
      fullData[3] = fullLen & 0xff;
      fullData.set(packageBytes, 4);

      const totalBits = fullData.length * 8;
      let bitIdx = 0;
      for (let i = 0; i < px.length && bitIdx < totalBits; i += 4) {
        for (let ch = 0; ch < 3 && bitIdx < totalBits; ch++) {
          const bytePos = Math.floor(bitIdx / 8);
          const bitPos = 7 - (bitIdx % 8);
          const bitVal = (fullData[bytePos] >> bitPos) & 1;
          px[i + ch] = (px[i + ch] & ~1) | bitVal;
          bitIdx++;
        }
      }
      ctx.putImageData(imgData, 0, 0);

      document.getElementById('embedResult').style.display = 'block';
      document.getElementById('embedDetails').innerText = 'Ukuran Muatan: ' + fullLen + ' Bytes | Format: PNG Lossless | Mode: ' + (isDeniable ? 'Sandi Kamuflase' : 'Sandi Tunggal');
      const dLink = document.getElementById('downloadLink');
      dLink.href = stegoCv.toDataURL('image/png');
      dLink.download = 'SANDI_STEGO_' + Date.now() + '.png';

      playTacticalChirp(1200, 'triangle', 0.12);
    }

    // Load Stego Image for Extraction
    function loadStegoImage(e) {
      const file = e.target.files[0];
      if (!file) return;
      const reader = new FileReader();
      reader.onload = ev => {
        loadedStegoImg = new Image();
        loadedStegoImg.onload = () => {
          const cv = document.getElementById('stegoExtractCanvas');
          cv.width = loadedStegoImg.width; cv.height = loadedStegoImg.height;
          cv.getContext('2d').drawImage(loadedStegoImg, 0, 0);
          cv.style.display = 'block';
        };
        loadedStegoImg.src = ev.target.result;
      };
      reader.readAsDataURL(file);
    }

    // Execute Extraction
    async function executeExtract() {
      if (!loadedStegoImg) { alert('Silakan unggah citra stego PNG.'); return; }
      const pass = document.getElementById('extractPass').value;
      if (!pass) { alert('Ketik kata sandi operasional.'); return; }

      const cv = document.getElementById('stegoExtractCanvas');
      const ctx = cv.getContext('2d');
      const px = ctx.getImageData(0, 0, cv.width, cv.height).data;

      // Read 32-bit length prefix
      let pLen = 0;
      let bitIdx = 0;
      for (let i = 0; i < px.length && bitIdx < 32; i += 4) {
        for (let ch = 0; ch < 3 && bitIdx < 32; ch++) {
          pLen = (pLen << 1) | (px[i + ch] & 1);
          bitIdx++;
        }
      }

      if (pLen <= 0 || pLen > 15000000) {
        alert('Tidak ditemukan muatan Sandi-Kartika valid pada citra ini.');
        return;
      }

      // Read payload bytes
      const payload = new Uint8Array(pLen);
      let payloadBitIdx = 0;
      const totalBits = pLen * 8;
      let curBit = 0;

      for (let i = 0; i < px.length && curBit < (32 + totalBits); i += 4) {
        for (let ch = 0; ch < 3 && curBit < (32 + totalBits); ch++) {
          if (curBit >= 32) {
            const bP = Math.floor(payloadBitIdx / 8);
            const bOff = 7 - (payloadBitIdx % 8);
            payload[bP] |= ((px[i + ch] & 1) << bOff);
            payloadBitIdx++;
          }
          curBit++;
        }
      }

      // Verify Header
      if (payload[0] !== 0x53 || payload[1] !== 0x44 || payload[2] !== 0x4B || payload[3] !== 0x52) {
        alert('Header Sandi-Kartika (SDKR) tidak cocok.');
        return;
      }

      const mode = payload[4];
      const view = new DataView(payload.buffer, payload.byteOffset, payload.byteLength);

      let masterBlock = null;
      let decoyBlock = null;

      if (mode === 0x01) {
        // Single Mode
        const mLen = view.getInt32(13);
        masterBlock = payload.slice(17, 17 + mLen);
      } else if (mode === 0x02) {
        // Deniable Mode
        const dLen = view.getInt32(13);
        decoyBlock = payload.slice(17, 17 + dLen);
        const mOff = 17 + dLen;
        const mLen = view.getInt32(mOff);
        masterBlock = payload.slice(mOff + 4, mOff + 4 + mLen);
      }

      // Helper to try decrypting a block: Salt(16) + IV(12) + CT
      async function tryDecrypt(block, pw) {
        if (!block || block.length < 28) return null;
        const salt = block.slice(0, 16);
        const iv = block.slice(16, 28);
        const ct = block.slice(28);
        try {
          const key = await deriveKey(pw, salt);
          const dec = await crypto.subtle.decrypt({ name: 'AES-GCM', iv }, key, ct);
          return JSON.parse(new TextDecoder().decode(dec));
        } catch(e) {
          return null;
        }
      }

      // Probe Master first
      const masterData = await tryDecrypt(masterBlock, pass);
      if (masterData) {
        // Check Time-Lock
        if (masterData.timeLockEpochMs && masterData.timeLockEpochMs > Date.now()) {
          pendingLockedPayload = masterData;
          showTimeLockCountdown(masterData.timeLockEpochMs);
          return;
        }
        renderExtractedPayload('SANGAT RAHASIA (MASTER KEY)', masterData, 'var(--danger)', 'DEFCON-1');
        return;
      }

      // Probe Decoy if in deniable mode
      if (decoyBlock) {
        const decoyData = await tryDecrypt(decoyBlock, pass);
        if (decoyData) {
          renderExtractedPayload('STATUS: LOGISTIK RUTIN TERBUKA (DECOY)', decoyData, 'var(--decoy)', 'UNCLASSIFIED');
          return;
        }
      }

      alert('Kata sandi salah atau integritas data gagal diverifikasi.');
    }

    function showTimeLockCountdown(epoch) {
      document.getElementById('timeLockHud').style.display = 'block';
      document.getElementById('extractResult').style.display = 'none';

      if (countdownInterval) clearInterval(countdownInterval);
      function update() {
        const diff = epoch - Date.now();
        if (diff <= 0) {
          clearInterval(countdownInterval);
          document.getElementById('timeLockHud').style.display = 'none';
          renderExtractedPayload('SANGAT RAHASIA (WAKTU OPERASI TIBA)', pendingLockedPayload, 'var(--danger)', 'DEFCON-1');
          return;
        }
        const hrs = String(Math.floor(diff / 3600000)).padStart(2, '0');
        const mins = String(Math.floor((diff % 3600000) / 60000)).padStart(2, '0');
        const secs = String(Math.floor((diff % 60000) / 1000)).padStart(2, '0');
        document.getElementById('countdownHudText').innerText = hrs + ':' + mins + ':' + secs;
      }
      update();
      countdownInterval = setInterval(update, 1000);
      playTacticalChirp(440, 'sawtooth', 0.2);
    }

    function overrideTimeLock() {
      const pin = document.getElementById('overridePin').value;
      if (pin === 'PUSSIBERAD-991') {
        if (countdownInterval) clearInterval(countdownInterval);
        document.getElementById('timeLockHud').style.display = 'none';
        renderExtractedPayload('SANGAT RAHASIA (BYPASS KOMANDAN)', pendingLockedPayload, 'var(--gold)', 'OVERRIDE');
      } else {
        alert('PIN Komandan Salah! Instruksi tetap terkunci.');
      }
    }

    function renderExtractedPayload(status, data, color, badgeText) {
      document.getElementById('timeLockHud').style.display = 'none';
      document.getElementById('extractResult').style.display = 'block';
      const titleEl = document.getElementById('extractStatusTitle');
      titleEl.innerText = status;
      titleEl.style.color = color;
      const badge = document.getElementById('extractBadge');
      badge.innerText = badgeText;
      badge.style.borderColor = color;
      badge.style.color = color;

      document.getElementById('resTitle').innerText = data.title;
      document.getElementById('resMsg').innerText = data.message;

      const coordsBox = document.getElementById('resCoordsBox');
      if (data.coordinates && data.coordinates.trim().length > 0) {
        coordsBox.style.display = 'block';
        coordsBox.innerText = 'SASARAN OPERASI: ' + data.coordinates;
      } else {
        coordsBox.style.display = 'none';
      }

      document.getElementById('resMeta').innerText = 'Klasifikasi: ' + (data.classification || 'TERBATAS') + ' | Unit: ' + (data.authorUnit || 'PUSSIBERAD') + ' | Dibuat: ' + new Date(data.createdAtEpochMs || Date.now()).toLocaleString();
      playTacticalChirp(1000, 'sine', 0.15);
    }

    // Bit-Plane Forensic Renderer
    function renderBitPlane(planeIdx) {
      const srcCanvas = document.getElementById('stegoCanvas') || document.getElementById('carrierCanvas');
      const cv = document.getElementById('inspectorCanvas');
      if (!srcCanvas || !srcCanvas.width) return;
      cv.width = srcCanvas.width; cv.height = srcCanvas.height;
      const ctx = cv.getContext('2d');
      const srcPx = srcCanvas.getContext('2d').getImageData(0, 0, cv.width, cv.height).data;
      const outData = ctx.createImageData(cv.width, cv.height);
      const out = outData.data;

      for (let i = 0; i < srcPx.length; i += 4) {
        let bit = 0;
        if (planeIdx === 0) bit = srcPx[i] & 1; // Red LSB
        else if (planeIdx === 1) bit = srcPx[i + 1] & 1; // Green LSB
        else if (planeIdx === 2) bit = srcPx[i + 2] & 1; // Blue LSB
        else bit = (srcPx[i] >> 7) & 1; // MSB
        const val = bit ? 255 : 0;
        out[i] = val; out[i + 1] = val; out[i + 2] = val; out[i + 3] = 255;
      }
      ctx.putImageData(outData, 0, 0);
    }

    // Initialize Default Procedural Carrier
    drawCarrierPreset(0);
  </script>
</body>
</html>
        """.trimIndent()

        FileOutputStream(htmlFile).use { out ->
            out.write(htmlContent.toByteArray(Charsets.UTF_8))
            out.flush()
        }

        val authority = "${context.packageName}.fileprovider"
        val uri = FileProvider.getUriForFile(context, authority, htmlFile)
        return Pair(htmlFile, uri)
    }
}
