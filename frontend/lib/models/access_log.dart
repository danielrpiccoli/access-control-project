import 'package:frontend/models/scheduling.dart';

class AccessLog {
  final int id;
  final DateTime entryTimeStamp;
  final bool success;
  final Scheduling scheduling;

  AccessLog({
    required this.id,
    required this.entryTimeStamp,
    required this.success,
    required this.scheduling,
  });

  factory AccessLog.fromJson(Map<String, dynamic> json) {
    return AccessLog(
      id: json['id'],
      entryTimeStamp: DateTime.parse(json['entryTimestamp']),
      success: json['success'],
      scheduling: Scheduling.fromJson(json['scheduling']),
      );
  }
}