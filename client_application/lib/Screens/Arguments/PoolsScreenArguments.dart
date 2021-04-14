import 'package:client_application/Domain/Pair.dart';
import 'package:client_application/Domain/Users/AppUser.dart';

class PoolsScreenArguments {

  AppUser appUser;
  String videoPath;
  List<Pair<int, int>> poolTimes;

  PoolsScreenArguments(this.appUser, this.videoPath, this.poolTimes);
  
}