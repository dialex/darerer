package com.diogonunes.darerer.fragments;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.diogonunes.darerer.R;
import com.diogonunes.darerer.StringRoulette;
import com.diogonunes.darerer.Utils;

public class FragmentKind extends Fragment {
    private static final String LOG_TAG = FragmentKind.class.getSimpleName();
    private static StringRoulette _kindActsRoulette, _encouragementsRoulette;

    private FloatingActionButton _fab;
    private TextView _txtDecision;
    private ImageView _imgDecision;
    private LinearLayout _layoutDefault, _layoutChallenge;

    public FragmentKind() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_kind, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        initFragment();
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
        Utils.ShowSnackBar(getView(), "TODO Kind button");
    }

    // Auxiliary

    private void initActivity() {
        _fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
    }

    private void initFragment() {
        // Instance variables
        _layoutDefault = (LinearLayout) getView().findViewById(R.id.layout_challenge_off);
        _layoutChallenge = (LinearLayout) getView().findViewById(R.id.layout_challenge_on);
//        _txtDecision = (TextView) getView().findViewById(R.id.txt_challenge_decision);
//        _imgDecision = (ImageView) getView().findViewById(R.id.img_meme_decision);

        String[] kindChallenges = getResources().getStringArray(R.array.kindness_challenges);
        _kindActsRoulette = new StringRoulette(kindChallenges);

        String[] encouragements = getResources().getStringArray(R.array.encouragements);
        _encouragementsRoulette = new StringRoulette(encouragements);
    }

    private void setTheme() {
        _fab.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_star_white));
        _fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorKindPrimary)));
    }
}