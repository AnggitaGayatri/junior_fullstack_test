<?php

namespace App\Http\Controllers;

use App\Models\Student;
use Illuminate\Http\Request;

class StudentController extends Controller
{
    // GET /api/students
    public function index()
    {
        return response()->json(Student::all());
    }

    // POST /api/students
    public function store(Request $request)
    {
        $request->validate([
            'student_number' => 'required|unique:students',
            'name' => 'required|string',
            'class' => 'required|string',
            'address' => 'nullable|string',
        ]);

        $student = Student::create($request->all());

        return response()->json([
            'message' => 'Student added successfully',
            'data' => $student
        ], 201);
    }

    // GET /api/students/{id}
    public function show($id)
    {
        $student = Student::find($id);
        if (!$student) {
            return response()->json(['message' => 'Student not found'], 404);
        }

        return response()->json($student);
    }

    // PUT /api/students/{id}
    public function update(Request $request, $id)
    {
        $student = Student::find($id);
        if (!$student) {
            return response()->json(['message' => 'Student not found'], 404);
        }

        $request->validate([
            'student_number' => 'sometimes|required|unique:students,student_number,' . $id,
            'name' => 'sometimes|required|string',
            'class' => 'sometimes|required|string',
            'address' => 'nullable|string',
        ]);

        $student->update($request->all());

        return response()->json([
            'message' => 'Student updated successfully',
            'data' => $student
        ]);
    }

    // DELETE /api/students/{id}
    public function destroy($id)
    {
        $student = Student::find($id);
        if (!$student) {
            return response()->json(['message' => 'Student not found'], 404);
        }

        $student->delete();

        return response()->json(['message' => 'Student deleted successfully']);
    }
}
