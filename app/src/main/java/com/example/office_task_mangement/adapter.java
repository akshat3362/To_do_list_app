package com.example.office_task_mangement;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class adapter extends FragmentPagerAdapter {
    public adapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                login_page login1 = new login_page();
                return  login1;
            case 1:
                reg_page reg_page = new reg_page();
                return reg_page;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0:
                return  "login";
            case 1:
                return "SignUp";
            default:
                return null;
        }
    }
}
