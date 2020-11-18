package com.akeijser.igtrader.api.epicdetails

import com.akeijser.igtrader.api.testhelpers.AbstractFeatureTest
import com.akeijser.igtrader.api.testhelpers.EpicDetailsFixture
import com.akeijser.igtrader.api.testhelpers.EpicFixture
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class EpicDetailServiceTest(): AbstractFeatureTest() {

    private val epicDetailService : EpicDetailService = mockk()

    @Test
    fun `Test get epic details for epic`(){

        //Prepare
        val epic = EpicFixture.create()
        val epicDetails = listOf(EpicDetailsFixture.create())

        every { epicDetailService.getEpicDetails(epic) } answers {epicDetails}

        //Execute
        val result = epicDetailService.getEpicDetails(epic)

        //Assert
        assertThat(result).isEqualTo(epicDetails)
    }

    @Test
    fun `Test get epic details for a single epic`(){

        //Prepare
        val epicName = "some epic name"
        val epicDetails = listOf(EpicDetailsFixture.create())
        every { epicDetailService.getEpicDetails(epicName) } answers {epicDetails}

        //Execute
        val result = epicDetailService.getEpicDetails(epicName)

        //Assert
        assertThat(result).isEqualTo(epicDetails)
    }

    @Test
    fun `Test get epics details for multiple epics`(){

        //Prepare
        val epicNames = listOf("name1", "name2")
        val epicDetails = listOf(EpicDetailsFixture.create())
        every { epicDetailService.getEpicDetails(epicNames) } answers {epicDetails}

        //Execute
        val result = epicDetailService.getEpicDetails(epicNames)

        //Assert
        assertThat(result).isEqualTo(epicDetails)
    }

    @Test
    fun `Test getEpicDetails`(){

        //Prepare
        val epic = "some epic"
        val epicDetails = listOf(EpicDetailsFixture.create())
        every { epicDetailService.getEpicDetails(epic, false) } answers {epicDetails}

        //Execute
        val result = epicDetailService.getEpicDetails(epic, false)

        //Assert
        assertThat(result).isEqualTo(epicDetails)
    }

    @Test
    fun `Test getEpicDetails for multiple epics`(){

        //Prepare
        val epic = listOf("some epic")
        val epicDetails = listOf(EpicDetailsFixture.create())
        every { epicDetailService.getEpicDetails(epic, false) } answers {epicDetails}

        //Execute
        val result = epicDetailService.getEpicDetails(epic, false)

        //Assert
        assertThat(result).isEqualTo(epicDetails)
    }
}