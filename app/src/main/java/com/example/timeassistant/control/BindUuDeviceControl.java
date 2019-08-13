package com.example.timeassistant.control;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.example.timeassistant.R;
import com.example.timeassistant.database.UuDeviceOperate;
import com.example.timeassistant.entity.UuDevice;
import com.example.timeassistant.factory.UdkFactory;
import com.example.timeassistant.utils.AttenAssiSharedPreferenceUtil;
import com.example.timeassistant.utils.SdcardLogTools;
import com.example.timeassistant.utils.UuAbnormalBusiness;
import com.example.timeassistant.utils.UuDeviceBusiness;
import com.zkteco.android.framework.async.thread.AsyncTaskEmulate;
import com.zkteco.android.framework.liteorm.DBHelper;
import com.zkteco.android.framework.utils.ZKLog;
import com.zkteco.android.framework.utils.ZKTools;
import com.zkteco.android.framework.view.dialog.ZKCustomDialogUtils;
import com.zkteco.coreservice.UdkHelper;
import com.zkteco.coreservice.UdkService;


/**
 * 描述：
 * <p/>
 * 日期：2016/7/18
 * 作者：kangjj
 */
public class BindUuDeviceControl extends BaseUuControl{
    private static final String TAG = BindUuDeviceControl.class.getSimpleName();

    public BindUuDeviceControl(Context context) {
        super(context);
    }

