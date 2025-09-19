<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\AuthController;
use App\Http\Controllers\StudentController;
use App\Http\Controllers\TeacherController;
use App\Http\Controllers\SubjectController;
use App\Http\Controllers\ScheduleController;

// Auth
Route::post('/register', [AuthController::class, 'register']);
Route::post('/login', [AuthController::class, 'login']);

Route::middleware('jwt.auth')->group(function () {

    // Route yang bisa diakses semua user yang login
    Route::get('/me', [AuthController::class, 'me']);
    Route::post('/logout', [AuthController::class, 'logout']);

    
    Route::middleware(\App\Http\Middleware\RoleMiddleware::class . ':admin')->group(function () {
        Route::apiResource('students', StudentController::class)->except(['index']);
        Route::apiResource('teachers', TeacherController::class);
        Route::apiResource('subjects', SubjectController::class)->except(['index','show']);
        Route::apiResource('schedules', ScheduleController::class)->except(['index','show']);
    });

   
    Route::get('/students', [StudentController::class, 'index']);
    Route::get('/students/{id}', [StudentController::class, 'show']);

   
    Route::get('/schedules', [ScheduleController::class, 'index']);
    Route::get('/schedules/{id}', [ScheduleController::class, 'show']);

    // GET jadwal khusus guru (misal jadwal mata pelajaran yang dia ajar)
    Route::middleware(\App\Http\Middleware\RoleMiddleware::class . ':guru')->get('my-schedule', [ScheduleController::class, 'mySchedule']);
});

