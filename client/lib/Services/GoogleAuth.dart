import 'package:client/Services/Authentication.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:google_sign_in/google_sign_in.dart';

class GoogleAuth extends Authentication {

  GoogleSignIn googleSignIn;
  GoogleSignInAccount account;

  GoogleAuth() {
    this.googleSignIn = GoogleSignIn();
    this.account = null;
  }

  @override
  Future<User> signIn() async{
    try {
      // await Firebase.initializeApp();
      this.account = await this.googleSignIn.signIn();
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
  Future<bool> signOut() async {
    await this.account.clearAuthCache();
    GoogleSignInAccount account = await this.googleSignIn.signOut();
    return account != null;
  }

}