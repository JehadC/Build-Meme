package com.example.user.buildmeme

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.mybottom_sectionfragment.*
import kotlinx.android.synthetic.main.mytop_picturefragment.*

class MainActivity : AppCompatActivity() {

    private val GALLERY_PICTURE : Int = 1
    @SuppressLint("ResourceType")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun createMeme(view: View) {
        //change top text view
        topText_view.text = topEdit_text.text
        // change bottom text view
        bottomText_view.text = bottomEdit_text.text
    }

    fun uploadImage(view: View) {
        // Create intent
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        //if Photo is not null
        //App can crashed if intent is not handled
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(galleryIntent, GALLERY_PICTURE)
        }
    }

    fun downloadImage(view: View){
        //set content value to memeCreation Frame layout
        val content : View = relativeLayout
        // call takeScreenshot function and pass content
        val bitmap : Bitmap = takeScreenShot(content)
        val savedImageURL = MediaStore.Images.Media.insertImage(
                contentResolver,
                bitmap,
                "Title Name",
                "Description"
        )
        Uri.parse(savedImageURL)
        //inform user that screenshot is saved
        Toast.makeText(this, "Image Saved" , Toast.LENGTH_LONG).show()
    }

    fun takeScreenShot(view: View): Bitmap {
        view.isDrawingCacheEnabled = true
        val bitmap = Bitmap.createBitmap(view.width,view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.isDrawingCacheEnabled = false
        view.draw(canvas)
        return bitmap
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data != null) {
            val photoUri = data.data
            //this gets Bitmap of Photo
            val selectedImage = MediaStore.Images.Media.getBitmap(this.contentResolver, photoUri)
            // convert selectedImage : Bitmap to Drawable
            val myDrawable : Drawable = BitmapDrawable(resources , selectedImage)
            //Set Layout Background
            relativeLayout.background = myDrawable
        }
    }
}