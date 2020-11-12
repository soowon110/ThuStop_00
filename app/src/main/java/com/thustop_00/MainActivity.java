package com.thustop_00;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.thustop_00.databinding.ActivityMainBinding;
import com.thustop_00.intro.IntroBaseFragment;

import java.util.Objects;


/*
 * Copyright (c) 2019 by ThusTop INC. all rights reserved
 * - Project name : ThuStop
 * - First written by Minseok Chae and Suwon Lee students of SungKyunKwan Univ, College of
 *   Information and Communication Engineering(ICE) and developers of ThusTop INC
 * - Created on 2020.07.28 / Last revision on 0000.00.00
 * - Version Beta 00
 * - Description : Android application for TheStop beta service.
 * - Update history(Please enter when other developer modifies this project.)
 *   - 20.07.28. Created as version Beta 00
 */


public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {
    /* Bind activity_main as variable*/
    private ActivityMainBinding binding;
    private Menu menu;
    private Toolbar toolbar;
    private ActionBar actionbar;
    /* Static variable indicates back button is available*/
    public static boolean isBackEnabled = false;
    public static boolean isExitEnabled = false;
    /* Handler for delay of splash fragment. It should be removed after loading delay added*/
    Handler H = new Handler(Looper.getMainLooper());
    //for checking first run
    public SharedPreferences prefs;
    private onBackPressedListener BackListener;


    //private long pressedTime = 0;
    public interface onBackPressedListener {
        void onBack();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* Link activity_main as binding, with doing setContentView(R.layout.activity_main);*/
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivityMain(this);
        prefs = getSharedPreferences("Pref", MODE_PRIVATE);  // check first run
        /* Setting toolbar referred  https://blog.naver.com/qbxlvnf11/221328098468*/
        toolbar = binding.tbMain;
        setSupportActionBar(toolbar);
        actionbar = getSupportActionBar();
        assert actionbar != null; // To prevent warning from setDisplayShowTitleEnabled
        actionbar.setDisplayShowTitleEnabled(false);
        actionbar.setDisplayHomeAsUpEnabled(false);
        toolbar.setNavigationIcon(R.drawable.icon_hamburger_white);
        /* At start, display splash fragment during loading*/
        actionbar.hide();
        setFragment(SplashFragment.newInstance());

        checkFirstRun();

    }

    /* Toolbar hamburger button click listener*/
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                if (isBackEnabled) onBackPressed();
                else openDrawer();
                return true;
            }
            case R.id.bt_notification: {
                Toast.makeText(getApplicationContext(), "알림버튼 눌림", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /*back button listener*/
    public void setOnBackPressedListener(onBackPressedListener listener) {
        BackListener = listener;
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            closeDrawer();
        } else if (BackListener != null) {
            BackListener.onBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        this.menu = menu;
        return true;
    }

    public void onNavigationExitClick(View view) {
        closeDrawer();
    }

    public void onLoginClick(View view) {
        addFragment(LoginFragment.newInstance());
        closeDrawer();
    }
    public void onNavPersonalHistoryClick(View view) {
        closeDrawer();
        addFragment(NavPersonalHistoryFragment.newInstance());
    }
    public void onNavPersonalPointClick(View view) {
        closeDrawer();
        addFragment(NavPointFragment.newInstance());
    }

    public void testToolbarLogin(View view) {
        binding.clDrawerHeadGuest.setVisibility(View.GONE);
        binding.clDrawerHeadMember.setVisibility(View.VISIBLE);
        binding.clMyPage.setVisibility(View.VISIBLE);
        binding.btLogout.setVisibility(View.VISIBLE);
        binding.tvLogout.setVisibility(View.VISIBLE);
    }

    public void checkFirstRun() {
        boolean isFirstRun = prefs.getBoolean("isFirstRun", true);
        if (isFirstRun) {
            prefs.edit().putBoolean("isFirstRun", false).apply();
            H.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setFragment(IntroBaseFragment.newInstance());
                }
            }, 1000);

        } else {
            H.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setFragment(MainFragment.newInstance());
                }
            }, 1000);
        }

    }

    /***********************************************************************************************
    ----------------------OnFragmentInteractionLister override 메소드들------------------------------
    ***********************************************************************************************/


    /*쌓여있는 BackStack 모두 날리고 fragment 바꿀때*/
    @Override
    public void setFragment(FragmentBase fr) {
        try {
            FragmentManager fm = getSupportFragmentManager();
            fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fm.beginTransaction()
                    .replace(R.id.fr_main, fr)
                    .commit();
        } catch (IllegalStateException ignore) {
        }
    }

    /*BackStack 쌓으면서 fragment 바꿀 때*/
    @Override
    public void addFragment(FragmentBase fr) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fr_main, fr)
                .addToBackStack(null)
                .commit();
    }

    /*BackStack 쌓지 않고 fragment 바꿀 때(돌아올 필요가 없는 fragment 갈 때*/
    @Override
    public void addFragmentNotBackStack(FragmentBase fr) {
        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fr_main, fr)
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void openDrawer() {
        binding.drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void closeDrawer() {
        binding.drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void lockDrawer(boolean isLocked) {
        if (isLocked) {
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else {
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    /**
     * setToolbarStyle 메소드
     * - toolbarState : 이름 그대로 _listener에 정의된 변수 이용하여 하면 됨
     * - title : 공백 시 없음, null 시 메인 타이틀, 입력하면 그대로 제목이 됨
     * */
    @Override
    public void setToolbarStyle(int toolbarState, String title){
        //제목을 설정하는 부분 null일 시 메인 타이틀
        if (title == null){
            findViewById(R.id.iv_title).setVisibility(View.VISIBLE);
            findViewById(R.id.tv_title).setVisibility(View.GONE);
        } else {
            findViewById(R.id.iv_title).setVisibility(View.GONE);
            findViewById(R.id.tv_title).setVisibility(View.VISIBLE);
            binding.tvTitle.setText(title);
        }
        //툴바 설정하는 부분
        isExitEnabled = false;
        isBackEnabled = false;
        actionbar.show();
        switch (toolbarState){
            case INVISIBLE:
                actionbar.hide();
            case GREEN_HAMBURGER:
                toolbar.setBackground(getDrawable(R.color.Primary));
                binding.tvTitle.setTextColor(getResources().getColor(R.color.White));
                toolbar.setNavigationIcon(R.drawable.icon_hamburger_white);
                menu.getItem(0).setIcon(R.drawable.icon_notification_white);
                menu.getItem(0).setEnabled(true);
                menu.getItem(0).setVisible(true);
                break;
            case GREEN_BACK:
                toolbar.setBackground(getDrawable(R.color.Primary));
                binding.tvTitle.setTextColor(getResources().getColor(R.color.White));
                toolbar.setNavigationIcon(R.drawable.icon_back_white);
                menu.getItem(0).setEnabled(false);
                menu.getItem(0).setVisible(false);
                isBackEnabled = true;
                break;
            case GREEN_BACK_EXIT:
                toolbar.setBackground(getDrawable(R.color.Primary));
                binding.tvTitle.setTextColor(getResources().getColor(R.color.White));
                toolbar.setNavigationIcon(R.drawable.icon_back_white);
                menu.getItem(0).setIcon(R.drawable.icon_exit_white);
                menu.getItem(0).setEnabled(true);
                menu.getItem(0).setVisible(true);
                isBackEnabled = true;
                isExitEnabled = true;
                break;
            case WHITE_HAMBURGER:
                toolbar.setBackground(getDrawable(R.color.White));
                binding.tvTitle.setTextColor(getResources().getColor(R.color.Primary));
                toolbar.setNavigationIcon(R.drawable.icon_hamburger_green);
                menu.getItem(0).setIcon(R.drawable.icon_notification_green);
                menu.getItem(0).setEnabled(true);
                menu.getItem(0).setVisible(true);
                break;
            case WHITE_BACK:
                toolbar.setBackground(getDrawable(R.color.White));
                binding.tvTitle.setTextColor(getResources().getColor(R.color.Primary));
                toolbar.setNavigationIcon(R.drawable.icon_back_green);
                menu.getItem(0).setEnabled(false);
                menu.getItem(0).setVisible(false);
                isBackEnabled = true;
                break;
            case WHITE_BACK_EXIT:
                toolbar.setBackground(getDrawable(R.color.White));
                binding.tvTitle.setTextColor(getResources().getColor(R.color.Primary));
                toolbar.setNavigationIcon(R.drawable.icon_back_green);
                menu.getItem(0).setIcon(R.drawable.icon_exit);
                menu.getItem(0).setEnabled(true);
                menu.getItem(0).setVisible(true);
                isBackEnabled = true;
                isExitEnabled = true;
                break;

        }
    }

    @Override
    public void hideKeyBoard() {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }


//해시키 필요할 때
/*    private void getAppKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.e("Hash key", something);
            }
        } catch (Exception e) {
            Log.e("name not found", e.toString());
        }
    }*/
}