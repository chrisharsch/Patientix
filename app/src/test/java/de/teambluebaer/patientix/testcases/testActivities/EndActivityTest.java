package de.teambluebaer.patientix.testcases.testActivities;

import android.content.Intent;
import android.widget.TextView;

import de.teambluebaer.patientix.activities.EndActivity;

/**
 * Created by Maren on 29.05.2015.
 */
public class EndActivityTest extends android.test.ActivityUnitTestCase<EndActivity> {

    private String text;
    private EndActivity endactivity;

    /**
     * Constructor for test class
     */
    public EndActivityTest() {
        super(EndActivity.class);
    }

    /**
     *
     * @throws Exception
     */
    public void setUp() throws Exception {
        super.setUp();
        Intent intent = new Intent(getInstrumentation().getTargetContext(), EndActivity.class);
        startActivity(intent, null, null);
        endactivity = getActivity();
    }

    /*      Für Button aber nicht Textfeld (hat aber onClick)
    public void testLayout() {
        text = de.teambluebaer.patientix.activities.EndActivity.R.id.textEnd;
        assertNotNull(endactivity.findViewById(text));
        TextView view = (TextView) endactivity.findViewById(text);
        assertEquals("Incorrect label of the button", "Start", view.getText());
    }   */

    public void tearDown() throws Exception {

    }

    public void testOnCreate() throws Exception {

    }

    public void testOnBackPressed() throws Exception {

    }
}