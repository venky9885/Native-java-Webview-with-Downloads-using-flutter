import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:text_view/text_view.dart';

void main() {
  const MethodChannel channel = MethodChannel('text_view');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  /*test('getPlatformVersion', () async {
    expect(await TextView.platformVersion, '42');
  });*/
}
