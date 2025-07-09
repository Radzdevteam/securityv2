package com.radzdev.security

import android.app.Activity
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.util.Log
import java.security.MessageDigest

object SecurityChecker {

    fun validate(activity: Activity, expectedSha1: String, expectedAppName: String, expectedPackage: String) {
        val currentSha1 = getAppSha1(activity)
        val currentAppName = getAppName(activity)
        val currentPackage = activity.packageName

        Log.d("SecurityChecker", "Expected SHA1: $expectedSha1")
        Log.d("SecurityChecker", "Current SHA1: $currentSha1")
        Log.d("SecurityChecker", "Expected App Name: $expectedAppName")
        Log.d("SecurityChecker", "Current App Name: $currentAppName")
        Log.d("SecurityChecker", "Expected Package: $expectedPackage")
        Log.d("SecurityChecker", "Current Package: $currentPackage")

        if (currentSha1 != expectedSha1 || currentAppName != expectedAppName || currentPackage != expectedPackage) {
            Log.e("SecurityChecker", "Security validation failed!")
            Log.e("SecurityChecker", "SHA1 match: ${currentSha1 == expectedSha1}")
            Log.e("SecurityChecker", "App name match: ${currentAppName == expectedAppName}")
            Log.e("SecurityChecker", "Package match: ${currentPackage == expectedPackage}")
            activity.finish()
        } else {
            Log.i("SecurityChecker", "Security validation passed!")
        }
    }

    private fun getAppSha1(activity: Activity): String {
        return try {
            val packageInfo = activity.packageManager.getPackageInfo(
                activity.packageName,
                PackageManager.GET_SIGNATURES
            )
            val signature: Signature = packageInfo.signatures!![0]
            val md = MessageDigest.getInstance("SHA1")
            md.update(signature.toByteArray())
            bytesToHex(md.digest())
        } catch (e: Exception) {
            Log.e("SecurityChecker", "Error getting SHA1: ${e.message}")
            ""
        }
    }

    private fun getAppName(activity: Activity): String {
        return try {
            val packageManager = activity.packageManager
            val applicationInfo = packageManager.getApplicationInfo(activity.packageName, 0)
            packageManager.getApplicationLabel(applicationInfo).toString()
        } catch (e: Exception) {
            Log.e("SecurityChecker", "Error getting app name: ${e.message}")
            ""
        }
    }

    private fun bytesToHex(bytes: ByteArray): String {
        val hexArray = "0123456789ABCDEF".toCharArray()
        val hexChars = CharArray(bytes.size * 2)
        for (j in bytes.indices) {
            val v = bytes[j].toInt() and 0xFF
            hexChars[j * 2] = hexArray[v ushr 4]
            hexChars[j * 2 + 1] = hexArray[v and 0x0F]
        }
        return String(hexChars)
    }
}