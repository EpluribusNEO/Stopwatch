package com.epluribusneo.stopwatch;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragStopwatch#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragStopwatch extends Fragment {

	private Button btnStartPause;
	private Button btnStop;
	private TextView tvTimerValue;

	private  long startTime = 0L;

	private Handler customHandler = new Handler();

	private long timeInMilliseconds = 0L;
	private long timeSwapBuff = 0L;
	private long updatedTime = 0L;

	private boolean isStarted = false;

/* ************************************** */

	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	public FragStopwatch() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment FragStopwatch.
	 */
	// TODO: Rename and change types and number of parameters
	public static FragStopwatch newInstance(String param1, String param2) {
		FragStopwatch fragment = new FragStopwatch();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		//return inflater.inflate(R.layout.fragment_frag_stopwatch, container, false);

		View rootView = inflater.inflate(R.layout.fragment_frag_stopwatch, container, false);
		btnStartPause = (Button)rootView.findViewById(R.id.btnStart);
		btnStop = (Button)rootView.findViewById(R.id.btnStop);
		tvTimerValue = (TextView)rootView.findViewById(R.id.tvTime);


		btnStartPause.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				timerStartStop();
			}
		});


		btnStop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startTime = 0L;
				timeSwapBuff = 0L;
				customHandler.removeCallbacks(updateTimeThread);
				isStarted = false;
				tvTimerValue.setText("00:00:000");
				btnStartPause.setText("Start");

			}
		});

		return rootView;
	}


	private Runnable updateTimeThread = new Runnable(){

		public void run(){
			timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

			updatedTime = timeSwapBuff + timeInMilliseconds;

			int seconds = (int)(updatedTime / 1000);
			int minuts = seconds / 60;
			seconds = seconds % 60;
			int milliseconds = (int)(updatedTime % 1000);
			tvTimerValue.setText("" + String.format("%02d",minuts) + ":"
					+ String.format("%02d", seconds) + ":"
					+ String.format("%03d", milliseconds));
			customHandler.postDelayed(this, 0);

		}
	};

	private  void showToast(String msg){
		Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
	}

	private void timerStartStop(){
		if(isStarted){
			timeSwapBuff += timeInMilliseconds;
			customHandler.removeCallbacks(updateTimeThread);
			btnStartPause.setText("Start");
		}else{
			startTime = SystemClock.uptimeMillis();
			customHandler.postDelayed(updateTimeThread, 0);
			btnStartPause.setText("Pause");
		}
		isStarted = !isStarted;
	}
}