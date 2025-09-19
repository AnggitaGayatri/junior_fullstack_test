<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;
use App\Models\Subject;

class SubjectSeeder extends Seeder
{
    public function run(): void
    {
        Subject::create(['name' => 'Matematika']);
        Subject::create(['name' => 'Bahasa Indonesia']);
        Subject::create(['name' => 'Fisika']);
    }
}
