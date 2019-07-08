package com.example.fragments;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

public class AndroidMeActivity extends AppCompatActivity implements MasterListFragment.OnItemClickListener {

    int headIndex = 0;
    int bodyIndex = 0;
    int legsIndex = 0;
    boolean twoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_me);

        if (findViewById(R.id.tablet_linear_layout) != null){
            twoPane = true;
            GridView gridView = findViewById(R.id.master_list_grid_view);
            gridView.setNumColumns(2);
            findViewById(R.id.next_button).setVisibility(View.GONE);
            populateFrameLayouts(savedInstanceState);
        } else {
            twoPane = false;
        }
    }

    private void populateFrameLayouts(Bundle savedInstanceState) {
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


            FragmentManager headFragmentManager = getSupportFragmentManager();
            FragmentManager bodyFragmentManager = getSupportFragmentManager();
            FragmentManager legsFragmentManager = getSupportFragmentManager();

            headFragmentManager.beginTransaction().replace(R.id.head_container, headFragment).commit();
            bodyFragmentManager.beginTransaction().replace(R.id.body_container, bodyFragment).commit();
            legsFragmentManager.beginTransaction().replace(R.id.legs_container, legsFragment).commit();

        }
    }

    @Override
    public void onItemClick(int postiton) {
        Toast.makeText(this, "Position CLicked: " + postiton, Toast.LENGTH_SHORT).show();

        // 0=Head, 1=Body, 2=Leg
        int indexOfBodyPart = postiton / 12;

        int indexPartClicked = postiton - 12 * indexOfBodyPart;

        // If in tablet view
        if (twoPane) {
            BodyPartFragment bodyPartFragment = new BodyPartFragment();
            switch (indexOfBodyPart){
                case 0:
                    bodyPartFragment.setmListIndex(indexPartClicked);
                    bodyPartFragment.setmImageIds(AndroidImageAssets.getHeads());
                    getSupportFragmentManager().beginTransaction().replace(R.id.head_container, bodyPartFragment).commit();
                    break;

                case 1:
                    bodyPartFragment.setmImageIds(AndroidImageAssets.getBodies());
                    bodyPartFragment.setmListIndex(indexPartClicked);
                    getSupportFragmentManager().beginTransaction().add(R.id.body_container, bodyPartFragment).commit();

                    break;

                case 2:
                    bodyPartFragment.setmImageIds(AndroidImageAssets.getLegs());
                    bodyPartFragment.setmListIndex(indexPartClicked);
                    getSupportFragmentManager().beginTransaction().add(R.id.legs_container, bodyPartFragment).commit();
                    break;
            }

        } else { // If in mobile view

            switch (indexOfBodyPart) {
                case 0:
                    headIndex = indexPartClicked;
                    break;
                case 1:
                    bodyIndex = indexPartClicked;
                    break;
                case 2:
                    legsIndex = indexPartClicked;
                    break;
            }

            Bundle bundle = new Bundle();
            bundle.putInt("headIndex", headIndex);
            bundle.putInt("bodyIndex", bodyIndex);
            bundle.putInt("legsIndex", legsIndex);

            final Intent intent = new Intent(this, MainActivity.class);
            intent.putExtras(bundle);

            findViewById(R.id.next_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(intent);
                }
            });
        }
    }
}
