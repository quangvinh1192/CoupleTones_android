package loginTest;

import android.test.ActivityInstrumentationTestCase2;

import com.example.group26.coupletones.loginPage;


/**
 * Created by gagan on 5/8/16.
 */
public class JUnit_test extends ActivityInstrumentationTestCase2<loginPage> {
    loginPage LoginPage;
    public JUnit_test(){
        super(loginPage.class);
    }
    public void test_first(){
        LoginPage = getActivity();
    }
}
