currentPath=$(pwd)
echo "Task Root Path: $taskRootPath"
echo "Current Path: $currentPath"
echo "Android Path: $ANDROID_HOME"
echo "Copy Finished"

export ANDROID_AVD_HOME="$ANDROID_HOME/avd"

echo "ANDROID_AVD_HOME $ANDROID_AVD_HOME"
mkdir "$ANDROID_AVD_HOME"

export PATH=$PATH:$ANDROID_HOME/emulator
export PATH=$PATH:$ANDROID_HOME/cmdline-tools
export PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin
export PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/lib
export PATH=$PATH:$ANDROID_HOME/tools
export PATH=$PATH:$ANDROID_HOME/platform-tools
echo "PATH: $PATH"

cd ../internal_test_app
flutter build apk --no-obfuscate -v

export APP_PATH=$PWD/build/app/outputs/flutter-apk/app-release.apk
export PLATFORM=android

export FORCE_API=35
export FORCE_IMAGE_DIR=google_apis_playstore

cd ..
. ../scripts/android/create_android_emulator.sh
. ../scripts/android/start_android_emulator.sh yes

cd test
npm install
./gradlew test
