package test.hammer.systems.ui.menu

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import test.hammer.systems.R
import test.hammer.systems.data.cloudStore.ProductsStorage
import test.hammer.systems.databinding.FragmentMenuBinding
import test.hammer.systems.databinding.ItemProductBinding
import test.hammer.systems.databinding.ItemTopPagerBinding
import java.lang.Math.abs

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val menuViewModel = ViewModelProvider(this).get(MenuViewModel::class.java)
        menuViewModel.getData()

        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        val root: View = binding.root

        with(binding.bannerPager){
            adapter = BannerAdapter(this@MenuFragment)
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            setPageTransformer(CompositePageTransformer().apply {
                addTransformer(MarginPageTransformer(40))
                addTransformer { page, position ->
                    page.scaleY = 0.85f + (1 - abs(position)) * 0.15f
                }
            })
        }

        menuViewModel.products.observe(viewLifecycleOwner){
            binding.scrollView.removeAllViews()
            if (it != null)
                for (product in it) {
                    Log.e("TAG", "onCreateView: $product")
                    val prBind = ItemProductBinding.inflate(inflater)
                    prBind.productName.text = product.name
                    prBind.productDescription.text = product.description
                    prBind.productPrice.text = product.price
                    prBind.productImage.setImageDrawable(product.pic)
                    binding.scrollView.addView(prBind.root)
                }
//            else ToDo: error occured
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class BannerAdapter(frag: Fragment): FragmentStateAdapter(frag){
    private val images = arrayListOf(
        frag.resources.getDrawable(R.drawable.pic_banner_1),
        frag.resources.getDrawable(R.drawable.pic_banner_2)
    )
    override fun getItemCount(): Int = images.size

    override fun createFragment(position: Int): Fragment = BannerFragment(images[position])

}

class BannerFragment(val image: Drawable): Fragment(){

    private lateinit var binding: ItemTopPagerBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = ItemTopPagerBinding.inflate(inflater, container, false)
        binding.bannerImage.setImageDrawable(image)
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }
}
