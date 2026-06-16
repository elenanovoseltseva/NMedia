package ru.netology.nmedia.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.util.StringArg
import ru.netology.nmedia.viewmodel.PostViewModel

class NewPostFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentNewPostBinding.inflate(inflater, container, false)

        val viewModel: PostViewModel by activityViewModels() //viewModels(ownerProducer = ::requireParentFragment)

        val content = arguments?.getString("content").orEmpty()
        binding.editText.setText(content)

        AndroidUtils.showKeyboard(binding.editText)

        binding.ok.setOnClickListener {
            val text = binding.editText.text.toString()

            if (text.isNotBlank()) {
                viewModel.saveById(text)
            }

            findNavController().navigateUp()
        }

        return binding.root
    }

    companion object {
        var Bundle.textArg by StringArg
    }
}


