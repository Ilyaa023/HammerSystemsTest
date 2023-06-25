package test.hammer.systems.domain.useCases

import test.hammer.systems.domain.models.ProductData

open class ICloudStoreRepo {
    open fun getProducts(callback: (ArrayList<ProductData>?) -> Unit){}
    open fun getProductMainPic(request: String, callback: (ProductData?) -> Unit){}
}