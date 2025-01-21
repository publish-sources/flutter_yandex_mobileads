ARG=$1
API=$(<../../scripts/.current_android_api)
DEVICES_COUNT=2

current_directory=$PWD

prepare_android_env () {
    export FORCE_API=$API
    . android/prepare_android_env.sh --run-test-local True --copy-from-resource False
}

if [[ $ARG == "recreate-android-emulators" || $ARG == "bootstrap-android" ]]; then
    cd ../../scripts
    prepare_android_env
    . android/emulator/create_emulator_from_params.sh "$API" "$DEVICES_COUNT" no
    cd $current_directory
fi

if [[ $ARG == "start-android-emulators" || $ARG == "bootstrap-android" ]]; then
    cd ../../scripts
    prepare_android_env
    for id in $(seq 1 $DEVICES_COUNT)
    do
        . android/emulator/exec_emulator.sh "google-$API-$id" yes no
    done
    . android/emulator/await_all_devices_boot.sh "$DEVICES_COUNT"
    cd $current_directory
fi

if [[ $ARG == "build-apk" || $ARG == "bootstrap-android" ]]; then
    cd ../internal_test_app
    flutter build apk --no-obfuscate -v
    cd ../test
fi

if [[ $ARG == "run-all-tests-android" || $ARG == "bootstrap-android" ]]; then
    export APP_PATH=$PWD/../internal_test_app/build/app/outputs/flutter-apk/app-release.apk
    export PLATFORM=android
    npm install
    ./gradlew test
fi

if [[ $ARG == "recreate-simulators" || $ARG == "bootstrap-ios" ]]; then
    for id in 1 2
    do
        xcrun simctl create "Test Device $id" com.apple.CoreSimulator.SimDeviceType.iPhone-15
    done
fi

if [[ $ARG == "build-ios" || $ARG == "bootstrap-ios" ]]; then
    cd ../internal_test_app
    flutter build ios --no-codesign --simulator
    cd ../test
fi

if [[ $ARG == "run-all-tests-ios" || $ARG == "bootstrap-ios" ]]; then
    source ../../scripts/build_web_driver_agent.sh
    export APP_PATH=$PWD/../internal_test_app/build/ios/iphonesimulator/Runner.app
    export PLATFORM=ios
    npm install
    ./gradlew test
fi
