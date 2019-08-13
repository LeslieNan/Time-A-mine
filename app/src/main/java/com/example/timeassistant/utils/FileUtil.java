package com.example.timeassistant.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.zkteco.android.framework.base.BaseApplication;
import com.zkteco.android.framework.utils.ZKConstant;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * 描述：
 * <p/>
 * 日期：2016/7/20
 * 作者：kangjj
 */
public class FileUtil {
    private static final String TAG = FileUtil.class.getSimpleName();

    public static String getFilePath() {
        File file = BaseApplication.getInstance().getContext().getExternalFilesDir("form");
        if (file == null) file = BaseApplication.getInstance().getContext().getFilesDir();
        Log.d(TAG, "getFilePath: " + file.getPath() + "/");
        return file.getPath() + "/";
    }

    public static String getCachePath() {
        File file = BaseApplication.getInstance().getContext().getExternalCacheDir();
        if (file == null) file = BaseApplication.getInstance().getContext().getCacheDir();
        Log.d(TAG, "getCachePath: " + file.getPath() + "/");
        return file.getPath() + "/";
    }

    public static String getOutPutDir(){
        String sdPath = getSDPath();
        String dirName = sdPath + ZKConstant.FILE_EXCEL +
                ZKConstant.EXCEL_XLS + File.separator+TimeUtil.DateTimeDetailFormatter.format(new Date());
        dirName = changeDirName(dirName);
        return dirName;
    }

