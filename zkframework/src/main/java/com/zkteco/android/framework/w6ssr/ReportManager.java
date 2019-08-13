package com.zkteco.android.framework.w6ssr;

/**
 * Created by apple on 16/9/19.
 */

public class ReportManager
{

    private int mReportManager;
    private String mInputFilePath;
    private String mTemplateXlsPath;
    private String mBeginDate;
    private String mEndDate;
    private JAttOptions mOptions;
    private ProgressUpdateListener progressListener;

    static
    {
        try {
            System.loadLibrary("W6SsrLib");

        }catch (Exception ex)
        {

        }

    }

    protected void  finalize()
    {
        try {
            finalizer(mReportManager);
        }finally {
            try {
                super.finalize();
            }catch (Throwable e)
            {
                e.printStackTrace();
            }
        }

    }

    public ReportManager()
    {
        mReportManager = init();
    }

    public void  setInputFilePath(String inputFilePath)
    {
        mInputFilePath = inputFilePath;
        native_setInputFilePath(mReportManager,inputFilePath);
    }

    public  void  setTemplateXlsPath(String templateXlsPath)
    {
        mTemplateXlsPath = templateXlsPath;
        native_setTemplateXlsPath(mReportManager,mTemplateXlsPath);
    }

    public  void  setBeginDate(String beginDate)
    {
        mBeginDate = beginDate;
        native_setBeginDate(mReportManager,mBeginDate);
    }

    public  void  setEndDate(String endDate)
    {
        mEndDate = endDate;
        native_setEndDate(mReportManager,mEndDate);
    }

    public  void  setOptions(JAttOptions options)
    {
        mOptions = options;
        native_setOptions(mReportManager,mOptions);
    }

    public  void  setXlsLanguage(int language)
    {
        setXlsLang(mReportManager,language);
    }

    public void setProgressCallBack()
    {
        native_setProgressCallBack(mReportManager,"progressUpdate");
    }

    //---这个是回调函数，子类可以继承该类从回调里面获取计算进度
    void progressUpdate(int current,int total,String description)
    {
        if(progressListener != null){
            progressListener.progressUpdate(current,total,description);
        }
    }

    public void setProgressListener(ProgressUpdateListener listener){
        progressListener = listener;
    }

    public  int calc(String outXlsFilePath)
    {
        return native_calc(mReportManager,outXlsFilePath);
    }

    public  native int   native_calc(int reportManager, String outXlsFilePath);
    public  native void  native_setInputFilePath(int reportManager,String inputFilePath);
    public  native void  native_setTemplateXlsPath(int reportManager,String templateXlsPath);
    public  native void  native_setBeginDate(int reportManager,String beginDate);
    public  native void  native_setEndDate(int reportManager,String endDate);
    public  native void  native_setOptions(int reportManager,JAttOptions options);

    private native void finalizer(int reportManager);
    public  native int init();
    public  static native  void printLogFile();
    public  static native  boolean setLocalize(String localizeFile);
    public  static native  boolean setLicenceKey(String licenceKey);
    public  static native  void setXlsLang(int reportManager,int language);
    public native void native_setProgressCallBack(int reportManager,String methodName);


    public interface ProgressUpdateListener {
        void  progressUpdate(int current,int total,String description);
    }
}



