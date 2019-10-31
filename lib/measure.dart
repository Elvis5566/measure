import 'dart:async';

import 'package:flutter/services.dart';

class Measure {
  static const MethodChannel _channel = const MethodChannel('plugins.flutter.io/measure');

  static Future<bool> get isDistanceMetric async => await _channel.invokeMethod("distanceIsMetric");

  static Future<bool> get isHeightWeightMetric async => await _channel.invokeMethod("heightIsMetric");
}
