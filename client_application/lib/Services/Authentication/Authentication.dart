import 'package:firebase_auth/firebase_auth.dart';

abstract class Authentication {

  FirebaseAuth auth;

  Authentication() {
    this.auth = FirebaseAuth.instance;
  }

  Future<User> signIn();
  Future<bool> signOut();

  Stream<User> get user {
    return this.auth.authStateChanges();
  }

}