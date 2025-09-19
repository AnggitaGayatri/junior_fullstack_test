<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;
use App\Models\Teacher;

class TeacherSeeder extends Seeder
{
    public function run(): void
    {
        // Pastikan subject_id 1,2,... sudah ada di tabel subjects
        Teacher::create([
            'nip' => 'T001',
            'full_name' => 'Guru A',
            'subject_id' => 1,
            'phone_number' => '081234567890',
            'status' => 'aktif',
        ]);

        Teacher::create([
            'nip' => 'T002',
            'full_name' => 'Guru B',
            'subject_id' => 2,
            'phone_number' => '081234567891',
            'status' => 'aktif',
        ]);

        Teacher::create([
            'nip' => 'T003',
            'full_name' => 'Guru C',
            'subject_id' => 3,
            'phone_number' => '081234567892',
            'status' => 'aktif',
        ]);

        Teacher::create([
            'nip' => 'T004',
            'full_name' => 'Guru D',
            'subject_id' => 4,
            'phone_number' => '081234567893',
            'status' => 'aktif',
        ]);
    }
}
