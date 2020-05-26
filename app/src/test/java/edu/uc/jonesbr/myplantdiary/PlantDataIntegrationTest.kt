package edu.uc.jonesbr.myplantdiary

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import edu.uc.jonesbr.myplantdiary.dto.Plant
import edu.uc.jonesbr.myplantdiary.service.PlantService
import edu.uc.jonesbr.myplantdiary.ui.main.MainViewModel
import io.mockk.mockk
import junit.framework.Assert.*
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class PlantDataIntegrationTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    lateinit var mvm: MainViewModel

    var plantService = mockk<PlantService>()

    @Test
    fun addition_isNotCorrect() {
        assertEquals(3, 1 + 2)
    }

    @Test
    fun confirmEasternRedbud_outputsEasternRedbud() {
        var plant: Plant = Plant("Cercis", "canadensis", "Eastern Redbud")
        assertEquals("Eastern Redbud", plant.toString())
    }

    @Test
    fun searchForRedbud_returnsRedbud() {
        givenAFeedOfPlantDataAreAvailable()
        whenSearchForRedbud()
        thenResultContainsEasternRedbud()
    }

    @Test
    fun searchForGarbage_returnsNothing() {
        givenAFeedOfPlantDataAreAvailable()
        whenISearchForGarbage()
        thenIGetZeroResults()
    }

    private fun givenAFeedOfPlantDataAreAvailable() {
        mvm = MainViewModel()
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
            assertTrue(redbudFound)
        }
    }

    private fun whenISearchForGarbage() {
        mvm.fetchPlants("sklujapouetllkjsdau")
    }

    private fun thenIGetZeroResults() {
        mvm.plants.observeForever {
            Assert.assertEquals(0, it.size)
        }
    }
}