package AddSpousePagetester;

/**
 * Created by gagan on 5/8/16.
 */
import android.test.ActivityInstrumentationTestCase2;

import com.example.group26.coupletones.AddSpousePage;

public class JUnit_test extends ActivityInstrumentationTestCase2<AddSpousePage> {

    AddSpousePage addSpousePage;

    public JUnit_test(){
        super(AddSpousePage.class);
    }
    public void test_first(){
        addSpousePage = getActivity();

    }

}
