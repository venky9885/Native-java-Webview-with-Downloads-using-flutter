import 'package:flutter/material.dart';
import 'package:text_view/text_view.dart';
import 'package:permission_handler/permission_handler.dart';
Future main() async {
  WidgetsFlutterBinding.ensureInitialized();
 /* await FlutterDownloader.initialize(
      debug: true // optional: set false to disable printing logs to console
  );*/
  await Permission.storage.request();
  runApp(MaterialApp(home: TextViewExample()));
}
/*void main() => runApp(
    WidgetsFlutterBinding.ensureInitialized();
    await FlutterDownloader.initialize(
    debug: true // optional: set false to disable printing logs to console
);
await Permission.storage.request();
    MaterialApp(home: TextViewExample()));*/

class TextViewExample extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      resizeToAvoidBottomInset: false,
        appBar: AppBar(title: const Text('Flutter TextView example')),
        body: Column(children: [
          Center(
              child: Container(
                  //padding: EdgeInsets.symmetric(vertical: 30.0),
                  width: MediaQuery.of(context).size.width ,
                  height: MediaQuery.of(context).size.height-100,
                //height: MediaQuery.of(context).size.height,

                  child: TextView(
                    onTextViewCreated: _onTextViewCreated,
                  ),
                  )),
          /*Expanded(
              flex: 3,
              child: Container(
                  color: Colors.blue[100],
                  child: Center(child: Text("Hello from Flutter!"))))*/
        ]));
  }

  void _onTextViewCreated(TextViewController controller) {
    controller.setText("https://dopagent.indiapost.gov.in/");
  }
}