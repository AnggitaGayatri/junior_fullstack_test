<?php

namespace App\Http\Controllers;

use App\Models\Teacher;
use Illuminate\Http\Request;

class TeacherController extends Controller
{
    // Tampilkan semua guru
    public function index()
    {
        $teachers = Teacher::all();
        return response()->json($teachers);
    }

    // Tambah guru baru
    public function store(Request $request)
    {
        $request->validate([
            'nip' => 'required|unique:teachers,nip',
            'full_name' => 'required',
            'subject_id' => 'required|exists:subjects,id',
            'phone_number' => 'required',
            'status' => 'required|in:aktif,nonaktif'
        ]);

        $teacher = Teacher::create($request->all());
        return response()->json($teacher, 201);
    }

    // Tampilkan satu guru
    public function show($id)
    {
        $teacher = Teacher::findOrFail($id);
        return response()->json($teacher);
    }

    // Update guru
    public function update(Request $request, $id)
    {
        $teacher = Teacher::findOrFail($id);

        $request->validate([
        'nip' => 'required|unique:teachers,nip,' . $id,
        'full_name' => 'required',
        'subject_id' => 'required|exists:subjects,id', 
        'phone_number' => 'required',
        'status' => 'required|in:aktif,nonaktif', 
        ]);

        $teacher->update($request->all());
        return response()->json($teacher);
    }

    // Hapus guru
    public function destroy($id)
    {
        $teacher = Teacher::findOrFail($id);
        $teacher->delete();
        return response()->json(['message' => 'Teacher deleted successfully']);
    }
}
