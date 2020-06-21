package prv.com.countryfacts.db

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class CountryDataEntity(
    @PrimaryKey(autoGenerate = true)
    val dataId: Long = 0,
    val title: String
)

@Entity
data class CountryFactEntity(
    @PrimaryKey(autoGenerate = true)
    val factId: Long = 0,
    val title: String?,
    val description: String?,
    val imageHref: String?,
    var countryDataId: Long
)

@Entity
data class FactAndDataEntity(
    @Embedded
    val countryDataEntity: CountryDataEntity,
    @Relation(
        parentColumn = "dataId",
        entityColumn = "countryDataId"
    )
    val factsList: List<CountryFactEntity>
)