<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;
use App\Models\Student;

class StudentSeeder extends Seeder
{
    public function run(): void
    {
        Student::create([
            'student_number' => 'S001',
            'name' => 'Siswa 1',
            'class' => 'X IPA 1',
            'address' => 'Jl. Merdeka No.1',
        ]);

        Student::create([
            'student_number' => 'S002',
            'name' => 'Siswa 2',
            'class' => 'X IPA 1',
            'address' => 'Jl. Merdeka No.2',    
        ]);
    }
}
