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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.diogonunes.darerer.R;
import com.diogonunes.darerer.StringRoulette;
import com.diogonunes.darerer.helpers.Analytics;
import com.diogonunes.darerer.helpers.Utils;

public class FragmentNice extends Fragment {
    private static final String LOG_TAG = FragmentNice.class.getSimpleName();
    private static StringRoulette _niceActionsRoulette, _niceModifiersRoulette, _encouragementsRoulette;

    private View _rootView;
    private FloatingActionButton _fab;
    private CardView _cardChallengeAction, _cardChallengeModifier;
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
        _rootView = inflater.inflate(R.layout.fragment_nice, container, false);
        initFragment();
        return _rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        setTheme();
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
        Analytics.logEvent("Challenge Nice", "Button", R.id.fab);

        // Picks a challenge
        String actionText = _niceActionsRoulette.roll();
        String modifierText = _niceModifiersRoulette.roll();

        // Displays it
        TextView cardActionText = (TextView) _rootView.findViewById(R.id.card_nice_challenge_action_title);
        TextView cardModifierText = (TextView) _rootView.findViewById(R.id.card_nice_challenge_modifier_title);
        cardActionText.setText(actionText.toUpperCase());
        cardModifierText.setText(modifierText.toUpperCase());

        // Allows the user to decide
        setDecision(_rootView, 0);
        showChallengeText();
    }

    public void onClickAcceptChallenge(View view) {
        Analytics.logEvent("Accept Challenge Kind", "Button", R.id.btn_nice_challenge_yes);
        setDecision(view, R.id.btn_nice_challenge_yes);
    }

    public void onClickDenyChallenge(View view) {
        Analytics.logEvent("Deny Challenge Kind", "Button", R.id.btn_nice_challenge_no);
        setDecision(view, R.id.btn_nice_challenge_no);

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

        _layoutDefault = (LinearLayout) _rootView.findViewById(R.id.layout_nice_challenge_off);
        _layoutChallenge = (LinearLayout) _rootView.findViewById(R.id.layout_nice_challenge_on);
        _txtDecision = (TextView) _rootView.findViewById(R.id.txt_nice_challenge_decision);
        _imgDecision = (ImageView) _rootView.findViewById(R.id.img_nice_meme_decision);
        _cardChallengeAction = (CardView) _rootView.findViewById(R.id.card_nice_challenge_action);
        _cardChallengeModifier = (CardView) _rootView.findViewById(R.id.card_nice_challenge_modifier);

        String[] niceActions = resourcesRoot.getStringArray(R.array.nice_challenges_actions);
        _niceActionsRoulette = new StringRoulette(niceActions);

        String[] niceModifiers = resourcesRoot.getStringArray(R.array.nice_challenges_modifiers);
        _niceModifiersRoulette = new StringRoulette(niceModifiers);

        String[] encouragements = resourcesRoot.getStringArray(R.array.encouragements);
        _encouragementsRoulette = new StringRoulette(encouragements);

        initButtonHandlers();
    }

    private void initButtonHandlers() {
        Button btnYes = (Button) _rootView.findViewById(R.id.btn_nice_challenge_yes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                onClickAcceptChallenge(v);
            }
        });

        Button btnNo = (Button) _rootView.findViewById(R.id.btn_nice_challenge_no);
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
            case R.id.btn_nice_challenge_yes:
                decisionText = R.string.challenge_accepted;
                decisionImage = ContextCompat.getDrawable(view.getContext(), R.drawable.img_meme_yes);
                break;
            case R.id.btn_nice_challenge_no:
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
        int themeColor = ContextCompat.getColor(getContext(), R.color.colorNicePrimary);

        if (_fab != null) {
            _fab.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_face_white));
            _fab.setBackgroundTintList(ColorStateList.valueOf(themeColor));
        }
        if (_cardChallengeAction != null)
            _cardChallengeAction.setCardBackgroundColor(themeColor);
        if (_cardChallengeModifier != null)
            _cardChallengeModifier.setCardBackgroundColor(themeColor);
    }
}