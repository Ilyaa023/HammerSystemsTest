package test.hammer.systems.data.cloudStore

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import test.hammer.systems.domain.models.ProductData
import test.hammer.systems.domain.useCases.ICloudStoreRepo
import java.io.File
import java.lang.Exception

class ProductsStorage: ICloudStoreRepo() {
    private val products = FirebaseDatabase.getInstance("https://hstest-5d6d7-default-rtdb.europe-west1.firebasedatabase.app/").reference
    private val productsPic = Firebase.storage("gs://hstest-5d6d7.appspot.com").reference

    override fun getProductMainPic(request: String, callback: (ProductData?) -> Unit) {
        productsPic.child(request).getBytes(Long.MAX_VALUE).addOnCompleteListener {
            try {
                if (it.isSuccessful){
                    callback(ProductData(pic = BitmapDrawable(BitmapFactory.decodeByteArray(it.result, 0, it.result.size))))
                }
                else
                    callback(null)
            } catch (e: Exception){
                e.stackTrace
                callback(null)
            }
        }
    }

    override fun getProducts(callback: (ArrayList<ProductData>?) -> Unit) {
        val list = ArrayList<ProductData>()
        var file: File? = null
        products.get().addOnCompleteListener {
            try {
                if (it.isSuccessful){
                    for (snap in it.result.children){
                        list.add(ProductData(
                            id = snap.key.toString(),
                            name = snap.child("0").value.toString(),
                            description = snap.child("1").value.toString(),
                            price = snap.child("2").value.toString()
                        ))
                    }
                    callback(list)
                }
                else
                    callback(null)
            } catch (e: Exception){
                e.stackTrace
                callback(null)
            }
        }
    }
}