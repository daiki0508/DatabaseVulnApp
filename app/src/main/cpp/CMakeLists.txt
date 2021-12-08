cmake_minimum_required(VERSION 3.4.1)

add_library(
        # 識別用ライブラリ名を指定
        shared_preference

        # 共有ライブラリとしてビルドさせる
        SHARED

        # C/C++ソースへの相対パス指定
        shared_preference.cpp
)
target_link_libraries(
        shared_preference
        android
        log
)

add_library(
        # 識別用ライブラリ名を指定
        database

        # 共有ライブラリとしてビルドさせる
        SHARED

        # C/C++ソースへの相対パス指定
        database.cpp
)
target_link_libraries(
        database
        android
        log
)

add_library(
        # 識別用ライブラリ名を指定
        Log

        # 共有ライブラリとしてビルドさせる
        SHARED

        # C/C++ソースへの相対パス指定
        log.cpp
)
target_link_libraries(
        Log
        android
        log
)