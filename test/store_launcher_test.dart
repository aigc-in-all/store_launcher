import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:store_launcher/store_launcher.dart';

void main() {
  const MethodChannel channel = MethodChannel('store_launcher');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await StoreLauncher.platformVersion, '42');
  });
}
