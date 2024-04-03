import android.graphics.Bitmap
import android.util.Base64
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bondoman_pdd.data.model.ScannerResponse
import com.example.bondoman_pdd.data.repository.ScannerRepository
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

class ScannerViewModel(private val scannerRepository: ScannerRepository) : ViewModel() {

    private val scanResult = MutableLiveData<Result<ScannerResponse>>()

    fun uploadImage(image: Bitmap, userToken: String) {
        viewModelScope.launch {
            scannerRepository.uploadBill(image, userToken) { response, error ->
                if (error != null) {
                    scanResult.postValue(Result.failure(error))
                } else if (response != null) {
                    scanResult.postValue(Result.success(response))
                }
            }
        }
    }
}