    public void bindDev(final String pin,final String pwd,final String ip, final String port,final UuDevice uuDevice) {
        SdcardLogTools.saveCrashInfoFile("bindDev : pin = " + pin + "  ip = " + ip + " port = " + port);
        ZKCustomDialogUtils.show((Activity) mContext, ZKCustomDialogUtils.DIALOG_LOADING);
        AsyncTaskEmulate.startAsyncTask(new AsyncTaskEmulate.PrepareCall<Void>() {

            @Override
            public Void call() {
                return null;
            }
        }, new AsyncTaskEmulate.BackgroundCall<Integer>() {

            @Override
            public Integer call(AsyncTaskEmulate.IProgressListener listener) {
                SdcardLogTools.saveCrashInfoFile("bindDev BindUuDeviceControl  Device.setUdk Info.");
                uuDevice.setUdkPort(ZKTools.getPortInt(port));
                uuDevice.setIpAddr(ip);

//                if (!UdkHelper.isConnect()) {
                SdcardLogTools.saveCrashInfoFile("bindDev BindUuDeviceControl  UdkFactory.connect(uuDevice) start");
                    long handle = UdkFactory.connect(uuDevice);
                SdcardLogTools.saveCrashInfoFile("bindDev BindUuDeviceControl  UdkFactory.connect(uuDevice) end handle = " + handle);
                    if (handle == 0) {
                        int tmpRet = UdkService.UdkGetLastError(handle);
                        SdcardLogTools.saveCrashInfoFile("bindDev BindUuDeviceControl  UdkService.UdkGetLastError = " + tmpRet);
                        return tmpRet;
                    }
//                }
                int ret;
                SdcardLogTools.saveCrashInfoFile("bindDev BindUuDeviceControl  UdkFactory.getDeviceParam(SerialNumber) start");
                String deviceSn = UdkFactory.getDeviceParam("~SerialNumber");              //序列号
                SdcardLogTools.saveCrashInfoFile("bindDev BindUuDeviceControl  UdkFactory.getDeviceParam(SerialNumber) end   deviceSn = " + deviceSn);
                String deviceName = UdkFactory.getDeviceParam("~DeviceName");              //设备名
                SdcardLogTools.saveCrashInfoFile("bindDev BindUuDeviceControl  UdkFactory.getDeviceParam(DeviceName) end   deviceName = " +
                        deviceName);
                String lanuage = UdkFactory.getDeviceParam(UdkFactory.DEVICEPARAM_LANGUAGE);
                SdcardLogTools.saveCrashInfoFile("bindDev BindUuDeviceControl  UdkFactory.getDeviceParam(DEVICEPARAM_LANGUAGE) end   lanuage = " +
                        lanuage);
                String deviceFeature = UdkFactory.getDeviceParam("IsQueryFeatures");
                // 空值再试一次
                if(TextUtils.isEmpty(deviceFeature)){
                    deviceFeature = UdkFactory.getDeviceParam("IsQueryFeatures");
                    SdcardLogTools.saveCrashInfoFile("bindDev BindUuDeviceControl  UdkFactory.getDeviceParam(IsQueryFeatures)2 end   deviceFeature " +
                            "= " +
                            deviceFeature);
                }
                SdcardLogTools.saveCrashInfoFile("bindDev BindUuDeviceControl  UdkFactory.getDeviceParam(IsQueryFeatures) end   deviceFeature = " +
                        deviceFeature);
                // 可能是连接失败
                if(TextUtils.isEmpty(deviceFeature)){
                    UdkHelper.disconnect();
                    return -1;
                }
                // 不等于支持的设备类型，则提示APP不支持设备
                if(!deviceFeature.equals(UdkFactory.DEVICE_SUPPORT)){
                    UdkHelper.disconnect();
                    return -4;
                }
                if(!TextUtils.isEmpty(deviceSn) &&!TextUtils.isEmpty(deviceName)){
                    if(UuDeviceOperate.hasDeviceSn(deviceName+"-"+deviceSn)){               //设备已经绑定过
                        UdkHelper.disconnect();
                        return -2;
                    }
                    SdcardLogTools.saveCrashInfoFile("bindDev BindUuDeviceControl  UdkFactory.bindDev start");
                    ret = UdkFactory.bindDev(uuDevice, pin, pwd);
                    SdcardLogTools.saveCrashInfoFile("bindDev BindUuDeviceControl  UdkFactory.bindDev end ret = " + ret);
                    if (ret >= 0 || ret == UdkFactory.BINDED) {
                            uuDevice.setLanuage(lanuage);
                            uuDevice.setDeviceSn(deviceName+"-"+deviceSn);
                            AttenAssiSharedPreferenceUtil.setDeviceSnAndName(uuDevice.getDeviceSn());
                            UuDevice mUuDevice= DBHelper.getInstance().getLiteOrm().queryById(uuDevice.getDeviceSn(),UuDevice.class);
                            if(mUuDevice!=null){
                                DBHelper.getInstance().getLiteOrm().update(uuDevice);
                                SdcardLogTools.saveCrashInfoFile("bindDev BindUuDeviceControl  DBHelper.getInstance().getLiteOrm().update(uuDevice)" +
                                        ";");
                            }else{
                                DBHelper.getInstance().getLiteOrm().insert(uuDevice);
                                SdcardLogTools.saveCrashInfoFile("bindDev BindUuDeviceControl  DBHelper.getInstance().getLiteOrm().insert(uuDevice)" +
                                        ";");
                            }
                        if(UdkFactory.getRight(uuDevice)!=1){           //获取权限 不等于1证明失败
                            SdcardLogTools.saveCrashInfoFile("bindDev BindUuDeviceControl  UdkFactory.getRight(uuDevice) 获取权限 不等于1证明失败");
                            AttenAssiSharedPreferenceUtil.setDeviceSnAndName("");//获取权限失败，不连接只绑定成功，所以设备名清空，为了在DeviceManagerFragment里面显示
                            UdkHelper.disconnect();
                            return -3;
                        }
                        AttenAssiSharedPreferenceUtil.setAuthority(uuDevice.isAdmin());
                        DBHelper.getInstance().getLiteOrm().update(uuDevice);//更新是否是管理员 这里的值占时没用到都是用上面的SP判断
                        SdcardLogTools.saveCrashInfoFile("bindDev BindUuDeviceControl  DBHelper.getInstance().getLiteOrm().update(uuDevice);");
                    }
                }else{
                    UdkHelper.disconnect();
                    return -1;
                }
                UdkHelper.disconnect();
                return ret;
            }
        }, new AsyncTaskEmulate.PostCall<Integer>() {

            @Override
            public void handle(Integer result) {
                ZKCustomDialogUtils.cancel();
                SdcardLogTools.saveCrashInfoFile("bindDev BindUuDeviceControl  AsyncTaskEmulate.PostCall<Integer>() = " + result);
                if(result == null){
                    ZKTools.toastShow(R.string.str_unknow_error);
                }else if (result>=0 || result == UdkFactory.BINDED) {
                    ZKTools.toastShow(R.string.bindSucceed);
                    AttenAssiSharedPreferenceUtil.setIsCheckNetwork(true);
                    ((BindUuDeviceActivity)mContext).onBindDevComplete();
                } else if(result==-2){
                    ZKTools.toastShow(R.string.str_has_binded);
                }else if(result==-3){
                    ZKTools.toastShow(R.string.str_bindSucceed_get_authority_fail);
                    ((BindUuDeviceActivity)mContext).onBindDevComplete();
                }else if(result==-4){
                    ZKTools.toastShow(R.string.err_device_unsupported);
                }else {
                    int id = UuDeviceBusiness.getErrorStringId(result);
                    if (id == UdkHelper.ERROR_UNKNOWN)
                        id = UuDeviceBusiness.getErrorStringId(UdkHelper.getLastError());
                    if (id == UdkHelper.ERROR_UNKNOWN){
                        id = R.string.str_unknow_error;
                    }
                    ZKTools.toastShow(id);
                }
            }
        }, null);
    }

