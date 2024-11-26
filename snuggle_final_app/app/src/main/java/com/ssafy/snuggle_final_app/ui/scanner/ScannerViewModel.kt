import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.snuggle_final_app.data.model.dto.FavoriteRequest
import com.ssafy.snuggle_final_app.data.service.RetrofitUtil
import kotlinx.coroutines.launch

class ScannerViewModel(application: Application) : AndroidViewModel(application) {

    private val _bookmarkClicked = MutableLiveData(false) // 북마크 클릭 상태
    val bookmarkClicked: LiveData<Boolean> get() = _bookmarkClicked

    private val _bookmarkResponse = MutableLiveData<String>() // 서버 응답 메시지
    val bookmarkResponse: LiveData<String> get() = _bookmarkResponse

    private val _bookmarkCount = MutableLiveData(0) // 북마크 개수
    val bookmarkCount: LiveData<Int> get() = _bookmarkCount

    // 북마크 토글
    fun toggleBookmark(userId: String, taggingId: String) {
        viewModelScope.launch {
            try {
                val request = FavoriteRequest(userId, taggingId)
                val response = RetrofitUtil.favoriteService.toggleFavorite(request)

                if (response.isSuccessful) {
                    val isAdded = response.body() ?: false
                    val isValid = if (isAdded) "Y" else "N"

                    _bookmarkClicked.postValue(isValid.equals("Y", ignoreCase = true)) // 상태 업데이트
                    _bookmarkCount.value = (_bookmarkCount.value ?: 0) + if (isAdded) 1 else -1
                    _bookmarkResponse.value = if (isAdded) "북마크가 추가되었습니다." else "북마크가 제거되었습니다."
                } else {
                    Log.e("BOOKMARK", "북마크 업데이트 실패: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("BOOKMARK", "네트워크 오류: ${e.message}")
            }
        }
    }

    // 초기 북마크 상태와 개수를 설정
    fun initializeBookmarkState(userId: String, taggingId: String) {
        viewModelScope.launch {
            try {
                Log.d("BOOKMARK", "initializeBookmarkState 호출: userId=$userId, taggingId=$taggingId")

                // 서버로부터 북마크 데이터 가져오기
                val response = RetrofitUtil.favoriteService.getUserFavorites(userId)
                if (response.isSuccessful) {
                    val favorites = response.body()?.map { favorite ->
                        // 서버에서 누락된 값 처리 (기본값 설정)
                        favorite.copy(
                            userId = favorite.userId ?: userId,
                            isValid = favorite.isValid ?: "N"
                        )
                    }
                    Log.d("BOOKMARK", "가공된 북마크 데이터: $favorites")

                    // 북마크 여부 확인
                    val isBookmarked = favorites?.any {
                        it.taggingId == taggingId && (it.isValid?.equals("Y", ignoreCase = true) ?: false)
                    } == true
                    _bookmarkClicked.postValue(isBookmarked)

                    // 북마크 카운트 설정
                    _bookmarkCount.postValue(favorites?.size ?: 0)

                    Log.d("BOOKMARK", "초기 북마크 상태: $isBookmarked, 북마크 개수: ${favorites?.size}")
                } else {
                    val errorMessage = "북마크 상태 조회 실패: ${response.code()} - ${response.errorBody()?.string()}"
                    handleFailure(errorMessage)
                }
            } catch (e: Exception) {
                val errorMessage = "네트워크 오류: ${e.message}"
                handleFailure(errorMessage)
            }
        }
    }


    // 실패 처리
    private fun handleFailure(message: String) {
        _bookmarkResponse.value = message
        Log.e("BOOKMARK", message)
    }
}

