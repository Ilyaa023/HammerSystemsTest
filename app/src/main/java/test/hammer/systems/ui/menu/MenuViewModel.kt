package test.hammer.systems.ui.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import test.hammer.systems.data.cloudStore.ProductsStorage
import test.hammer.systems.domain.models.ProductData
import test.hammer.systems.domain.useCases.GetProducts
import javax.security.auth.callback.Callback

class MenuViewModel : ViewModel() {

    private val _products = MutableLiveData<ArrayList<ProductData>?>()
    val products: LiveData<ArrayList<ProductData>?> = _products

    fun getData(){
        GetProducts(ProductsStorage()).execute {
            _products.value = null
            _products.value = it
        }
    }
}