package com.diogonunes.darerer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static KindnessChallenger _kindnessChallenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeActivity();
    }

    private void initializeActivity() {
        String[] kindnessChallenges = getResources().getStringArray(R.array.kindness_challenges);
        _kindnessChallenger = new KindnessChallenger(kindnessChallenges);
    }

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

    // Event Handling

    public void onClickFab(View view) {
        // Picks a challenge
        String challengeDesc = _kindnessChallenger.getChallenge();

        // Displays it
        TextView cardChallenge = (TextView) findViewById(R.id.card_title);
        cardChallenge.setText(challengeDesc);

        // Let's the use decide
        LinearLayout layoutChallengeDecision = (LinearLayout) findViewById(R.id.layout_decision);
        layoutChallengeDecision.setVisibility(View.VISIBLE);
    }

    public void onClickAcceptChallenge(View view) {
        TextView txtDecision = (TextView) findViewById(R.id.challenge_decision);
        txtDecision.setText(R.string.challenge_accepted);
    }

    public void onClickDenyChallenge(View view) {
        TextView txtDecision = (TextView) findViewById(R.id.challenge_decision);
        txtDecision.setText(R.string.challenge_denied);
    }

    public void onClickConsiderChallenge(View view) {
        TextView txtDecision = (TextView) findViewById(R.id.challenge_decision);
        txtDecision.setText(R.string.challenge_considered);
    }
}
