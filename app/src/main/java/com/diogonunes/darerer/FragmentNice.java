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

//        _layoutDefault.setVisibility(View.VISIBLE);
//        _layoutChallenge.setVisibility(View.GONE);
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

//    public void onClickAcceptChallenge(View view) {
//        setDecision(view, R.id.btn_challenge_yes);
//    }
//
//    public void onClickDenyChallenge(View view) {
//        setDecision(view, R.id.btn_challenge_no);
//
//        if (Utils.getRandomBool(30)) {
//            Snackbar snackbar = Snackbar
//                    .make(view, _encourageRoulette.roll(), Snackbar.LENGTH_INDEFINITE)
//                    .setAction(R.string.dialog_action_sorry, new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            // close self
//                        }
//                    });
//            snackbar.show();
//        }
//    }

//    private void setDecision(View view, int decision) {
//        int decisionText;
//        Drawable decisionImage;
//
//        switch (decision) {
//            case R.id.btn_challenge_yes:
//                decisionText = R.string.challenge_accepted;
//                decisionImage = ContextCompat.getDrawable(view.getContext(), R.drawable.img_meme_yes);
//                break;
//            case R.id.btn_challenge_no:
//                decisionText = R.string.challenge_denied;
//                decisionImage = ContextCompat.getDrawable(view.getContext(), R.drawable.img_meme_no);
//                break;
//            default:
//                decisionText = R.string.challenge_considered;
//                decisionImage = ContextCompat.getDrawable(view.getContext(), R.drawable.img_meme_maybe);
//                break;
//        }
//
//        //TODO
////        _txtDecision.setText(decisionText);
////        _imgDecision.setImageDrawable(decisionImage);
//    }

    // Auxiliary

    private void initActivity() {
        _fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
    }

    private void initFragment() {
        // Instance variables
//        _layoutDefault = (LinearLayout) getView().findViewById(R.id.layout_challenge_off);
//        _layoutChallenge = (LinearLayout) getView().findViewById(R.id.layout_challenge_on);
//        _txtDecision = (TextView) getView().findViewById(R.id.txt_challenge_decision);
//        _imgDecision = (ImageView) getView().findViewById(R.id.img_meme_decision);

        String[] niceChallenges = getResources().getStringArray(R.array.nice_challenges);
        _niceActsRoulette = new StringRoulette(niceChallenges);

        String[] encouragements = getResources().getStringArray(R.array.encouragements);
        _encouragementsRoulette = new StringRoulette(encouragements);
    }

    private void setTheme() {
        _fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_face_white));
    }
}