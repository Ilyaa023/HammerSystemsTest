package test.hammer.systems.domain.models

import android.graphics.Bitmap
import android.graphics.drawable.Drawable

class ProductData(
        var id: String = "Pizza0",
        var name: String = "dummy name",
        var description: String = "dummy description",
        var price: String = "от 345",
        var pic: Drawable? = null
) {}