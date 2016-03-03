package com.diogonunes.darerer.fragments;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
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

    private FloatingActionButton _fab;
    private CardView _cardChallenge;
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
        String challengeDesc = _kindChallengesRoulette.roll();

        // Displays it
        TextView cardChallenge = (TextView) getView().findViewById(R.id.card_kind_challenge_title);
        cardChallenge.setText(challengeDesc);

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
        initButtonHandlers();

        // Instance variables
        View rootView = getView();
        _layoutDefault = (LinearLayout) rootView.findViewById(R.id.layout_kind_challenge_off);
        _layoutChallenge = (LinearLayout) rootView.findViewById(R.id.layout_kind_challenge_on);
        _cardChallenge = (CardView) rootView.findViewById(R.id.card_kind_challenge);
        _txtDecision = (TextView) rootView.findViewById(R.id.txt_kind_challenge_decision);
        _imgDecision = (ImageView) rootView.findViewById(R.id.img_kind_meme_decision);

        String[] kindChallenges = getResources().getStringArray(R.array.kindness_challenges);
        _kindChallengesRoulette = new StringRoulette(kindChallenges);

        String[] encouragements = getResources().getStringArray(R.array.encouragements);
        _encouragementsRoulette = new StringRoulette(encouragements);
    }

    private void initButtonHandlers() {
        Button btnYes = (Button) getView().findViewById(R.id.btn_kind_challenge_yes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                onClickAcceptChallenge(v);
            }
        });

        Button btnNo = (Button) getView().findViewById(R.id.btn_kind_challenge_no);
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
        int themeColor = ContextCompat.getColor(getContext(), R.color.colorKindPrimary);

        _fab.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_star_white));
        _fab.setBackgroundTintList(ColorStateList.valueOf(themeColor));
        _cardChallenge.setBackgroundColor(themeColor);
    }
}