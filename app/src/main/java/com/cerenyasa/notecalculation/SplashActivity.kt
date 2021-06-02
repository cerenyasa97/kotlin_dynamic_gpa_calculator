package com.cerenyasa.notecalculation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.animation.AnimationUtils
import com.cerenyasa.notecalculation.databinding.ActivitySplashBinding


class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val button = binding.navigationBtn
        val image = binding.splashImage

        var slidingToBottom = AnimationUtils.loadAnimation(this, R.anim.sliding_to_bottom)
        var slidingToTop = AnimationUtils.loadAnimation(this, R.anim.sliding_to_top)

        button.animation = AnimationUtils.loadAnimation(this, R.anim.sliding_from_bottom)
        image.animation = AnimationUtils.loadAnimation(this, R.anim.sliding_from_top)

        binding.navigationBtn.setOnClickListener {
            button.animation = slidingToBottom
            image.animation = slidingToTop

            object : CountDownTimer(1000, 1000){
                override fun onTick(millisUntilFinished: Long) {
                    var intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                }

                override fun onFinish() {}
            }.start()
        }
    }
}