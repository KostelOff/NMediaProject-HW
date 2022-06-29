package ru.netology.nmedia

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.util.focusAndShowKeyboard
import ru.netology.nmedia.util.hideKeyBoard
import ru.netology.nmedia.viewModel.PostViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PostsAdapter(viewModel)
        binding.postsRecyclerView.adapter = adapter

        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        binding.saveButton.setOnClickListener {
            with(binding.contentEditText) {
                val content = text.toString()
                viewModel.onSaveButtonClicked(content)

                clearFocus()
                hideKeyBoard()
            }
        }

        binding.closeEditButton.setOnClickListener {
            with(binding.contentEditText) {
                viewModel.onCloseEditClicked()
                clearFocus()
                hideKeyBoard()
            }
            binding.groupForEdit.visibility = View.GONE
        }

        viewModel.currentPost.observe(this) { currentPost ->
            with(binding.contentEditText) {
                val content = currentPost?.content
                setText(content)
                if (content != null) {
                    binding.editMessageTextContent.text = content
                    binding.groupForEdit.visibility = View.VISIBLE
                    focusAndShowKeyboard()
                } else {
                    binding.groupForEdit.visibility = View.GONE
                    clearFocus()
                    hideKeyBoard()
                }
            }
        }
    }
}