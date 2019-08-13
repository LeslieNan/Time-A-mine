package com.example.timeassistant.utils;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.example.timeassistant.R;
import com.example.timeassistant.control.BindUuDeviceControl;
import com.example.timeassistant.database.UuDeviceOperate;
import com.example.timeassistant.entity.UuDevice;
import com.zkteco.android.framework.utils.ZKConstant;
import com.zkteco.android.framework.utils.ZKLog;
import com.zkteco.android.framework.utils.ZKTools;
import com.zkteco.android.framework.view.dialog.DialogCallback;
import com.zkteco.android.framework.view.dialog.DialogInfo;
import com.zkteco.android.framework.view.dialog.ZKCustomDialogUtils;
import com.zkteco.coreservice.UdkHelper;

/**
 * Created by xqc on 2015/12/17.
 * ZKTeco App
 */
public class UuAbnormalBusiness {
    private static final String TAG = "UuAbnormalBusiness";
    private static final int ERROR_UDK_PWD = -14;
    private static final int ERROR_UDK_PORT = -203;
    private static final int ERROR_USER_PWD = -2010;
    private static final int ERROR_UNBIND = -2013;   //被人顶掉回返回
    private static final int ERROR_NO_USER = -2008;   //
    private static final int ERROR_NETWORK = -2333;

    private static void wrongUser(Context context) {
        ZKLog.d(TAG, "wrongUser: ");
        UuDevice uuDevice = UuDeviceBusiness.getCurrentDevice();
        if (uuDevice.isUnbind()) {
//            BindUuDeviceActivity.start(context, false);TODO
            return;
        }
        uuDevice.setBindPin(null);
        uuDevice.setBindPwd(null);
        uuDevice.setBindName(null);
        uuDevice.setBindHwId(null);
        UuDeviceOperate.updateDevice(uuDevice);
//        BindUuDeviceActivity.start(context, true);TODO
    }

    private static void wrongUdkPort(final Context context, final String pwd, final UuDevice uuDevice) {
        ZKLog.d(TAG, "wrongUdkPort: ");
        final DialogInfo dialogInfo = new DialogInfo();
        dialogInfo.setmRightText(context.getString(R.string.str_ok));
        dialogInfo.setmLeftText(context.getString(R.string.str_cancel));
        dialogInfo.setmDialogTitle(context.getString(R.string.str_inputNewPort));
        dialogInfo.setmDialogStyle(ZKCustomDialogUtils.DIALOG_EDIT);
        ZKCustomDialogUtils.show(context, dialogInfo, new DialogCallback() {
            @Override
            public void onInputConfirm(String text) {
                super.onInputConfirm(text);
                try {
                    int port = Integer.parseInt(text);
                    new BindUuDeviceControl(context).connect(port, pwd, uuDevice, null);
                } catch (Exception e) {
                    ZKTools.toastShow(R.string.str_inputNewPort);
                    wrongUdkPort(context, pwd, uuDevice);
                }
            }

            @Override
            public void onNegative() {
                super.onNegative();
            }
        });
    }

    private static void wrongUdkPwd(final Context context, final int port, final UuDevice uuDevice) {
        ZKLog.d(TAG, "wrongUdkPwd: ");
        DialogInfo dialogInfo = new DialogInfo();
        dialogInfo.setmDialogTitle(context.getString(R.string.str_inputPwd));
        dialogInfo.setmRightText(context.getString(R.string.str_ok));
        dialogInfo.setmLeftText(context.getString(R.string.str_cancel));
        dialogInfo.setmDialogStyle(ZKCustomDialogUtils.DIALOG_EDIT);
        ZKCustomDialogUtils.show(context, dialogInfo, new DialogCallback() {
            @Override
            public void onInputConfirm(String text) {
                super.onInputConfirm(text);
                if (text.length() < ZKConstant.MAX_UDK_PWD_LENGTH) {
                    new BindUuDeviceControl(context).connect(port, text, uuDevice, null);
                } else ZKTools.toastShow(R.string.str_wrongUdkPwd);
            }

            @Override
            public void onNegative() {
                super.onNegative();
            }
        });
    }

    public static void handleError(Context context, UuDevice uuDevice) {
        if (uuDevice == null) return;
        handleError(context, uuDevice.getUdkPort(), uuDevice.getUdkPwd(), uuDevice);
    }

    public static void handleError(final Context context, final int port, final String pwd, final UuDevice uuDevice) {
        if (!UuDeviceBusiness.isDeviceMode()) {
//            UuDeviceBusiness.checkChangeWifi(context, R.string.uu_operateFailed);
            return;
        }

        if (uuDevice.isUnbind()) {
            wrongUser(context);
            return;
        }

        final int finalErrorCode = UdkHelper.getLastError();
        ZKLog.d(TAG, "handleResult: " + finalErrorCode);
        if (finalErrorCode >= 0) return;
        ZKLog.e(TAG, "handleError: " + finalErrorCode);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {

                switch (finalErrorCode) {
                    case ERROR_UDK_PWD:
                        wrongUdkPwd(context, port, uuDevice);
                        break;
                    case ERROR_UDK_PORT:
                        wrongUdkPort(context, pwd, uuDevice);
                        break;
                    case ERROR_NO_USER:
                    case ERROR_UNBIND:
                    case ERROR_USER_PWD:
                        wrongUser(context);
                        break;
                    default:
                        int toastStringId = UuDeviceBusiness.getErrorStringId(finalErrorCode);
                        if (toastStringId == UdkHelper.ERROR_UNKNOWN) {
                            toastStringId = R.string.str_unknow_error;
                        }
                        ZKTools.toastShow(toastStringId);
                        break;
                }
            }
        });
    }

}
