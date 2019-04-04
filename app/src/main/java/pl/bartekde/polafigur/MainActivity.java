package pl.bartekde.polafigur;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public final static int CODE_TRIANGLE   = 10;
    public static final int CODE_RECTANGLE  = 11;
    public static final int CODE_CIRCLE     = 12;
    private double area;

    TextView resultTextView;
    Button triangleButton;
    Button rectangleButton;
    Button circleButton;
    Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = findViewById(R.id.resultTextView);

        triangleButton = findViewById(R.id.triangleButton);
        rectangleButton = findViewById(R.id.rectangleButton);
        circleButton = findViewById(R.id.circleButton);
        resetButton = findViewById(R.id.resetButton);

        area = 0.0;

    }

    public void clickTriangleButton(View v) {
        Intent myIntent = new Intent(MainActivity.this, CalculateTriangleActivity.class);
        startActivityForResult(myIntent, CODE_TRIANGLE);
    }

    public void clickRectangleButton(View v) {
        Intent myIntent = new Intent(MainActivity.this, CalculateRectangleActivity.class);
        startActivityForResult(myIntent, CODE_RECTANGLE);
    }

    public void clickCircleButton(View v) {
        Intent myIntent = new Intent(MainActivity.this, CalculateCircleActivity.class);
        startActivityForResult(myIntent, CODE_CIRCLE);
    }

    public void clickResetButton(View v) {
        area = 0.0;
        resultTextView.setText("0.0");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data != null) {

            // check what code was returned and then perform actions
            String newArea = "";
            switch (requestCode) {
                case CODE_TRIANGLE:
                    newArea = data.getStringExtra(CalculateTriangleActivity.TRIANGLE_RESULT);
                    break;
                case CODE_RECTANGLE:
                    newArea = data.getStringExtra(CalculateRectangleActivity.RECTANGLE_RESULT);
                    break;
                case CODE_CIRCLE:
                    newArea = data.getStringExtra(CalculateCircleActivity.CIRCLE_RESULT);
                    break;
            }

            area += Double.parseDouble(newArea);
        }

        ((TextView) findViewById(R.id.resultTextView)).setText(String.format(Locale.getDefault(), "%.2f", area));
    }
}