    public void unBindDev(final String pin, final String pwd, final String ip, final String port, final UuDevice uuDevice, final HistoryConnectAdapter  mAdapter){
        ZKCustomDialogUtils.show((Activity) mContext, ZKCustomDialogUtils.DIALOG_LOADING);
        AsyncTaskEmulate.startAsyncTask(new AsyncTaskEmulate.PrepareCall<Void>() {

            @Override
            public Void call() {
                return null;
            }
        }, new AsyncTaskEmulate.BackgroundCall<Integer>() {

            @Override
            public Integer call(AsyncTaskEmulate.IProgressListener listener) {
                uuDevice.setUdkPort(ZKTools.getPortInt(port));
                uuDevice.setIpAddr(ip);

                long handle = UdkFactory.connect(uuDevice);
                if (handle == 0) return -1;

                int ret = UdkFactory.unBindDev(uuDevice, pin, pwd);

                return ret;
            }
        }, new AsyncTaskEmulate.PostCall<Integer>() {

            @Override
            public void handle(Integer result) {
                if(result == null){
                    ZKTools.toastShow(R.string.str_unknow_error);
                }else if(result < 0){
                    int id = UuDeviceBusiness.getErrorStringId(result);
                    if (id == UdkHelper.ERROR_UNKNOWN)
                        id = UuDeviceBusiness.getErrorStringId(UdkHelper.getLastError());
                    ZKTools.toastShow(id);
                }else{
                    ZKTools.toastShow(R.string.unbindSucceed);
                    UuDeviceOperate.delete(uuDevice);
                    mAdapter.refreshData(UuDeviceOperate.getDeviceListNoEquals(AttenAssiSharedPreferenceUtil.getDeviceSnAndName()));
                }
                ZKCustomDialogUtils.cancel();
            }
        }, null);
    }

