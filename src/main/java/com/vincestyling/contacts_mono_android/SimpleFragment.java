package com.vincestyling.contacts_mono_android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;

public class SimpleFragment extends Fragment {
    public static final String DATA = "data";
    private int[] colors = {
            0xffff4444, 0xffffbb33, 0xffbebebe, 0xff323232, 0xff669900, 0xff0099cc,
            0xff99cc00, 0xffaa66cc, 0xffff8800, 0xff006400, 0xff778899, 0xff191970,
    };
    private String text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        text = savedInstanceState != null ? savedInstanceState.getString(DATA) : getArguments().getString(DATA);

        TextView txvText = ((TextView) view.findViewById(R.id.txvText));
        txvText.setText(text);
        txvText.setBackgroundColor(shuffleArray());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(DATA, text);
    }

    private int shuffleArray() {
        Random rnd = new Random();
        for (int i = colors.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            int a = colors[index];
            colors[index] = colors[i];
            colors[i] = a;
        }
        return colors[0];
    }
}
