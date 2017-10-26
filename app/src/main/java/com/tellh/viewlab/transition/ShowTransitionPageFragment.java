package com.tellh.viewlab.transition;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tellh.viewlab.R;

public class ShowTransitionPageFragment extends Fragment {

    public static ShowTransitionPageFragment newInstance() {
        return new ShowTransitionPageFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_transition_animation, container, false);
        Button btnShow = (Button) view.findViewById(R.id.btn_show_transition);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), ListActivity.class));
            }
        });
        return view;
    }
}