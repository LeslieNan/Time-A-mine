package com.zkteco.android.framework.async.thread;

import android.app.ProgressDialog;
import android.os.AsyncTask;

/**
 * 模拟异步操作
 *
 * @author Dave
 *
 */
public class AsyncTaskEmulate {

	/**
	 * 开始异步任务
	 *
	 * @param <Progress>
	 * @param prepareCall
	 * @param backgroundCall
	 * @param postCall
	 * @return
	 */
	public static <Params, Progress, Result> AsyncTask<Params, Progress, Result> startAsyncTask(
			final PrepareCall<Void> prepareCall,
			final BackgroundCall<Result> backgroundCall,
			final PostCall<Result> postCall, final ProgressDialog dialog) {
		AsyncTask<Params, Progress, Result> task = new AsyncTask<Params, Progress, Result>() {

			@Override
			protected Result doInBackground(Params... params) {
				try {
					return backgroundCall.call(new IProgressListener() {

						@Override
						public void onProgressChanged(int pProgress) {
							onProgressUpdate(pProgress);
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(Result result) {
				super.onPostExecute(result);
				if (dialog != null) {
					dialog.dismiss();
				}
				postCall.handle(result);
			}


			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				prepareCall.call();
			}

			/**
			 * 更新进度框
			 */
			public void onProgressUpdate(final Integer... values) {
				if (dialog != null)
					dialog.setProgress(values[0]);
			};
		};
		task.execute((Params[]) null);
		return task;
	}

	/**
	 * 开始异步任务
	 *
	 * @param <Progress>
	 * @param prepareCall
	 * @param backgroundCall
	 * @param postCall
	 * @return
	 */
	public static <Params, Progress, Result> AsyncTask<Params, Progress, Result> startAsyncTask(
			final PrepareCall<Void> prepareCall,
			final BackgroundCall<Result> backgroundCall, final OnProgressUpdate onProgressUpdate ,
			final PostCall<Result> postCall) {
		AsyncTask<Params, Progress, Result> task = new AsyncTask<Params, Progress, Result>() {

			@Override
			protected Result doInBackground(Params... params) {
				try {
					return backgroundCall.call(new IProgressListener() {

						@Override
						public void onProgressChanged(int pProgress) {
							onProgressUpdate(pProgress);
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(Result result) {
				super.onPostExecute(result);
				postCall.handle(result);
			}


			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				prepareCall.call();
			}

			/**
			 * 更新进度框
			 */
			public void onProgressUpdate(final int... values) {
				if(onProgressUpdate!=null){
					onProgressUpdate.updateProgress(values[0]);
				}
			};
		};
		task.execute((Params[]) null);
		return task;
	}

	public interface OnProgressUpdate{
		void updateProgress(int value);
	}

	/**
	 * Call on the {@link AsyncTask#onPreExecute() }
	 *
	 * @author Dave
	 *
	 * @param <V>
	 */
	public interface PrepareCall<V> {
		V call();
	}

	/**
	 * Call on the {@link AsyncTask#doInBackground() }
	 *
	 * @author Dave
	 *
	 * @param <V>
	 */
	public interface BackgroundCall<V> {
		V call(IProgressListener listener);
	}

	/**
	 * Call on the {@link AsyncTask#onPostExecute() }
	 *
	 * @author Dave
	 *
	 * @param <V>
	 */
	public interface PostCall<V> {
		/**
		 * handle something
		 * @param result
		 */
		void handle(V result);
	}

	/**
	 * 观察者
	 *
	 * @ClassName: IProgressListener
	 */
	public interface IProgressListener {

		/**
		 * 进度发生改变的时候调用
		 *
		 * @param pProgress
		 */
		public void onProgressChanged(final int pProgress);
	}
}