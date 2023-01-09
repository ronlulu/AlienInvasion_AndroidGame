package com.example.ronlulwi_205857394;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.button.MaterialButton;

public class FragmentList extends Fragment {

    private TextView[] scoresDisplay ;
    private MaterialButton[] showDisplay;
    private CallBack_UserProtocol callBack_userProtocol;
    private ScoreList scoreList;
    private ActivityScore activityScore;


    public void setCallBack_userProtocol(CallBack_UserProtocol callBack_userProtocol) {
        this.callBack_userProtocol = callBack_userProtocol;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        scoreList = activityScore.getScoreList();
        findViews(view);
        initViews();
        return view;
    }

    private void findViews(View view) {
        scoresDisplay = new TextView[]{
                view.findViewById(R.id.list_LBL_score0),
                view.findViewById(R.id.list_LBL_score1),
                view.findViewById(R.id.list_LBL_score2),
                view.findViewById(R.id.list_LBL_score3),
                view.findViewById(R.id.list_LBL_score4),
                view.findViewById(R.id.list_LBL_score5),
                view.findViewById(R.id.list_LBL_score6),
                view.findViewById(R.id.list_LBL_score7),
                view.findViewById(R.id.list_LBL_score8),
                view.findViewById(R.id.list_LBL_score9),
        };
        showDisplay = new MaterialButton[]{
                view.findViewById(R.id.list_BTN_show0),
                view.findViewById(R.id.list_BTN_show1),
                view.findViewById(R.id.list_BTN_show2),
                view.findViewById(R.id.list_BTN_show3),
                view.findViewById(R.id.list_BTN_show4),
                view.findViewById(R.id.list_BTN_show5),
                view.findViewById(R.id.list_BTN_show6),
                view.findViewById(R.id.list_BTN_show7),
                view.findViewById(R.id.list_BTN_show8),
                view.findViewById(R.id.list_BTN_show9),
        };

        }

    private void initViews() {
        for (int i = 0; i < scoreList.getScores().size(); i++)
            scoresDisplay[i].setText("Score: " + scoreList.getScores().get(i).getScore());

        View.OnClickListener onClickListener0 = view -> user1Clicked(0);
        View.OnClickListener onClickListener1 = view -> user1Clicked(1);
        View.OnClickListener onClickListener2 = view -> user1Clicked(2);
        View.OnClickListener onClickListener3 = view -> user1Clicked(3);
        View.OnClickListener onClickListener4 = view -> user1Clicked(4);
        View.OnClickListener onClickListener5 = view -> user1Clicked(5);
        View.OnClickListener onClickListener6 = view -> user1Clicked(6);
        View.OnClickListener onClickListener7 = view -> user1Clicked(7);
        View.OnClickListener onClickListener8 = view -> user1Clicked(8);
        View.OnClickListener onClickListener9 = view -> user1Clicked(9);
        showDisplay[0].setOnClickListener(onClickListener0);
        showDisplay[1].setOnClickListener(onClickListener1);
        showDisplay[2].setOnClickListener(onClickListener2);
        showDisplay[3].setOnClickListener(onClickListener3);
        showDisplay[4].setOnClickListener(onClickListener4);
        showDisplay[5].setOnClickListener(onClickListener5);
        showDisplay[6].setOnClickListener(onClickListener6);
        showDisplay[7].setOnClickListener(onClickListener7);
        showDisplay[8].setOnClickListener(onClickListener8);
        showDisplay[9].setOnClickListener(onClickListener9);
        for (int i = scoreList.getScores().size(); i < 10; i++) {
            showDisplay[i].setVisibility(View.INVISIBLE);
            scoresDisplay[i].setVisibility(View.INVISIBLE);
        }
    }


    private void user1Clicked(int index) {
        if (callBack_userProtocol != null) {
            callBack_userProtocol.displayOnMap(index);
        }
    }


    public void setActivity(ActivityScore activityScore){
        this.activityScore = activityScore;
    }

}


