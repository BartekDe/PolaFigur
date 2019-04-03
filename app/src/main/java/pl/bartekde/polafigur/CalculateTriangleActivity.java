package pl.bartekde.polafigur;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

class Triangle {

    private double a, b, c;
    private double alpha, beta, gamma;
    private boolean isValid = false;
    private boolean validAngles = false;

    public Triangle(double a, double b, double c) {
        if((a > 0 && b > 0 && c > 0) && (a + b > c && a + c > b && b + c > a)) {
            isValid = true;
            this.a = a;
            this.b = b;
            this.c = c;
        } else {
            isValid = false;
        }
    }

    public Triangle(double alpha, double beta) {
        if(alpha > 0 && beta > 0 && alpha < 180 && beta < 180) {
            validAngles = true;
            this.alpha = alpha;
            this.beta = beta;
        } else {
            alpha = 0.0;
            beta = 0.0;
            gamma = 0.0;
            validAngles = false;
        }
    }

    public double area() {
        if(this.isValid) {
            double p = (a + b + c) / 2.0;
            double w = (p - a) * (p - b) * (p - c) * p;
            return Math.sqrt(w); // wymaga sprawdzenia
        } else return 0.0;
    }

    public double getGamma() {
        gamma = 180 - alpha - beta;
        return gamma;
    }

}

public class CalculateTriangleActivity extends AppCompatActivity {

    Button triangleCalcButton;

    TextView triangleResultTextView;

    public final static String TRIANGLE_RESULT = "Area of triangle= ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_triangle);

        triangleCalcButton = findViewById(R.id.triangleCalcButton);
        triangleResultTextView = findViewById(R.id.triangleResultTextView);

        View.OnClickListener listener1 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get the lengths of the triangle here
                double a = Double.parseDouble(((EditText)findViewById(R.id.aEditText)).getText().toString());
                double b = Double.parseDouble(((EditText)findViewById(R.id.bEditText)).getText().toString());
                double c = Double.parseDouble(((EditText)findViewById(R.id.cEditText)).getText().toString());

                Triangle t = new Triangle(a, b, c);
                double triangleArea = t.area(); // calculate beforehand to avoid double calculation if the triangle is valid
                if(triangleArea != 0.0) {
                    triangleResultTextView.setText(Double.toString(triangleArea));
                } else triangleResultTextView.setText("Invalid triangle!");

            }
        };

        // add listener to the button that calculates the area of a triangle
        triangleCalcButton.setOnClickListener(listener1);

        // add second way to calculate the area - with angles of the triangle
        findViewById(R.id.triangleAnglesCalcButton)
                .setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // get two of three triangle angles
                                double alpha = Double.parseDouble(((EditText)findViewById(R.id.alfaEditText)).getText().toString());
                                double beta = Double.parseDouble(((EditText)findViewById(R.id.betaEditText)).getText().toString());

                                Triangle t = new Triangle(alpha, beta);
                                double gammaResult = t.getGamma();
                                if(gammaResult > 0) {
                                    ((TextView)findViewById(R.id.gammaResult)).setText(Double.toString(gammaResult));
                                } else ((TextView)findViewById(R.id.gammaResult)).setText("Invalid angles!");
                            }
                        }


                );

        // calculate the area and go to previous activity
        ((Button) findViewById(R.id.backAndAddButton)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String areaString = ((TextView)findViewById(R.id.triangleResultTextView)).getText().toString();

                        /* TODO: stop crashing when areaString is null, and the null is passed back into MainActivity */

                        Intent backIntent = new Intent();

                        backIntent.putExtra(TRIANGLE_RESULT, areaString);

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

                        backIntent.putExtra(TRIANGLE_RESULT, "0.0");

                        setResult(RESULT_OK, backIntent);

                        finish();
                    }
                }
        );

    }

}
