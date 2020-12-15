import 'package:client/Services/Authentication.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:google_sign_in/google_sign_in.dart';

class GoogleAuth extends Authentication {

  GoogleSignIn googleSignIn;
  GoogleAuth() {

    this.googleSignIn = GoogleSignIn();
  }

  @override
  Future<User> signIn() async{
    try {
      await Firebase.initializeApp();
      print('-2');
      final GoogleSignInAccount account = await this.googleSignIn.signIn();
      print('-1');
      final GoogleSignInAuthentication authentication = await account.authentication;
      print('0');
      final AuthCredential credential = GoogleAuthProvider.credential(
          accessToken: authentication.accessToken,
          idToken: authentication.idToken);
      print('1');
      final UserCredential userCredential = await this.auth.signInWithCredential(credential);
      print('2');
      final User user = userCredential.user;
      print(user);
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