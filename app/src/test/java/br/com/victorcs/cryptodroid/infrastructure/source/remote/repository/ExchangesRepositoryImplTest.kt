package br.com.victorcs.cryptodroid.infrastructure.source.remote.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import br.com.victorcs.core.model.Response
import br.com.victorcs.cryptodroid.base.CoroutineTestRule
import br.com.victorcs.cryptodroid.domain.repository.IExchangesRepository
import br.com.victorcs.cryptodroid.shared.test.DataMockTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
@SmallTest
class ExchangesRepositoryImplTest {

    private val repository = mockk<IExchangesRepository>(relaxed = true)

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutineTestRule()

    @Test
    fun givenSuccess_whenGetExchanges_thenReturnSuccessfully() = runTest {
        val exchangesResponseMock = DataMockTest.mockSuccessExchangeResponse
        val expected = DataMockTest.mockExchangeList

        coEvery { repository.getExchanges() } returns exchangesResponseMock

        val result = repository.getExchanges()

        assert(result is Response.Success && result.data == expected)
    }
}
