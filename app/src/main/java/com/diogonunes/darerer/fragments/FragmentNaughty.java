package com.diogonunes.darerer.fragments;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.diogonunes.darerer.R;
import com.diogonunes.darerer.StringRoulette;
import com.diogonunes.darerer.helpers.Analytics;
import com.diogonunes.darerer.helpers.Utils;
import com.diogonunes.darerer.settings.Constants;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class FragmentNaughty extends Fragment {
    private static final String LOG_TAG = FragmentNaughty.class.getSimpleName();
    private static StringRoulette _naughtyActsRoulette;

    private View _rootView;
    private AdView _adView;
    private FloatingActionButton _fab;
    private CardView _cardChallenge;
    private LinearLayout _layoutDefault, _layoutChallenge;

    public FragmentNaughty() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _rootView = inflater.inflate(R.layout.fragment_naughty, container, false);
        initFragment();
        return _rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        showWelcomeText();
        showAd();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            setTheme();
        }
    }

    // Event Handling

    public void fabOnClick() {
        Analytics.logEvent("Naughty Challenge", "Button", R.id.fab);

        // Displays a challenge
        String challengeDesc = _naughtyActsRoulette.roll();
        TextView cardChallengeText = (TextView) _rootView.findViewById(R.id.card_naughty_challenge_title);
        cardChallengeText.setText(challengeDesc);

        // Allows the user to decide
        showChallengeText();
    }

    // Ads

    private void showAd() {
        if (Utils.hasInternetConnection(getContext())) {
            Log.d(LOG_TAG, "Showing ads (Internet: ON)");
            AdRequest adRequest = new AdRequest.Builder().addTestDevice(Constants.TESTING_DEVICE_ID).build();
            _adView.loadAd(adRequest);
            _adView.setVisibility(View.VISIBLE);
        } else {
            Log.d(LOG_TAG, "Hiding ads (Internet: OFF)");
            _adView.setVisibility(View.GONE);
        }
    }

    // Auxiliary

    private void initActivity() {
        _fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
    }

    private void initFragment() {
        _layoutDefault = (LinearLayout) _rootView.findViewById(R.id.layout_naughty_challenge_off);
        _layoutChallenge = (LinearLayout) _rootView.findViewById(R.id.layout_naughty_challenge_on);
        _cardChallenge = (CardView) _rootView.findViewById(R.id.card_naughty_challenge);
        _adView = (AdView) _rootView.findViewById(R.id.adView_banner_footer);

        String[] naughtyChallenges = getResources().getStringArray(R.array.naughty_challenges);
        _naughtyActsRoulette = new StringRoulette(naughtyChallenges);
    }

    private void showWelcomeText() {
        _layoutDefault.setVisibility(View.VISIBLE);
        _layoutChallenge.setVisibility(View.GONE);
    }

    private void showChallengeText() {
        _layoutDefault.setVisibility(View.GONE);
        _layoutChallenge.setVisibility(View.VISIBLE);
    }

    private void setTheme() {
        int themeColor = ContextCompat.getColor(getContext(), R.color.colorNaughtyPrimary);

        if (_fab != null) {
            _fab.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_heart_white));
            _fab.setBackgroundTintList(ColorStateList.valueOf(themeColor));
        }
        if (_cardChallenge != null)
            _cardChallenge.setBackgroundColor(themeColor);
    }
}