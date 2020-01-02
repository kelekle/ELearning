package com.star.e_learning;

import androidx.test.rule.ActivityTestRule;

import com.star.e_learning.ui.activity.LoginActivity;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void wrongEmail() {
        onView(withId(R.id.et_login_username)).perform(typeText("123456"));
        onView(withId(R.id.et_login_pwd)).perform(typeText("12345abcde"));
        onView(withId(R.id.bt_login_submit)).perform(click());
        onView(withText("请输入正确的邮箱"))
                .inRoot(withDecorView(not(is(mActivityRule
                        .getActivity()
                        .getWindow()
                        .getDecorView()))))
                .check(matches(isDisplayed()));
    }

    @Test
    public void wrongPassword() {
        onView(withId(R.id.et_login_username)).perform(typeText("123456@qq.com"));
        onView(withId(R.id.et_login_pwd)).perform(typeText("12345"));
        onView(withId(R.id.bt_login_submit)).perform(click());
        onView(withText("请输入6-12位密码"))
                .inRoot(withDecorView(not(is(mActivityRule
                        .getActivity()
                        .getWindow()
                        .getDecorView()))))
                .check(matches(isDisplayed()));
    }

}
