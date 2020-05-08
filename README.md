# store_launcher

A Flutter plugin for launching a Store in the mobile platform. Supports AppStore and PlayStore.

## Usage
To use this plugin, add `store_launcher` as a [dependency in your pubspec.yaml file](https://flutter.dev/platform-plugins/).

### Example

``` dart
import 'dart:async';

import 'package:flutter/material.dart';
import 'package:store_launcher/store_launcher.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  final myController = TextEditingController();

  @override
  void dispose() {
    myController.dispose();
    super.dispose();
  }

  Future<void> openWithStore() async {
    var appId = myController.text;
    print('app id: $appId');
    try {
      StoreLauncher.openWithStore(appId).catchError((e) {
        print('ERROR> $e');
      });
    } on Exception catch (e) {
      print('$e');
    }
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin store_launcher example app'),
        ),
        body: Container(
          padding: EdgeInsets.all(12.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.center,
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              TextField(
                controller: myController,
                decoration: InputDecoration(
                    hintText: 'Please enter Package Name',
                    border: OutlineInputBorder(
                        borderSide: BorderSide(color: Colors.teal))),
              ),
              RaisedButton(
                onPressed: () {
                  openWithStore();
                },
                child: Text('Open With Store'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
```
