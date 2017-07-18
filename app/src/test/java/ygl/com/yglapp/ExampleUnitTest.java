package ygl.com.yglapp;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {

    private static final String FAKE_STRING = "HELLO WORLD";

    @Mock
    Context mMockContext;

    @Mock
    Button testButton;

    @Mock
    View.OnClickListener clickListener;

    @Test
    public void addition_isCorrect() throws Exception {

        testButton = new Button(mMockContext);
        //testButton.setOnClickListener(clickListener);


        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                when(mMockContext.getString(R.string.good_luck))
                        .thenReturn(FAKE_STRING );
            }
        });

        testButton.performClick();

    }
}