    public void connect(final int port, final String pwd, final UuDevice uuDevice,final ConnectCallBack connectCallBack) {
        ZKCustomDialogUtils.show((Activity) mContext, ZKCustomDialogUtils.DIALOG_LOADING);
        AsyncTaskEmulate.startAsyncTask(new AsyncTaskEmulate.PrepareCall<Void>() {

            @Override
            public Void call() {
                return null;
            }
        }, new AsyncTaskEmulate.BackgroundCall<Integer>() {

            @Override
            public Integer call(AsyncTaskEmulate.IProgressListener listener) {
                String ip = uuDevice.getIpAddr();
                SdcardLogTools.saveCrashInfoFile("connect BindUuDeviceControl  call  ip : " + ip);
                if (TextUtils.isEmpty(ip)) {
                    ip = UuDeviceBusiness.getGateway();
                    SdcardLogTools.saveCrashInfoFile("connect BindUuDeviceControl  getGateway : " + ip);
                }

                long handle = UdkFactory.connect(ip, pwd, port);            //连接返回可以能是负数也可能是正数，只要不等于0就说明连接上
                SdcardLogTools.saveCrashInfoFile("connect BindUuDeviceControl  UdkFactory.connect end  handle = " + handle);
                if (handle != 0) {
                    uuDevice.setUdkPort(port);
                    uuDevice.setUdkPwd(pwd);
                    SdcardLogTools.saveCrashInfoFile("connect BindUuDeviceControl  UdkFactory.getDeviceParam(~SerialNumber) start " + handle);
                    String deviceSn = UdkFactory.getDeviceParam("~SerialNumber");              //序列号
                    SdcardLogTools.saveCrashInfoFile("connect BindUuDeviceControl  UdkFactory.getDeviceParam(~SerialNumber) end deviceSn = " +
                            deviceSn);
                    String deviceName = UdkFactory.getDeviceParam("~DeviceName");              //设备名
                    SdcardLogTools.saveCrashInfoFile("connect BindUuDeviceControl  UdkFactory.getDeviceParam(~SerialNumber) end deviceName = " +
                            deviceName);
//                    String deviceFeature = UdkFactory.getDeviceParam("IsQueryFeatures");
//                    // 空值再试一次
//                    if(TextUtils.isEmpty(deviceFeature)){
//                        deviceFeature = UdkFactory.getDeviceParam("IsQueryFeatures");
//                    }
//                    // 为空，或不支持当前APP(为空则是获取失败，获取失败默认不能进去，先这么写)
////                    if(!TextUtils.isEmpty(deviceFeature) && !deviceFeature.equals(UdkFactory.DEVICE_SUPPORT)){
////                        return -4;
////                    }
//                    // 可能是连接失败
//                    if(TextUtils.isEmpty(deviceFeature)){
//                        return -1;
//                    }
//                    // 不等于支持的设备类型，则提示APP不支持设备
//                    if(!deviceFeature.equals(UdkFactory.DEVICE_SUPPORT)){
//                        return -4;
//                    }
                    if(!TextUtils.isEmpty(deviceSn) &&!TextUtils.isEmpty(deviceName)) {
                        if(!uuDevice.getDeviceSn().equals(deviceName + "-" + deviceSn)){                //直联模式下  两台机子的ip可能一样，所以会到不同的机器上去
                            UdkHelper.disconnect();
                            return -3;
                        }
                        uuDevice.setDeviceSn(deviceName + "-" + deviceSn);
                        AttenAssiSharedPreferenceUtil.setDeviceSnAndName(uuDevice.getDeviceSn());
                        UuDevice mUuDevice = DBHelper.getInstance().getLiteOrm().queryById(uuDevice.getDeviceSn(), UuDevice.class);
                        if (mUuDevice != null) {
                            DBHelper.getInstance().getLiteOrm().update(uuDevice);
                            SdcardLogTools.saveCrashInfoFile("connect BindUuDeviceControl  DBHelper.getInstance().getLiteOrm().update(uuDevice);");
                        } else {
                            DBHelper.getInstance().getLiteOrm().insert(uuDevice);
                            SdcardLogTools.saveCrashInfoFile("connect BindUuDeviceControl  DBHelper.getInstance().getLiteOrm().insert(uuDevice);");
                        }
                    }
                    /*if(uuDevice.isAdmin()){         //管理员必须获取到选线
                        if(checkRight(uuDevice)){
                            return -2;              //自定义返回-2代表管理员 但是没获取到权限， 下面要做连接失败的提示。
                        }
                    }else{*/

                    if(UdkFactory.getRight(uuDevice)!=1){           //获取权限 不等于1证明失败
                        UdkHelper.disconnect();
                        return -2;
                    }
                    if(uuDevice.getRight() == -1){
                        UdkHelper.disconnect();
                        return -5;
                    }
                    AttenAssiSharedPreferenceUtil.setAuthority(uuDevice.isAdmin());
                    /*}*/
                }
                UdkHelper.disconnect();
                return handle!=0?1:-1;               //1代表正常连接 -1代表连接失败
            }
        }, new AsyncTaskEmulate.PostCall<Integer>() {

            @Override
            public void handle(Integer result) {
                SdcardLogTools.saveCrashInfoFile("connect BindUuDeviceControl  AsyncTaskEmulate.PostCall<Integer>() result = " + result);
                ZKCustomDialogUtils.cancel();
                if(result == null){
                    ZKTools.toastShow(R.string.str_unknow_error);
                }else if (result>0) {
                    if(connectCallBack!=null){
                        if(!TextUtils.isEmpty(AttenAssiSharedPreferenceUtil.getDeviceSnAndName())){
                            connectCallBack.connectSuccess();
                            ZKTools.toastShow(R.string.str_connetcedSucceed);
                        }else{
                            connectCallBack.connectFail();
                        }
                    }
                } else if(result==-2){
                    ZKTools.toastShow(R.string.str_get_authority_fail);              //管理员获取权限失败 提示连接失败进行重新连接
                } else if(result==-3){
                    ZKTools.toastShow(R.string.str_connect_fail);              //管理员获取权限失败 提示连接失败进行重新连接
                }else if(result==-4){
                    ZKTools.toastShow(R.string.err_device_unsupported);
                }else if(result == -5){
                    ZKTools.toastShow(R.string.err_device_userexception);
                }else{
                    if(connectCallBack!=null){
                        connectCallBack.connectFail();
                    }else {
                        UuAbnormalBusiness.handleError(mContext, port, pwd, uuDevice);
                    }
                }
            }
        }, null);
    }

