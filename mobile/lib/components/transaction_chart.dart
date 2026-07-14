import 'package:flutter/material.dart';
import 'package:mobile/models/transaction.dart';

class Day {
  final String title;
  final List<Transaction> transactions;

  Day({required this.title, required this.transactions});
}

class Week {
  Day segunda = Day(title: "segunda", transactions: []);
  Day terca = Day(title: "terca", transactions: []);
  Day quarta = Day(title: "quarta", transactions: []);
  Day quinta = Day(title: "quinta", transactions: []);
  Day sexta = Day(title: "sexta", transactions: []);
  Day sabado = Day(title: "sabado", transactions: []);
  Day domingo = Day(title: "domingo", transactions: []);

  List<Day> get days => [
    segunda,
    terca,
    quarta,
    quinta,
    sexta,
    sabado,
    domingo,
  ];
}

Week groupCurrentWeek(List<Transaction> transactions) {
  final now = DateTime.now();

  // Cria uma data com horários zerados
  // Dpois subtrai os dias atuais da semana para conseguir o DateTime do primeiro dia da semana
  final startOfWeek = DateTime(
    now.year,
    now.month,
    now.day,
  ).subtract(Duration(days: now.weekday - 1));
  // Aq pega o DateTime do último dia
  final endOfWeek = startOfWeek.add(const Duration(days: 7));

  final week = Week();

  for (final t in transactions) {
    // Valida se tá dentro da semana atual
    if (t.date.isBefore(startOfWeek) || !t.date.isBefore(endOfWeek)) {
      continue;
    }

    switch (t.date.weekday) {
      case DateTime.monday:
        week.segunda.transactions.add(t);
        break;
      case DateTime.tuesday:
        week.terca.transactions.add(t);
        break;
      case DateTime.wednesday:
        week.quarta.transactions.add(t);
        break;
      case DateTime.thursday:
        week.quinta.transactions.add(t);
        break;
      case DateTime.friday:
        week.sexta.transactions.add(t);
        break;
      case DateTime.saturday:
        week.sabado.transactions.add(t);
        break;
      case DateTime.sunday:
        week.domingo.transactions.add(t);
        break;
    }
  }

  return week;
}

class TransactionChart extends StatelessWidget {
  final List<Transaction> transactions;
  const TransactionChart({super.key, required this.transactions});

  double getTotal() {
    return transactions.fold(0.0, (sum, t) => sum + t.value);
  }

  @override
  Widget build(BuildContext context) {
    Week week = groupCurrentWeek(transactions);
    return Container(
      color: Colors.white70,
      height: 200,
      width: double.infinity,
      child: Row(
        children: [
          Column(children: [Container(color: Colors.red)]),
        ],
      ),
    );
  }
}
