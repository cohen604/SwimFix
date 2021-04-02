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
      final GoogleSignInAccount account = await this.googleSignIn.signIn();
      final GoogleSignInAuthentication authentication = await account.authentication;
      final AuthCredential credential = GoogleAuthProvider.credential(
          accessToken: authentication.accessToken,
          idToken: authentication.idToken);
      final UserCredential userCredential = await this.auth.signInWithCredential(credential);
      final User user = userCredential.user;
      //print(user);
      return user;
    }
    catch (e) {
      print(e.toString());
      return null;
    }
  }

  @override
  void signOut() async {
    this.googleSignIn.signOut();
  }

}