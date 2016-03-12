package com.diogonunes.darerer.fragments;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.diogonunes.darerer.R;
import com.diogonunes.darerer.StringRoulette;
import com.diogonunes.darerer.Utils;

public class FragmentKind extends Fragment {
    private static final String LOG_TAG = FragmentKind.class.getSimpleName();
    private static StringRoulette _kindChallengesRoulette, _encouragementsRoulette;

    private View _rootView;
    private FloatingActionButton _fab;
    private CardView _cardChallenge;
    private TextView _txtChallenge, _txtDecision;
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
        _rootView = inflater.inflate(R.layout.fragment_kind, container, false);
        initFragment();

        if ((savedInstanceState != null) && (savedInstanceState.getInt("currentTabIndex") == 0)) {
            boolean isChallengeOn = savedInstanceState.getBoolean("isChallengeOn");
            String challengeText = savedInstanceState.getString("challengeAction");

            if (isChallengeOn) {
                _txtChallenge.setText(challengeText);
                setDecision(getView(), 0);
                showChallengeText();
            } else {
                showWelcomeText();
            }
        }
        return _rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        showWelcomeText();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            setTheme();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("currentTabIndex", 0);
        outState.putBoolean("isChallengeOn", _layoutChallenge.getVisibility() == View.VISIBLE);
        outState.putString("challengeAction", (String) ((TextView) _rootView.findViewById(R.id.card_kind_challenge_title)).getText());
        outState.putString("challengeModifier", "");
    }

    // Event Handling

    public void fabOnClick() {
        // Picks a challenge & Display it
        String challengeDesc = _kindChallengesRoulette.roll();
        _txtChallenge.setText(challengeDesc);

        // Allows the user to decide
        setDecision(getView(), 0);
        showChallengeText();
    }

    public void onClickAcceptChallenge(View view) {
        setDecision(view, R.id.btn_kind_challenge_yes);
    }

    public void onClickDenyChallenge(View view) {
        setDecision(view, R.id.btn_kind_challenge_no);

        if (Utils.getRandomBool(30)) {
            Snackbar snackbar = Snackbar
                    .make(view, _encouragementsRoulette.roll(), Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.dialog_action_sorry, new View.OnClickListener() {
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
        _fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
    }

    private void initFragment() {
        Resources resourcesRoot = getResources();

        _layoutDefault = (LinearLayout) _rootView.findViewById(R.id.layout_kind_challenge_off);
        _layoutChallenge = (LinearLayout) _rootView.findViewById(R.id.layout_kind_challenge_on);
        _cardChallenge = (CardView) _rootView.findViewById(R.id.card_kind_challenge);
        _txtChallenge = (TextView) _rootView.findViewById(R.id.card_kind_challenge_title);
        _txtDecision = (TextView) _rootView.findViewById(R.id.txt_kind_challenge_decision);
        _imgDecision = (ImageView) _rootView.findViewById(R.id.img_kind_meme_decision);

        String[] kindChallenges = resourcesRoot.getStringArray(R.array.kindness_challenges);
        _kindChallengesRoulette = new StringRoulette(kindChallenges);

        String[] encouragements = resourcesRoot.getStringArray(R.array.encouragements);
        _encouragementsRoulette = new StringRoulette(encouragements);

        initButtonHandlers();
    }

    private void initButtonHandlers() {
        Button btnYes = (Button) _rootView.findViewById(R.id.btn_kind_challenge_yes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                onClickAcceptChallenge(v);
            }
        });

        Button btnNo = (Button) _rootView.findViewById(R.id.btn_kind_challenge_no);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                onClickDenyChallenge(v);
            }
        });
    }

    private void showWelcomeText() {
        _layoutDefault.setVisibility(View.VISIBLE);
        _layoutChallenge.setVisibility(View.GONE);
    }

    private void showChallengeText() {
        _layoutDefault.setVisibility(View.GONE);
        _layoutChallenge.setVisibility(View.VISIBLE);
    }

    private void setDecision(View view, int decision) {
        int decisionText;
        Drawable decisionImage;

        switch (decision) {
            case R.id.btn_kind_challenge_yes:
                decisionText = R.string.challenge_accepted;
                decisionImage = ContextCompat.getDrawable(view.getContext(), R.drawable.img_meme_yes);
                break;
            case R.id.btn_kind_challenge_no:
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

    private void setTheme() {
        Log.d(LOG_TAG, "Updating theme");
        int themeColor = ContextCompat.getColor(getContext(), R.color.colorKindPrimary);

        if (_fab != null) {
            _fab.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_star_white));
            _fab.setBackgroundTintList(ColorStateList.valueOf(themeColor));
        }
        if (_cardChallenge != null)
            _cardChallenge.setBackgroundColor(themeColor);
    }
}