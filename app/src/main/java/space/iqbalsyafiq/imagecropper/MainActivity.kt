package space.iqbalsyafiq.imagecropper

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import space.iqbalsyafiq.imagecropper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCropImage.setOnClickListener {
            startCrop(
                // Change image url (from camera or gallery) here
                Uri.parse("android.resource://space.iqbalsyafiq.imagecropper/drawable/dummy")
            )
        }
    }

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            // use the returned uri
            val uriContent = result.uriContent
            binding.ivImage.setImageURI(uriContent) // set the image to image view
        } else {
            // an error occurred
            Toast.makeText(
                this,
                result.error.toString(),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun startCrop(imageUri: Uri) {
        // start cropping activity for pre-acquired image saved on the device and customize settings
        cropImage.launch(
            options(uri = imageUri) {
                setGuidelines(CropImageView.Guidelines.ON)
                setOutputCompressFormat(Bitmap.CompressFormat.PNG)
            }
        )
    }
}