package com.example.barbershop.ui.mujerModule


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.barbershop.R
import com.example.barbershop.databinding.FragmentMujerBinding
import com.example.barbershop.databinding.ItemCorteBinding
import com.example.barbershop.ui.entities.User
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class MujerFragment : Fragment() {

    private lateinit var mBinding: FragmentMujerBinding
    private lateinit var mFirebaseAdapter: FirebaseRecyclerAdapter<User, UserHolder>
    private lateinit var mLayoutManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mBinding = FragmentMujerBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //instancia y referencia
        val query =
            FirebaseDatabase.getInstance().reference.child("categorias").orderByChild("sexo")
                .equalTo("Mujer")

        val options =
            FirebaseRecyclerOptions.Builder<User>().setQuery(query) {
                val user = it.getValue(User::class.java)
                user!!.id = it.key!!
                user
            }.build()

        mFirebaseAdapter = object : FirebaseRecyclerAdapter<User, UserHolder>(options) {
            private lateinit var mContext: Context

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
                mContext = parent.context

                val vieww = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_corte, parent, false)
                return UserHolder(vieww)
            }


            override fun onBindViewHolder(holder: UserHolder, position: Int, model: User) {
                val user = getItem(position)

                with(holder) {
                    setListener(user)

                    binding.tvNameCorte.text = user.nombre
                    binding.tvDescripcionCorte.text = user.descripcion
                    Glide.with(mContext)
                        .load(user.photoUrl)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .into(binding.ivPhoto)
                }
            }

            @SuppressLint("NotifyDataSetChanged") // error interno de firebase
            override fun onDataChanged() {
                super.onDataChanged()
                mBinding.progressBar.visibility = View.GONE
                notifyDataSetChanged()

            }

            override fun onError(error: DatabaseError) {
                super.onError(error)
                //Toast.makeText(mContext, error.message, Toast.LENGTH_SHORT).show()
            }
        }

        mLayoutManager = LinearLayoutManager(context)

        mBinding.recyclerViewMujer.apply {
            setHasFixedSize(true)
            layoutManager = mLayoutManager
            adapter = mFirebaseAdapter
        }

    }

    override fun onStart() {
        super.onStart()
        mFirebaseAdapter.startListening()

    }

    override fun onStop() {
        super.onStop()
        mFirebaseAdapter.stopListening()

    }

    private fun deletePost(user: User) {
        //alertDialog
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.dialog_delete_snapshot)
            .setPositiveButton(R.string.dialog_delete_confirm) { _, _ ->
                val databaseReference = FirebaseDatabase.getInstance().reference.child("categorias")
                databaseReference.child(user.id).removeValue()
            }
            .setNegativeButton(R.string.dialog_delete_cancel, null)
            .show()
    }

    inner class UserHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemCorteBinding.bind(view)

        fun setListener(user: User) {
            binding.cbDelete.setOnClickListener { deletePost(user) }
        }
    }

}