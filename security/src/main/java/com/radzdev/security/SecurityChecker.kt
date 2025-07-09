package com.radzdev.security

import android.app.Activity
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.util.Log
import java.security.MessageDigest

object SecurityChecker {

    fun validate(activity: Activity, expectedSha1: String, expectedAppName: String, expectedPackage: String) {
        val currentAppName = getAppName(activity)
        val currentPackage = activity.packageName
        val detectedSha1 = getCertificateFingerprint(activity)

        Log.i("SecurityChecker", "=== SECURITY VALIDATION ===")
        Log.i("SecurityChecker", "DETECTED SHA1: $detectedSha1")
        Log.i("SecurityChecker", "EXPECTED SHA1: $expectedSha1")
        Log.i("SecurityChecker", "DETECTED App Name: $currentAppName")
        Log.i("SecurityChecker", "EXPECTED App Name: $expectedAppName")
        Log.i("SecurityChecker", "DETECTED Package: $currentPackage")
        Log.i("SecurityChecker", "EXPECTED Package: $expectedPackage")
        Log.i("SecurityChecker", "========================")

        if (detectedSha1 != expectedSha1 || currentAppName != expectedAppName || currentPackage != expectedPackage) {
            Log.e("SecurityChecker", "❌ SECURITY VALIDATION FAILED!")
            Log.e("SecurityChecker", "SHA1 Match: ${if (detectedSha1 == expectedSha1) "✅ PASS" else "❌ FAIL"}")
            Log.e("SecurityChecker", "App Name Match: ${if (currentAppName == expectedAppName) "✅ PASS" else "❌ FAIL"}")
            Log.e("SecurityChecker", "Package Match: ${if (currentPackage == expectedPackage) "✅ PASS" else "❌ FAIL"}")
            Log.e("SecurityChecker", "🚨 APP WILL BE TERMINATED 🚨")
            activity.finish()
        } else {
            Log.i("SecurityChecker", "✅ SECURITY VALIDATION PASSED!")
        }
    }

    private fun getCertificateFingerprint(activity: Activity): String {
        return try {
            val packageInfo = activity.packageManager.getPackageInfo(
                activity.packageName,
                PackageManager.GET_SIGNATURES
            )
            val signature: Signature = packageInfo.signatures!![0]
            val cert = signature.toByteArray()
            val input = java.io.ByteArrayInputStream(cert)
            val cf = java.security.cert.CertificateFactory.getInstance("X509")
            val c = cf.generateCertificate(input) as java.security.cert.X509Certificate
            val md = MessageDigest.getInstance("SHA1")
            val publicKey = md.digest(c.encoded)
            bytesToHexWithColons(publicKey)
        } catch (e: Exception) {
            Log.e("SecurityChecker", "Error getting certificate fingerprint: ${e.message}")
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

    private fun bytesToHexWithColons(bytes: ByteArray): String {
        val hexArray = "0123456789ABCDEF".toCharArray()
        val result = StringBuilder()
        for (j in bytes.indices) {
            val v = bytes[j].toInt() and 0xFF
            result.append(hexArray[v ushr 4])
            result.append(hexArray[v and 0x0F])
            if (j < bytes.size - 1) result.append(":")
        }
        return result.toString()
    }
}