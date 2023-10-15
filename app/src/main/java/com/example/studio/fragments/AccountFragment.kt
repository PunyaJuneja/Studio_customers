package com.example.studio.fragments

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import com.example.studio.R

class AccountFragment : Fragment() {
    lateinit var image: ImageView

    final val req_code:Int=1000

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        image = view.findViewById(R.id.image)
        val text = view.findViewById<TextView>(R.id.text)
        // val uploadicon=findViewById<ImageView>(R.id.uploadicon)
        //    profile = findViewById(R.id.profilepic)
        text.setOnClickListener {
//            val intent = Intent(this,UploadImage::class.java)
//            startActivity(intent)
            val igallery = Intent(Intent.ACTION_PICK)
            igallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(igallery, req_code)
        }

}

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode== AppCompatActivity.RESULT_OK)
        {
            if(requestCode==req_code)
            {
                //   val uri= data!!.data
                image.setImageURI(data!!.data)
                //  Log.d(ContentValues.TAG,"zscazsfcasdfcadsfadcf ${uri}")

            }
        }
    }
}