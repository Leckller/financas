import 'package:flutter/material.dart';
import 'package:mobile/models/transaction.dart';

void main() => runApp(ExpensesApp());

class ExpensesApp extends StatelessWidget {
  const ExpensesApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(home: MyHomePage());
  }
}

class MyHomePageState extends State<MyHomePage> {
  List<Transaction> transactions = [Transaction.newTransaction("title", 2.0.toStringAsFixed(2))];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Color.fromRGBO(0, 208, 158, 1),
      body: CustomScrollView(
        slivers: [
          SliverToBoxAdapter(
            child: Container(
              padding: EdgeInsets.fromLTRB(16, 32, 16, 8),
              height: 150,
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        "Olá, Bem vindo",
                        style: TextStyle(
                          fontSize: 24,
                          fontWeight: FontWeight.w500,
                        ),
                      ),
                      Text("Bom dia", style: TextStyle(fontSize: 16)),
                    ],
                  ),
                ],
              ),
            ),
          ),
          SliverFillRemaining(
            hasScrollBody: false,
            child: Container(
              padding: EdgeInsets.all(8),
              color: Colors.white,
              child: Column(
                spacing: 10,
                children: transactions
                    .map((t) => Card(
                      child: SizedBox(
                        height: 80,
                        child: Row(children: [
                        Text(t.value, style: TextStyle(
                        ),)
                                            ]),
                      )))
                    .toList(),
              ),
            ),
          ),
        ],
      ),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key});

  @override
  State<StatefulWidget> createState() {
    return MyHomePageState();
  }
}
