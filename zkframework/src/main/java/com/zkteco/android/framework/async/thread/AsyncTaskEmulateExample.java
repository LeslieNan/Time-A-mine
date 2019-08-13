package com.zkteco.android.framework.async.thread;

import android.os.AsyncTask;

import com.zkteco.android.framework.async.thread.AsyncTaskEmulate.IProgressListener;


public class AsyncTaskEmulateExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AsyncTask<Void, String, String> task = AsyncTaskEmulate.startAsyncTask(
				new AsyncTaskEmulate.PrepareCall<Void>() {

					@Override
					public Void call() {
						return null;
					}

				}, new AsyncTaskEmulate.BackgroundCall<String>() {

					@Override
					public String call(IProgressListener listener) {
						int i = 1000;
						while (i > 0) {
							// doStuff;
							listener.onProgressChanged(i / 10);
							i--;
						}
						return "";
					}

				}, new AsyncTaskEmulate.PostCall<String>() {

					@Override
					public void handle(String result) {

					}
				}, null);
	}

}
