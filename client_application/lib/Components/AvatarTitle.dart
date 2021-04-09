import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import 'Avatar.dart';

class AvatarTitle extends StatelessWidget {

  String title;
  Function isSelected;
  String description;
  Function onClick;
  Color background;
  Color backgroundSelected;

  AvatarTitle(this.title, this.isSelected, this.description, this.onClick,
      this.background, this.backgroundSelected);

  @override
  Widget build(BuildContext context) {
    return Container(
      child: Align(
        alignment: Alignment.topLeft,
        child: Row(
          children: [
            Avatar(
              title,
              isSelected,
              background,
              backgroundSelected,
              onClick: onClick,
            ),
            Text(description,
              style: TextStyle(
                fontSize: 21 * MediaQuery
                    .of(context)
                    .textScaleFactor,
                color: Colors.black,
                fontWeight: FontWeight.normal,
                decoration: TextDecoration.none,
              ),
            ),
          ],
        ),
      ),
    );
  }
}
