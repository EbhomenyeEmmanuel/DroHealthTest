package com.esq.drohealthtest.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.esq.drohealthtest.R
import com.esq.drohealthtest.data.model.BagItemDatabaseModel
import com.esq.drohealthtest.data.model.StoreItem
import com.esq.drohealthtest.data.model.StoreItemDatabaseModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [StoreItemDatabaseModel::class, BagItemDatabaseModel::class],
    version = 1,
    exportSchema = false
)
abstract class DroHealthStoreDatabase : RoomDatabase() {
    abstract fun storeDao(): StoreItemsDao
    abstract fun bagItemsDao(): BagItemsDao

    companion object {
        // Singleton prevents multiple instances of database opening at the same time
        @Volatile
        private var INSTANCE: DroHealthStoreDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): DroHealthStoreDatabase {

            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DroHealthStoreDatabase::class.java,
                    context.getString(R.string.database_name)
                ).addCallback(StoredItemsDatabaseCallback(scope)).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class StoredItemsDatabaseCallback(private val scope: CoroutineScope): RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database->
                    scope.launch {
                        val storeDao = database.storeDao()
                        prePopulateDatabase(storeDao)
                    }
                }
            }

            //add items
            private suspend fun prePopulateDatabase(storeDao: StoreItemsDao){
                val x =  listOf(
                    StoreItemDatabaseModel(id = 0, medicineIcon = R.drawable.ic_launcher_background, mainName = "Kezitil Sus", otherName = "Cefuroxime Axetil", medicineTypeName = "Oral Suspension - 125mg", medicinePrice = 1820)
                    , StoreItemDatabaseModel(id = 1,medicineIcon = R.drawable.ic_launcher_background, mainName = "Kezitil", otherName = "Cefuroxime Axetil", medicineTypeName = "Tablet - 250mg", medicinePrice = 1140
                    ), StoreItemDatabaseModel(id = 2,medicineIcon = R.drawable.ic_launcher_background, mainName = "Garlic Oil", otherName = "Garlic Oil", medicineTypeName = "Soft Gel - 650mg", medicinePrice = 385
                    ), StoreItemDatabaseModel(id = 3,medicineIcon = R.drawable.ic_launcher_background, mainName = "Folic Acid (100)", otherName = "Folic Acid", medicineTypeName = "Tablet - 5mg", medicinePrice = 170)
                    ,StoreItemDatabaseModel(id = 4,medicineIcon = R.drawable.ic_launcher_background, mainName = "Folic Acid (150)", otherName = "Folic Acid", medicineTypeName = "Tablet - 5mg", medicinePrice = 150
                    ))
                storeDao.insertAllStoredItems(x)
            }
        }
    }

}