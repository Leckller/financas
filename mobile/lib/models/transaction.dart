import 'dart:math';

class Transaction {

  final String id;
  final String title;
  final double value;
  final DateTime date;

  Transaction({required this.id, required this.title, required this.value, required this.date});

  static Transaction newTransaction(String title, double value) {
    return Transaction(id: Random().nextDouble().toString(), title: title, value: value, date: DateTime.now());
  }

}