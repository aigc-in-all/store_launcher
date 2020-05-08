#import "StoreLauncherPlugin.h"
#if __has_include(<store_launcher/store_launcher-Swift.h>)
#import <store_launcher/store_launcher-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "store_launcher-Swift.h"
#endif

@implementation StoreLauncherPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftStoreLauncherPlugin registerWithRegistrar:registrar];
}
@end
