package com.example.cce;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.android.material.tabs.TabLayout;

import java.io.IOException;

public class BibleFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;

    private final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";
    private SharedPreferencesLibrary spl;
    private Chapter chapter;
    private Menu menu;
    private TextView text;
    private BookDataSource book_data_source;
    private ListView verse_list;
    private VerseListAdapter verse_list_adapter;
    private Button prev, next, index;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            savedInstanceState.putInt(STATE_SELECTED_POSITION, 2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bible, container, false);

        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.pager);

        tabLayout.setupWithViewPager(viewPager);

        BibleAdapter bibleAdapter = new BibleAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        bibleAdapter.addFragments(new BookFragment(), "Book");
        bibleAdapter.addFragments(new ChapterFragment(), "Chapter");
        bibleAdapter.addFragments(new VerseFragment(), "Verse");

        viewPager.setAdapter(bibleAdapter);

        spl = new SharedPreferencesLibrary(getActivity());
        chapter = spl.getBibleIndex();

        if(chapter.getChapter() == 0)
        {
            chapter = new Chapter("KEJADIAN", 1);
            spl.setBibleIndex(chapter);
            spl.setLanguage(false);
        }

        book_data_source = new BookDataSource(getActivity());

        verse_list = (ListView) rootView.findViewById(R.id.listView);
        prev = (Button) rootView.findViewById(R.id.button1);
        next = (Button) rootView.findViewById(R.id.button3);
        index = (Button) rootView.findViewById(R.id.button2);

        index.setText(chapter.toString());

        Typeface font_awesome = Typeface.createFromAsset(getActivity().getAssets(), "fontawesome-webfont.ttf");
        prev.setTypeface(font_awesome);
        next.setTypeface(font_awesome);

        index.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Home();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chapter.getChapter() > 1)
                {
                    spl.setBibleIndex(new Chapter(chapter.getBook_name(), (chapter.getChapter()-1)));
                    Fragment fragment = new Bible();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chapter.getChapter() != book_data_source.getNumChapters(chapter.getBook_name()).size())
                {
                    spl.setBibleIndex(new Chapter(chapter.getBook_name(), (chapter.getChapter()+1)));
                    Fragment fragment = new Bible();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        });

        if(spl.getLanguage())
        {
            if(spl.getVersion())
            {
                ArrayList<String> verse_array = book_data_source.getEnglishVersesNIV(chapter);
                for(int i = 0; i < verse_array.size(); i++)
                {
                    if(verse_array.get(i) == null || verse_array.get(i).equals(""))
                    {
                        verse_array.remove(i);
                    }
                }
                verse_list_adapter = new VerseListAdapter(verse_array, getActivity());
                verse_list.setAdapter(verse_list_adapter);
            }
            else
            {
                ArrayList<String> verse_array = book_data_source.getEnglishVersesESV(chapter);
                for(int i = 0; i < verse_array.size(); i++)
                {
                    if(verse_array.get(i) == null || verse_array.get(i).equals(""))
                    {
                        verse_array.remove(i);
                    }
                }
                verse_list_adapter = new VerseListAdapter(verse_array, getActivity());
                verse_list.setAdapter(verse_list_adapter);
            }
        }
        else
        {
            ArrayList<String> verse_array = book_data_source.getIndonesianVerses(chapter);
            for(int i = 0; i < verse_array.size(); i++)
            {
                if(verse_array.get(i) == null || verse_array.get(i).equals(""))
                {
                    verse_array.remove(i);
                }
            }
            verse_list_adapter = new VerseListAdapter(verse_array, getActivity());
            verse_list.setAdapter(verse_list_adapter);
        }

        final ActionBar actionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();

//        verse_list.setOnScrollListener(new AbsListView.OnScrollListener() {
//
//            private int mLastFirstVisibleItem;
//
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//            }
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount){
//                if (mLastFirstVisibleItem < firstVisibleItem)
//                {
//                    if (actionBar.isShowing())
//                    {
//                        actionBar.hide();
//                    }
//                }
//                if (mLastFirstVisibleItem > firstVisibleItem)
//                {
//                    if (!actionBar.isShowing())
//                    {
//                        actionBar.show();
//                    }
//                }
//                mLastFirstVisibleItem = firstVisibleItem;
//            }
//        });

        return view;

    }
}