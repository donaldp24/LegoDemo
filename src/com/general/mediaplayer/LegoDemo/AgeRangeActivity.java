package com.general.mediaplayer.LegoDemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

/**
 * Created by donald on 4/7/14.
 */
public class AgeRangeActivity extends BaseActivity implements ViewPager.OnPageChangeListener, GestureDetector.OnDoubleTapListener {

    private ViewPager mCardsViewPager;
    private float MIN_SCALE = 1f - 1f / 4f;
    private float MAX_SCALE = 1f;
    private int mCurrItem = 0;
    private float alphaWeight = 1.3f;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agerange);

        // back button
        Button btnBack = (Button)findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AgeRangeActivity.this, ScanMediaActivity.class);
                startActivity(intent);
                overridePendingTransition(TransformManager.GetBackInAnim(), TransformManager.GetBackOutAnim());
                finish();
            }
        });

        mCardsViewPager = (ViewPager) findViewById(R.id.viewpager_cards);
        mCardsViewPager.setAdapter(new CardsPagerAdapter());
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mCardsViewPager.getLayoutParams();
        int nWidth = params.width;
        int nImageWidth = params.height;
        mCardsViewPager.setPageMargin(-(nWidth / 2 - nImageWidth / 2));
        mCardsViewPager.setOffscreenPageLimit(3);
        mCardsViewPager.setOnPageChangeListener(this);

        ResolutionSet._instance.iterateChild(findViewById(R.id.layout_agerange));
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Toast.makeText(getApplicationContext(), "Current Item : " + mCardsViewPager.getCurrentItem(), Toast.LENGTH_SHORT).show();
        mCurrItem = mCardsViewPager.getCurrentItem();

        Intent intent = null;
        if (mCurrItem == 0)
        {
            intent = new Intent(this, ResultActivity.class);
            intent.putExtra(CommonData.PARAM_KEY, CommonData.KEY_AGERANGE0);
        }

        if (intent != null)
        {
            startActivity(intent);
            overridePendingTransition(TransformManager.GetContinueInAnim(), TransformManager.GetContinueOutAnim());
            finish();
        }
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    private class CardsPagerAdapter extends PagerAdapter {

        private boolean mIsDefaultItemSelected = false;

        private int[] mCards = {
                R.drawable.agerange_range0,
                R.drawable.agerange_range3,
                R.drawable.agerange_range5,
                R.drawable.agerange_range7,
                R.drawable.agerange_range9,
                R.drawable.agerange_range12plus
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
                    if (mCurrItem == 0) // age range 0 - 2
                    {
                        intent = new Intent(AgeRangeActivity.this, ResultActivity.class);
                        intent.putExtra(CommonData.PARAM_KEY, CommonData.KEY_AGERANGE0);
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

            ((ViewGroup)cardImageView.getParent()).removeView(cardImageView);

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