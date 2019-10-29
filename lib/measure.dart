import 'dart:async';

import 'package:flutter/services.dart';

enum Unit { metric, imperial }

class Measure {
  static const MethodChannel _channel = const MethodChannel('plugins.flutter.io/measure');

  static Future<bool> get isDistanceMetric async => await _channel.invokeMethod("distanceIsMetric");

  static Future<bool> get isHeightWeightMetric async => await _channel.invokeMethod("heightIsMetric");

  static Future<Unit> getUnit() async {
    final bool isMetric = await Measure.isDistanceMetric;
    return (isMetric) ? Unit.metric : Unit.imperial;
  }

  static Future<Unit> getHeightWeightUnit() async {
    final bool isMetric = await Measure.isHeightWeightMetric;
    return (isMetric) ? Unit.metric : Unit.imperial;
  }
}
