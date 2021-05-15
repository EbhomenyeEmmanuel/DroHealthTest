package com.esq.drohealthtest.data.database

import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.esq.drohealthtest.R
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.esq.drohealthtest.data.model.BagItemDatabaseModel
import com.esq.drohealthtest.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class BagItemsDaoTest {
    private lateinit var database: DroHealthStoreDatabase
    private lateinit var dao: BagItemsDao
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), DroHealthStoreDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.bagItemsDao()
    }

    @After
    fun tearDown(){
        database.close()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun insertBagItem() = runBlockingTest {
        val bagItem = BagItemDatabaseModel(id = 1,drugIcon = R.drawable.ic_launcher_background, drugName = "Folic Acid (100)", drugType = "Tablet - 5mg", drugPrice = "170", drugQuantity = 1)
        val bagItem2 = BagItemDatabaseModel(id = 2,drugIcon = R.drawable.ic_launcher_background, drugName = "Folic Acid (100)", drugType = "Tablet - 5mg", drugPrice = "170", drugQuantity = 1)

        dao.saveBagItem(bagItem)
        val allBagItems = dao.getAllItemsInBag().asLiveData().getOrAwaitValue()
        assertThat(bagItem).isIn(allBagItems)
    }

}