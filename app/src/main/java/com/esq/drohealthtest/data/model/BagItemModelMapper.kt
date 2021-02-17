package com.esq.drohealthtest.data.model

import com.esq.drohealthtest.data.interfaces.EntityMapper
import javax.inject.Inject

class BagItemModelMapper
@Inject constructor() : EntityMapper<BagItemDatabaseModel, BagItem> {
    override fun mapFromEntity(entity: BagItemDatabaseModel): BagItem {
        return BagItem(
            id = entity.id,
            drugIcon = entity.drugIcon,
            drugName = entity.drugName,
            drugType = entity.drugType,
            drugPrice = entity.drugPrice,
            drugQuantity = entity.drugQuantity
        )
    }

    override fun mapToEntity(domainModel: BagItem): BagItemDatabaseModel {
        return BagItemDatabaseModel(
            id = domainModel.id,
            drugIcon = domainModel.drugIcon,
            drugName = domainModel.drugName,
            drugType = domainModel.drugType,
            drugPrice = domainModel.drugPrice,
            drugQuantity = domainModel.drugQuantity
        )
    }

    fun mapFromEntityList(entities: List<BagItemDatabaseModel>): List<BagItem> {
        return entities.map {
            mapFromEntity(it)
        }
    }
}