    public static String getExcelDir(String outDir) {
        String sdPath = getSDPath();
        if (TextUtils.isEmpty(sdPath)) {
            return "";
        }
//        String xlsDir = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        String[] dirPaths = {ZKConstant.EXCEL_OUTPUT, ZKConstant.EXCEL_DATA,
                ZKConstant.EXCEL_TEMP, ZKConstant.EXCEL_LOCALE, ZKConstant.EXCEL_LOCALE,
                ZKConstant.EXCEL_XLS
        };
        for (String dirPath : dirPaths) {
            File file = new File(sdPath + ZKConstant.FILE_EXCEL + dirPath);
            if(!file.exists())
                makeDir(file);
        }
        File outPutDir = new File(outDir);
        if(!outPutDir.exists())
            makeDir(outPutDir);
        //把asset/SSRTemplateS.xls文件拷贝到 sdcard目录下SSRTemplateS.xls
        String tempSSRPath = sdPath + ZKConstant.FILE_EXCEL + ZKConstant.EXCEL_TEMP;
        deleteDir(new File(tempSSRPath));
//        String timepFilePaths[] = {ZKConstant.EXCEL_SSRTEMPLATES,ZKConstant.EXCEL_SSRATTRECORDS,ZKConstant.EXCEL_DATEFORMULATEMP};
//        for (String filePath : timepFilePaths) {         //每次都删除，因为模板文件可能会换
//            File file = new File(tempSSRPath+filePath);
//            if(file.exists())
//                file.delete();
//        }
        copyFilesFassets(ZKConstant.ASSETS_TEMP,tempSSRPath);
        //把asset/SSRTemplateS.xls文件拷贝到 sdcard目录下SSRTemplateS.xls
        String localPath=sdPath + ZKConstant.FILE_EXCEL + ZKConstant.EXCEL_LOCALE;
        copyFilesFassets(ZKConstant.ASSETS_LOCALE,localPath);
        try{
            copyAssetDirToFiles(BaseApplication.getInstance().getContext(), ZKConstant.ASSETS_SYSTEMDAT);
        }catch (Exception e){
            e.printStackTrace();
        }
//        return sdPath + ZKConstant.FILE_EXCEL/*fileOut.getParent()*/;
        return sdPath + ZKConstant.FILE_EXCEL;
    }

    public static String getFileExcelDir(){
        return getSDPath() + ZKConstant.FILE_EXCEL+ ZKConstant.EXCEL_XLS;
    }

    /**
     * 修改目录名字，末尾加1
     * @param dirName
     * @return
     */
    private static String changeDirName(String dirName){
        if(new File(dirName).exists()){
            String start = dirName.substring(0,dirName.lastIndexOf("-"));
            String end =  dirName.substring(dirName.lastIndexOf("-")+1) ;
            end = (Integer.valueOf(end)+1)+"";
            return changeDirName(start+"-"+end);
        }
        return dirName;
    }

    public static void makeDir(File dir) {
        if(dir == null){
            return;
        }
        if (dir.getParentFile() != null && !dir.getParentFile().exists()) {
            makeDir(dir.getParentFile());
        }
        if(!dir.exists())
            dir.mkdir();
    }

    public static String getSDPath() {
        File sdDir = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            sdDir = Environment.getExternalStorageDirectory();
        }
        return sdDir == null ? null : sdDir.toString();
    }

    /**
     * 根据byte数组，以及指定长度 ，生成文件
     */
    public static void writeByte2File(byte[] bfile,int[] bufferLength , String filePath,boolean isAppend) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {

            file = new File(filePath);
            fos = new FileOutputStream(file,isAppend);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile,0,bufferLength[0]);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 从assets目录中复制整个文件夹内容
     *
     * @param oldPath String  原文件路径  如：/aa
     * @param newPath String  复制后路径  如：xx:/bb/cc
     */
    public static void copyFilesFassets( String oldPath, String newPath) {
        try {
            String fileNames[] = BaseApplication.getInstance().getContext().getAssets().list(oldPath);//获取assets目录下的所有文件及目录名
            if (fileNames.length > 0) {//如果是目录
                File file = new File(newPath);
                file.mkdirs();//如果文件夹不存在，则递归
                for (String fileName : fileNames) {
                    copyFilesFassets(oldPath + "/" + fileName, newPath + "/" + fileName);
                }
            } else {//如果是文件
                InputStream is = BaseApplication.getInstance().getContext().getAssets().open(oldPath);
                FileOutputStream fos = new FileOutputStream(new File(newPath));
                byte[] buffer = new byte[1024];
                int byteCount = 0;
                while ((byteCount = is.read(buffer)) != -1) {//循环从输入流读取 buffer字节
                    fos.write(buffer, 0, byteCount);//将读取的输入流写入到输出流
                }
                fos.flush();//刷新缓冲区
                is.close();
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void reName(File file ,String rename){
        if(file!=null && file.exists()) {
            file.renameTo(new File(rename));
        }
    }

    public static void copyAssetDirToFiles(Context context, String dirname)
            throws IOException {
        File dir = new File(context.getFilesDir()+File.separator+dirname);
        dir.mkdir();

        AssetManager assetManager = context.getAssets();
        String[] children = assetManager.list(dirname);
        for (String child : children) {
            child = dirname + '/' + child;
            String[] grandChildren = assetManager.list(child);
            if (0 == grandChildren.length)
                copyAssetFileToFiles(context, child);
            else
                copyAssetDirToFiles(context, child);
        }
    }
    public static void copyAssetFileToFiles(Context context, String filename)
            throws IOException {
        InputStream is = context.getAssets().open(filename);
        byte[] buffer = new byte[is.available()];
        is.read(buffer);
        is.close();

        File of = new File(context.getFilesDir() + "/" + filename);
        of.createNewFile();
        FileOutputStream os = new FileOutputStream(of);
        os.write(buffer);
        os.close();
    }

    public static void deleteDir(File file){
        if(file == null){
            return;
        }
        if(file.exists()){
            if(file.isDirectory()){
                File[] files = file.listFiles();
                if(files == null || files.length <= 0){
                    file.delete();
                    return;
                }
                for(File f : files){
                    deleteDir(f);
                }
                file.delete();
            }
        }
    }


    /**
     　　* 保存文件
     　　* @param toSaveString
     　　* @param filePath
     　　*/
    public static void saveFile(String toSaveString)
    {
        try{
            String filePath = getSDPath()+"/ZKJson/ "+ SdcardLogTools.formatter.format(new Date()) + ".txt";
            File saveFile = new File(filePath);
            if (!saveFile.exists())
            {
                File dir = new File(saveFile.getParent());
                dir.mkdirs();
                saveFile.createNewFile();
            }else{
                saveFile.delete();
                saveFile.createNewFile();
            }

            FileOutputStream outStream = new FileOutputStream(saveFile);
            outStream.write(toSaveString.getBytes());
            outStream.close();
            } catch (FileNotFoundException e)
        {
            e.printStackTrace();
            } catch (IOException e)
        {
            e.printStackTrace();
            }
    }
}
