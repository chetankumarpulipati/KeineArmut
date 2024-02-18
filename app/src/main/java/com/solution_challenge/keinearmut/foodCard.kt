package com.solution_challenge.keinearmut

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream
import java.util.*

class foodCard : AppCompatActivity() {

    private val REQUEST_IMAGE_PICK = 1
    private lateinit var photoImageView: ImageView
    private lateinit var button_booked: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.food_card)
        photoImageView = findViewById(R.id.photoImageView)
        photoImageView.setOnClickListener {
            dispatchPickImageIntent()
        }
        button_booked = findViewById(R.id.button_booked)
        button_booked.setOnClickListener {
            Toast.makeText(this, "Your food will be delivered in 30 minutes", Toast.LENGTH_SHORT).show()
        }
    }

    private fun dispatchPickImageIntent() {
        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(pickIntent, REQUEST_IMAGE_PICK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_PICK -> {
                    val imageUri = data?.data
                    if (imageUri != null) {
                        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                        photoImageView.setImageBitmap(bitmap)
                        val description = "Your description here" // Set the description
                        uploadImageToFirebaseStorage(bitmap, description)
                        Toast.makeText(this, "Image uploaded", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


    private fun uploadImageToFirebaseStorage(bitmap: Bitmap, description: String) {
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference
        Log.d("Firebase_IMAGE_IMAGE", "uploadImageToFirebaseStorage called")
        val imagesRef: StorageReference = storageRef.child("mountains.jpg")

        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        val uploadTask = imagesRef.putBytes(data)
        uploadTask.addOnSuccessListener { taskSnapshot ->
            imagesRef.downloadUrl.addOnSuccessListener { uri ->
                // Get the download URL for the image
                val imageUrl = uri.toString()
                uploadDescriptionToDatabase(imageUrl, description)
                // Handle successful upload
                Toast.makeText(this, "Image uploaded successfully", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { exception ->
            // Handle unsuccessful upload
            Toast.makeText(this, "Image upload failed", Toast.LENGTH_SHORT).show()
            Log.e(TAG, "Error uploading image", exception)
        }
    }
    private fun uploadDescriptionToDatabase(imageUrl: String, description: String) {
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("descriptions")
        val key = ref.push().key ?: ""
        val data = mapOf(
            "imageUrl" to imageUrl,
            "description" to description
        )
        ref.child(key).setValue(data)
    }
    companion object {
        private const val TAG = "foodCard"
    }
}
