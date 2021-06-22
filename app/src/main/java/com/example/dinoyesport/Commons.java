package com.example.dinoyesport;

public interface Commons {
    // Game fps information
    int MAX_FPS = 60;

    // map speed indication
    float MAP_BASIC_VELOCITY = 0.25f;

    // Jump information
    float DINO_MAX_JUMP_TIME= 2.0f * MAX_FPS;
    float DINO_JUMP_SPEED = 0.15f * MAX_FPS;
    float DINO_MAX_VERTICAL_VELOCITY = 6.0f;
}
