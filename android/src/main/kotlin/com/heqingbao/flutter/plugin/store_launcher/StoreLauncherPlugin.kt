package com.heqingbao.flutter.plugin.store_launcher

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.net.Uri
import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar

/** StoreLauncherPlugin */
class StoreLauncherPlugin : FlutterPlugin, MethodCallHandler {

    private lateinit var context: Context

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        val channel = MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "store_launcher")
        context = flutterPluginBinding.applicationContext
        channel.setMethodCallHandler(this)
    }

    // This static function is optional and equivalent to onAttachedToEngine. It supports the old
    // pre-Flutter-1.12 Android projects. You are encouraged to continue supporting
    // plugin registration via this function while apps migrate to use the new Android APIs
    // post-flutter-1.12 via https://flutter.dev/go/android-project-migration.
    //
    // It is encouraged to share logic between onAttachedToEngine and registerWith to keep
    // them functionally equivalent. Only one of onAttachedToEngine or registerWith will be called
    // depending on the user's project. onAttachedToEngine or registerWith must both be defined
    // in the same class.
    companion object {
        @JvmStatic
        fun registerWith(registrar: Registrar) {
            val channel = MethodChannel(registrar.messenger(), "store_launcher")
            channel.setMethodCallHandler(StoreLauncherPlugin())
        }
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        if (call.method == "openWithStore") {
            if (!call.hasArgument("app_id")) {
                result.error("1", "Missing Parameter in method: (${call.method})", null)
                return
            }
            val packageName: String = call.argument("app_id")!!
            if (openWithPlayStore(packageName)) {
                result.success("ok")
            } else {
                result.error("1", "Unknown Error in method: (${call.method})", null)
            }
        } else {
            result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    }

    private fun openWithPlayStore(appId: String): Boolean {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appId"))
        var marketFound = false
        val apps: List<ResolveInfo> = context.packageManager.queryIntentActivities(intent, 0)
        for (app in apps) {
            if (app.activityInfo.applicationInfo.packageName != "com.android.vending") {
                continue
            }
            val act = app.activityInfo
            val componentName = ComponentName(act.applicationInfo.packageName, act.name)
            // make sure it does NOT open in the stack of your activity
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            // task reparenting if needed
            intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
            // if the Google Play was already open in a search result
            //  this make sure it still go to the app page you requested
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            // this make sure only the Google Play app is allowed to
            // intercept the intent
            intent.component = componentName
            context.startActivity(intent)
            marketFound = true
            break
        }
        if (!marketFound) {
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appId"))
            if (webIntent.resolveActivity(context.packageManager) == null) {
                return false
            }
            context.startActivity(intent)
        }
        return true
    }
}
