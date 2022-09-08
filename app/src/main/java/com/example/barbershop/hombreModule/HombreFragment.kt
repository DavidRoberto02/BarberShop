package com.example.barbershop.hombreModule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.barbershop.databinding.FragmentHombreBinding


class HombreFragment : Fragment() {

    private lateinit var mBinding: FragmentHombreBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mBinding = FragmentHombreBinding.inflate(inflater, container, false)

        return mBinding.root
    }


}