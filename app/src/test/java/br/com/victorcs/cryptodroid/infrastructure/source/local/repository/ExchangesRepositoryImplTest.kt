package br.com.victorcs.cryptodroid.infrastructure.source.local.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import br.com.victorcs.core.model.Response
import br.com.victorcs.cryptodroid.base.CoroutineTestRule
import br.com.victorcs.cryptodroid.data.entity.ExchangeResponse
import br.com.victorcs.cryptodroid.data.entity.IconResponse
import br.com.victorcs.cryptodroid.domain.mapper.DomainMapper
import br.com.victorcs.cryptodroid.domain.model.Exchange
import br.com.victorcs.cryptodroid.domain.model.Icon
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

    private val provider = mockk<IExchangeLocalProvider>(relaxed = true)
    private val exchangeMapper = mockk<DomainMapper<ExchangeResponse, Exchange>>(relaxed = true)
    private val iconMapper = mockk<DomainMapper<IconResponse, Icon>>(relaxed = true)
    private val repository = ExchangesRepositoryImpl(provider, exchangeMapper, iconMapper)

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutineTestRule()

    @Test
    fun whenGetExchanges_thenReturnSuccess() = runTest {
        val exchangeJson = DataMockTest.MOCK_JSON_EXCHANGES
        val exchangeList = DataMockTest.mockExchangeList

        coEvery { provider.loadJSONFile(ExchangeLocalProviderType.EXCHANGES) } returns exchangeJson
        coEvery { exchangeMapper.toDomain(any<List<ExchangeResponse>>()) } returns exchangeList

        val result = repository.getExchanges()

        assert(result is Response.Success && result.data == exchangeList)
    }

    @Test
    fun whenGetIcons_thenReturnSuccess() = runTest {
        val iconsJson = DataMockTest.MOCK_JSON_ICONS
        val iconList = DataMockTest.mockExchangeIcons

        coEvery { provider.loadJSONFile(ExchangeLocalProviderType.ICONS) } returns iconsJson
        coEvery { iconMapper.toDomain(any<List<IconResponse>>()) } returns iconList

        val result = repository.getIcons()

        assert(result is Response.Success && result.data == iconList)
    }

    @Test
    fun givenInvalidJson_whenGetExchanges_thenReturnEmptySuccess() = runTest {
        coEvery { provider.loadJSONFile(ExchangeLocalProviderType.EXCHANGES) } returns DataMockTest.MOCK_EMPTY_JSON_ARRAY
        coEvery { exchangeMapper.toDomain(any<List<ExchangeResponse>>()) } returns emptyList()

        val result = repository.getExchanges()

        assert(result is Response.Success && result.data.isEmpty())
    }

    @Test
    fun givenInvalidJson_whenGetIcons_thenReturnEmptySuccess() = runTest {
        coEvery { provider.loadJSONFile(ExchangeLocalProviderType.ICONS) } returns DataMockTest.MOCK_EMPTY_JSON_ARRAY
        coEvery { iconMapper.toDomain(any<List<IconResponse>>()) } returns emptyList()

        val result = repository.getIcons()

        assert(result is Response.Success && result.data.isEmpty())
    }
}
