package com.general.mediaplayer.LegoDemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;

import android.support.v4.view.ViewPager;

import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import hidusb.UsbManager;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;

public class ScanMediaActivity extends BaseActivity implements OnPageChangeListener {

    private ViewPager mCardsViewPager;
    private float MIN_SCALE = 1f - 1f / 4f;
    private float MAX_SCALE = 1f;
    private int mCurrItem = 0;
    private float alphaWeight = 1.3f;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // initialize resolution set
        Point ptSize = ResolutionSet.getScreenSize(ScanMediaActivity.this, true, false);
        ResolutionSet._instance.setResolution(ptSize.x, ptSize.y, false);

        // rearrange children
        ResolutionSet._instance.iterateChild(findViewById(R.id.layout_main));

        mCardsViewPager = (ViewPager) findViewById(R.id.viewpager_cards);
        mCardsViewPager.setAdapter(new CardsPagerAdapter());
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mCardsViewPager.getLayoutParams();
        int nWidth = params.width;
        int nImageWidth = params.height;
        mCardsViewPager.setPageMargin(-(nWidth / 2 - nImageWidth / 2));
        mCardsViewPager.setOffscreenPageLimit(3);
        mCardsViewPager.setOnPageChangeListener(this);

        // start usb hub
        if (CommonData.LIGHT_MODE == CommonData.LIGHT_USBHID)
        {
            Application application = (Application) getApplication();
            UsbManager usbManager = application.getUsbManager();
            usbManager.startUsb();
        }
    }

    private class CardsPagerAdapter extends PagerAdapter {

        private boolean mIsDefaultItemSelected = false;

        private int[] mCards = {
                R.drawable.main_themes,
                R.drawable.main_exclusives,
                R.drawable.main_agerange
                };

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LinearLayout itemLayout = (LinearLayout)getLayoutInflater().inflate(R.layout.imageview_card, null);
            ImageView cardImageView = (ImageView)itemLayout.findViewById(R.id.content_imgview);
            cardImageView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCurrItem = mCardsViewPager.getCurrentItem();

                    Intent intent = null;
                    if (mCurrItem == 0) // themes
                    {
                        intent = new Intent(ScanMediaActivity.this, ThemesActivity.class);
                    }
                    else if (mCurrItem == 1) //exclusive
                    {
                        intent = new Intent(ScanMediaActivity.this, ResultActivity.class);
                        intent.putExtra(CommonData.PARAM_KEY, CommonData.KEY_EXCLUSIVE);
                    }
                    else if (mCurrItem == 2)    //age range
                    {
                        intent = new Intent(ScanMediaActivity.this, AgeRangeActivity.class);
                    }

                    if (intent != null)
                    {
                        startActivity(intent);
                        overridePendingTransition(TransformManager.GetContinueInAnim(), TransformManager.GetContinueOutAnim());
                        finish();
                    }
                }
            });
            cardImageView.setImageDrawable(getResources().getDrawable(mCards[position]));
            cardImageView.setTag(position);

            if (!mIsDefaultItemSelected) {
                cardImageView.setScaleX(MAX_SCALE);
                cardImageView.setScaleY(MAX_SCALE);
                cardImageView.setAlpha(1f);
                mIsDefaultItemSelected = true;
            } else {
                cardImageView.setScaleX(MIN_SCALE);
                cardImageView.setScaleY(MIN_SCALE);
                cardImageView.setAlpha(1f - 1f / alphaWeight);
            }

            container.addView(cardImageView);
            return cardImageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mCards.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        for (int i = 0; i < mCardsViewPager.getChildCount(); i++) {
            View cardView = mCardsViewPager.getChildAt(i);
            int itemPosition = (Integer) cardView.getTag();

            if (itemPosition == position) {
                cardView.setScaleX(MAX_SCALE - positionOffset / 4f);
                cardView.setScaleY(MAX_SCALE - positionOffset / 4f);
                cardView.setAlpha(1 - positionOffset / alphaWeight);
            }

            if (itemPosition == (position + 1)) {
                cardView.setScaleX(MIN_SCALE + positionOffset / 4f);
                cardView.setScaleY(MIN_SCALE + positionOffset / 4f);
                cardView.setAlpha(1 - 1 / alphaWeight + positionOffset / alphaWeight);
            }
        }
    }

    @Override
    public void onPageSelected(int position) {

    }
}
