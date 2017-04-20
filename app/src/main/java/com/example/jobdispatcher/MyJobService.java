package com.example.jobdispatcher;

import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import java.text.DateFormat;
import java.util.Date;

public class MyJobService extends JobService{
	private static final String TAG = "MyJobService";

	@Override
	public boolean onStartJob(JobParameters jobParameters) {
		String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
		Log.d(TAG, currentDateTimeString);
		return false;
	}

	@Override
	public boolean onStopJob(JobParameters jobParameters) {
		Log.d(TAG, "Job cancelled!");
		return false;
	}
}