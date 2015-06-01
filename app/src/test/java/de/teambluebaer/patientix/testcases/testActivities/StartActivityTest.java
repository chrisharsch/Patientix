package de.teambluebaer.patientix.testcases.testActivities;

import android.widget.Button;
import android.widget.TextView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import de.teambluebaer.patientix.BuildConfig;
import de.teambluebaer.patientix.R;
import de.teambluebaer.patientix.activities.StartActivity;


// import static org.junit.Assert.assertThat;


/**
 * Created by Maren on 29.05.2015.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)

public class StartActivityTest {

    StartActivity startActivity = Robolectric.setupActivity(StartActivity.class);

    private TextView textViewPatientName;
    private TextView textViewPatientBirth;
    private TextView textViewExameName;

    @Test
    public void shouldNotBeNull() {
        //assertThat(startActivity).isNotNull();

        TextView textViewName = (TextView) startActivity.findViewById(R.id.patientname);
        //assertThat(textViewName).isNotNull();

        TextView textViewId = (TextView) startActivity.findViewById(R.id.patientid);
        //assertThat(textViewId).isNotNull();

        TextView textViewExamination = (TextView) startActivity.findViewById(R.id.textExamination);
        //assertThat(textViewName).isNotNull();

        Button buttonStart = (Button) startActivity.findViewById(R.id.startbtn);
        //assertThat(button).isNotNull();

        Button buttonUpdate = (Button) startActivity.findViewById(R.id.startbtn);
        //assertThat(button).isNotNull();
    }
}