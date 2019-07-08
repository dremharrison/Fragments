package com.example.fragments;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = getIntent().getExtras();

        Integer headIndex = (Integer) bundle.get("headIndex");
        Integer bodyIndex = (Integer) bundle.get("bodyIndex");
        Integer legsIndex = (Integer) bundle.get("legsIndex");

        if (savedInstanceState == null) {
            BodyPartFragment headFragment = new BodyPartFragment();
            BodyPartFragment bodyFragment = new BodyPartFragment();
            BodyPartFragment legsFragment = new BodyPartFragment();

            headFragment.setmImageIds(AndroidImageAssets.getHeads());
            headFragment.setmListIndex(headIndex);

            bodyFragment.setmImageIds(AndroidImageAssets.getBodies());
            bodyFragment.setmListIndex(bodyIndex);

            legsFragment.setmImageIds(AndroidImageAssets.getLegs());
            legsFragment.setmListIndex(legsIndex);


            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction().replace(R.id.head_container, headFragment).commit();
            fragmentManager.beginTransaction().add(R.id.body_container, bodyFragment).commit();
            fragmentManager.beginTransaction().add(R.id.legs_container, legsFragment).commit();

        }
    }
}
