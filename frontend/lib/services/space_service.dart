import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:frontend/models/space.dart';
import 'package:frontend/services/auth_service.dart';

class SpaceService {
  final String baseUrl = 'http://localhost:8080';
  final AuthService authService = AuthService();

  Future<List<Space>> getAllSpaces() async {
    final token = await authService.getToken();
    final response = await http.get(
      Uri.parse('$baseUrl/spaces'),
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer $token',
      },
    );

    if (response.statusCode == 200) {
      final List<dynamic> jsonList = jsonDecode(response.body);
      return jsonList.map((json)=> Space.fromJson(json)).toList();
    }
    return[];
  }

  Future<Space?> getSpaceById(int id) async {
    final token = await authService.getToken();
    final response = await http.get(
      Uri.parse('$baseUrl/spaces/$id'),
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer $token',
      },
    );

    if (response.statusCode == 200) {
      return Space.fromJson(jsonDecode(response.body));
    }
    return null;
  }
}
