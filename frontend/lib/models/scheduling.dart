import 'package:frontend/models/app_user.dart';
import 'package:frontend/models/space.dart';

class Scheduling {
  final int id;
  final DateTime scheduledDate;
  final String startTime;
  final String endTime;
  final String status;
  final AppUser user;
  final Space space;

  Scheduling({
    required this.id,
    required this.scheduledDate,
    required this.startTime,
    required this.endTime,
    required this.status,
    required this.user,
    required this.space,
  });

  factory Scheduling.fromJson(Map<String, dynamic> json) {
    return Scheduling(
      id: json['id'], 
      scheduledDate: DateTime.parse(json['scheduledDate']), 
      startTime: json['startTime'], 
      endTime: json['endTime'], 
      status: json['status'], 
      user: AppUser.fromJson(json['user']), 
      space: Space.fromJson(json['space']),
    );
  }
}