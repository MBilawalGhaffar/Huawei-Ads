package com.my.huaweitestads.huaweiads;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.huawei.hms.ads.AdListener;
import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.BannerAdSize;
import com.huawei.hms.ads.HwAds;
import com.huawei.hms.ads.InterstitialAd;
import com.huawei.hms.ads.banner.BannerView;
import com.huawei.hms.ads.nativead.MediaView;
import com.huawei.hms.ads.nativead.NativeAd;
import com.huawei.hms.ads.nativead.NativeAdLoader;
import com.huawei.hms.ads.nativead.NativeView;
import com.my.huaweitestads.R;

public class HuaweiAds {

    public static HuaweiAds instance;
    public static Context mContext;
    private InterstitialAd interstitialAd;

    public static HuaweiAds getInstance(){
        if(instance==null){
            instance=new HuaweiAds();
        }
        return instance;
    }

    public void init(Context context) {
        HwAds.init(context);
        loadInterstitial(context);
    }

    private void loadInterstitial(final Context context) {
        interstitialAd = new InterstitialAd(context);
        // "testb4znbuh3n2" is a dedicated test ad unit ID. Before releasing your app, replace the test ad unit ID with the formal one.
        interstitialAd.setAdId(context.getString(R.string.video_ad_id));
        AdParam adParam = new AdParam.Builder().build();
        interstitialAd.loadAd(adParam);
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

            }
            @Override
            public void onAdFailed(int errorCode) {

            }
            @Override
            public void onAdClosed() {

            }
            @Override
            public void onAdClicked() {

            }
            @Override
            public void onAdLeave() {

            }
            @Override
            public void onAdOpened() {
                interstitialAd=null;
                loadInterstitial(context);
            }
            @Override
            public void onAdImpression() {

            }
        });
    }

    public void interstitialAd(Activity activity){
        if(interstitialAd != null && interstitialAd.isLoaded()){
            interstitialAd.show(activity);
        }
    }
    public void bannerAd(Context context, final FrameLayout frameLayout) {
        BannerView banner = new BannerView(context);
        banner.setAdId(context.getString(R.string.banner_ad_id));
        banner.setBannerAdSize(BannerAdSize.BANNER_SIZE_320_50);
        banner.setBannerRefresh(60);
        frameLayout.addView(banner);
        banner.setAdListener(new AdListener(){
            @Override
            public void onAdFailed(int i) {
                super.onAdFailed(i);
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();

            }
        });
        banner.loadAd(new AdParam.Builder().build());
    }
    public void nativeAd(final Activity activity, final FrameLayout frameLayout) {
        NativeAdLoader.Builder builder = new NativeAdLoader.Builder(activity, activity.getString(R.string.ad_id_native));
        builder.setNativeAdLoadedListener(new NativeAd.NativeAdLoadedListener() {
            @Override
            public void onNativeAdLoaded(NativeAd nativeAd) {
                NativeView nativeView = (NativeView) activity.getLayoutInflater().inflate(R.layout.ad_native, null);
                NativeView nativeView2 = (NativeView) activity.getLayoutInflater().inflate(R.layout.ad_native_small, null);
                // Register and populate the native ad asset views.
                initNativeAdView(nativeAd, nativeView2);
                frameLayout.removeAllViews();
                frameLayout.addView(nativeView2);
            }
        }).setAdListener(new AdListener() {
            @Override
            public void onAdFailed(int errorCode) {

            }
        });
        NativeAdLoader nativeAdLoader = builder.build();
        nativeAdLoader.loadAd(new AdParam.Builder().build());
        //no of ads
//        nativeAdLoader.loadAds(new AdParam.Builder().build(), 5);



    }
    private void initNativeAdView(NativeAd nativeAd, NativeView nativeView) {
        // Register and populate the title view.
        nativeView.setTitleView(nativeView.findViewById(R.id.ad_title));
        ((TextView) nativeView.getTitleView()).setText(nativeAd.getTitle());
        // Register and populate the multimedia view.
        nativeView.setMediaView((MediaView) nativeView.findViewById(R.id.ad_media));
        nativeView.getMediaView().setMediaContent(nativeAd.getMediaContent());
        // Register and populate other asset views.
        nativeView.setAdSourceView(nativeView.findViewById(R.id.ad_source));
        nativeView.setCallToActionView(nativeView.findViewById(R.id.ad_call_to_action));
        if (null != nativeAd.getAdSource()) {
            ((TextView) nativeView.getAdSourceView()).setText(nativeAd.getAdSource());
        }
        nativeView.getAdSourceView()
                .setVisibility(null != nativeAd.getAdSource() ? View.VISIBLE : View.INVISIBLE);
        if (null != nativeAd.getCallToAction()) {
            ((Button) nativeView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }
        nativeView.getCallToActionView()
                .setVisibility(null != nativeAd.getCallToAction() ? View.VISIBLE : View.INVISIBLE);

        // Register the native ad object.
        nativeView.setNativeAd(nativeAd);
    }
}
