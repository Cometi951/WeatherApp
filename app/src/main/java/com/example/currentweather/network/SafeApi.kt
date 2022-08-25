package com.example.currentweather.network

import com.example.currentweather.model.CurrentWeatherModel
import com.example.currentweather.model.ForeCast3HoursModel
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

object SafeApi {

    private const val DEFAULT_RETRY_COUNT = 3

    suspend fun<T: Any> safeApiCall(
        needRetry: Boolean = true,
        call: suspend () -> T
    ): Results<T> {
        var count = 0
        var backOffTime = 2_000L
        while (count < DEFAULT_RETRY_COUNT) {
            try {
                val response = call.invoke()
                count = DEFAULT_RETRY_COUNT + 1

                if (response is ForeCast3HoursModel){
                    if (response.cod == "200") {
                        return if (response != null) {
                            Results.Success(response)
                        } else {
                            Results.Loading
                        }
                    }
                }else if (response is CurrentWeatherModel){
                    if (response.cod!! == 200) {
                        return if (response != null) {
                            Results.Success(response)
                        } else {
                            Results.Loading
                        }
                    }
                }

            } catch (socketTimeOutException: SocketTimeoutException) {
                if (socketTimeOutException.message != null) {
                    return Results.Error("Parse error : ${socketTimeOutException.message}")
                } else {
                    return Results.Error("")
                }
            } catch (ioException: IOException) {
                ioException.printStackTrace()
                if (needRetry) {
                    delay(backOffTime)
                    count++
                    backOffTime *= 2
                } else {
                    if (ioException.message != null) {
                        return Results.Error("")
                    } else {
                        return Results.Error("")
                    }
                }
            } catch (cancellationException: CancellationException) {
                cancellationException.printStackTrace()
                if (cancellationException.message != null) {
                    return Results.Error(
                        "Parse error : ${cancellationException.message}",
                    )
                } else {
                    return Results.Error("ERROR_CODE_CANCELLATION_JOB")
                }

            } catch (syntaxException: JsonSyntaxException) {
                if (syntaxException.message != null) {
                    return Results.Error("${syntaxException.message}")
                } else {
                    return Results.Error("Something went wrong")
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
                if (exception.message != null) {
                    return Results.Error("Parse error : ${exception.message}")
                } else {
                    // An exception was thrown when calling the API so we're converting this to an ErrorBody
                    return Results.Error("SomeThing went wrong")
                }
            }
        }
        return Results.Error("Something went wrong")
    }
}