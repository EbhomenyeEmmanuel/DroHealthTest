package com.esq.drohealthtest.data.database

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.esq.drohealthtest.R
import com.esq.drohealthtest.data.model.BagItemDatabaseModel
import com.esq.drohealthtest.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
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
class BagItemsDaoTest {
    private lateinit var database: DroHealthStoreDatabase
    private lateinit var dao: BagItemsDao

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
        dao = database.bagItemsDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testInsertBagItem() = runBlockingTest {
        val bagItem = BagItemDatabaseModel(
            id = 1,
            drugIcon = R.drawable.ic_launcher_background,
            drugName = "Folic Acid (100)",
            drugType = "Tablet - 5mg",
            drugPrice = "170",
            drugQuantity = 1
        )
        val bagItem2 = BagItemDatabaseModel(
            id = 2,
            drugIcon = R.drawable.ic_launcher_background,
            drugName = "Folic Acid (100)",
            drugType = "Tablet - 5mg",
            drugPrice = "170",
            drugQuantity = 1
        )

        dao.saveBagItem(bagItem)
        val allBagItems = dao.getAllItemsInBag().getOrAwaitValue()
        assertThat(bagItem).isIn(allBagItems)

        val noOfBagItems = dao.getNumberOfItemsInBag().getOrAwaitValue()
        assertThat(noOfBagItems).isEqualTo(1)
    }

    @Test
    fun testClearBagItems() = runBlockingTest {
        val bagItem2 = BagItemDatabaseModel(
            id = 2,
            drugIcon = R.drawable.ic_launcher_background,
            drugName = "Folic Acid (100)",
            drugType = "Tablet - 5mg",
            drugPrice = "170",
            drugQuantity = 1
        )

        dao.saveBagItem(bagItem2)
        dao.deleteAll()
        val noOfBagItems = dao.getNumberOfItemsInBag().getOrAwaitValue()
        assertThat(noOfBagItems).isEqualTo(0)
    }

    @Test
    fun testGetNoOfBagItems() = runBlockingTest {
        val bagItem2 = BagItemDatabaseModel(
            id = 2,
            drugIcon = R.drawable.ic_launcher_background,
            drugName = "Folic Acid (100)",
            drugType = "Tablet - 5mg",
            drugPrice = "170",
            drugQuantity = 1
        )

        dao.saveBagItem(bagItem2)
        val noOfBagItems = dao.getNumberOfItemsInBag().getOrAwaitValue()
        assertThat(noOfBagItems).isEqualTo(1)
    }

}