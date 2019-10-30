package co.velodash.measure;

import android.app.Activity;
import android.os.Build;

import java.util.Locale;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * MeasurePlugin
 */
public class MeasurePlugin implements MethodCallHandler {

    public static final String[] DISTANCE_IMPERIAL_COUNTRIES = {"US", "GB", "LR", "MM"};
    public static final String[] HEIGHT_WEIGHT_TEMPERATURE_IMPERIAL_COUNTRIES = {"US", "GB", "LR", "MM"};

    private final Activity _activity;

    private MeasurePlugin(Activity activity) {
        this._activity = activity;
    }

    /**
     * Plugin registration.
     */
    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "plugins.flutter.io/measure");
        channel.setMethodCallHandler(new MeasurePlugin(registrar.activity()));
    }

    @SuppressWarnings("deprecation")
    private Locale _getCurrentLocale() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return _activity.getBaseContext().getResources().getConfiguration().getLocales().get(0);
        } else {
            return _activity.getBaseContext().getResources().getConfiguration().locale;
        }
    }

    private boolean _isMetricFromList(String[] data) {
        final String countryCode = this._getCurrentLocale().getCountry();
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
