package com.example.barbershop.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.barbershop.databinding.FragmentInicioBinding


class InicioFragment : Fragment() {

    private lateinit var mBinding: FragmentInicioBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mBinding = FragmentInicioBinding.inflate(inflater, container, false)

        mBinding.cvCorteHombre.setOnClickListener { navigateFragment() }

        mBinding.cvCorteMujer.setOnClickListener { navigateFragmentMujer() }

        mBinding.cvProductos.setOnClickListener { navigateFragmentProducto() }

        return mBinding.root

    }

    private fun navigateFragmentProducto() {

    }

    private fun navigateFragmentMujer() {

    }

    private fun navigateFragment() {

    }

}