package comhkdg.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hkdg.lettersidebar.LetterSideBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.textView);
        LetterSideBar letterSideBar = findViewById(R.id.letterSideBar);
        String[] letter = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        letterSideBar.setLetters(letter);
        letterSideBar.setOnTouchingTextListener((text) -> {
            if (text == null) {
                textView.setVisibility(View.GONE);
                textView.setText("");
            } else {
                textView.setVisibility(View.VISIBLE);
                textView.setText(text);
            }
        });
    }
}
