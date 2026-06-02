class Space {
  final int id;
  final String name;
  final int capacity;
  final String description;
  final String status;

  Space({
    required this.id,
    required this.name,
    required this.capacity,
    required this.description,
    required this.status,
  });

  factory Space.fromJson(Map<String, dynamic> json) {
    return Space(
      id: json['id'],
      name: json['name'],
      capacity: json['capacity'],
      description: json['description'],
      status: json['status'],
    );
  }
}