    public void unConnect(final DisConnectCallBack mDisConnectCallBack) {
        ZKCustomDialogUtils.show((Activity) mContext, ZKCustomDialogUtils.DIALOG_LOADING);
        AsyncTaskEmulate.startAsyncTask(new AsyncTaskEmulate.PrepareCall<Void>() {

            @Override
            public Void call() {
                return null;
            }
        }, new AsyncTaskEmulate.BackgroundCall<Boolean>() {

            @Override
            public Boolean call(AsyncTaskEmulate.IProgressListener listener) {
                UdkHelper.disconnect();
                return true;               //1代表正常连接 -1代表连接失败
            }
        }, new AsyncTaskEmulate.PostCall<Boolean>() {

            @Override
            public void handle(Boolean result) {
                ZKCustomDialogUtils.cancel();
                mDisConnectCallBack.disConnectSuccess();
//                ZKTools.toastShow(R.string.str_connetcedSucceed);
            }
        }, null);
    }

    public void findId(final Activity mContex,final UuDevice device , final ConnectCallBack connectCallBack) {
        ZKCustomDialogUtils.show((Activity) mContext, ZKCustomDialogUtils.DIALOG_LOADING);
        AsyncTaskEmulate.startAsyncTask(new AsyncTaskEmulate.PrepareCall<Void>() {

            @Override
            public Void call() {
                return null;
            }
        }, new AsyncTaskEmulate.BackgroundCall<Boolean>() {

            @Override
            public Boolean call(AsyncTaskEmulate.IProgressListener listener) {
                long handle = UdkFactory.connect(device);
                if(handle != 0){
                    UdkFactory.getRight(device);
                    if(!TextUtils.isEmpty(device.getBindPin())){
                        ZKLog.d("findId","连接过的ID为 : " + device.getBindPin());
                        ZKTools.toastShow("连接过的ID为 : " + device.getBindPin());
                    }else{
                        ZKLog.e("findId","获取ID失败");
                        ZKTools.toastShow("获取ID失败");
                    }
                }else{
                    ZKLog.e("findId","连接失败");
                    ZKTools.toastShow("连接失败");
                    return false;
                }
                return true;               //1代表正常连接 -1代表连接失败
            }
        }, new AsyncTaskEmulate.PostCall<Boolean>() {

            @Override
            public void handle(Boolean result) {
                ZKCustomDialogUtils.cancel();
                if(result == null || !result){
                    connectCallBack.connectFail();
                }else{
                    connectCallBack.connectSuccess();
                }
//                ZKTools.toastShow(R.string.str_connetcedSucceed);
            }
        }, null);
    }

    public interface DisConnectCallBack{
        void disConnectSuccess();
    }

    public interface ConnectCallBack{
        void connectSuccess();
        void connectFail();
    }
}
