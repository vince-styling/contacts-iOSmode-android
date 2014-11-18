package com.vincestyling.contacts_mono_android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements ViewPager.OnPageChangeListener {
    private List<Character> alphaList = new ArrayList<Character>();
    private List<Contact> contactList = new ArrayList<Contact>();

    private ViewPager alphaContentPager, mainContentPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        generateDummyData();

        alphaContentPager = (ViewPager) findViewById(R.id.alphaContentPager);
        alphaContentPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Fragment frag = new SimpleFragment();
                Bundle bundle = new Bundle();
                bundle.putString(SimpleFragment.DATA, alphaList.get(position).toString());
                frag.setArguments(bundle);
                return frag;
            }

            @Override
            public int getCount() {
                return alphaList.size();
            }
        });

        mainContentPager = (ViewPager) findViewById(R.id.mainContentPager);
        mainContentPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Fragment frag = new SimpleFragment();
                Bundle bundle = new Bundle();
                bundle.putString(SimpleFragment.DATA, contactList.get(position).name);
                frag.setArguments(bundle);
                return frag;
            }

            @Override
            public int getCount() {
                return contactList.size();
            }
        });
        mainContentPager.setOnPageChangeListener(this);

        findViewById(R.id.btnPreviousPage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currIndex = mainContentPager.getCurrentItem();
                if (--currIndex >= 0) mainContentPager.setCurrentItem(currIndex);
            }
        });

        findViewById(R.id.btnNextPage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currIndex = mainContentPager.getCurrentItem();
                if (++currIndex < contactList.size()) mainContentPager.setCurrentItem(currIndex);
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        Log.e("", String.format("pos : %d, Offset : %f, Pixels : %d", position, positionOffset, positionOffsetPixels));
        int comingPos = position;
        boolean isForward = true;
        if (position < selectedPos) {
            isForward = false;
        } else if (positionOffset != 0f) {
            comingPos++;
        }

        int alphaCurrPos = alphaContentPager.getCurrentItem();
        char currChar = alphaList.get(alphaCurrPos);
        if (comingPos < contactList.size() && currChar != contactList.get(comingPos).alpha) {
            if (isForward) {
                if (positionOffset == 0f) alphaCurrPos++;
            } else alphaCurrPos--;
            float left = (alphaCurrPos + positionOffset) * alphaContentPager.getWidth();
            Log.e("", String.format("pos : %d, offset : %f", alphaCurrPos, positionOffset));
            alphaContentPager.scrollTo((int) left, 0);
        }

        // settling the position at the end.
        if (positionOffset == 0f) {
            int index = alphaList.indexOf(contactList.get(position).alpha);
            float left = index * alphaContentPager.getWidth();
            alphaContentPager.scrollTo((int) left, 0);
        }
    }

    private int selectedPos;

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            selectedPos = mainContentPager.getCurrentItem();
            Contact contact = contactList.get(selectedPos);
            int index = alphaList.indexOf(contact.alpha);
            alphaContentPager.setCurrentItem(index, false);
            Log.e("", String.format("alphaContentPager selected : %d", index));
        }
    }

    @Override
    public void onPageSelected(int position) {
    }

    private class Contact {
        char alpha;
        String name;
        private Contact(char alpha, String name) {
            this.alpha = alpha;
            this.name = name;
        }
    }

    public void generateDummyData() {
        char cr = 'A';
        alphaList.add(cr);
        contactList.add(new Contact(cr, "Ada"));
        contactList.add(new Contact(cr, "Amy"));
        contactList.add(new Contact(cr, "Anderson"));

        cr = 'B';
        alphaList.add(cr);
        contactList.add(new Contact(cr, "Ben"));
        contactList.add(new Contact(cr, "Bill"));
        contactList.add(new Contact(cr, "Bryant"));

        cr = 'C';
        alphaList.add(cr);
        contactList.add(new Contact(cr, "Chris"));

        cr = 'D';
        alphaList.add(cr);
        contactList.add(new Contact(cr, "David"));

        cr = 'F';
        alphaList.add(cr);
        contactList.add(new Contact(cr, "Foye"));

        cr = 'G';
        alphaList.add(cr);
        contactList.add(new Contact(cr, "Garnett"));

        cr = 'H';
        alphaList.add(cr);
        contactList.add(new Contact(cr, "Hill"));
        contactList.add(new Contact(cr, "Hayes"));
        contactList.add(new Contact(cr, "Howard"));
        contactList.add(new Contact(cr, "Hamilton"));
    }
}
