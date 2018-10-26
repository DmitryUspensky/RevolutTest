package ru.uspensky.revolut.data.network.submitter

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.uspensky.revolut.di.DaggerWrapper
import java.util.concurrent.TimeUnit

/**
 * Created by Dmitry Uspensky on 22/10/2018.
 */

// Code
private const val HTTP_OK = 200

// Timeout
private const val CONNECT_TIMEOUT_SEC = 10L
private const val READ_TIMEOUT_SEC = 60L

// Url
private const val BASE_URL = "https://revolut.duckdns.org/"

abstract class BaseSubmitter<T> {

    private val gson: Gson = GsonBuilder().create()

    private val httpClient: OkHttpClient =
            OkHttpClient.Builder()
                    .connectTimeout(CONNECT_TIMEOUT_SEC, TimeUnit.SECONDS)
                    .readTimeout(READ_TIMEOUT_SEC, TimeUnit.SECONDS)
                    .build()

    protected val retrofit: Retrofit =
            Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient)
                    .build()

    val observable: Observable<T> = initObservable()

    protected abstract fun createCall(): Call<T>

    private fun submit(): T? {
        val retrofitResponse = createCall().execute()
        return when (retrofitResponse.code()) {
            HTTP_OK -> retrofitResponse.body()
            else -> null
        }
    }

    private fun initObservable(): Observable<T> {
        return Observable.create<T> { e: ObservableEmitter<T> ->
                try {
                    if (e.isDisposed)
                        return@create
                    val response: T? = submit()
                    if (response != null) {
                        e.onNext(response)
                        e.onComplete()
                    }
                } catch (t: Throwable) {
                    if (!e.isDisposed) {
                        e.onError(t)
                    }
                }
        }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
    }

}