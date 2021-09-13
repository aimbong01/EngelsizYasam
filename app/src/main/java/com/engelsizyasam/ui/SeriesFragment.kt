package com.engelsizyasam.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.engelsizyasam.R
import com.engelsizyasam.adapter.SeriesAdapter
import com.engelsizyasam.adapter.SeriesListener
import com.engelsizyasam.databinding.FragmentSeriesBinding
import com.engelsizyasam.model.SeriesCardModel
import com.engelsizyasam.viewmodel.SeriesViewModel

class SeriesFragment : Fragment() {

    private val seriesList: ArrayList<SeriesCardModel> = arrayListOf()
    private var seriesName: String = ""
    private var seriesPage: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        seriesList.add(SeriesCardModel("PL_VIYA-L9VnISXsjCYfyeVYLsuZeY-D__", "Diriliş Ertuğrul", 4, "dirilisertugrul"))
        seriesList.add(SeriesCardModel("PL_VIYA-L9VnIoZJjL1YaJGmmfw7nI8-A5", "Teşkilat", 1, "teskilat"))
        seriesList.add(SeriesCardModel("PL_VIYA-L9VnKL00e3LGd5IBurT2SUvAVL", "Gönül Dağı", 1, "gonuldagi"))
        seriesList.add(SeriesCardModel("PL_VIYA-L9VnJ6kLMdj4xC554TLhBsW6-l", "Masumlar Apartmani", 1, "masumlarapartmani"))
        seriesList.add(SeriesCardModel("PL_VIYA-L9VnIEw36Mw-wtN7ydYHvpVFbK", "Aslan Ailem", 1, "aslanailem"))
        seriesList.add(SeriesCardModel("PL_VIYA-L9VnL0tyGydgGCBCxGpx5gutV7", "Yunus Emre", 1, "yunusemre"))
        seriesList.add(SeriesCardModel("PL_VIYA-L9VnKIJBMMROXc5-7Sl15-oUU_", "Yeşil Deniz", 2, "yesildeniz"))
        seriesList.add(SeriesCardModel("PL_VIYA-L9VnKTrFDAWpAUlm9FcVNOxn02", "Adını Sen Koy", 4, "adinisenkoy"))
        seriesList.add(SeriesCardModel("PL_VIYA-L9VnKSTAuwyPsyjVh4vQ_RekNe", "Payitaht Abdulhamid", 4, "payitaht"))

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentSeriesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_series, container, false)
        val application = requireNotNull(this.activity).application
        val viewModel = ViewModelProvider(this).get(SeriesViewModel::class.java)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        val adapter = SeriesAdapter(application, SeriesListener { playlistId, seriesName, seriesPage ->
            this.seriesName = seriesName
            this.seriesPage = seriesPage

            viewModel.onSeriesClicked(playlistId)
        })

        viewModel.navigateToSeriesDetail.observe(viewLifecycleOwner, {
            it?.let {
                findNavController().navigate(SeriesFragmentDirections.actionSeriesFragmentToSeriesDetailFragment(it, this.seriesName, this.seriesPage))
                viewModel.onSeriesDetailNavigated()
            }
        })

        binding.recyclerView.adapter = adapter

        adapter.data = seriesList

        return binding.root
    }
}