//createAccessLog(int schedulingId)
//getAccessLogById(int id)
import 'dart:convert';
import 'package:frontend/models/access_log.dart';
import 'package:http/http.dart' as http;
import 'package:frontend/services/auth_service.dart';

class AccessLogService {
  final String baseUrl = 'http://localhost:8080';
  final AuthService authService = AuthService();

  Future<AccessLog?> getAccessLogById(int id) async {
    final token = await authService.getToken();
    final response = await http.get(
      Uri.parse('$baseUrl/accessLogs/$id'),
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer $token',
      },
    );
    if (response.statusCode == 200) {
      return AccessLog.fromJson(jsonDecode(response.body));
    }
    return null;
  }

  Future<AccessLog?> createAccessLog({
    required int schedulingId,
    required int userId,
    required int spaceId,
    required String entryTimeStamp,
  }) async {
    final token = await authService.getToken();

    final response = await http.post(
      Uri.parse('$baseUrl/accessLogs'),
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer $token',
      },
      body: jsonEncode({
        'userId': userId,
        'spaceId': spaceId,
        'schedulingId': schedulingId,
        'entryTimeStamp': entryTimeStamp,
      }),
    );
    if (response.statusCode == 201) {
      return AccessLog.fromJson(jsonDecode(response.body));
    }
    return null;
  }
}