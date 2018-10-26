package ru.uspensky.revolut.domain.interactor


/**
 * Created by Dmitry Uspensky on 23/10/2018.
 */
enum class Ticker {
    AUD {
        override val description: String
            get() = "Australian Dollar"
    },
    BGN {
        override val description: String
            get() = "Bulgarian Lev"
    },
    BRL {
        override val description: String
            get() = "Brazilian Real"
    },
    CAD {
        override val description: String
            get() = "Canadian Dollar"
    },
    CHF {
        override val description: String
            get() = "Swiss Franc"
    },
    CNY {
        override val description: String
            get() = "Yuan Renminbi"
    },
    CZK {
        override val description: String
            get() = "Czech Koruna"
    },
    DKK {
        override val description: String
            get() = "Danish Krone"
    },
    EUR {
        override val description: String
            get() = "Euro"
    },
    GBP {
        override val description: String
            get() = "Pound Sterling"
    },
    HKD {
        override val description: String
            get() = "Hong Kong Dollar"
    },
    HRK {
        override val description: String
            get() = "Kuna"
    },
    HUF {
        override val description: String
            get() = "Forint"
    },
    IDR {
        override val description: String
            get() = "Rupiah"
    },
    ILS {
        override val description: String
            get() = "New Israeli Sheqel"
    },
    INR {
        override val description: String
            get() = "Indian Rupee"
    },
    ISK {
        override val description: String
            get() = "Iceland Krona"
    },
    JPY {
        override val description: String
            get() = "Yen"
    },
    KRW {
        override val description: String
            get() = "South Korean Won"
    },
    MXN {
        override val description: String
            get() = "Mexican Peso"
    },
    MYR {
        override val description: String
            get() = "Malaysian Ringgit"
    },
    NOK {
        override val description: String
            get() = "Norwegian Krone"
    },
    NZD {
        override val description: String
            get() = "New Zealand Dollar"
    },
    PHP {
        override val description: String
            get() = "Philippine Peso"
    },
    PLN {
        override val description: String
            get() = "Zloty"
    },
    RON {
        override val description: String
            get() = "Romanian Leu"
    },
    RUB {
        override val description: String
            get() = "Russian Ruble"
    },
    SEK {
        override val description: String
            get() = "Swedish Krona"
    },
    SGD {
        override val description: String
            get() = "Singapore Dollar"
    },
    THB {
        override val description: String
            get() = "Baht"
    },
    TRY {
        override val description: String
            get() = "Turkish Lira"
    },
    USD {
        override val description: String
            get() = "US Dollar"
    },
    ZAR {
        override val description: String
            get() = "Rand"
    };
    abstract val description: String
}