package br.com.victorcs.cryptodroid.infrastructure.source.local.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import br.com.victorcs.cryptodroid.base.CoroutineTestRule
import br.com.victorcs.cryptodroid.core.model.Response
import br.com.victorcs.cryptodroid.data.entity.ExchangeResponse
import br.com.victorcs.cryptodroid.domain.mapper.DomainMapper
import br.com.victorcs.cryptodroid.domain.model.Exchange
import br.com.victorcs.cryptodroid.shared.test.DataMockTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.kotlin.any

@ExperimentalCoroutinesApi
@SmallTest
class ExchangeDetailsRepositoryImplTest {

    private val provider = mockk<IExchangeLocalProvider>(relaxed = true)
    private val mapper = mockk<DomainMapper<ExchangeResponse, Exchange>>(relaxed = true)
    private val repository = ExchangeDetailsRepositoryImpl(provider, mapper)

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutineTestRule()

    @Test
    fun givenValidExchangeId_whenGetExchangeDetails_thenReturnSuccess() = runTest {
        val exchangeJson = DataMockTest.MOCK_JSON_EXCHANGES
        val exchangeList = DataMockTest.mockExchangeList

        coEvery { provider.loadJSONFile(ExchangeLocalProviderType.EXCHANGES) } returns exchangeJson
        coEvery { mapper.toDomain(any<List<ExchangeResponse>>()) } returns exchangeList

        val result = repository.getExchangeDetails(any())

        assert(result is Response.Success && result.data == exchangeList)
    }

    @Test
    fun givenInvalidJson_whenGetExchangeDetails_thenReturnEmptySuccess() = runTest {
        coEvery { provider.loadJSONFile(ExchangeLocalProviderType.EXCHANGES) } returns DataMockTest.MOCK_EMPTY_JSON_ARRAY
        coEvery { mapper.toDomain(any<List<ExchangeResponse>>()) } returns emptyList()

        val result = repository.getExchangeDetails(any())

        assert(result is Response.Success && result.data.isEmpty())
    }
}
