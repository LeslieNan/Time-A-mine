package com.zkteco.coreservice;

/**
 * Created by dragon on 2016/4/27.
 */
public class UdkService {
    static {
        System.loadLibrary("udk");
    }

    public static int UDK_PROT_DEFAULT = 0;
    public static int UDK_PROT_PULL = 1;
    public static int UDK_PROT_STANDALONE = 2;

    public static native long
    UdkConnect(int[] prot, String parameters);

    public static native void
    UdkDisconnect(long h);

    public static native int
    UdkGetLastError(long h);

    public static native int
    UdkSearchDevices(String commtype, String address, byte[] buffer, int[] bufferlength);

    public static native int
    UdkGetLockCount(long h);

    public static native int
    UdkSetLockState(long h, int lock_no, int opentime);

    public static native int
    UdkGetLockState(long h, int lock_no);

    public static native int
    UdkGetComPwd(long h, byte[] buffer, int[] BufferLen);

    public static native int
    UdkMobileAtt(long h, int operate, String parameters, byte[] buffer, int[] bufferlength);

    public static native int
    UdkGetDeviceParam(long h, byte[] buffer, int BufferLen, String items);


    public static native int
    UdkSetDeviceParam(long h, String itemandvalues);

    public static native int
    UdkSetCallBack(long h, CBInterface cb);

    public static native int
    UdkResetCallBack(long h, CBInterface cb);

    public static native int
    UdkResetDeviceExt(long h, String parameters);

    public static native int

    UdkGenerateAttReport(long h, int type, String timeSelect);

    public static native int
    UdkGetFileByName_ex(long h, String filename, String filepath);


    public static native int
    UdkGetFileByName(long h, String filename, byte[] FileBuffer, int[] DataLen);

    public static native int
    UdkDeleteFileByName(long h, String filename);

    public static native int
    UdkMkdir(String filename, String filepath);

    public static native int
    UdkSetDeptSche(long h, String DeptSche);

    public static native int
    UdkSetDept(long h, String Dept);

    public static native int
    UdkSetSche(long h, String Sche);

    public static native int
    UdkGetDeptList(long h, byte[] DeptList, int[] size);

    public static native int
    UdkGetSche(long h, byte[] Sche);


    public static native int
    UdkSetPersonalSche(long h, String pin2ShiftId);

    public static native int
    UdkGetUserNameAndPin2(long h, byte[] Pin2AndName, int[] size);

    public static native int
    UdkTest(int a, byte[] str2, String str);

    /////////////////////////////////////////////////////////////////
    public static native int
    UdkGetDeviceData(long h, byte[] Buffer, int BufferSize, String TableName, String FieldNames, String Filter, String Options);

    public static native int
    UdkGetDeviceDataCount(long h, String TableName, String Filter, String Options);

    public static native int
    UdkControlDevice(long h, long OperationID, long Param1, long Param2, long Param3, long Param4, String Options);

    public static native int
    UdkSetDeviceData(long h, String TableName, String Datas, String Options);

    public static native int
    UdkDeleteDeviceData(long h, String TableName, String Datas, String Options);

    public static native int
    UdkBase64WriteToBMP(long h, String base64, byte[] fileBmp);

    public static native int
    UdkGetRTLog(long h, byte[] Buffer, int BufferSize);

    public static native int
    UdkSearchDevice(long h, String CommType, String Address, byte[] Buffer, int[] BufferSize);

    public static native int
    UdkModifyIPAddress(long h, String CommType, String Address, byte[] Buffer, int BufferSize);

    public static native int
    UdkLastError(long h);

    public static native int
    UdkSetDeviceFileData(long h, String FileName, String Buffer, int BufferSize, String Options);

    public static native int
    UdkGetDeviceFileData(long h, byte[] Buffer, int[] BufferSize, String FileName, String Options);

    public static native int
    UdkGetTableStruct(long h, byte[] buffer, int buflen);

    public static native int
    UdkSetTableStruct(long h, String table_struct);

    public static native int
    UdkSetAppid(long h, String appid);

    public static native int
    UdkGetAttDataByTime(long h, String Path, String StartTime, String EndTime);

    public static native int
    UdkGetFileByName2_ex(long h, String filename, String filepath);

    public static native int
    UdkGetFileByName2(long h, String filename, byte[] FileBuffer, int[] DataLen);

    public static native int
    UdkGetMemorySize(long h);

    public static native int
    UdkGetFile(long h, String filename, int StartIndex, byte[] FileBuffer, int[] ReadLen);

    public static native int
    UdkDormancySocket(long h);

    public static native int
    UdkActivateSocket(long h);

    public static native int
    UdkGetInnerVersion(long Handle, byte[] Version, int[] Size);

    public static native int
    UDKClearDataEx2(long Handle, int id);

    public static native int
    SetEncodeType(int type);


    //new add
    public static native int UdkSetPersonalUserInfo(long h, String UserId, String FirstName, String LastName, int userPemission, String Password);

    public static native int UdkGetPersonalUserInfo(long h, String UserId, byte[] FirstName, byte[] LastName, int[] userPemission, byte[] Password);

    public static native int UdkGetUserFingerInfo(long h, String UserId, int[] FingerNum, int[] FingerId1, int[] FingerId2, int[] FingerId3);


    //public static native int UdkDeleteUser(long h,int userId);
    //public static native int UdkStartEnroll(long h, int userID, int fingerID);
    //public static native int UdkGetUserFingerInfo(long h, int userId, int[]fingerNum,int[] fingererId1, int[] fingererId2, int[] fingererId3);
    //public static native int UdkSetUserFingerInfo(long h, int userId, int[]fingerNum,int[] fingererId1, int[] fingererId2, int[] fingererId3);
    //public static native int UdkDeleteEnrollFinger(long h,int userId,int fingerId);
    //public static native int UdkDeleteDataBaseFinger(long h,int userId,int fingerId);

    public interface CBInterface {
        /**
         * the download process callback function
         *
         * @param type 0:means totalsize;1:means size has been read
         * @param size
         */
        public abstract void DownloadProcess(int type, int size);


        //内部自行实现， 接收int DataType, int nDataSize, char* DataBuffer数据。
        //static void readCallBack(void* cookie, int DataType, int nDataSize, char* DataBuffer)
        //{

        //自行实现
        //}

    }
}

