import 'package:flutter/material.dart';
import 'package:frontend/models/scheduling.dart';
import 'package:frontend/models/app_user.dart';
import 'package:frontend/services/auth_service.dart';
import 'package:frontend/services/scheduling_service.dart';
import 'package:frontend/services/access_log_service.dart';

class SchedulingsScreen extends StatefulWidget {
  const SchedulingsScreen({super.key});

  @override
  State<SchedulingsScreen> createState() => _SchedulingsScreenState();
}

class _SchedulingsScreenState extends State<SchedulingsScreen> {
  final AuthService authService = AuthService();
  final SchedulingService schedulingService = SchedulingService();
  final AccessLogService accessLogService = AccessLogService();

  List<Scheduling> schedulings = [];
  AppUser? currentUser;
  bool isLoading = true;

  @override
  void initState() {
    super.initState();
    loadData();
  }

  Future<void> loadData() async {
    final user = await authService.getCurrentUser();
    final allSchedulings = await schedulingService.getAllSchedulings();

    setState(() {
      currentUser = user;
      schedulings = allSchedulings
        .where((s) => s.user.id == user?.id)
        .toList();
      isLoading = false;
    });
  }

  Future<void> handleEnter(Scheduling scheduling) async {
    final accessLog = await accessLogService.createAccessLog(
      schedulingId: scheduling.id,
    );

    if (accessLog != null) {
      showDialog(
        context: context,
        builder: (context) => AlertDialog(
          title: const Text('Access Granted.'),
          content: Text(
            "You entered ${scheduling.space.name} at ${accessLog.entryTimeStamp.hour.toString().padLeft(2, '0')}:${accessLog.entryTimeStamp.minute.toString().padLeft(2, '0')}"),
          actions: [
            TextButton(
              onPressed: () => Navigator.pop(context),
              child: const Text('Ok'),
            ),
          ],
        ),
      );
    } else {
      showDialog(
        context: context,
        builder: (context) => AlertDialog(
          title: const Text('Access Denied.'),
          content: const Text('Could not log your entry. Please try again.'),
          actions: [
            TextButton(
              onPressed: () => Navigator.pop(context),
              child: const Text('Ok'),
            ),
          ],
        ),
      );
    }
  }

  @override 
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('My Bookings')),
      body: isLoading
        ? const Center(child: CircularProgressIndicator())
        : schedulings.isEmpty
          ? const Center(child: Text('No bookings yet'))
          : ListView.builder(
              itemCount: schedulings.length,
              itemBuilder: (context, index) {
                final scheduling = schedulings[index];
                return Card(
                  margin: const EdgeInsets.symmetric(
                    horizontal: 16, vertical: 8),
                  child: ListTile(
                    title: Text(scheduling.space.name),
                    subtitle: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text('Date: ${scheduling.scheduledDate.toString().split(' ')[0]}'),
                        Text('Time: ${scheduling.startTime} - ${scheduling.endTime}'),
                        Text('Status: ${scheduling.status}'),
                      ],
                    ),
                    isThreeLine: true,
                    trailing: ElevatedButton(
                      onPressed: () => handleEnter(scheduling),
                      child: const Text('Enter'),
                    ),
                  ),
                ); 
              },
          ),
    );
  }
}