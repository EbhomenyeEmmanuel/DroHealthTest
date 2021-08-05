package com.esq.drohealthtest.data.database

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.esq.drohealthtest.R
import com.esq.drohealthtest.data.model.StoreItem
import com.esq.drohealthtest.data.model.StoreItemDatabaseModel
import com.esq.drohealthtest.getOrAwaitValue
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class StoreItemsDaoTest {
    private lateinit var database: DroHealthStoreDatabase
    private lateinit var dao: StoreItemsDao

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        try {
            database = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(), DroHealthStoreDatabase::class.java
            ).allowMainThreadQueries().build()
        } catch (e: Exception) {
            Log.i(this.javaClass.simpleName, e.message!!)
        }
        dao = database.storeDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testItemSearch() = runBlockingTest {
        val storeItem = StoreItemDatabaseModel(
            id = 1,
            medicineIcon = R.drawable.ic_launcher_background,
            mainName = "Folic Acid (100)",
            medicineTypeName = "Tablet - 5mg",
            medicinePrice = 170,
            otherName = "Folic Acid "
        )

        val storeItem2 = StoreItemDatabaseModel(
            id = 5,
            medicineIcon = R.drawable.ic_launcher_background,
            mainName = "Garlic Oil",
            otherName = "Garlic Oil",
            medicineTypeName = "Soft Gel - 650mg",
            medicinePrice = 385
        )

        val listOfStores = listOf(storeItem, storeItem2)
        dao.insertAllStoredItems(listOfStores)

        //Search
        val searchResults = dao.findItemWithName("Garlic Oil").asLiveData().getOrAwaitValue()
        Truth.assertThat(searchResults).isEqualTo(null)

    }

}