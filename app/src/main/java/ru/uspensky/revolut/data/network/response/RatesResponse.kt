package ru.uspensky.revolut.data.network.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Dmitry Uspensky on 22/10/2018.
 */
data class RatesResponse(
        @SerializedName("base")
        val base: String,

        @SerializedName("date")
        val date: String,

        @SerializedName("rates")
        val rates: Rates
)

data class Rates(
        @SerializedName("AUD")
        var aud: Double,

        @SerializedName("BGN")
        var bgn: Double,

        @SerializedName("BRL")
        var brl: Double,

        @SerializedName("CAD")
        var cad: Double,

        @SerializedName("CHF")
        var chf: Double,

        @SerializedName("CNY")
        var cny: Double,

        @SerializedName("CZK")
        var czk: Double,

        @SerializedName("DKK")
        var dkk: Double,

        @SerializedName("EUR")
        var eur: Double,

        @SerializedName("GBP")
        var gbp: Double,

        @SerializedName("HKD")
        var hkd: Double,

        @SerializedName("HRK")
        var hrk: Double,

        @SerializedName("HUF")
        var huf: Double,

        @SerializedName("IDR")
        var idr: Double,

        @SerializedName("ILS")
        var ils: Double,

        @SerializedName("INR")
        var inr: Double,

        @SerializedName("ISK")
        var isk: Double,

        @SerializedName("JPY")
        var jpy: Double,

        @SerializedName("KRW")
        var krw: Double,

        @SerializedName("MXN")
        var mxn: Double,

        @SerializedName("MYR")
        var myr: Double,

        @SerializedName("NOK")
        var nok: Double,

        @SerializedName("NZD")
        var nzd: Double,

        @SerializedName("PHP")
        var php: Double,

        @SerializedName("PLN")
        var pln: Double,

        @SerializedName("RON")
        var ron: Double,

        @SerializedName("RUB")
        var rub: Double,

        @SerializedName("SEK")
        var sek: Double,

        @SerializedName("SGD")
        var sgd: Double,

        @SerializedName("THB")
        var thb: Double,

        @SerializedName("TRY")
        var tryy: Double,

        @SerializedName("USD")
        var usd: Double,

        @SerializedName("ZAR")
        var zar: Double
)

fun Rates.multiple(count: Double) {
        this.aud = aud * count
        this.bgn = bgn * count
        this.brl = brl * count
        this.cad = cad * count
        this.chf = chf * count
        this.cny = cny * count
        this.czk = czk * count
        this.dkk = dkk * count
        this.eur = eur * count
        this.gbp = gbp * count
        this.hkd = hkd * count
        this.hrk = hrk * count
        this.huf = huf * count
        this.idr = idr * count
        this.ils = ils * count
        this.inr = inr * count
        this.isk = isk * count
        this.jpy = jpy * count
        this.krw = krw * count
        this.mxn = mxn * count
        this.myr = myr * count
        this.nok = nok * count
        this.nzd = nzd * count
        this.php = php * count
        this.pln = pln * count
        this.ron = ron * count
        this.rub = rub * count
        this.sek = sek * count
        this.sgd = sgd * count
        this.thb = thb * count
        this.tryy = tryy * count
        this.usd = usd * count
        this.zar = zar * count
}