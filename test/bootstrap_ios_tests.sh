cd ../internal_test_app
flutter build ios --no-codesign --simulator
export APP_PATH=$PWD/../internal_test_app/build/ios/iphonesimulator/Runner.app
export PLATFORM=ios
for id in 1 2
do
    xcrun simctl create "Flutter Test Device $id" com.apple.CoreSimulator.SimDeviceType.iPhone-15
done
cd ../test
npm install
./gradlew test
