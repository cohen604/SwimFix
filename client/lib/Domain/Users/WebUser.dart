import 'package:client/Domain/Users/Swimmer.dart';
import 'package:client/Domain/Users/UserPermissions.dart';

class WebUser {

  Swimmer swimmer;
  UserPermissions permissions;

  WebUser(this.swimmer, this.permissions);
}