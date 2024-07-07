package br.com.app.credit_authorizer.model

enum class ResponseCode(val code: String) {
    APPROVED("00"),
    INSUFFICIENT_FUNDS("51"),
    OTHER_ERROR("07");

    companion object {
        fun fromCode(code: String): ResponseCode? {
            return values().find { it.code == code }
        }
    }
}

