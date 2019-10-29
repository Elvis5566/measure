#import "MeasurePlugin.h"
#import <measure/measure-Swift.h>

@implementation MeasurePlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftMeasurePlugin registerWithRegistrar:registrar];
}
@end
