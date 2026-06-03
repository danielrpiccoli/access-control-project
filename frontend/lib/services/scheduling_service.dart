import 'dart:convert';
import 'package:frontend/models/scheduling.dart';
import 'package:http/http.dart' as http;
import 'package:frontend/services/auth_service.dart';

class SchedulingService {
  final String baseUrl = 'http://localhost:8080';
  final AuthService authService = AuthService();

  Future<List<Scheduling>> getAllSchedulings() async {
    final token = await authService.getToken();
    final response = await http.get(
      Uri.parse('$baseUrl/schedulings'),
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer $token',
      },
    );

    if (response.statusCode == 200) {
      final List<dynamic> jsonList = jsonDecode(response.body);
      return jsonList.map((json) => Scheduling.fromJson(json)).toList();
    }
    return[];
  }

  Future<Scheduling?> getSchedulingById(int id) async {
    final token = await authService.getToken();
    final response = await http.get(
      Uri.parse('$baseUrl/schedulings/$id'),
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer $token',
      },
    );

    if (response.statusCode == 200) {
      return Scheduling.fromJson(jsonDecode(response.body));
    }
    return null;
  }

  Future<Scheduling?> createScheduling({
    required int userId,
    required int spaceId,
    required String scheduledDate,
    required String startTime,
    required String endTime,
  }) async {
    final token = await authService.getToken();

    final response = await http.post(
      Uri.parse('$baseUrl/schedulings'),
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer $token',
      },
      body: jsonEncode({
        'userId': userId,
        'spaceId': spaceId,
        'scheduledDate': scheduledDate,
        'startTime': startTime,
        'endTime': endTime,
      }),
    );

    if (response.statusCode == 201) {
      return Scheduling.fromJson(jsonDecode(response.body));
    }
    return null;
  }
}