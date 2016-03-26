package com.diogonunes.darerer.fragments;

import android.app.Notification;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
import android.widget.Toast;

import com.diogonunes.darerer.R;
import com.diogonunes.darerer.StringRoulette;
import com.diogonunes.darerer.helpers.Analytics;
import com.diogonunes.darerer.helpers.Utils;
import com.diogonunes.darerer.settings.Constants;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class FragmentKind extends Fragment {
    private static final String LOG_TAG = FragmentKind.class.getSimpleName();
    private static StringRoulette _kindChallengesRoulette, _encouragementsRoulette;

    private View _rootView;
    private AdView _adView;
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
        return _rootView;
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
        Analytics.logEvent("Challenge Kind", "Button", R.id.fab);

        if (canHaveNewChallenge()) {
            // Displays a new challenge
            String challengeDesc = _kindChallengesRoulette.roll();
            _txtChallenge.setText(challengeDesc);
            // Allows the user to decide
            setDecision(_rootView, 0);
            showChallengeText();
        } else {
            Toast.makeText(getContext(), getResources().getString(R.string.dialog_warning_pendingDecision), Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickAcceptChallenge(View view) {
        Analytics.logEvent("Accept Challenge Kind", "Button", R.id.btn_kind_challenge_yes);
        setDecision(view, R.id.btn_kind_challenge_yes);

        Context rootContext = getContext();
        Notification dare = Utils.createNotification(rootContext, (String) _txtDecision.getText(), (String) _txtChallenge.getText(), R.drawable.ic_face_white, R.color.colorKindPrimary);
        Utils.showNotification(rootContext, dare);
        Toast.makeText(rootContext, R.string.dialog_warning_challengeAccepted, Toast.LENGTH_SHORT).show();
    }

    public void onClickDenyChallenge(View view) {
        Analytics.logEvent("Deny Challenge Kind", "Button", R.id.btn_kind_challenge_no);
        setDecision(view, R.id.btn_kind_challenge_no);

        if (Utils.getRandomBool(30)) {
            Snackbar snackbar = Snackbar
                    .make(view, _encouragementsRoulette.roll(), Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.dialog_action_sorry, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // close itself
                        }
                    });
            snackbar.show();
        }
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

    private boolean canHaveNewChallenge() {
        return (_layoutChallenge.getVisibility() != View.VISIBLE) ||
                ((_txtDecision != null) && (_txtDecision.getText() != getString(R.string.challenge_considered)));
    }

    private void initActivity() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            _fab = (FloatingActionButton) activity.findViewById(R.id.fab);
        } else
            Log.w(LOG_TAG, "initActivity: getActivity() returned null");
    }

    private void initFragment() {
        if (_rootView != null) {
            _layoutDefault = (LinearLayout) _rootView.findViewById(R.id.layout_kind_challenge_off);
            _layoutChallenge = (LinearLayout) _rootView.findViewById(R.id.layout_kind_challenge_on);
            _cardChallenge = (CardView) _rootView.findViewById(R.id.card_kind_challenge);
            _txtChallenge = (TextView) _rootView.findViewById(R.id.card_kind_challenge_title);
            _txtDecision = (TextView) _rootView.findViewById(R.id.txt_kind_challenge_decision);
            _imgDecision = (ImageView) _rootView.findViewById(R.id.img_kind_meme_decision);
            _adView = (AdView) _rootView.findViewById(R.id.adView_banner_footer);
        } else
            Log.d(LOG_TAG, "initFragment: _rootView returned null");

        Resources resourcesRoot = getResources();
        if (resourcesRoot != null) {
            String[] kindChallenges = resourcesRoot.getStringArray(R.array.kindness_challenges);
            _kindChallengesRoulette = new StringRoulette(kindChallenges);

            String[] encouragements = resourcesRoot.getStringArray(R.array.encouragements);
            _encouragementsRoulette = new StringRoulette(encouragements);
        } else
            Log.d(LOG_TAG, "initFragment: getResources() returned null");

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
        int themeColor = ContextCompat.getColor(getContext(), R.color.colorKindPrimary);

        if (_fab != null) {
            _fab.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_star_white));
            _fab.setBackgroundTintList(ColorStateList.valueOf(themeColor));
        }
        if (_cardChallenge != null)
            _cardChallenge.setBackgroundColor(themeColor);
    }
}