import 'package:firebase_auth/firebase_auth.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'Authentication.dart';

class GoogleAuth extends Authentication {

  GoogleSignIn googleSignIn;
  GoogleAuth() {

    this.googleSignIn = GoogleSignIn();
  }

  @override
  Future<User> signIn() async{
    try {
      await Firebase.initializeApp();
      //print(1);
      final GoogleSignInAccount account = await this.googleSignIn.signIn();
      //print(2);
      final GoogleSignInAuthentication authentication = await account.authentication;
      //print(3);
      final AuthCredential credential = GoogleAuthProvider.credential(
          accessToken: authentication.accessToken,
          idToken: authentication.idToken);
      //print(4);
      final UserCredential userCredential = await this.auth.signInWithCredential(credential);
      //print(5);
      final User user = userCredential.user;
      //print(user);
      return user;
    }
    catch (e) {
      print('error');
      print(e.toString());
      print('end error');
      return null;
    }
  }

  @override
  void signOut() async {
    this.googleSignIn.signOut();
  }

}