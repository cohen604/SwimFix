package Domain.StatisticsData;

public interface IStatistic {

    double getHeadRecognitionPercent();

    int getHeadExpected();

    int getHeadActual();

    double getRightShoulderRecognitionPercent();

    int getRightShoulderExpected();

    int getRightShoulderActual();

    double getRightElbowRecognitionPercent();

    int getRightElbowExpected();

    int getRightElbowActual();

    double getRightWristRecognitionPercent();

    int getRightWristExpected();

    int getRightWristActual();

    double getLeftShoulderRecognitionPercent();

    int getLeftShoulderExpected();

    int getLeftShoulderActual();

    double getLeftElbowRecognitionPercent();

    int getLeftElbowExpected();

    int getLeftElbowActual();

    double getLeftWristRecognitionPercent();

    int getLeftWristExpected();

    int getLeftWristActual();

}
