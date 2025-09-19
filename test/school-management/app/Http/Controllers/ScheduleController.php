<?php

namespace App\Http\Controllers;

use App\Models\Schedule;
use Illuminate\Http\Request;

class ScheduleController extends Controller
{
    // Tampilkan semua jadwal (Admin) / jadwal guru
    public function index(Request $request)
    {
        $user = $request->user();
        
        if($user->role === 'admin'){
            $schedules = Schedule::with(['teacher', 'student', 'subject'])->get();
        } else {
            // Guru hanya bisa lihat jadwalnya sendiri
            $schedules = Schedule::with(['teacher', 'student', 'subject'])
                                 ->where('teacher_id', $user->teacher->id)
                                 ->get();
        }

        return response()->json($schedules);
    }

    // Tampilkan satu jadwal
    public function show($id)
    {
        $schedule = Schedule::with(['teacher', 'student', 'subject'])->findOrFail($id);
        return response()->json($schedule);
    }

    // Tambah jadwal baru (Admin)
    public function store(Request $request)
    {
        $request->validate([
            'teacher_id' => 'required|exists:teachers,id',
            'student_id' => 'required|exists:students,id',
            'subject_id' => 'required|exists:subjects,id',
            'day' => 'required|string',
            'time' => 'required',
        ]);

        $schedule = Schedule::create($request->all());
        return response()->json($schedule, 201);
    }

    // Update jadwal (Admin)
    public function update(Request $request, $id)
    {
        $schedule = Schedule::findOrFail($id);

        $request->validate([
            'teacher_id' => 'required|exists:teachers,id',
            'student_id' => 'required|exists:students,id',
            'subject_id' => 'required|exists:subjects,id',
            'day' => 'required|string',
            'time' => 'required',
        ]);

        $schedule->update($request->all());
        return response()->json($schedule);
    }

    // Hapus jadwal (Admin)
    public function destroy($id)
    {
        $schedule = Schedule::findOrFail($id);
        $schedule->delete();
        return response()->json(['message' => 'Schedule deleted successfully']);
    }
}
