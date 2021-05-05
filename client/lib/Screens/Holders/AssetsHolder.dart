class AssetsHolder {

  static AssetsHolder _assetsHolder;

  static AssetsHolder getInstance() {
    if(_assetsHolder == null) {
      _assetsHolder = new AssetsHolder();
    }
    return _assetsHolder;
  }

  String getIntroVideo() {
    return 'assets/videos/intro.mp4';
  }

  String getGoogleIcon() {
    return "assets/google_logo.png";
  }

  String getSwimmerBackGround() {
    return 'assets/images/about_screen_background.png';
  }

  String getSwimmerImage() {
    return 'assets/images/swimmer_image.png';
  }

  String getAdminImage() {
    return 'assets/images/admin_image.png';
  }

  String getCoachImage() {
    return 'assets/images/coach_image.png';
  }

  String getResearcherImage() {
    return '/assets/images/researcher_image.png';
  }

  String getDownloadAppArmeabi() {
    return '/assets/assets/releases/app-armeabi-v7a-release.apk';
  }

  String getDownloadAppArm64() {
    return '/assets/assets/releases/app-arm64-v8a-release.apk';
  }

  String getDownloadAppX86() {
    return '/assets/assets/releases/app-x86_64-release.apk';
  }

}