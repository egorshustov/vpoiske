package com.egorshustov.vpoiske.userlist

import androidx.lifecycle.viewModelScope
import com.egorshustov.vpoiske.base.BaseViewModel
import com.egorshustov.vpoiske.data.source.UsersRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserListViewModel @Inject constructor(
    private val usersRepository: UsersRepository
) : BaseViewModel<UserListState>(UserListState()) {
    init {
        onSearchClick()
    }

    fun onSearchClick() {
        viewModelScope.launch {
            val res = usersRepository.searchUsers(2, 18, 26, 14, 3)
        }
    }
}