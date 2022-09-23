package com.example.barbershop.ui.addModule

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.example.barbershop.R
import com.example.barbershop.databinding.FragmentAddCorteBinding
import com.example.barbershop.ui.entities.User
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class AddCorteFragment : Fragment() {

    private lateinit var binding: FragmentAddCorteBinding

    //Firebase
    private lateinit var mStorageReference: StorageReference
    private lateinit var mDatabaseReference: DatabaseReference

    //Storage Url and path
    private val pathCorte = "categorias"
    private var mPhotoSelecterUrl: Uri? = null

    //opengallery
    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                mPhotoSelecterUrl = it.data?.data
                binding.imgPhoto.setImageURI(mPhotoSelecterUrl)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAddCorteBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSubmit.setOnClickListener {
            if (binding.etNombreCorte.text!!.isNotEmpty()) {
                submitCorte()
            } else {
                binding.etNombreCorte.requestFocus()
                binding.etNombreCorte.error = getString(R.string.required_campos)
            }
        }

        binding.btnSelect.setOnClickListener { openGallery() }


        mStorageReference = FirebaseStorage.getInstance().reference
        mDatabaseReference = FirebaseDatabase.getInstance().reference.child(pathCorte)

    }



    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryResult.launch(intent)
    }

    private fun submitCorte() {
        binding.progressBar.visibility = View.VISIBLE
        val key = mDatabaseReference.push().key!!

        val storageReference = mStorageReference.child(pathCorte)
            .child(FirebaseAuth.getInstance().currentUser!!.uid).child(key)
        if (mPhotoSelecterUrl != null) {
            storageReference.putFile(mPhotoSelecterUrl!!)
                .addOnProgressListener {
                    val progress = (100 * it.bytesTransferred / it.totalByteCount).toDouble()
                    binding.progressBar.progress = progress.toInt()
                    binding.tvTitle.text = "$progress%"
                }
                .addOnCompleteListener {
                    binding.progressBar.visibility = View.INVISIBLE
                }
                .addOnSuccessListener {
                    Snackbar.make(
                        binding.root, R.string.post_publicado,
                        Snackbar.LENGTH_SHORT
                    )
                        .show()
                    it.storage.downloadUrl.addOnSuccessListener { result ->
                        saveCorte(
                            key,
                            result.toString(),
                            binding.spinnerCategory.selectedItem.toString().trim(),
                            binding.etDescripcionCorte.text.toString().trim(),
                            binding.etNombreCorte.text.toString().trim()
                        )
                        binding.etNombreCorte.setText("")
                        binding.etDescripcionCorte.setText("")
                        binding.imgPhoto.setImageURI(null)
                        binding.tvTitle.text = getString(R.string.post_message_title)
                    }
                }
                .addOnFailureListener {
                    Snackbar.make(
                        binding.root, R.string.instantanea_fallida,
                        Snackbar.LENGTH_SHORT
                    )
                        .show()
                }
        }
    }

    private fun saveCorte(key: String?, url: String, sexo: String, description: String, name: String) {
        val corteEntity = User( photoUrl = url, Sexo = sexo, descripcion = description, nombre = name)
        mDatabaseReference.child(key!!).setValue(corteEntity)
    }


}