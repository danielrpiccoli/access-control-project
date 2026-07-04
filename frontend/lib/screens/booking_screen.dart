import 'package:flutter/material.dart';
import 'package:frontend/models/space.dart';
import 'package:frontend/services/auth_service.dart';
import 'package:frontend/services/scheduling_service.dart';

class BookingScreen extends StatefulWidget {
  const BookingScreen({super.key});

  @override
  State<BookingScreen>  createState() => _BookingScreenState();
}

class _BookingScreenState extends State<BookingScreen> {
  final SchedulingService schedulingService = SchedulingService();
  final AuthService authService = AuthService();

  DateTime? selectedDate;
  TimeOfDay? selectedStartTime;
  TimeOfDay? selectedEndTime;
  bool isLoading = false;
  String? errorMessage;

  String formatDate(DateTime date) {
    return "${date.year}-${date.month.toString().padLeft(2, '0')}-${date.day.toString().padLeft(2, '0')}";
  }

  String formatTime(TimeOfDay time) {
    return "${time.hour.toString().padLeft(2, '0')}:${time.minute.toString().padLeft(2, '0')}:00";  
  }

  Future<void> pickDate() async {
    final date = await showDatePicker(
      context: context,
      initialDate: DateTime.now(),
      firstDate: DateTime.now(),
      lastDate: DateTime.now().add(const Duration(days: 30)),
    );
    if (date != null) {
      setState(() => selectedDate = date);
    }
  }

  Future<void> pickStartTime() async {
    final time = await showTimePicker(context: context, initialTime: TimeOfDay.now());
    if (time != null) {
      setState(() => selectedStartTime = time);
    }
  }

  Future<void> pickEndTime() async {
    final time = await showTimePicker(context: context, initialTime: TimeOfDay.now());
    if (time != null) {
      setState(() => selectedEndTime = time);
    }
  }

  Future<void> handleBooking(Space space) async {
    if (selectedDate == null || selectedStartTime == null || selectedEndTime == null) {
      setState(() => errorMessage = 'Please select date and times');
      return;
    }

    setState(() {
      isLoading = true;
      errorMessage = null;
    });

    final token = await authService.getToken();
    if (token == null) {
      setState(() => isLoading = false);
      Navigator.pushReplacementNamed(context, '/login');
      return;
    }

    final currentUser = await authService.getCurrentUser();
    if (currentUser == null) {
      setState(() => isLoading = false);
      Navigator.pushReplacementNamed(context, '/login');
      return;
    }

    final scheduling = await schedulingService.createScheduling(
      userId: currentUser.id,
      spaceId: space.id,
      scheduledDate: formatDate(selectedDate!),
      startTime: formatTime(selectedStartTime!),
      endTime: formatTime(selectedEndTime!),
    ); 

    setState(() => isLoading = false);

    if (scheduling != null) {
      showDialog(
        context: context,
        builder: (context) => AlertDialog(
          title: const Text('Booking Confirmed'),
          content: const Text('Your space has been booked successfully'),
          actions: [
            TextButton(
              onPressed: () {
                Navigator.pop(context);
                Navigator.pushReplacementNamed(context, '/home');
              },
              child: const Text('Ok'),
            ),
          ],
        ),
      );
    }
    else {
      setState(() => errorMessage = 'Booking failed. Please try again');
    }
  }

  @override
  Widget build(BuildContext context) {
    final Space space = ModalRoute.of(context)!.settings.arguments as Space;

    return Scaffold(
      appBar: AppBar(title: Text('Book ${space.name}')),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            Card(
              child: Padding(
                padding: const EdgeInsets.all(16.0),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(space.name,
                    style: const TextStyle(
                      fontSize: 20, fontWeight: FontWeight.bold)),
                    const SizedBox(height: 8),
                    Text('Capacity: ${space.capacity}'),
                    Text('Status: ${space.status}'),
                    Text(space.description),
                  ],
                ),
              ),
            ),
            const SizedBox(height: 24),
            ListTile(
              title: Text(selectedDate == null
                ? 'Select Date'
                : formatDate(selectedDate!)),
              leading: const Icon(Icons.calendar_today),
              onTap: pickDate,
            ),
            ListTile(
              title: Text(selectedStartTime == null
                ? 'Select Start Time'
                : formatTime(selectedStartTime!)),
              leading: const Icon(Icons.access_time),
              onTap: pickStartTime,
            ),
            ListTile(
              title: Text(selectedEndTime == null
                ? 'Select End Time'
                : formatTime(selectedEndTime!)),
              leading: const Icon(Icons.access_time_filled),
              onTap: pickEndTime,
            ),
            const SizedBox(height: 24),
            if (errorMessage != null)
              Text(errorMessage!,
                style: const TextStyle(color: Colors.red)),
            const SizedBox(height: 8),
            isLoading
              ? const Center(child: CircularProgressIndicator())
              : ElevatedButton(
                onPressed: () => handleBooking(space),
                child: const Text('Book Space'),
              ),
          ],
        ),
      ),
    );
  }
}