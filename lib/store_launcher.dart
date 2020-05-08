import 'dart:async';

import 'package:flutter/services.dart';

class StoreLauncher {
  static const MethodChannel _channel = const MethodChannel('store_launcher');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<void> openWithStore(String appId) async {
    await _channel.invokeMethod('openWithStore', {"app_id": appId});
  }
}
