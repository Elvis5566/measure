package co.velodash.measure;

import android.content.Context;
import android.os.Build;

import android.content.pm.PackageManager;
import java.util.Locale;
import android.telephony.TelephonyManager;
import android.util.Log;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.embedding.engine.plugins.FlutterPlugin;

/**
 * MeasurePlugin
 */
public class MeasurePlugin implements MethodCallHandler, FlutterPlugin {

    public static final String[] DISTANCE_IMPERIAL_COUNTRIES = {"US", "GB", "LR", "MM"};
    public static final String[] HEIGHT_WEIGHT_TEMPERATURE_IMPERIAL_COUNTRIES = {"US", "GB", "LR", "MM"};

    private static final String CHANNEL_NAME = "plugins.flutter.io/measure";
    private Context applicationContext;
    private MethodChannel channel;

    @Override
    public void onAttachedToEngine(FlutterPluginBinding flutterPluginBinding) {
        channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), CHANNEL_NAME);
        channel.setMethodCallHandler(this);
        applicationContext = flutterPluginBinding.getApplicationContext();
    }

    @Override
    public void onDetachedFromEngine(FlutterPluginBinding flutterPluginBinding) {
        channel.setMethodCallHandler(null);
        applicationContext = null;
    }

    @SuppressWarnings("deprecation")
    private String _getCurrentContryCode() {
        if(applicationContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_TELEPHONY)){
            TelephonyManager tm = (TelephonyManager) applicationContext.getSystemService(Context.TELEPHONY_SERVICE);
            String countryCodeValue = tm.getNetworkCountryIso();
//            Log.d("CountryCode", countryCodeValue);
            return countryCodeValue;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return applicationContext.getResources().getConfiguration().getLocales().get(0).getCountry();
        } else {
            return applicationContext.getResources().getConfiguration().locale.getCountry();
        }
    }

    private boolean _isMetricFromList(String[] data) {
        final String countryCode = this._getCurrentContryCode();
        for (String usesImperialCountry : data) {
            if (usesImperialCountry.equalsIgnoreCase(countryCode)) {
                return false;
            }
        }
        return true;
    }

    private boolean _isHeightWeightMetric() {
        return _isMetricFromList(MeasurePlugin.HEIGHT_WEIGHT_TEMPERATURE_IMPERIAL_COUNTRIES);
    }

    private boolean _isDistanceMetric() {
        return _isMetricFromList(MeasurePlugin.DISTANCE_IMPERIAL_COUNTRIES);
    }

    @Override
    public void onMethodCall(MethodCall call, Result result) {
        switch (call.method) {
            case "distanceIsMetric":
                result.success(this._isDistanceMetric());
                break;
            case "heightIsMetric":
                result.success(this._isHeightWeightMetric());
                break;
            default:
                result.notImplemented();
        }
    }
}
