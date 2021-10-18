package com.engelsizyasam.view

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.engelsizyasam.R
import com.engelsizyasam.database.BookDatabase
import com.engelsizyasam.databinding.FragmentBookVoiceDetailBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.concurrent.TimeUnit
import android.view.animation.LinearInterpolator

import android.view.animation.Animation

import android.view.animation.RotateAnimation
import androidx.navigation.findNavController

class BookVoiceDetailFragment : Fragment() {
    lateinit var viewModel: BookVoiceDetailViewModel
    lateinit var binding: FragmentBookVoiceDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_book_voice_detail, container, false)
        val application = requireNotNull(this.activity).application
        val arguments: BookVoiceDetailFragmentArgs by navArgs()
        val dataSource = BookDatabase.getInstance(application).bookDatabaseDao
        val viewModelFactory = BookVoiceDetailViewModelFactory(arguments.bookId, dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory).get(BookVoiceDetailViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomBar)
        navBar.visibility = View.INVISIBLE

        binding.backButton.setOnClickListener {
            viewModel.stopButton()
            it.findNavController().popBackStack()
        }

        viewModel.getBook().observe(viewLifecycleOwner, { book ->

            if (viewModel.mediaPlayer == null) {

                viewModel.initialize(book.bookVoiceUrl)
            }
            initialiseSeekBar()
            val rotate = RotateAnimation(0F, 360F, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
            rotate.duration = 30000
            rotate.interpolator = LinearInterpolator()
            rotate.repeatCount = -1
            rotate.fillAfter = true
            val id = application.resources.getIdentifier("com.engelsizyasam:drawable/book_${book.bookImage}", null, null)
            binding.imageView.setImageResource(id)
            binding.imageView.startAnimation(rotate)

            binding.progressBar.visibility = View.GONE
            binding.startButton.isEnabled = true
            binding.pauseButton.isEnabled = true
            binding.stopButton.isEnabled = true


            binding.startButton.setOnClickListener {
                viewModel.playButton(book.bookVoiceUrl)
                initialiseSeekBar()
            }

            binding.pauseButton.setOnClickListener {
                viewModel.pauseButton()
            }

            binding.stopButton.setOnClickListener {
                viewModel.stopButton()
                binding.seekBar.progress = 0
                binding.currentDuration.text = "0:0:0"
            }

            binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if (fromUser) {
                        viewModel.mediaPlayer?.seekTo(progress)
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }

            })

        })

        return binding.root
    }

    private fun initialiseSeekBar() {
        binding.seekBar.max = viewModel.mediaPlayer!!.duration

        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                try {
                    binding.seekBar.progress = viewModel.mediaPlayer!!.currentPosition
                    val current = (String.format(
                        "%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(viewModel.mediaPlayer!!.currentPosition.toLong()),
                        TimeUnit.MILLISECONDS.toMinutes(viewModel.mediaPlayer!!.currentPosition.toLong()) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(viewModel.mediaPlayer!!.currentPosition.toLong())),
                        TimeUnit.MILLISECONDS.toSeconds(viewModel.mediaPlayer!!.currentPosition.toLong()) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(viewModel.mediaPlayer!!.currentPosition.toLong()))
                    ))
                    viewModel.onCurrent(current)
                    val total = (String.format(
                        "%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(viewModel.mediaPlayer!!.duration.toLong()),
                        TimeUnit.MILLISECONDS.toMinutes(viewModel.mediaPlayer!!.duration.toLong()) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(viewModel.mediaPlayer!!.duration.toLong())),
                        TimeUnit.MILLISECONDS.toSeconds(viewModel.mediaPlayer!!.duration.toLong()) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(viewModel.mediaPlayer!!.duration.toLong()))
                    ))
                    viewModel.onTotal(total)

                    handler.postDelayed(this, 1000)
                } catch (e: Exception) {
                    binding.seekBar.progress = 0
                }
            }
        }, 0)
    }

    override fun onPause() {
        viewModel.stopButton()
        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomBar)
        navBar.visibility = View.VISIBLE
        super.onPause()
    }

}