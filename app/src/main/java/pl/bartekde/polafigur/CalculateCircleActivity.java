package pl.bartekde.polafigur;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

class Circle {
    private double radius;
    private boolean isValid = false;

    public Circle(double _radius) {
        if(_radius > 0) {
            radius = _radius;
            isValid = true;
        } else isValid = false;
    }

    public double area() {
        if(isValid) return Math.PI * radius * radius;
        else return 0.0;
    }
}

public class CalculateCircleActivity extends AppCompatActivity {

    public static final String CIRCLE_RESULT = "Area of circle= ";

    private Button circleCalcButton;
    private TextView circleResultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_circle);

        circleCalcButton = findViewById(R.id.circleCalcButton);
        circleResultTextView = findViewById(R.id.circleResultTextView);

        View.OnClickListener listener1 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get the lengths of the triangle here
                double r = Double.parseDouble(((EditText)findViewById(R.id.rEditText)).getText().toString());

                Circle circle = new Circle(r);
                double circleArea = circle.area(); // calculate beforehand to avoid double calculation if the triangle is valid
                if(circleArea != 0.0) {
                    circleResultTextView.setText(Double.toString(circleArea));
                } else circleResultTextView.setText("Invalid circle");

            }
        };

        // add listener to the button that calculates the area of a triangle
        circleCalcButton.setOnClickListener(listener1);

        // calculate the area and go to previous activity
        ((Button) findViewById(R.id.backAndAddButton)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String areaString = ((TextView)findViewById(R.id.circleResultTextView)).getText().toString();

                        Intent backIntent = new Intent();

                        backIntent.putExtra(CIRCLE_RESULT, areaString);

                        setResult(RESULT_OK, backIntent);

                        finish();
                    }
                }
        );


        // rezygnacja
        ((Button) findViewById(R.id.backButton)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent backIntent = new Intent();

                        backIntent.putExtra(CIRCLE_RESULT, "0.0");

                        setResult(RESULT_OK, backIntent);

                        finish();
                    }
                }
        );

    }
}
