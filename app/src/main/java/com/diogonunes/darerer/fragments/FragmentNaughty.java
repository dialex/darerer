package com.diogonunes.darerer.fragments;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.diogonunes.darerer.R;
import com.diogonunes.darerer.StringRoulette;

public class FragmentNaughty extends Fragment {
    private static final String LOG_TAG = FragmentNaughty.class.getSimpleName();
    private static StringRoulette _naughtyActsRoulette;

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
        return inflater.inflate(R.layout.fragment_naughty, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        initFragment();
        showWelcomeText();
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
        // Picks a challenge
        String challengeDesc = _naughtyActsRoulette.roll();

        // Displays it
        TextView cardChallengeText = (TextView) getView().findViewById(R.id.card_naughty_challenge_title);
        cardChallengeText.setText(challengeDesc);

        // Allows the user to decide
        showChallengeText();
    }

    // Auxiliary

    private void initActivity() {
        _fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
    }

    private void initFragment() {
        View rootView = getView();
        // Instance variable
        _layoutDefault = (LinearLayout) rootView.findViewById(R.id.layout_naughty_challenge_off);
        _layoutChallenge = (LinearLayout) rootView.findViewById(R.id.layout_naughty_challenge_on);
        _cardChallenge = (CardView) rootView.findViewById(R.id.card_naughty_challenge);

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

        _fab.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_heart_white));
        _fab.setBackgroundTintList(ColorStateList.valueOf(themeColor));
        _cardChallenge.setBackgroundColor(themeColor);
    }
}