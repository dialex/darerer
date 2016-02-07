package com.diogonunes.darerer;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static KindnessChallenger _kindnessChallenger;

    private TextView _txtDecision;
    private ImageView _imgDecision;
    private LinearLayout _layoutDefault, _layoutChallenge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeActivity();
    }

    @Override
    protected void onStart() {
        super.onStart();

        _layoutDefault.setVisibility(View.VISIBLE);
        _layoutChallenge.setVisibility(View.GONE);
    }

    private void initializeActivity() {
        // Instance variables
        _layoutDefault = (LinearLayout) findViewById(R.id.layout_challenge_off);
        _layoutChallenge = (LinearLayout) findViewById(R.id.layout_challenge_on);
        _txtDecision = (TextView) findViewById(R.id.txt_challenge_decision);
        _imgDecision = (ImageView) findViewById(R.id.img_meme_decision);

        String[] kindnessChallenges = getResources().getStringArray(R.array.kindness_challenges);
        _kindnessChallenger = new KindnessChallenger(kindnessChallenges);
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
        String challengeDesc = _kindnessChallenger.getChallenge();

        // Displays it
        TextView cardChallenge = (TextView) findViewById(R.id.card_title);
        cardChallenge.setText(challengeDesc);

        // Allows the user to decide
        _layoutDefault.setVisibility(View.GONE);
        _layoutChallenge.setVisibility(View.VISIBLE);
    }

    public void onClickAcceptChallenge(View view) {
        _txtDecision.setText(R.string.challenge_accepted);
        _imgDecision.setImageDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.img_meme_yes));
    }

    public void onClickDenyChallenge(View view) {
        _txtDecision.setText(R.string.challenge_denied);
        _imgDecision.setImageDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.img_meme_no));
    }

    public void onClickConsiderChallenge(View view) {
        _txtDecision.setText(R.string.challenge_considered);
    }

    // Auxiliary
}
