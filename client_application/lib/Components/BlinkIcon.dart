import 'package:flutter/cupertino.dart';

class BlinkIcon extends StatefulWidget {

  IconData icon;
  Color color;

  BlinkIcon(this.icon, this.color);

  @override
  _BlinkIconState createState() => _BlinkIconState();
}

class _BlinkIconState extends State<BlinkIcon>
    with SingleTickerProviderStateMixin {

  AnimationController _controller;

  @override
  void initState() {
    super.initState();
    _controller = new AnimationController(vsync: this, duration: Duration(seconds: 1));
    _controller.repeat(reverse: true);
  }

  @override
  void dispose() {
    _controller?.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return FadeTransition(
      opacity: _controller,
      child: Icon(
          this.widget.icon,
          color: this.widget.color,
          size: 35,
        ),
    );
  }
}
