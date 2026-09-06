package com.android.mykoodugalapplication.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.mykoodugalapplication.activity.SparrowSpottedActivity.Companion.REQUEST_UPLOAD_IMAGES
import com.android.mykoodugalapplication.commonUtils.Common
import com.android.mykoodugalapplication.databinding.FragmentNestStatusBinding

class SparrowOccupationStatusActivity :AppCompatActivity() {

    private lateinit var binding:FragmentNestStatusBinding
    private var imageList = ArrayList<String>()

    companion object {
        const val REQUEST_UPLOAD_IMAGES = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=FragmentNestStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intiview()

    }

    private fun intiview(){
        // ✅ LOGIC
        binding.cbVisiting.setOnCheckedChangeListener { _, isChecked ->
            binding.layoutVisiting.visibility = if (isChecked) View.VISIBLE else View.GONE
        }

        binding.cbNesting.setOnCheckedChangeListener { _, isChecked ->
            binding.layoutNesting.visibility = if (isChecked) View.VISIBLE else View.GONE
        }

        binding.cbChicks.setOnCheckedChangeListener { _, isChecked ->
            binding.layoutChicks.visibility = if (isChecked) View.VISIBLE else View.GONE
        }

        binding.cbEggs.setOnCheckedChangeListener { _, isChecked ->
            binding.etEggDate.visibility = if (isChecked) View.VISIBLE else View.GONE
        }

        binding.btnNext.setOnClickListener {
            val  intent=Intent(this,EnvironmentalDataActivity::class.java)
            startActivity(intent)
        }

        binding.etStartDate.setOnClickListener {
            Common.showDatePicker(this) { selectedDate ->
                binding.etStartDate.setText(selectedDate)

            }
        }

        binding.etEndDate.setOnClickListener {
            Common.showDatePicker(this) { selectedDate ->
                binding.etEndDate.setText(selectedDate)

            }
        }
        binding.etEggDate.setOnClickListener {
            Common.showDatePicker(this) { selectedDate ->
                binding.etEggDate.setText(selectedDate)

            }
        }
        binding.etChickDate.setOnClickListener {
            Common.showDatePicker(this) { selectedDate ->
                binding.etChickDate.setText(selectedDate)

            }
        }
        binding.etFlewDate.setOnClickListener {
            Common.showDatePicker(this) { selectedDate ->
                binding.etFlewDate.setText(selectedDate)

            }
        }

        binding.backIv.setOnClickListener {
            finish()
        }

        binding.uploadBt.setOnClickListener {
            val intent = Intent(this, ImageUploadActivity::class.java)
            intent.putStringArrayListExtra("images", imageList)
            startActivityForResult(intent, REQUEST_UPLOAD_IMAGES)
        }

        binding.tvViewImages.setOnClickListener {
            if (imageList.isEmpty())
                return@setOnClickListener

            val intent = Intent(this, ImageUploadActivity::class.java)
            intent.putStringArrayListExtra("images", imageList)
            startActivityForResult(intent, SparrowSpottedActivity.REQUEST_UPLOAD_IMAGES)
        }

    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SparrowSpottedActivity.REQUEST_UPLOAD_IMAGES &&
            resultCode == Activity.RESULT_OK) {

            imageList =
                data?.getStringArrayListExtra("images")
                    ?: ArrayList()

            binding.tvImageCount.text =
                imageList.size.toString()
        }

    }
}