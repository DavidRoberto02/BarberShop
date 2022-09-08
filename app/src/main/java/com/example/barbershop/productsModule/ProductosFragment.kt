package com.example.barbershop.productsModule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.barbershop.databinding.FragmentProductosBinding


class ProductosFragment : Fragment() {

    private lateinit var mBinding: FragmentProductosBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mBinding = FragmentProductosBinding.inflate(inflater, container, false)

        return mBinding.root
    }

}