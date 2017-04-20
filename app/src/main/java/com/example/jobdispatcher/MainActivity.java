package com.example.jobdispatcher;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
	private static final String JOB_TAG = "MyJobService";
	private FirebaseJobDispatcher mDispatcher;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		findViewById(R.id.btn_schedule).setOnClickListener(this);
		findViewById(R.id.btn_cancel).setOnClickListener(this);

		mDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btn_schedule: scheduleJob(); break;
			case R.id.btn_cancel: cancelJob(JOB_TAG); break;
		}
	}

	private void scheduleJob() {
		Job myJob = mDispatcher.newJobBuilder()
				.setService(MyJobService.class)
				.setTag(JOB_TAG)
				.setRecurring(true)
				.setTrigger(Trigger.executionWindow(5, 30))
				.setLifetime(Lifetime.UNTIL_NEXT_BOOT)
				.setReplaceCurrent(false)
				.setConstraints(Constraint.ON_ANY_NETWORK)
				.setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
				.build();
		mDispatcher.mustSchedule(myJob);
		Toast.makeText(this, R.string.job_scheduled, Toast.LENGTH_LONG).show();
	}

	private void cancelJob(String jobTag) {
		if ("".equals(jobTag)) {
			mDispatcher.cancelAll();
		} else {
			mDispatcher.cancel(jobTag);
		}
		Toast.makeText(this, R.string.job_cancelled, Toast.LENGTH_LONG).show();
	}
}