import 'package:firebase_auth/firebase_auth.dart';

abstract class Authentication {

  FirebaseAuth auth;

  Authentication() {
    this.auth = FirebaseAuth.instance;
  }

  Future<User> signIn();
  void signOut();

  Stream<User> get user {
    return this.auth.authStateChanges();
  }

}