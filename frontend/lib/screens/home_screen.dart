import 'package:flutter/material.dart';
import 'package:frontend/models/space.dart';
import 'package:frontend/services/auth_service.dart';
import 'package:frontend/services/space_service.dart';

class HomeScreen extends StatefulWidget{
  const HomeScreen({super.key});

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  final SpaceService spaceService = SpaceService();
  final AuthService authService = AuthService();
  List<Space> spaces = [];
  bool isLoading = true;

  @override
  void initState() {
    super.initState();
    loadSpaces();
  }

  Future<void> loadSpaces() async {
    final result = await spaceService.getAllSpaces();
    setState(() {
      spaces = result;
      isLoading = false;
    });
  }

  Future<void> handleLogout() async {
    await authService.logout();
    Navigator.pushReplacementNamed(context, '/login');
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Available Spaces'),
        actions: [
          IconButton(
            icon: const Icon(Icons.logout),
            onPressed: handleLogout,
          ),
        ],
      ),
      body: isLoading
        ? const Center(child: CircularProgressIndicator())
        : spaces.isEmpty
          ? const Center(child: Text('No spaces available'))
          : ListView.builder(
              itemCount: spaces.length,
              itemBuilder: (context, index) {
                final space = spaces[index];
                return Card(
                  margin: const EdgeInsets.symmetric(
                    horizontal: 16, vertical: 8),
                  child: ListTile(
                    title: Text(space.name),
                    subtitle: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text('Capacity: ${space.capacity}'),
                        Text('Status: ${space.status}'),
                        Text(space.description),
                      ],
                    ),
                    isThreeLine: true,
                    trailing: ElevatedButton(
                      onPressed: () {
                        Navigator.pushNamed(
                          context,
                          '/booking',
                          arguments: space,
                        );
                      },
                      child: const Text('Book'),
                    ),
                  ),
                );
              },
          ),
    );
  }
}
