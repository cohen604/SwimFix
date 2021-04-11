import 'package:client_application/Domain/Pair.dart';
import 'package:client_application/Domain/Users/AppUser.dart';

class PoolsScreenArguments {

  AppUser appUser;
  List<Pair<int, int>> poolTimes;

  PoolsScreenArguments(this.appUser, this.poolTimes);
  
}