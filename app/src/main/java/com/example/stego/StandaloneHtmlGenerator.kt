package com.example.stego

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

/**
 * Generates an all-in-one, zero-dependency, standalone single-file HTML/JS application.
 * Enables military personnel to execute AES-256-GCM encryption and LSB steganography
 * on laptops or phones directly in any web browser without installing any APK.
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
  <title>SANDI-KARTIKA // PUSSIBERAD</title>
  <style>
    :root {
      --bg: #090e0b;
      --card: #121e17;
      --border: #1f382a;
      --primary: #2ee59d;
      --text: #e8f3ed;
      --muted: #839b8e;
      --gold: #e5b842;
      --decoy: #60a5fa;
      --danger: #ff4848;
    }
    * { box-sizing: border-box; margin: 0; padding: 0; font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, monospace; }
    body { background: var(--bg); color: var(--text); padding: 16px; max-width: 760px; margin: auto; }
    header { background: #0f1c14; border: 1px solid var(--border); border-radius: 8px; padding: 12px 16px; margin-bottom: 16px; display: flex; justify-content: space-between; align-items: center; }
    .badge { font-size: 10px; font-weight: bold; padding: 2px 6px; border-radius: 4px; background: rgba(46,229,157,0.15); color: var(--primary); border: 1px solid var(--primary); }
    .nav-tabs { display: flex; gap: 8px; margin-bottom: 16px; }
    .tab-btn { flex: 1; padding: 10px; background: var(--card); color: var(--muted); border: 1px solid var(--border); border-radius: 6px; cursor: pointer; font-weight: bold; font-size: 12px; }
    .tab-btn.active { background: var(--primary); color: #000; border-color: var(--primary); }
    .card { background: var(--card); border: 1px solid var(--border); border-radius: 8px; padding: 16px; margin-bottom: 16px; }
    h2 { font-size: 14px; margin-bottom: 12px; color: var(--primary); letter-spacing: 0.5px; }
    label { display: block; font-size: 11px; color: var(--muted); margin-bottom: 4px; }
    input, textarea, select { width: 100%; padding: 10px; background: #0a110d; border: 1px solid var(--border); border-radius: 6px; color: var(--text); margin-bottom: 12px; font-size: 12px; }
    input:focus, textarea:focus { border-color: var(--primary); outline: none; }
    .btn { width: 100%; padding: 12px; background: var(--primary); color: #000; border: none; border-radius: 6px; font-weight: bold; cursor: pointer; font-size: 13px; }
    .btn:hover { filter: brightness(1.1); }
    .result-box { background: #070d0a; border: 1px solid var(--border); border-radius: 6px; padding: 12px; margin-top: 12px; }
    canvas { max-width: 100%; border-radius: 6px; border: 1px solid var(--border); display: block; margin: 8px 0; }
  </style>
</head>
<body>
  <header>
    <div>
      <div style="font-weight:900; letter-spacing:1px; color:var(--text);">SANDI-KARTIKA // WEB SUITE</div>
      <div style="font-size:10px; color:var(--muted);">100% OFFLINE ZERO-SERVER TACTICAL STEGO</div>
    </div>
    <div class="badge">AIR-GAPPED ENCLAVE</div>
  </header>

  <div class="nav-tabs">
    <button class="tab-btn active" onclick="switchTab('embed')">1. SISIP SANDI</button>
    <button class="tab-btn" onclick="switchTab('extract')">2. URAI SANDI</button>
  </div>

  <div id="tab-embed">
    <div class="card">
      <h2>PILIH FOTO CARRIER</h2>
      <input type="file" id="carrierFile" accept="image/*" onchange="loadCarrierImage(event)">
      <canvas id="carrierCanvas" width="400" height="300" style="display:none;"></canvas>
    </div>

    <div class="card">
      <h2>PESAN RAHASIA OPERASI (MASTER)</h2>
      <label>JUDUL PERINTAH</label>
      <input type="text" id="masterTitle" value="PO KARTIKA SAKTI - FASE 2">
      <label>ISI INSTRUKSI TAKTIS</label>
      <textarea id="masterMsg" rows="3">1. Sasaran: Radar musuh sektor barat daya. 2. Waktu Serangan: 04.30 WIB. Sandi: RAJAWALI-9.</textarea>
      <label>KATA SANDI UTAMA</label>
      <input type="password" id="masterPass" value="KARTIKA-2026">
    </div>

    <div class="card" style="border-color:var(--decoy)">
      <h2 style="color:var(--decoy)">SANDI KAMUFLASE (DECOY / UMPAN)</h2>
      <label>LAPORAN LOGISTIK UMPAN</label>
      <input type="text" id="decoyTitle" value="LAPORAN RUTIN BAMA PEKAN KE-IV">
      <textarea id="decoyMsg" rows="2">Stok aman 14 hari: Beras 450 Kg, Minyak 60 L. Tambahan gas lusa.</textarea>
      <label>KATA SANDI UMPAN (DURESS KEY)</label>
      <input type="password" id="decoyPass" value="BAMA-LOGISTIK">
    </div>

    <button class="btn" onclick="executeEmbed()">ENKRIPSI & SISIP KE CITRA STEGO</button>
    <div id="embedResult" style="display:none;" class="card">
      <h2 style="color:var(--primary);">SUKSES! CITRA STEGO SIAP:</h2>
      <canvas id="stegoCanvas"></canvas>
      <a id="downloadLink" class="btn" style="display:block; text-align:center; text-decoration:none; margin-top:8px;">UNDUH FILE STEGO (.PNG LOSSLESS)</a>
    </div>
  </div>

  <div id="tab-extract" style="display:none;">
    <div class="card">
      <h2>UNGGAH CITRA STEGO</h2>
      <input type="file" id="stegoFile" accept="image/png" onchange="loadStegoImage(event)">
      <label>MASUKKAN KATA SANDI OPERASIONAL (MASTER ATAU UMPAN)</label>
      <input type="password" id="extractPass" placeholder="Ketik kata sandi...">
      <button class="btn" onclick="executeExtract()">URAI INSTRUKSI TAKTIS</button>
    </div>

    <div id="extractResult" style="display:none;" class="card">
      <h2 id="extractStatusTitle">HASIL DEKRIPSI:</h2>
      <div class="result-box">
        <h3 id="resTitle" style="margin-bottom:6px;"></h3>
        <p id="resMsg" style="font-size:12px; line-height:1.5; white-space:pre-wrap;"></p>
      </div>
    </div>
  </div>

  <script>
    let carrierImg = null;
    let stegoImg = null;

    function switchTab(t) {
      document.getElementById('tab-embed').style.display = t === 'embed' ? 'block' : 'none';
      document.getElementById('tab-extract').style.display = t === 'extract' ? 'block' : 'none';
      document.querySelectorAll('.tab-btn').forEach((b, i) => {
        b.className = 'tab-btn ' + ((i===0 && t==='embed') || (i===1 && t==='extract') ? 'active' : '');
      });
    }

    function loadCarrierImage(e) {
      const file = e.target.files[0];
      if (!file) return;
      const reader = new FileReader();
      reader.onload = ev => {
        carrierImg = new Image();
        carrierImg.onload = () => {
          const cv = document.getElementById('carrierCanvas');
          cv.width = carrierImg.width;
          cv.height = carrierImg.height;
          cv.getContext('2d').drawImage(carrierImg, 0, 0);
          cv.style.display = 'block';
        };
        carrierImg.src = ev.target.result;
      };
      reader.readAsDataURL(file);
    }

    function loadStegoImage(e) {
      const file = e.target.files[0];
      if (!file) return;
      const reader = new FileReader();
      reader.onload = ev => {
        stegoImg = new Image();
        stegoImg.src = ev.target.result;
      };
      reader.readAsDataURL(file);
    }

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

    async function executeEmbed() {
      if (!carrierImg) { alert('Pilih foto carrier terlebih dahulu.'); return; }
      const mPass = document.getElementById('masterPass').value;
      const dPass = document.getElementById('decoyPass').value;
      if (!mPass) { alert('Sandi Master wajib diisi.'); return; }

      const mPayload = JSON.stringify({ type:'REAL', title: document.getElementById('masterTitle').value, message: document.getElementById('masterMsg').value });
      const dPayload = JSON.stringify({ type:'DECOY', title: document.getElementById('decoyTitle').value, message: document.getElementById('decoyMsg').value });

      const enc = new TextEncoder();
      // Encrypt Master
      const mSalt = crypto.getRandomValues(new Uint8Array(16));
      const mIv = crypto.getRandomValues(new Uint8Array(12));
      const mKey = await deriveKey(mPass, mSalt);
      const mCt = new Uint8Array(await crypto.subtle.encrypt({ name: 'AES-GCM', iv: mIv }, mKey, enc.encode(mPayload)));

      // Encrypt Decoy
      const dSalt = crypto.getRandomValues(new Uint8Array(16));
      const dIv = crypto.getRandomValues(new Uint8Array(12));
      const dKey = await deriveKey(dPass, dSalt);
      const dCt = new Uint8Array(await crypto.subtle.encrypt({ name: 'AES-GCM', iv: dIv }, dKey, enc.encode(dPayload)));

      // Pack format: [SDKR (4B)] + [Mode: 0x02] + [Time: 8B] + [DecoyLen: 4B] + [Decoy: 16+12+CT] + [MasterLen: 4B] + [Master: 16+12+CT]
      const dBlockLen = 16 + 12 + dCt.length;
      const mBlockLen = 16 + 12 + mCt.length;
      const totalLen = 4 + 1 + 8 + 4 + dBlockLen + 4 + mBlockLen;
      const buf = new ArrayBuffer(totalLen);
      const view = new DataView(buf);
      const u8 = new Uint8Array(buf);

      // Magic SDKR
      u8[0] = 0x53; u8[1] = 0x44; u8[2] = 0x4B; u8[3] = 0x52; u8[4] = 0x02;
      let offset = 13;
      view.setInt32(offset, dBlockLen); offset += 4;
      u8.set(dSalt, offset); offset += 16;
      u8.set(dIv, offset); offset += 12;
      u8.set(dCt, offset); offset += dCt.length;

      view.setInt32(offset, mBlockLen); offset += 4;
      u8.set(mSalt, offset); offset += 16;
      u8.set(mIv, offset); offset += 12;
      u8.set(mCt, offset);

      // Embed LSB into canvas
      const cv = document.getElementById('stegoCanvas');
      cv.width = carrierImg.width;
      cv.height = carrierImg.height;
      const ctx = cv.getContext('2d');
      ctx.drawImage(carrierImg, 0, 0);
      const imgData = ctx.getImageData(0, 0, cv.width, cv.height);
      const px = imgData.data;

      // Prefix length 4 bytes
      const fullLen = u8.length;
      const fullBytes = new Uint8Array(4 + fullLen);
      fullBytes[0] = (fullLen >> 24) & 0xff;
      fullBytes[1] = (fullLen >> 16) & 0xff;
      fullBytes[2] = (fullLen >> 8) & 0xff;
      fullBytes[3] = fullLen & 0xff;
      fullBytes.set(u8, 4);

      let bitIdx = 0;
      const totalBits = fullBytes.length * 8;
      for (let i = 0; i < px.length && bitIdx < totalBits; i += 4) {
        for (let ch = 0; ch < 3 && bitIdx < totalBits; ch++) {
          const bytePos = Math.floor(bitIdx / 8);
          const bitPos = 7 - (bitIdx % 8);
          const bitVal = (fullBytes[bytePos] >> bitPos) & 1;
          px[i + ch] = (px[i + ch] & ~1) | bitVal;
          bitIdx++;
        }
      }
      ctx.putImageData(imgData, 0, 0);

      document.getElementById('embedResult').style.display = 'block';
      const dLink = document.getElementById('downloadLink');
      dLink.href = cv.toDataURL('image/png');
      dLink.download = 'SANDI_STEGO_' + Date.now() + '.png';
    }

    async function executeExtract() {
      if (!stegoImg) { alert('Unggah citra stego PNG.'); return; }
      const pass = document.getElementById('extractPass').value;
      if (!pass) { alert('Ketik kata sandi.'); return; }

      const cv = document.createElement('canvas');
      cv.width = stegoImg.width; cv.height = stegoImg.height;
      const ctx = cv.getContext('2d');
      ctx.drawImage(stegoImg, 0, 0);
      const px = ctx.getImageData(0, 0, cv.width, cv.height).data;

      // Extract length 32 bits
      let pLen = 0;
      let bitIdx = 0;
      for (let i = 0; i < px.length && bitIdx < 32; i += 4) {
        for (let ch = 0; ch < 3 && bitIdx < 32; ch++) {
          pLen = (pLen << 1) | (px[i + ch] & 1);
          bitIdx++;
        }
      }

      if (pLen <= 0 || pLen > 10000000) { alert('Tidak ditemukan sandi valid.'); return; }
      const payload = new Uint8Array(pLen);
      let payloadBitIdx = 0;
      const totalBits = pLen * 8;

      let pixelOffset = 0;
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

      // Parse payload
      const view = new DataView(payload.buffer, payload.byteOffset, payload.byteLength);
      // Mode deniable: decoy at offset 13, master after
      const dLen = view.getInt32(13);
      const dSalt = payload.slice(17, 33);
      const dIv = payload.slice(33, 45);
      const dCt = payload.slice(45, 17 + dLen);

      const mOffset = 17 + dLen;
      const mLen = view.getInt32(mOffset);
      const mSalt = payload.slice(mOffset + 4, mOffset + 20);
      const mIv = payload.slice(mOffset + 20, mOffset + 32);
      const mCt = payload.slice(mOffset + 32, mOffset + 4 + mLen);

      // Probe Master
      try {
        const mKey = await deriveKey(pass, mSalt);
        const dec = await crypto.subtle.decrypt({ name: 'AES-GCM', iv: mIv }, mKey, mCt);
        const data = JSON.parse(new TextDecoder().decode(dec));
        showResult('KLASIFIKASI: SANGAT RAHASIA (MASTER)', data, 'var(--danger)');
        return;
      } catch(e) {}

      // Probe Decoy
      try {
        const dKey = await deriveKey(pass, dSalt);
        const dec = await crypto.subtle.decrypt({ name: 'AES-GCM', iv: dIv }, dKey, dCt);
        const data = JSON.parse(new TextDecoder().decode(dec));
        showResult('STATUS: LOGISTIK RUTIN TERBUKA (DECOY)', data, 'var(--decoy)');
        return;
      } catch(e) {}

      alert('Kata sandi tidak cocok atau autentikasi GCM gagal.');
    }

    function showResult(badge, data, color) {
      document.getElementById('extractResult').style.display = 'block';
      const titleEl = document.getElementById('extractStatusTitle');
      titleEl.innerText = badge;
      titleEl.style.color = color;
      document.getElementById('resTitle').innerText = data.title;
      document.getElementById('resMsg').innerText = data.message;
    }
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
