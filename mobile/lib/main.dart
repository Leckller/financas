import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:mobile/screens/auth_page.dart';
import 'package:mobile/utils/app_routes.dart';

void main() => runApp(const MyApp());

class MyApp extends StatelessWidget {
  const MyApp({super.key});
  final bool isAndroid = true;

  @override
  Widget build(BuildContext context) {
    return isAndroid ? MaterialApp(
      title: "Finanças App",
      home: AuthPage(),
      routes: {
        AppRoutes.AUTH: (ctx) => AuthPage()
      },
    ) : CupertinoApp(
      title: 'Finanças App',
      home: CupertinoPageScaffold(
        navigationBar: CupertinoNavigationBar(
          middle: Text('Cupertino App Bar'),
        ),
        child: Center(
          child: Text('Hello World'),
        ),
      ),
    );
  }
}