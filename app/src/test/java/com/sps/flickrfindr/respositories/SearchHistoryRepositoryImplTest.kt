package com.sps.flickrfindr.respositories

import android.content.SharedPreferences
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test


class SearchHistoryRepositoryImplTest {

    @Test
    fun `getSearchHistory returns list of searches from shared preferences`() {
        val sharedPreferences = mockk<SharedPreferences>()
        every {
            sharedPreferences.getStringSet(
                "SEARCH_HISTORY",
                emptySet()
            )
        } returns setOf("search1", "search2", "search3")
        val repository = SearchHistoryRepositoryImpl(sharedPreferences)

        val searchList = repository.getSearchHistory()

        assertThat(searchList).isEqualTo(setOf("search1", "search2", "search3"))
    }

    @Test
    fun `addSearchToHistory adds new search string to existing search history`() {
        val sharedPreferences = mockk<SharedPreferences>()
        every {
            sharedPreferences.getStringSet(
                "SEARCH_HISTORY",
                emptySet()
            )
        } returns setOf("SEARCH1", "SEARCH2", "SEARCH3")
        val editor = mockk<SharedPreferences.Editor>()
        every { sharedPreferences.edit() } returns editor
        every { editor.putStringSet(any(), any()) } returns editor
        every { editor.apply() } returns Unit
        val repository = SearchHistoryRepositoryImpl(sharedPreferences)

        repository.addSearchToHistory("search4")

        verify {
            sharedPreferences.getStringSet("SEARCH_HISTORY", emptySet())
            sharedPreferences.edit()
            editor.putStringSet("SEARCH_HISTORY", setOf("SEARCH1", "SEARCH2", "SEARCH3", "SEARCH4"))
            editor.apply()
        }
    }

    @Test
    fun `removeSearchFromHistory removes search string from history`() {
        val sharedPreferences = mockk<SharedPreferences>()
        every {
            sharedPreferences.getStringSet(
                "SEARCH_HISTORY",
                emptySet()
            )
        } returns setOf("SEARCH1", "SEARCH2", "SEARCH3")
        val editor = mockk<SharedPreferences.Editor>()
        every { sharedPreferences.edit() } returns editor
        every { editor.putStringSet(any(), any()) } returns editor
        every { editor.apply() } returns Unit
        val repository = SearchHistoryRepositoryImpl(sharedPreferences)

        repository.removeSearchFromHistory("search2")

        verify {
            sharedPreferences.getStringSet("SEARCH_HISTORY", emptySet())
            sharedPreferences.edit()
            editor.putStringSet("SEARCH_HISTORY", setOf("SEARCH1", "SEARCH3"))
            editor.apply()
        }
    }
}