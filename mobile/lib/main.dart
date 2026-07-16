import 'package:flutter/material.dart';
import 'package:mobile/components/transaction_chart.dart';
import 'package:mobile/models/transaction.dart';

void main() => runApp(ExpensesApp());

class ExpensesApp extends StatelessWidget {
  const ExpensesApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      theme: ThemeData(
        primarySwatch: Colors.lightGreen,
        colorScheme: ColorScheme.fromSeed(
          seedColor: Color.fromRGBO(0, 208, 158, 1),
          brightness: Brightness.light,
        ),
        fontFamily: "ChelseaMarket",
      ),
      home: MyHomePage(),
    );
  }
}

class MyHomePageState extends State<MyHomePage> {
  List<Transaction> transactions = [];

  TextEditingController titleControler = TextEditingController();
  TextEditingController valueControler = TextEditingController();

  void _submitModal() {
    String title = titleControler.text;
    double value =
        double.tryParse(valueControler.text.replaceAll(',', '.')) ?? 0;

    if (value == 0 || title.isEmpty) {
      return;
    }

    Transaction newTransaction = Transaction.newTransaction(title, value);

    setState(() {
      transactions.add(newTransaction);
    });

    titleControler.clear();
    valueControler.clear();

    Navigator.of(context).pop();
  }

  void _deleteTransaction(String id) {
    setState(() {
      transactions.removeWhere((tr) => tr.id == id);
    });
  }

  void _openTransactionModal(BuildContext context) {
    showModalBottomSheet(
      context: context,
      builder: (_) => Container(
        color: Colors.white,
        width: double.infinity,
        padding: EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.end,
          spacing: 8,
          children: [
            SizedBox(
              width: double.infinity,
              child: Text(
                "Nova transação",
                style: TextStyle(fontSize: 18, fontWeight: FontWeight.w500),
              ),
            ),
            TextField(
              controller: titleControler,
              decoration: InputDecoration(labelText: "Título:"),
              style: TextStyle(fontSize: 24),
            ),
            TextField(
              controller: valueControler,
              decoration: InputDecoration(labelText: "Valor R\$"),
              keyboardType: TextInputType.numberWithOptions(),
              style: TextStyle(fontSize: 24),
            ),
            SizedBox(
              width: double.infinity,
              child: ElevatedButton(
                onPressed: () => _submitModal(),
                child: Text(
                  "Lançar",
                  style: TextStyle(fontSize: 16, fontWeight: FontWeight.w500),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Color.fromRGBO(0, 208, 158, 1),
      body: CustomScrollView(
        slivers: [
          SliverToBoxAdapter(
            child: Container(
              padding: EdgeInsets.fromLTRB(16, 32, 16, 8),
              height: 320,
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
                  Row(
                    children: [
                      Text(
                        "Saldo: ${transactions.fold(0.0, (sum, t) => sum + t.value)}",
                      ),
                    ],
                  ),
                  TransactionChart(transactions: transactions)
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
                children: transactions.isEmpty
                    ? <Widget>[
                        Image.asset("assets/images/waiting.png", height: 200),
                      ]
                    : transactions
                          .map(
                            (t) => Card(
                              child: Container(
                                height: 80,
                                padding: EdgeInsets.all(16),
                                child: Row(
                                  mainAxisAlignment:
                                      MainAxisAlignment.spaceBetween,
                                  children: [
                                    Text(
                                      t.value.toString(),
                                      style: TextStyle(),
                                    ),
                                    Column(
                                      children: [
                                        Text(t.title),
                                        Text(t.date.toString()),
                                      ],
                                    ),
                                    IconButton(
                                      onPressed: () => _deleteTransaction(t.id),
                                      icon: Icon(Icons.delete),
                                    ),
                                  ],
                                ),
                              ),
                            ),
                          )
                          .toList(),
              ),
            ),
          ),
        ],
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          _openTransactionModal(context);
        },
        child: Icon(Icons.add),
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
