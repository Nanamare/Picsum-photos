package com.nanamare.starter.ui

import android.content.res.Configuration
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.nanamare.core.util.viewBinding
import com.nanamare.starter.R
import com.nanamare.starter.databinding.ActivityPhotoBinding
import com.nanamare.starter.ui.adapter.PagingLoadStateAdapter
import com.nanamare.starter.ui.adapter.PhotoAdapter
import com.nanamare.starter.util.getResourceId
import com.nanamare.starter.util.getScreenWidth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider

// TODO 코드량 많아지면 Base 분리하기
@AndroidEntryPoint
class PhotoActivity : AppCompatActivity() {

    @Inject
    lateinit var gridLayoutManager: Provider<GridLayoutManager>

    @Inject
    @Named("span_count")
    lateinit var spanCountProvider: Provider<Int>

    private val spanCountTrigger by lazy { MutableLiveData(Unit) }

    private val binding by viewBinding(ActivityPhotoBinding::inflate)

    // TODO alpha 끝나면 CreationExtras 로 교체해보기
    private val viewModel: PhotoViewModel by viewModels<PhotoViewModelImpl>()

    private val photoAdapter by lazy {
        PhotoAdapter(getScreenWidth(this) / spanCountProvider.get()) {
            val intent = PhotoDetailActivity.getIntent(this, it.downloadUrl.orEmpty())
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
        addCollector()
    }

    private fun initView() {
        with(binding.rv) {
            adapter =
                photoAdapter.withLoadStateFooter(footer = PagingLoadStateAdapter(photoAdapter::retry))
            layoutManager = gridLayoutManager.get().apply {
                addSpanStrategy()
            }
        }

        photoAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        photoAdapter.addLoadStateListener { loadState ->
            with(binding.placeHolderView) {
                when {
                    isEmpty(loadState) -> changeMode(PlaceHolderView.State.EMPTY)
                    isError(loadState) -> changeMode(PlaceHolderView.State.ERROR)
                    else -> isVisible = false
                }
            }
        }

        binding.placeHolderView.button.setOnClickListener { photoAdapter.retry() }

    }

    private fun GridLayoutManager.addSpanStrategy() {
        spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int =
                when (photoAdapter.getItemViewType(position)) {
                    PhotoAdapter.VIEW_TYPE_IMAGE -> spanCountProvider.get()
                    else -> 1
                }
        }
    }

    private fun isEmpty(loadState: CombinedLoadStates) =
        loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && photoAdapter.itemCount == 0

    private fun isError(loadState: CombinedLoadStates) =
        loadState.mediator?.refresh is LoadState.Error && photoAdapter.itemCount == 0

    private fun addCollector() {
        lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.photo.collect(photoAdapter::submitData)
                }


                launch {
                    viewModel.error.collect { errorMsg ->
                        Snackbar.make(binding.root, errorMsg.toString(), Snackbar.LENGTH_INDEFINITE)
                            .apply {
                                view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).maxLines =
                                    Int.MAX_VALUE
                            }
                            .show()
                    }
                }
            }
        }

        spanCountTrigger.observe(this) { onSpanCountChanged() }
    }

    private fun onSpanCountChanged() {
        binding.rv.layoutManager = gridLayoutManager.get().apply {
            addSpanStrategy()
        }
    }

    override fun onBackPressed() {
        onShowCloseAlertDialog()
    }

    private fun onShowCloseAlertDialog() {
        MaterialAlertDialogBuilder(this, R.style.ThemeOverlay_AlertDialog_Image)
            .setView(
                ImageView(this).apply {
                    setImageResource(context.getResourceId(R.attr.closeIcon))
                }
            )
            .setTitle(getString(R.string.title_close_app))
            .setPositiveButton(R.string.cancel, null)
            .setNegativeButton(R.string.close) { _, _ -> finishAndRemoveTask() }
            .show()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        spanCountTrigger.value = Unit
    }


}