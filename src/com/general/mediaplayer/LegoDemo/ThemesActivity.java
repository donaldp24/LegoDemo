package com.general.mediaplayer.LegoDemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by donald on 4/4/14.
 */
public class ThemesActivity extends BaseActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.themes);

        // back button
        Button btnBack = (Button)findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThemesActivity.this, ScanMediaActivity.class);
                startActivity(intent);
                overridePendingTransition(TransformManager.GetBackInAnim(), TransformManager.GetBackOutAnim());
                finish();
            }
        });

        // architecture button
        Button btnArchitecture = (Button)findViewById(R.id.btn_architecture);
        btnArchitecture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoResult(CommonData.KEY_ARCHITECTURE);
            }
        });

        // castle button
        Button btnCastle = (Button)findViewById(R.id.btn_castle);
        btnCastle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoResult(CommonData.KEY_CASTLE);
            }
        });

        // city button
        Button btnCity = (Button)findViewById(R.id.btn_city);
        btnCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoResult(CommonData.KEY_CITY);
            }
        });

        // galaxy button
        Button btnGalaxy = (Button)findViewById(R.id.btn_galaxy);
        btnGalaxy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoResult(CommonData.KEY_GALAXY);
            }
        });

        // juniors button
        Button btnJuniors = (Button)findViewById(R.id.btn_juniors);
        btnJuniors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoResult(CommonData.KEY_JUNIORS);
            }
        });

        // starwars button
        Button btnStarwars = (Button)findViewById(R.id.btn_starwars);
        btnStarwars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoResult(CommonData.KEY_STARWAS);
            }
        });

        // rearrange children
        ResolutionSet._instance.iterateChild(findViewById(R.id.layout_themes));
    }

    private void gotoResult(int key)
    {
        Intent intent = new Intent(ThemesActivity.this, ResultActivity.class);
        intent.putExtra(CommonData.PARAM_KEY, key);
        startActivity(intent);
        overridePendingTransition(TransformManager.GetContinueInAnim(), TransformManager.GetContinueOutAnim());
        finish();
    }
}