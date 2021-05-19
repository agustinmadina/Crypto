package com.bitso.sample.network.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Agustin Madina on 26/04/2021.
 */
@JsonClass(generateAdapter = true)
data class MovieDetail(
    val adult: Boolean,

    @Json(name = "backdrop_path")
    val backdropPath: String,

    @Json(name = "belongs_to_collection")
    val belongsToCollection: Any? = null,

    val budget: Long,
    val genres: List<Genre>,
    val homepage: String,
    val id: Long,

    @Json(name = "imdb_id")
    val imdbID: String,

    @Json(name = "original_language")
    val originalLanguage: String,

    @Json(name = "original_title")
    val originalTitle: String,

    val overview: String,
    val popularity: Double,

    @Json(name = "poster_path")
    val posterPath: String,

    @Json(name = "production_companies")
    val productionCompanies: List<ProductionCompany>,

    @Json(name = "production_countries")
    val productionCountries: List<ProductionCountry>,

    @Json(name = "release_date")
    val releaseDate: String,

    val revenue: Long,
    val runtime: Long,

    @Json(name = "spoken_languages")
    val spokenLanguages: List<SpokenLanguage>,

    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,

    @Json(name = "vote_average")
    val voteAverage: Double,

    @Json(name = "vote_count")
    val voteCount: Long
)

@JsonClass(generateAdapter = true)
data class Genre (
    val id: Long,
    val name: String
)

@JsonClass(generateAdapter = true)
data class ProductionCompany (
    val id: Long,

    @Json(name = "logo_path")
    val logoPath: String,

    val name: String,

    @Json(name = "origin_country")
    val originCountry: String
)

@JsonClass(generateAdapter = true)
data class ProductionCountry (
    @Json(name = "iso_3166_1")
    val iso3166_1: String,

    val name: String
)

@JsonClass(generateAdapter = true)
data class SpokenLanguage (
    @Json(name = "english_name")
    val englishName: String,

    @Json(name = "iso_639_1")
    val iso639_1: String,

    val name: String
)