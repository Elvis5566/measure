import Flutter
import UIKit

public class SwiftMeasurePlugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "plugins.flutter.io/measure", binaryMessenger: registrar.messenger())
    let instance = SwiftMeasurePlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }
    
    private func _isDistanceMetric () -> Bool{
        let locale = NSLocale.autoupdatingCurrent as NSLocale
        let result = (locale as NSLocale).object(forKey: .measurementSystem) as? String
        if( result == "Metric") {
            return true
        }
        return false
    }

    private func _isHeightWeightMetric() -> Bool {
        let locale = NSLocale.autoupdatingCurrent as NSLocale
        return locale.usesMetricSystem
    }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    if (call.method == "distanceIsMetric") {
        result(_isDistanceMetric())
    }
    else if (call.method == "heightIsMetric") {
        result(_isHeightWeightMetric())
    }
  }
}
