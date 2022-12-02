package com.karthik.newsegmentio

import android.app.NotificationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.clevertap.android.sdk.CleverTapAPI
import com.karthik.newsegmentio.databinding.ActivityMainBinding
import com.segment.analytics.Analytics
import com.segment.analytics.Traits
import com.segment.analytics.android.integrations.clevertap.CleverTapIntegration

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var cleverTapDefaultInstance: CleverTapAPI? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.VERBOSE)
        cleverTapDefaultInstance = CleverTapAPI.getDefaultInstance(applicationContext)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CleverTapAPI.createNotificationChannelGroup(
                applicationContext,
                "testkk1",
                "Notification Test"
            )
        }
        CleverTapAPI.createNotificationChannel(
            applicationContext, "testkk123", "Notification Test", "CleverTap Notification Test",
            NotificationManager.IMPORTANCE_MAX, true
        )
        CleverTapAPI.createNotificationChannel(
            applicationContext,
            "testkk1234",
            "KK Notification Test",
            "KK CleverTap Notification Test",
            NotificationManager.IMPORTANCE_MAX,
            true
        )

        cleverTapDefaultInstance?.enableDeviceNetworkInfoReporting(true)

        //data via SDk only from segment to CT
        binding.fromSegmentToCT.setOnClickListener {
            fromSegmentToCT()
        }
    }

    //will send from segment to CleverTap
    private fun fromSegmentToCT() {
        val analytics = Analytics.Builder(this, "your_write_key")
            .logLevel(Analytics.LogLevel.VERBOSE)
            .use(CleverTapIntegration.FACTORY)
            .build()

        val traits = Traits()
        traits.putEmail(binding.email.text.toString())
        traits["string"] = "hello"
        analytics.identify(binding.identity.text.toString(), traits, null)
        analytics.track("KK Segment CT Completed")
        Toast.makeText(applicationContext, "fromSegmentToCT clicked", Toast.LENGTH_SHORT).show()
    }
}