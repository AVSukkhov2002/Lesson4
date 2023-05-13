package ru.mirea.sukhovav.thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import ru.mirea.sukhovav.thread.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



    }

    public void Click(View view){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int count = 0;
                for (int i = 0; i < 7; i++){
                    count = 4 + count;
                }
                String countStr = "в этом месце "+ String.valueOf(count) + " пар" ;
                binding.textView.post(new Runnable() {
                    public void run() {
                        binding.textView.setText(countStr);
                    }
                });
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}