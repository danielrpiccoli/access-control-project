import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';
import 'package:frontend/models/app_user.dart';

class AuthService {
final String baseUrl = 'http://localhost:8080';

  Future<String?> login(String email, String password) async {
    final response = await http.post(
      Uri.parse('$baseUrl/auth/login'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({'email': email, 'password': password}),
    );

    if (response.statusCode == 200) {
      final token = response.body;
      final prefs = await SharedPreferences.getInstance();
      await prefs.setString('token', token);
      return token;
    }
    return null;
  }

  Future<bool> register(String name, String email, String password) async {
    final response = await http.post(
      Uri.parse('$baseUrl/auth/register'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({
          'name': name,
          'email': email,
          'password': password,
       }
      ),
    );
    return response.statusCode == 201;
  }

  Future<String?> getToken() async {
    final prefs = await SharedPreferences.getInstance();
    return prefs.getString('token');
  }
  Future<void> logout() async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.remove('token');
  }

  Future<AppUser?> getCurrentUser() async {
    final token = await getToken();
    final response = await http.get(
        Uri.parse('$baseUrl/auth/me'),
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer $token',
        },
    );
    if (response.statusCode == 200) {
        return AppUser.fromJson(jsonDecode(response.body));
    }
    return null;
  }
}
