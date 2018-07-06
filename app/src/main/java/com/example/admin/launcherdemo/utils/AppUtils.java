package com.example.admin.launcherdemo.utils;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xinxin Shi
 */

public class AppUtils {
    public static final String PACKAGE_OTA = "com.sunmi.ota";
    public static final String PACKAGE_MARKET = "woyou.market";
    public static final String PACKAGE_HARD_WARE_KEEPER = "com.woyou.hardwarekeeper";
    public static final String PACKAGE_UDH = "com.woyou.udh";
    public static final String PACKAGE_SETTING = "com.android.settings";

    /**
     * 过滤自定义的App和已下载的App
     *
     * @param packageManager
     * @return
     */
    public static List<AppInfo> scanInstallApp(PackageManager packageManager) {
        List<AppInfo> myAppInfos = new ArrayList<AppInfo>();
        List<AppInfo> mFilterApps = new ArrayList<AppInfo>();
        try {
            List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
            for (int i = 0; i < packageInfos.size(); i++) {
                PackageInfo packageInfo = packageInfos.get(i);
                //过滤指定的app
                String tempPackageName = packageInfo.packageName;
                if (tempPackageName.equals(PACKAGE_OTA) || tempPackageName.equals(PACKAGE_MARKET)
                        || tempPackageName.equals(PACKAGE_HARD_WARE_KEEPER) || tempPackageName
                        .equals(PACKAGE_UDH) || tempPackageName.equals(PACKAGE_SETTING)) {
                    AppInfo appInfo = new AppInfo();
                    appInfo.setAppName((String) packageInfo.applicationInfo.loadLabel
                            (packageManager));
                    if (packageInfo.applicationInfo.loadIcon(packageManager) == null) {
                        continue;
                    }
                    appInfo.setAppIcon(packageInfo.applicationInfo.loadIcon(packageManager));
                    setAppInfoName(packageManager, packageInfo, appInfo);
                    mFilterApps.add(appInfo);
                    continue;
                }

                //过滤掉系统app
                if ((ApplicationInfo.FLAG_SYSTEM & packageInfo.applicationInfo.flags) != 0) {
                    continue;
                }

                AppInfo appInfo = new AppInfo();
                appInfo.setAppName((String) packageInfo.applicationInfo.loadLabel(packageManager));
                if (packageInfo.applicationInfo.loadIcon(packageManager) == null) {
                    continue;
                }
                Drawable drawable = packageInfo.applicationInfo.loadIcon(packageManager);
                appInfo.setAppIcon(drawable);
                setAppInfoName(packageManager, packageInfo, appInfo);
                myAppInfos.add(appInfo);
            }
            myAppInfos.addAll(mFilterApps);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("TAG", "错误日志：" + e.getMessage());
        }
        return myAppInfos;
    }

    private static void setAppInfoName(PackageManager packageManager, PackageInfo packageInfo,
                                       AppInfo appInfo) {
        String packageName = packageInfo.packageName;
        String className = "";
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageInfo.packageName);
        List<ResolveInfo> resolveinfoList = packageManager.queryIntentActivities(resolveIntent, 0);
        if(resolveinfoList.size()==0){
            return;
        }
        ResolveInfo resolveinfo = resolveinfoList.iterator().next();
        if (resolveinfo != null) {
            className = resolveinfo.activityInfo.name;
        }
        Log.i("TAG", "此时的包名为：" + packageName + "此时的类名：" + className);
        appInfo.setPackageName(packageName);
        appInfo.setClassName(className);
    }
}
