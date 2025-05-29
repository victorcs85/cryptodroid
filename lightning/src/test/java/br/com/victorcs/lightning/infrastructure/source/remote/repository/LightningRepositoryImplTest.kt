package br.com.victorcs.lightning.infrastructure.source.remote.repository


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import br.com.victorcs.core.model.Response
import br.com.victorcs.lightning.base.CoroutineTestRule
import br.com.victorcs.lightning.data.mapper.NodeMapper
import br.com.victorcs.lightning.domain.repository.IRankingsConnectivityRepository
import br.com.victorcs.lightning.infrastructure.source.remote.LightningAPI
import br.com.victorcs.lightning.shared.test.DataMockTest
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
@SmallTest
class LightningRepositoryImplTest {

    private val api = mockk<LightningAPI>()
    private val mapper = mockk<NodeMapper>()

    private lateinit var repository: IRankingsConnectivityRepository

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutineTestRule()

    @Before
    fun setUp() {
        repository = RankingsConnectivityRepositoryImpl(api, mapper)
    }

    @Test
    fun givenSuccessResponse_whenGetLightning_thenReturnsData() = runTest {
        val dto = DataMockTest.mockNodeResponse
        val expected = DataMockTest.mockLightning

        coEvery { api.getRankingsConnectivity() } returns dto
        every { mapper.toDomain(dto) } returns expected

        val result = repository.getRankingsConnectivity()

        assert(result is Response.Success && result.data == expected)
    }

    @Test
    fun givenApiException_whenGetLightning_thenReturnsError() = runTest {
        coEvery { api.getRankingsConnectivity() } throws RuntimeException("API Error")

        val result = repository.getRankingsConnectivity()

        assert(result is Response.Error)
    }
}
