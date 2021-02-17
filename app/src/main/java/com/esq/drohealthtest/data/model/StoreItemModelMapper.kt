package com.esq.drohealthtest.data.model

import com.esq.drohealthtest.data.interfaces.EntityMapper
import javax.inject.Inject

class StoreItemModelMapper
@Inject constructor() : EntityMapper<StoreItemDatabaseModel, StoreItem> {
    override fun mapFromEntity(entity: StoreItemDatabaseModel): StoreItem {
        return StoreItem(
            id = entity.id,
            medicineIcon = entity.medicineIcon,
            mainName = entity.mainName,
            otherName = entity.otherName,
            medicineTypeName = entity.medicineTypeName,
            medicinePrice = entity.medicinePrice
        )
    }

    override fun mapToEntity(domainModel: StoreItem): StoreItemDatabaseModel {
        return StoreItemDatabaseModel(
            id = domainModel.id,
            medicineIcon = domainModel.medicineIcon,
            mainName = domainModel.mainName,
            otherName = domainModel.otherName,
            medicineTypeName = domainModel.medicineTypeName,
            medicinePrice =  domainModel.medicinePrice
        )
    }

    fun mapFromEntityList(entities: List<StoreItemDatabaseModel>): List<StoreItem> {
        return entities.map {
            mapFromEntity(it)
        }
    }
}