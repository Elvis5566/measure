import 'dart:async';

import 'package:flutter/services.dart';

class Measure {
  static const MethodChannel _channel = const MethodChannel('plugins.flutter.io/measure');

  static Future<bool> get distanceIsMetric async => await _channel.invokeMethod("distanceIsMetric");

  static Future<bool> get heightIsMetric async => await _channel.invokeMethod("heightIsMetric");
}


