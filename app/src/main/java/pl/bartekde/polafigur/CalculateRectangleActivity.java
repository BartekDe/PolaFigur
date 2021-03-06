package pl.bartekde.polafigur;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


class Rectangle {

    private double a, b;
    private boolean isValid = false;

    public Rectangle(double a, double b) {
        if(a > 0 && b > 0) {
            isValid = true;
            this.a = a;
            this.b = b;
        } else {
            isValid = false;
        }
    }

    public double area() {
        if(this.isValid) {
            return a * b;
        } else return 0.0;
    }

}


public class CalculateRectangleActivity extends AppCompatActivity {

    private boolean hasCalculated = false;

    public static final String RECTANGLE_RESULT = "Area of rectangle= ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_rectangle);

        Button rectangleCalcButton = findViewById(R.id.rectangleCalcButton);
        final TextView rectangleResultTextView = findViewById(R.id.rectangleResultTextView);

        final Activity thisActivity = this;

        View.OnClickListener listener1 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText aEditText = findViewById(R.id.aEditText);
                EditText bEditText = findViewById(R.id.bEditText);

                if(TextUtils.isEmpty(aEditText.getText()) ||
                        TextUtils.isEmpty(bEditText.getText())) {
                    rectangleResultTextView.setText("Rectangle needs 2 sides!");
                } else {

                    // get the lengths of the rectangle here
                    double a = Double.parseDouble(aEditText.getText().toString());
                    double b = Double.parseDouble(bEditText.getText().toString());

                    Rectangle r = new Rectangle(a, b);
                    double rectangleArea = r.area(); // calculate beforehand to avoid double calculation if the rectangle is valid
                    if (rectangleArea != 0.0) {
                        rectangleResultTextView.setText(Double.toString(rectangleArea));
                        hideSoftKeyboard(thisActivity);
                    } else rectangleResultTextView.setText("Invalid rectangle");

                    hasCalculated = true;
                }
            }
        };

        // add listener to the button that calculates the area of a rectangle
        rectangleCalcButton.setOnClickListener(listener1);

        // calculate the area and go to previous activity
        ((Button) findViewById(R.id.backAndAddButton)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(hasCalculated) {
                            String areaString = ((TextView) findViewById(R.id.rectangleResultTextView)).getText().toString();

                            Intent backIntent = new Intent();

                            backIntent.putExtra(RECTANGLE_RESULT, areaString);

                            setResult(RESULT_OK, backIntent);

                            finish();
                        } else rectangleResultTextView.setText("Calculate your area first!");
                    }
                }
        );


        // rezygnacja
        ((Button) findViewById(R.id.backButton)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent backIntent = new Intent();

                        backIntent.putExtra(RECTANGLE_RESULT, "0.0");

                        setResult(RESULT_OK, backIntent);

                        finish();
                    }
                }
        );

    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
}
