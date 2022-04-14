package com.example.appforteacher.Presentation.View

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatSpinner
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appforteacher.Domain.ViewModel.ViewModel
import com.example.appforteacher.Domain.ViewModel.ViewModelFactory
import com.example.appforteacher.R
import com.github.dhaval2404.imagepicker.ImagePicker

class CreateAnswerFragment : Fragment() {

    var switch: AppCompatSpinner? = null
    var taskBody: EditText? = null
    var addImage: ImageView? = null
    var recyclerView: RecyclerView? = null
    var editText: EditText? = null

    private lateinit var vm: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm = ViewModelProvider(requireActivity()).get(ViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_answer, container, false)

        init(view)

        return view
    }

    private fun init(view: View){
        recyclerView = view.findViewById(R.id.recyclerview_gallery)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = GridLayoutManager(requireContext(),6)
        vm.galleryAdapterLiveData.observe(requireActivity()) {
            recyclerView?.adapter = it
        }

        taskBody = view.findViewById(R.id.taskBody)
        taskBody!!.setText(vm.taskBody.value)
        taskBody!!.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                vm.setTaskBody(taskBody!!.text.toString())
            }
        })

        addImage = view.findViewById(R.id.addImage)
        addImage!!.setOnClickListener {
            ImagePicker.with(requireActivity())
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start()
        }

        switch = view.findViewById(R.id.switchType)
        switch!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 != 0) {
                    vm.changeFragment("Test")
                    vm.replace("CreateTestFragment")
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        editText = view.findViewById(R.id.AddAnswer)
        if (vm.listAnswersAnswer.value!!.size != 0){
            editText!!.setText(vm.listAnswersAnswer.value!!.get(0))
        }
        editText!!.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                vm.setAnswer(editText!!.text.toString())
            }

        })
    }
}