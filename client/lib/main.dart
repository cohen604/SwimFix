import 'package:flutter/material.dart';
import 'Services/connectionHandler.dart';
void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {

  MyHomePage({Key key, this.title}) : super(key: key);

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {

  ConnectionHandelr connectionHandelr;
  Future<String> name;

  @override
  void initState() {
    super.initState();
    this.connectionHandelr = new ConnectionHandelr("", "");
  }

  void foo() {
    setState(() {
      name = this.connectionHandelr.getMessage("/upload");
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text(
              'You have pushed the button this many times:',
            ),
            FutureBuilder<String>(
              future: this.name,
              builder: (context, snapshot) {
                if (snapshot.hasData) {
                  return Text("result: ${snapshot.data}");
                }
                return Text("None");
                }
              ),
            ],
          ),
        ),
      floatingActionButton: FloatingActionButton(
        onPressed: foo,
        tooltip: 'Send',
        child: Icon(Icons.add),
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
}
//
