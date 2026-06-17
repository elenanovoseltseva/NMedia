package ru.netology.nmedia.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
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

        val content = arguments?.getString("content")
        val draft = viewModel.draft.value

        val textToShow = content?.takeIf { it.isNotBlank() }
            ?: draft.orEmpty()

        binding.editText.setText(textToShow)

        AndroidUtils.showKeyboard(binding.editText)

        binding.ok.setOnClickListener {
            val text = binding.editText.text.toString()

            if (text.isNotBlank()) {
                viewModel.saveById(text)
            }

            findNavController().navigateUp()
        }

        binding.editText.doOnTextChanged { text, _, _, _ ->
            viewModel.changeDraft(text.toString())
        }



        return binding.root
    }

    companion object {
        var Bundle.textArg by StringArg
    }
}


