cmake_minimum_required(VERSION 3.18.1)

project("hermes422repro")

set(PROJECT_ROOT "${CMAKE_SOURCE_DIR}/../../../../..")
set(REACT_NATIVE_ROOT "${PROJECT_ROOT}/node_modules/react-native")
set(REACT_NATIVE_AAR_DIR "${CMAKE_CURRENT_BINARY_DIR}/react-native-aar")

set(JSI_HEADER_DIR "${REACT_NATIVE_ROOT}/ReactCommon/jsi")

# Extract libjsi.so from the React Native AAR
file(GLOB_RECURSE REACT_NATIVE_AAR ${REACT_NATIVE_ROOT}/android/*/react-native-*.aar)
file(ARCHIVE_EXTRACT INPUT ${REACT_NATIVE_AAR} DESTINATION ${REACT_NATIVE_AAR_DIR})
set(JSI_SO "${REACT_NATIVE_AAR_DIR}/jni/${ANDROID_ABI}/libjsi.so")

add_library(hermes422repro SHARED hermes422repro.cpp)

find_library(log-lib log)

message(STATUS $JSI_HEADER_DIR)
target_include_directories(hermes422repro PRIVATE
    ${JSI_HEADER_DIR}
)
target_link_libraries(hermes422repro
    ${log-lib}
    ${JSI_SO}
)
