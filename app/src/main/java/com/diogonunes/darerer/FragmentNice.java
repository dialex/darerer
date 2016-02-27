package com.diogonunes.darerer;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FragmentNice extends Fragment {
    private static final String LOG_TAG = FragmentNice.class.getSimpleName();
    private static StringRoulette _niceActsRoulette, _encouragementsRoulette;

    private FloatingActionButton _fab;
    private TextView _txtDecision;
    private ImageView _imgDecision;
    private LinearLayout _layoutDefault, _layoutChallenge;

    public FragmentNice() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nice, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        initActivity();
        setTheme();

        _layoutDefault.setVisibility(View.VISIBLE);
        _layoutChallenge.setVisibility(View.GONE);
    }

    // Events

    public void fabOnClick() {
        Utils.ShowSnackBar(getView(), "Nice button");
    }

    // Auxiliary

    private void initActivity() {
        // Instance variables
        _layoutDefault = (LinearLayout) getView().findViewById(R.id.layout_challenge_off);
        _layoutChallenge = (LinearLayout) getView().findViewById(R.id.layout_challenge_on);
        _txtDecision = (TextView) getView().findViewById(R.id.txt_challenge_decision);
        _imgDecision = (ImageView) getView().findViewById(R.id.img_meme_decision);
        _fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        String[] niceChallenges = getResources().getStringArray(R.array.nice_challenges);
        _niceActsRoulette = new StringRoulette(niceChallenges);

        String[] encouragements = getResources().getStringArray(R.array.encouragements);
        _encouragementsRoulette = new StringRoulette(encouragements);
    }

    private void setTheme() {
        _fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_white));
    }
}