package com.business.yourtimes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.business.yourtimes.Survey.SurveyFragment;

public class SurveyActivity extends AppCompatActivity {
    private Button done;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        if (((GlobalClass) getApplicationContext()).isOld()) {
            Intent intent = new Intent(SurveyActivity.this, MainActivity.class);
            startActivity(intent);

            finish();
        }

        /* Done 버튼 클릭 시 MainActivity로 전환 */
        done = (Button) findViewById(R.id.survey_done_btn);
        done.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = ((GlobalClass) getApplicationContext()).numSelected();

                if (num == 3) {
                    ((GlobalClass) getApplicationContext()).register();
                    Intent intent = new Intent(SurveyActivity.this, MainActivity.class);
                    startActivity(intent);

                    finish();
                }
                else {
                    Toast.makeText(SurveyActivity.this, getResources().getString(R.string.survey_notice_over_toast), Toast.LENGTH_SHORT).show();
                }
            }
        });

        /* FrameLayout에 survey 버튼 출력 */
        Fragment fg = SurveyFragment.newInstance();

        FragmentTransaction childFt = getSupportFragmentManager().beginTransaction();
        childFt.replace(R.id.survey_frame, fg);
        childFt.addToBackStack(null);
        childFt.commit();
    }

}
