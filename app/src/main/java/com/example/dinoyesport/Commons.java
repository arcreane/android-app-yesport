package com.example.dinoyesport;

public interface Commons {
    // Game fps information
    int MAX_FPS = 60;

    // map speed indication
    float MAP_BASIC_VELOCITY = 0.25f;

    // Jump information
    float DINO_MAX_JUMP_TIME= 2.0f * MAX_FPS;
    float DINO_JUMP_SPEED = 0.12f * MAX_FPS;
    float DINO_MAX_VERTICAL_VELOCITY = 5.0f;

    int GAME_SPEED = 15;
    int MAX_GAME_SPEED = 25;
}
