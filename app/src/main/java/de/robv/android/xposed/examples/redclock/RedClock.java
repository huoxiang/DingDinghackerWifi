package de.robv.android.xposed.examples.redclock;

import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;


public class RedClock implements IXposedHookLoadPackage {
    public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {

        // 打印装载的apk程序包名
        XposedBridge.log("Launch app: " + lpparam.packageName);
        XposedBridge.log("handleLoadPackage 执行 packageName=" + lpparam.packageName);
        Log.v("handleLoadPackag Name=", lpparam.packageName);
        if (!lpparam.packageName.equals("com.android.systemui"))
            return;

        findAndHookMethod("com.android.systemui.statusbar.policy.Clock", lpparam.classLoader, "updateClock", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                TextView tv = (TextView) param.thisObject;
                String text = tv.getText().toString();
                tv.setText(text + " :)");
                tv.setTextColor(Color.RED);
                Log.v("redcolock","redcolock");
            }
        });

        //XposedHelpers.getObjectField(obj, "a");
    }
}