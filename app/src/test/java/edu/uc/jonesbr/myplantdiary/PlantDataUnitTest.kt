package edu.uc.jonesbr.myplantdiary

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import edu.uc.jonesbr.myplantdiary.dto.Plant
import edu.uc.jonesbr.myplantdiary.service.PlantService
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.rules.TestRule
import edu.uc.jonesbr.myplantdiary.ui.main.MainViewModel
import io.mockk.every
import io.mockk.mockk

class PlantDataUnitTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    lateinit var mvm: MainViewModel

    var plantService = mockk<PlantService>()

    @Test
    fun confirmEasternRedbud_outputsEasternRedbud() {
        var plant: Plant = Plant("Cercis", "canadensis", "Eastern Redbud")
        assertEquals("Eastern Redbud", plant.toString())
    }

    @Test
    fun searchForRedbud_returnsRedbud() {
        givenAFeedOfMockedPlantDataAvailable()
        whenSearchForRedbud()
        thenResultContainsEasternRedbud()
    }

    private fun givenAFeedOfMockedPlantDataAvailable() {
        mvm = MainViewModel()
        createMockData()
    }

    private fun createMockData() {
        var allPlantsLiveData = MutableLiveData<ArrayList<Plant>>()
        var allPlants = ArrayList<Plant>()
        // Create and add plants to our collection.
        var redbud = Plant("Cercis", "canadensis", "Eastern Redbud")
        allPlants.add(redbud)

        var redOak = Plant("Quercus", "rubra", "Red Oak")
        allPlants.add(redOak)

        var whiteOak = Plant("Quercus", "alba", "White Oak")
        allPlants.add(whiteOak)

        allPlantsLiveData.postValue(allPlants)

        every { plantService.fetchPlants(any()) } returns allPlantsLiveData

        mvm.plantService = plantService
    }

    private fun whenSearchForRedbud() {
        mvm.fetchPlants("Redbud")
    }

    private fun thenResultContainsEasternRedbud() {
        var redbudFound = false
        mvm.plants.observeForever {
            // here is where we do the observing
            assertNotNull(it)
            assertTrue(it.size > 0)
            it.forEach {
                if (it.genus == "Cercis" && it.species == "canadensis" && it.common.contains("Eastern Redbud")) {
                    redbudFound = true
                }
            }
        }
        assertTrue(redbudFound)
    }

    @Test
    fun searchForGarbage_returnsNothing() {
        givenAFeedOfMockedPlantDataAvailable()
        whenISearchForGarbage()
        thenIGetZeroResults()
    }

    private fun whenISearchForGarbage() {
        mvm.fetchPlants("sklujapouetllkjsdau")
    }

    private fun thenIGetZeroResults() {
        mvm.plants.observeForever {
            assertEquals(0, it.size)
        }
    }
}