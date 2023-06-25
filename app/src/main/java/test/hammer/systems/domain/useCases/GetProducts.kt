package test.hammer.systems.domain.useCases

import android.util.Log
import test.hammer.systems.domain.models.ProductData

class GetProducts(val repo: ICloudStoreRepo) {
    fun execute(callback: (ArrayList<ProductData>?) -> Unit){
        repo.getProducts {
            if (it != null)
                for(product in it) {
                    repo.getProductMainPic(product.id + ".png") { it1 ->
                        it1?.let { it2 ->
                            it[it.indexOf(product)].pic = it2.pic
                        }
                        callback(it)
                    }
                }
            else
                callback(null)
        }
    }
}