import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AnimationTime extends AppCompatActivity {
    // Declare UI elements
    private EditText input;
    private TextView result;
    private TextView countdownTimer;
    private ImageView pulsingImage;
    private Animation pulseAnimation;
    private CountDownTimer countDownTimer;
    private boolean isTimerRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_time);

        // Initialize UI elements
        input = findViewById(R.id.input);
        result = findViewById(R.id.result);
        countdownTimer = findViewById(R.id.countdownTimer);
        pulsingImage = findViewById(R.id.pulsingImageView);
        pulseAnimation = AnimationUtils.loadAnimation(AnimationTime.this, R.anim.pulsing_animation);

        // Initialize stop button click listener
        Button stopButton = findViewById(R.id.stop);
        stopButton.setOnClickListener(v -> stopCountdownTimer());

        // Initialize start button click listener
        findViewById(R.id.buttonAnimationStart).setOnClickListener(v -> performCalculations());
    }

    // Method to perform calculations
    private void performCalculations() {
        String inputValue = input.getText().toString().trim();

        if (inputValue.isEmpty()) {
            // Show a toast message if input is empty
            Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Placeholder calculation: Multiply the input value by 2
            double resultValue = Double.parseDouble(inputValue) * 2;

            // Display the result in the TextView
            result.setText(String.valueOf(resultValue));

            // Placeholder code for countdown timer
            long animationDuration = 5000;
            countdownTimer.setText(formatTime(animationDuration));

            // Start the pulsing animation
            pulsingImage.startAnimation(pulseAnimation);
            pulsingImage.setVisibility(View.VISIBLE);

            // Start the countdown timer
            countDownTimer = new CountDownTimer(animationDuration, 1000) {
                public void onTick(long millisUntilFinished) {
                    // Update countdown timer text every second
                    countdownTimer.setText(formatTime(millisUntilFinished));
                }
                public void onFinish() {
                    // Stop animation when timer finishes
                    pulsingImage.clearAnimation();
                    // Enable start button and disable stop button
                    findViewById(R.id.buttonAnimationStart).setEnabled(true);
                    isTimerRunning = false;
                    findViewById(R.id.stop).setEnabled(false);
                    pulsingImage.setVisibility(View.INVISIBLE);
                }
            }.start();

        } catch (NumberFormatException e) {
            // Show error message if parsing fails
            Toast.makeText(this, "Error: Unable to parse input value", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    // Method to stop the countdown timer
    private void stopCountdownTimer() {
        if (countDownTimer != null) {
            // Cancel the countdown timer
            countDownTimer.cancel();
            // Stop the pulsing animation
            pulsingImage.clearAnimation();
            // Hide the pulsing image
            pulsingImage.setVisibility(View.INVISIBLE);
        }
    }

    // Method to format time in HH:mm:ss format
    private String formatTime(long millisUntilFinished) {
        int seconds = (int) (millisUntilFinished / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;
        int hours = minutes / 60;
        minutes = minutes % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
