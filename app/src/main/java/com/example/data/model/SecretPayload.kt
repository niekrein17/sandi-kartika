package com.example.data.model

import org.json.JSONObject

enum class PayloadClassification(val displayName: String, val levelCode: String) {
    TOP_SECRET("SANGAT RAHASIA (TOP SECRET)", "DEFCON-1"),
    CONFIDENTIAL("RAHASIA TAKTIS (CONFIDENTIAL)", "DEFCON-2"),
    RESTRICTED("TERBATAS (RESTRICTED)", "DEFCON-3"),
    DECOY_ROUTINE("LOGISTIK UMUM / TERBUKA (DECOY)", "UNCLASSIFIED")
}

data class SecretPayload(
    val type: String, // "REAL" or "DECOY"
    val title: String,
    val message: String,
    val coordinates: String? = null,
    val classification: PayloadClassification = PayloadClassification.TOP_SECRET,
    val timeLockEpochMs: Long = 0L, // 0 = no time lock
    val createdAtEpochMs: Long = System.currentTimeMillis(),
    val authorUnit: String = "PUSSIBERAD // SAT-01",
    val hashIntegrity: String = ""
) {
    fun toJsonString(): String {
        val obj = JSONObject()
        obj.put("type", type)
        obj.put("title", title)
        obj.put("message", message)
        obj.put("coordinates", coordinates ?: "")
        obj.put("classification", classification.name)
        obj.put("timeLockEpochMs", timeLockEpochMs)
        obj.put("createdAtEpochMs", createdAtEpochMs)
        obj.put("authorUnit", authorUnit)
        obj.put("hashIntegrity", hashIntegrity)
        return obj.toString()
    }

    companion object {
        fun fromJsonString(jsonStr: String): SecretPayload {
            val obj = JSONObject(jsonStr)
            val classifName = obj.optString("classification", PayloadClassification.TOP_SECRET.name)
            val classification = try {
                PayloadClassification.valueOf(classifName)
            } catch (e: Exception) {
                PayloadClassification.TOP_SECRET
            }
            return SecretPayload(
                type = obj.optString("type", "REAL"),
                title = obj.optString("title", "INSTRUKSI TAKTIS"),
                message = obj.optString("message", ""),
                coordinates = obj.optString("coordinates", "").ifEmpty { null },
                classification = classification,
                timeLockEpochMs = obj.optLong("timeLockEpochMs", 0L),
                createdAtEpochMs = obj.optLong("createdAtEpochMs", System.currentTimeMillis()),
                authorUnit = obj.optString("authorUnit", "PUSSIBERAD"),
                hashIntegrity = obj.optString("hashIntegrity", "")
            )
        }
    }
}

enum class ExtractionStatus {
    IDLE,
    PROCESSING,
    SUCCESS_MASTER,
    SUCCESS_DECOY,
    ERROR_WRONG_PASSWORD,
    ERROR_NO_PAYLOAD,
    ERROR_CORRUPTED,
    ERROR_FILE_LOAD
}

data class DecryptedResult(
    val status: ExtractionStatus,
    val payload: SecretPayload? = null,
    val isDecoy: Boolean = false,
    val isTimeLocked: Boolean = false,
    val timeLockRemainingMs: Long = 0L,
    val executionDurationMs: Long = 0L,
    val errorMessage: String? = null
)
