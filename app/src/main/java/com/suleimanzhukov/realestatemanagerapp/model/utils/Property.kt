package com.suleimanzhukov.realestatemanagerapp.model.utils

data class Property(
    var id: Long,
    var cost: String,
    var address: String,
    var timePosted: String,
    var beds: Int,
    var Baths: Int,
    var garage: Int,
    var area: String,
    var overview: String,
    var apartment: Boolean,
    var like: Boolean,
    var forSale: Boolean
)