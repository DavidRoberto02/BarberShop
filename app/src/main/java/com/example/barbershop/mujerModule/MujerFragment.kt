package com.example.barbershop.mujerModule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.barbershop.databinding.FragmentMujerBinding


class MujerFragment : Fragment() {

    private lateinit var mBinding: FragmentMujerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mBinding = FragmentMujerBinding.inflate(inflater, container, false)

        return mBinding.root
    }



}