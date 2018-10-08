package com.jingnuo.quanmbshop.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class AdapterFragment  extends FragmentPagerAdapter{
    //存放Fragment的数组
    private List<Fragment> fragmentsList;
    private FragmentManager fragmentManager;
    private List<String> tags;

    public AdapterFragment(FragmentManager fm,List<Fragment> fragmentsList) {
        super(fm);
        this.fragmentsList = fragmentsList;
        this.fragmentManager=fm;
        this.tags = new ArrayList<>();
    }
    public void setNewFragments(List<Fragment> fragments) {
        if (this.tags != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            for (int i = 0; i < tags.size(); i++) {
                fragmentTransaction.remove(fragmentManager.findFragmentByTag(tags.get(i)));
            }
            fragmentTransaction.commit();
            fragmentManager.executePendingTransactions();
            tags.clear();
        }
        this.fragmentsList = fragments;
        notifyDataSetChanged();
    }
//    public void setFragments(List<Fragment> fragments) {
//        if(this.fragmentsList != null){
//            FragmentTransaction ft = fragmentManager.beginTransaction();
//            for(Fragment f:this.fragmentsList){
//                ft.remove(f);
//            }
//            ft.commit();
//            ft=null;
//            fragmentManager.executePendingTransactions();
//        }
//        this.fragmentsList = fragments;
//        notifyDataSetChanged();
//    }

    @Override
    public Fragment getItem(int position) {
        return fragmentsList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentsList.size();
    }
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        Fragment fragment = fragmentsList.get(position);
//        fragmentManager.beginTransaction().hide(fragment).commit();
//    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        tags.add(makeFragmentName(container.getId(), getItemId(position)));
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        this.fragmentManager.beginTransaction().show(fragment).commit();
        return fragment;
    }
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
    private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }
}
