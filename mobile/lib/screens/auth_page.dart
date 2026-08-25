import 'package:flutter/material.dart';

class AuthPage extends StatelessWidget {
  const AuthPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(
        children: [Text("Olá!")],
      ),
      bottomNavigationBar: NavigationBar(destinations: [
        IconButton(onPressed: () {}, icon: Icon(Icons.home)),
        IconButton(onPressed: () {}, icon: Icon(Icons.search)),
        IconButton(onPressed: () {}, icon: Icon(Icons.swap_horiz_outlined)),
        IconButton(onPressed: () {}, icon: Icon(Icons.layers)),
        IconButton(onPressed: () {}, icon: Icon(Icons.person)),
      ],),
    );
  }
}