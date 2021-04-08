import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import 'Avatar.dart';
import 'AvatarTitle.dart';

class AvatarChild extends StatelessWidget {

  String title;
  Function isSelected;
  String description;
  Color background;
  Color backgroundSelected;
  Widget child;

  AvatarChild(this.title, this.isSelected, this.description, this.background,
      this.backgroundSelected, {this.child});

  @override
  Widget build(BuildContext context) {
    return Container(
      child: Column(
        children: [
          Align(
            alignment: Alignment.topLeft,
            child: Row(
              children: [
                AvatarTitle(
                    title,
                    isSelected,
                    description,
                    null,
                    background,
                    backgroundSelected,
                ),
              ],
            ),
          ),
          child,
        ],
      ),
    );
  }
}
