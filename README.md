# store_launcher

A Flutter plugin for launching a Store in the mobile platform. Supports AppStore, PlayStore and AppGallery.

## Usage
To use this plugin, add `store_launcher` as a [dependency in your pubspec.yaml file](https://flutter.dev/platform-plugins/).

### Example

``` dart
import 'package:store_launcher/store_launcher.dart';

try {
  final appId = 'com.example.yourapppackageid';
  StoreLauncher.openWithStore(appId).catchError((e) {
    print('ERROR> $e');
  });
} catch (e) {
  print(e.toString());
}
```
