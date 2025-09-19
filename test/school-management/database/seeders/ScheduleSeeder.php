<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;
use App\Models\Schedule;
use App\Models\Teacher;
use App\Models\Subject;

class ScheduleSeeder extends Seeder
{
    public function run(): void
    {
        $teacher1 = Teacher::first();
        $teacher2 = Teacher::skip(1)->first();

        $subject1 = Subject::first();
        $subject2 = Subject::skip(1)->first();

        Schedule::create([
            'teacher_id' => $teacher1->id,
            'subject_id' => $subject1->id,
            'day' => 'Senin',
            'time' => '08:00-10:00'
        ]);

        Schedule::create([
            'teacher_id' => $teacher2->id,
            'subject_id' => $subject2->id,
            'day' => 'Selasa',
            'time' => '10:00-12:00'
        ]);
    }
}
