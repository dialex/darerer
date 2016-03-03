package com.diogonunes.darerer.fragments;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.diogonunes.darerer.R;
import com.diogonunes.darerer.StringRoulette;
import com.diogonunes.darerer.Utils;

public class FragmentNice extends Fragment {
    private static final String LOG_TAG = FragmentNice.class.getSimpleName();
    private static StringRoulette _niceActionsRoulette, _niceModifiersRoulette, _encouragementsRoulette;

    private FloatingActionButton _fab;
    private CardView _cardChallenge;
    private TextView _txtDecision;
    private ImageView _imgDecision;
    private LinearLayout _layoutDefault, _layoutChallenge;

    public FragmentNice() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nice, container, false);
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
        Utils.ShowSnackBar(getView(), "TODO nice");
    }

    // Auxiliary

    private void initActivity() {
        _fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
    }

    private void initFragment() {
        Resources resourcesRoot = getResources();

        // Instance variables
//        _layoutDefault = (LinearLayout) getView().findViewById(R.id.layout_nice_challenge_off);
//        _layoutChallenge = (LinearLayout) getView().findViewById(R.id.layout_nice_challenge_on);
//        _txtDecision = (TextView) getView().findViewById(R.id.txt_kind_challenge_decision);
//        _imgDecision = (ImageView) getView().findViewById(R.id.img_kind_meme_decision);

        String[] niceActions = resourcesRoot.getStringArray(R.array.nice_challenges_actions);
        _niceActionsRoulette = new StringRoulette(niceActions);

        String[] niceModifiers = resourcesRoot.getStringArray(R.array.nice_challenges_modifiers);
        _niceModifiersRoulette = new StringRoulette(niceModifiers);

        String[] encouragements = resourcesRoot.getStringArray(R.array.encouragements);
        _encouragementsRoulette = new StringRoulette(encouragements);
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
        int themeColor = ContextCompat.getColor(getContext(), R.color.colorNicePrimary);

        _fab.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_face_white));
        _fab.setBackgroundTintList(ColorStateList.valueOf(themeColor));
        _cardChallenge.setBackgroundColor(themeColor);
    }
}