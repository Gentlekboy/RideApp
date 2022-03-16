package com.gentlekboy.rideapp.model.data

/**
 * Class for handling network responses
 */
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?, msg: String?): Resource<T> {
            return Resource(Status.SUCCESS, data, msg)
        }

        fun <T> error(msg: String?, data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }


    fun resolveMessage(): String =
        when {
            message.isNullOrBlank() -> {
                "An Unknown Error occurred"
            }
            else -> {
                message
            }
        }

}

/**
Sets status for response from api calls
 */
enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}