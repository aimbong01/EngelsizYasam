package com.engelsizyasam.view

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import com.engelsizyasam.R
import com.engelsizyasam.databinding.FragmentScholarDetailBinding
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
@AndroidEntryPoint
class ScholarDetailFragment : Fragment() {

    private lateinit var application: Application
    private lateinit var binding: FragmentScholarDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_scholar_detail, container, false)
        application = requireNotNull(this.activity).application
        val args: ScholarDetailFragmentArgs by navArgs()

        val viewModelFactory = ScholarDetailViewModelFactory(application, args.link)

        val viewModel = ViewModelProvider(this, viewModelFactory).get(ScholarDetailViewModel::class.java)

        binding.lifecycleOwner = this


        PRDownloader.initialize(application)

        binding.progressBar.visibility = View.VISIBLE
        val fileName = "myFile.pdf"
        downloadPdfFromInternet(viewModel.link, getRootDirPath(application), fileName)

        return binding.root
    }

    private fun downloadPdfFromInternet(url: String, dirPath: String, fileName: String) {
        PRDownloader.download(url, dirPath, fileName).build().start(object : OnDownloadListener {
            override fun onDownloadComplete() {
                val downloadedFile = File(dirPath, fileName)
                binding.progressBar.visibility = View.GONE
                showPdfFromFile(downloadedFile)
            }

            override fun onError(error: com.downloader.Error?) {
                Toast.makeText(application, "İndirme Başarısız : $error", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun showPdfFromFile(file: File) {
        binding.pdfView.fromFile(file)
            .defaultPage(0)
            .enableAnnotationRendering(true)
            .scrollHandle(DefaultScrollHandle(application))
            .load()
    }

    private fun getRootDirPath(context: Context): String {
        return if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            val file: File = ContextCompat.getExternalFilesDirs(context.applicationContext, null)[0]
            file.absolutePath
        } else {
            context.applicationContext.filesDir.absolutePath
        }
    }

}
