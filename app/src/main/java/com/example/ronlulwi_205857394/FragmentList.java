package com.example.ronlulwi_205857394;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class FragmentList extends Fragment {

    private MaterialTextView list_LBL_userScore;
    private MaterialButton list_BTN_showOnMap;
    private CallBack_UserProtocol callBack_userProtocol;

    public void setCallBack_userProtocol(CallBack_UserProtocol callBack_userProtocol) {
        this.callBack_userProtocol = callBack_userProtocol;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        findViews(view);
        initViews();

        //changeTitle("List Page");
        return view;
    }

    private void findViews(View view) {
        list_LBL_userScore = view.findViewById(R.id.list_LBL_userScore);
        list_BTN_showOnMap = view.findViewById(R.id.list_BTN_showOnMap);
    }
    private void initViews() {

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user1Clicked();/////////////////////////////
            }
        };
        list_BTN_showOnMap.setOnClickListener(onClickListener);
    }
    private void user1Clicked() {
        if (callBack_userProtocol != null) {
            callBack_userProtocol.displayOnMap();
        }
    }




}


