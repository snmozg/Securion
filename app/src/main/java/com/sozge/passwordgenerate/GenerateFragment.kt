package com.sozge.passwordgenerate

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.sozge.passwordgenerate.databinding.FragmentGenerateBinding
import kotlin.random.Random

class GenerateFragment : Fragment() {
    private var _binding: FragmentGenerateBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentGenerateBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.generateButton.setOnClickListener{ generateButtonClicked(it) }
    }
    fun generateButtonClicked(view: View){
        binding.apply {
            generateButton.setOnClickListener {
                val selectedOptions = mutableListOf<Char>()

                if(checkboxLowercase.isChecked){
                    selectedOptions.addAll(('a'..'z'))
                }
                if(checkboxUppercase.isChecked){
                    selectedOptions.addAll(('A'..'Z'))
                }
                if(checkboxNumbers.isChecked){
                    selectedOptions.addAll(('0'..'9'))
                }
                if(checkboxSymbols.isChecked){
                    selectedOptions.addAll(("!+$%#*_?".toList()))
                }
                if(selectedOptions.isEmpty()){
                    Toast.makeText(requireContext(), "please select options.", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
                val passwordLength = 8
                if(checkboxLowercase.isChecked && checkboxUppercase.isChecked && checkboxNumbers.isChecked && checkboxSymbols.isChecked){
                    Toast.makeText(requireContext(), "YOUR PASSWORD IS SO STRONG!", Toast.LENGTH_SHORT).show()
                }
                val randomPassword = buildString {
                    repeat(passwordLength){
                        val randomIndex = Random.nextInt(0,selectedOptions.size)
                        append(selectedOptions[randomIndex])
                    }
                }
                tvPassword.text = randomPassword
            }
            tvPassword.setOnClickListener {
                val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("TextViewText", tvPassword.text)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(requireContext(),"copied.", Toast.LENGTH_LONG).show()
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}