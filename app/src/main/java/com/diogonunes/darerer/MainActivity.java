package com.diogonunes.darerer;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static StringRoulette _kindnessActsRoulette, _encourageRoulette;

    private TextView _txtDecision;
    private ImageView _imgDecision;
    private LinearLayout _layoutDefault, _layoutChallenge;
    private Toolbar _toolbar;
    private TabLayout _tabLayout;
    private ViewPager _viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initActivity();

        setSupportActionBar(_toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) supportActionBar.setDisplayHomeAsUpEnabled(true);

        initViewPager(_viewPager);
        _tabLayout.setupWithViewPager(_viewPager);
        initTabIcons();
    }

    @Override
    protected void onStart() {
        super.onStart();

        _layoutDefault.setVisibility(View.VISIBLE);
        _layoutChallenge.setVisibility(View.GONE);
    }

    // Event Handling

    //    @Override
    //    public boolean onCreateOptionsMenu(Menu menu) {
    //        // Inflate the menu; this adds items to the action bar if it is present.
    //        getMenuInflater().inflate(R.menu.menu_main, menu);
    //        return true;
    //    }

    //    @Override
    //    public boolean onOptionsItemSelected(MenuItem item) {
    //        // Handle action bar item clicks here. The action bar will
    //        // automatically handle clicks on the Home/Up button, so long
    //        // as you specify a parent activity in AndroidManifest.xml.
    //        int id = item.getItemId();
    //
    //        //noinspection SimplifiableIfStatement
    //        if (id == R.id.action_settings) {
    //            return true;
    //        }
    //
    //        return super.onOptionsItemSelected(item);
    //    }

    public void onClickFab(View view) {
        // Picks a challenge
        String challengeDesc = _kindnessActsRoulette.roll();

        // Displays it
        TextView cardChallenge = (TextView) findViewById(R.id.card_title);
        cardChallenge.setText(challengeDesc);

        // Allows the user to decide
        setDecision(view, 0);
        _layoutDefault.setVisibility(View.GONE);
        _layoutChallenge.setVisibility(View.VISIBLE);
    }

    public void onClickAcceptChallenge(View view) {
        setDecision(view, R.id.btn_challenge_yes);
    }

    public void onClickDenyChallenge(View view) {
        setDecision(view, R.id.btn_challenge_no);

        if (Utils.getRandomBool(30)) {
            Snackbar snackbar = Snackbar
                    .make(view, _encourageRoulette.roll(), Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.dialog_sorry, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // close self
                        }
                    });
            snackbar.show();
        }
    }

    // Auxiliary

    private void initActivity() {
        // Instance variables
        _layoutDefault = (LinearLayout) findViewById(R.id.layout_challenge_off);
        _layoutChallenge = (LinearLayout) findViewById(R.id.layout_challenge_on);
        _txtDecision = (TextView) findViewById(R.id.txt_challenge_decision);
        _imgDecision = (ImageView) findViewById(R.id.img_meme_decision);
        _toolbar = (Toolbar) findViewById(R.id.toolbar);
        _viewPager = (ViewPager) findViewById(R.id.viewpager);
        _tabLayout = (TabLayout) findViewById(R.id.tabs);

        String[] kindnessChallenges = getResources().getStringArray(R.array.kindness_challenges);
        _kindnessActsRoulette = new StringRoulette(kindnessChallenges);

        String[] encouragements = getResources().getStringArray(R.array.encouragements);
        _encourageRoulette = new StringRoulette(encouragements);
    }

    private void initTabIcons() {
        TabLayout.Tab tab;

        tab = _tabLayout.getTabAt(0);
        if (tab != null) tab.setIcon(R.drawable.ic_heart_white);

        tab = _tabLayout.getTabAt(1);
        if (tab != null) tab.setIcon(R.drawable.ic_star_white);

        tab = _tabLayout.getTabAt(2);
        if (tab != null) tab.setIcon(R.drawable.ic_face_white);
    }

    private void initViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment(), "Kind");
        adapter.addFragment(new TwoFragment(), "Nice");
        adapter.addFragment(new ThreeFragment(), "Naughty");
        viewPager.setAdapter(adapter);
    }

    private void setDecision(View view, int decision) {
        int decisionText;
        Drawable decisionImage;

        switch (decision) {
            case R.id.btn_challenge_yes:
                decisionText = R.string.challenge_accepted;
                decisionImage = ContextCompat.getDrawable(view.getContext(), R.drawable.img_meme_yes);
                break;
            case R.id.btn_challenge_no:
                decisionText = R.string.challenge_denied;
                decisionImage = ContextCompat.getDrawable(view.getContext(), R.drawable.img_meme_no);
                break;
            default:
                decisionText = R.string.challenge_considered;
                decisionImage = ContextCompat.getDrawable(view.getContext(), R.drawable.img_meme_maybe);
                break;
        }

        _txtDecision.setText(decisionText);
        _imgDecision.setImageDrawable(decisionImage);
    }
}
