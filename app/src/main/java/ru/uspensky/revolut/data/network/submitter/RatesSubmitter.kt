package ru.uspensky.revolut.data.network.submitter

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Url
import ru.uspensky.revolut.data.network.response.RatesResponse
import ru.uspensky.revolut.domain.interactor.Ticker

/**
 * Created by Dmitry Uspensky on 22/10/2018.
 */
class RatesSubmitter(private val ticker: Ticker) : BaseSubmitter<RatesResponse>() {

    override fun createCall(): Call<RatesResponse> {
        return retrofit.create(IRequest::class.java).getRates("latest?base=${ticker.name}")
    }

    private interface IRequest {
        @Headers("Content-Type: application/json")
        @GET
        fun getRates(@Url url: String): Call<RatesResponse>
    }
}