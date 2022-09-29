package com.my.huaweitestads

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.my.huaweitestads.huaweiads.HuaweiAds

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        HuaweiAds.getInstance().bannerAd(this,findViewById(R.id.banner_ad))
        HuaweiAds.getInstance().nativeAd(this,findViewById(R.id.native_ad))
    }
}